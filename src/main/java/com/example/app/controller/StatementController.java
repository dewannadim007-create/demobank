package com.example.app.controller;

import com.example.app.model.Transaction;
import com.example.app.model.User;
import com.example.app.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * StatementController - Handles both Statement (Limits) and History pages
 */
@Controller
public class StatementController {

    @Autowired
    private TransactionService transactionService;

    // ==========================================
    // 1. STATEMENT PAGE (Limits & Stats)
    // ==========================================
    @GetMapping("/statement")
    public String showStatementPage(HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            return "redirect:/login";
        }

        // Daily wallet expense
        String[] dailyWalletStatement = transactionService.getDailyWalletExpense(loggedUser);
        model.addAttribute("dailyWalletExpense", dailyWalletStatement[0]);
        model.addAttribute("dailyWalletTr", dailyWalletStatement[1]);

        // Daily account expense
        String[] dailyAccountStatement = transactionService.getDailyAccountExpense(loggedUser);
        model.addAttribute("dailyAccountExpense", dailyAccountStatement[0]);
        model.addAttribute("dailyAccountTr", dailyAccountStatement[1]);

        // Monthly wallet expense (current month)
        String currentMonth = LocalDate.now().getMonth().toString();
        String[] monthlyWalletStatement = transactionService.getMonthlyWalletExpense(loggedUser, currentMonth);
        model.addAttribute("monthlyWalletExpense", monthlyWalletStatement[0]);
        model.addAttribute("monthlyWalletTr", monthlyWalletStatement[1]);

        // Monthly account expense (current month)
        String[] monthlyAccountStatement = transactionService.getMonthlyAccountExpense(loggedUser, currentMonth);
        model.addAttribute("monthlyAccountExpense", monthlyAccountStatement[0]);
        model.addAttribute("monthlyAccountTr", monthlyAccountStatement[1]);

        model.addAttribute("user", loggedUser);

        return "statement";
    }

    // ==========================================
    // 2. HISTORY PAGE (List & Filters)
    // ==========================================
    @GetMapping("/history")
    public String showHistoryPage(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "days", required = false) String days,
            HttpSession session,
            Model model) {

        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            return "redirect:/login";
        }

        List<Transaction> allTransactions = transactionService.getTransactionList(loggedUser.getAccount());

        List<Transaction> filteredTransactions = allTransactions.stream()
                .filter(t -> {
                    // Search Filter (Ref, Type, or other party)
                    if (search != null && !search.trim().isEmpty()) {
                        String lowerSearch = search.toLowerCase();
                        String otherParty = t.getSender().equals(loggedUser.getAccount()) ? t.getReceiver()
                                : t.getSender();

                        boolean matchRef = t.getRef() != null && t.getRef().toLowerCase().contains(lowerSearch);
                        boolean matchType = t.getType().toLowerCase().contains(lowerSearch);
                        boolean matchParty = otherParty.toLowerCase().contains(lowerSearch);

                        if (!matchRef && !matchType && !matchParty) {
                            return false;
                        }
                    }

                    // Type Filter
                    if (type != null && !type.isEmpty() && !type.equalsIgnoreCase("All")) {
                        if (!t.getType().equalsIgnoreCase(type)) {
                            return false;
                        }
                    }

                    // Time Filter
                    if (days != null && !days.isEmpty() && !days.equalsIgnoreCase("All")) {
                        try {
                            long d = Long.parseLong(days);
                            LocalDateTime cutoff = LocalDateTime.now().minusDays(d);
                            if (t.getTransactionDate() != null && t.getTransactionDate().isBefore(cutoff)) {
                                return false;
                            }
                        } catch (NumberFormatException e) {
                            // Ignore invalid numbers
                        }
                    }

                    return true;
                })
                .collect(Collectors.toList());

        model.addAttribute("transactions", filteredTransactions);
        model.addAttribute("searchTerm", search);
        model.addAttribute("selectedType", type);
        model.addAttribute("selectedDays", days);
        model.addAttribute("user", loggedUser);

        return "history";
    }

}
