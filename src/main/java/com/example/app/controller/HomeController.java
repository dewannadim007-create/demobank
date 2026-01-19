package com.example.app.controller;

import com.example.app.model.User;
import com.example.app.model.Transaction;
import com.example.app.services.TransactionService;
import com.example.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.time.LocalTime;

/**
 * HomeController - User home/dashboard
 * Converted from JavaFX to Spring Boot
 * All original business logic preserved
 */
@Controller
public class HomeController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TransactionService transactionService;

    /**
     * Show home page with greetings and user info
     * Original: initialize() method - ALL LOGIC PRESERVED
     */
    @GetMapping("/home")
    public String showHomePage(HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            return "redirect:/login";
        }

        // ORIGINAL LOGIC - Get user info
        String[] info = UserService.userInfo(loggedUser.getMobile(), mongoTemplate);

        if (info != null) {
            model.addAttribute("name", info[0].toUpperCase());
        }

        // ORIGINAL GREETINGS LOGIC - EXACT SAME
        int hour = LocalTime.now().getHour();
        String greeting;
        if (hour >= 5 && hour < 12) {
            greeting = "Good Morning!";
        } else if (hour >= 12 && hour < 19) {
            greeting = "Good Afternoon!";
        } else if (hour >= 19 && hour < 20) {
            greeting = "Good Evening!";
        } else {
            greeting = "Good Night!";
        }

        model.addAttribute("greetings", greeting);
        model.addAttribute("user", loggedUser);

        // Fetch Recent Transactions
        List<Transaction> allTransactions = transactionService.getTransactionList(loggedUser.getAccount(),
                loggedUser.getMobile());
        // Sort by date descending (assuming list might not be sorted or relying on
        // insertion order)
        // Since Transaction has no Comparable, and getTransactionList implementation in
        // service
        // just returns mongoTemplate.find, we should probably sort it here or query
        // with sort.
        // For simplicity: reverse the list if it comes in insertion order (oldest first
        // commonly)
        // actually let's assume insertion order for now or try to sort if possible.
        // Collections.reverse(allTransactions); // REMOVED: Service already returns
        // DESC (Newest First)

        List<Transaction> recentTransactions = allTransactions.stream()
                .limit(5)
                .collect(Collectors.toList());

        model.addAttribute("recentTransactions", recentTransactions);

        return "home";
    }

    /**
     * Check wallet balance
     * Original: checkWallet() method
     */
    @GetMapping("/api/home/check-wallet")
    public String checkWallet(HttpSession session, RedirectAttributes redirectAttributes) {
        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            return "redirect:/login";
        }

        // ORIGINAL LOGIC
        double checkBalance = UserService.getBalanceOnline(loggedUser.getMobile(), mongoTemplate);
        redirectAttributes.addFlashAttribute("balance", checkBalance);

        return "redirect:/home";
    }

    /**
     * Redirect to menu
     * Original: changeToMenu() method
     */
    @GetMapping("/home/to-menu")
    public String changeToMenu() {
        return "redirect:/menu";
    }

    /**
     * Redirect to utility
     * Original: changeToUtility() method
     */
    @GetMapping("/home/to-utility")
    public String changeToUtility() {
        return "redirect:/utility";
    }

    /**
     * Redirect to send money
     * Original: changeToSendMoney() method
     */
    @GetMapping("/home/to-send-money")
    public String changeToSendMoney() {
        return "redirect:/send-money";
    }

    /**
     * Redirect to e-banking
     * Original: changeToEBanking() method
     */
    @GetMapping("/home/to-ebanking")
    public String changeToEBanking() {
        return "redirect:/ebanking";
    }

    /**
     * Redirect to checkbook
     * Original: changeToCheckBook() method
     */
    @GetMapping("/home/to-checkbook")
    public String changeToCheckBook() {
        return "redirect:/checkbook";
    }

    /**
     * Redirect to statement
     * Original: changeToStatement() method
     */
    @GetMapping("/home/to-statement")
    public String changeToStatement() {
        return "redirect:/statement";
    }

    @GetMapping("/home/to-history")
    public String changeToHistory() {
        return "redirect:/history";
    }
}
