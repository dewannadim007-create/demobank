package com.example.app.controller;

import com.example.app.model.User;
import com.example.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

/**
 * EBankingController - Converted from JavaFX to Spring Boot
 * All original business logic preserved
 */
@Controller
public class EBankingController {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * Show e-banking page with bank and wallet balances
     * Original: Methods - checkWallet() and checkBank()
     */
    @GetMapping("/ebanking")
    public String showEBankingPage(HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            return "redirect:/login";
        }

        // ORIGINAL LOGIC - Get wallet balance
        double walletBalance = UserService.getBalanceOnline(loggedUser.getMobile(), mongoTemplate);

        // ORIGINAL LOGIC - Get bank balance
        double bankBalance = UserService.getBalanceAccount(loggedUser.getAccount(), mongoTemplate);

        model.addAttribute("walletBalance", walletBalance);
        model.addAttribute("bankBalance", bankBalance);
        model.addAttribute("user", loggedUser);

        return "eBanking";
    }

    /**
     * Check wallet balance separately (AJAX endpoint)
     * Original: checkWallet() method
     */
    @GetMapping("/api/ebanking/check-wallet")
    public String checkWallet(HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            return "redirect:/login";
        }

        // ORIGINAL LOGIC
        double walletBalance = UserService.getBalanceOnline(loggedUser.getMobile(), mongoTemplate);
        model.addAttribute("walletBalance", walletBalance);

        return "redirect:/ebanking";
    }

    /**
     * Check bank balance separately (AJAX endpoint)
     * Original: checkBank() method
     */
    @GetMapping("/api/ebanking/check-bank")
    public String checkBank(HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            return "redirect:/login";
        }

        // ORIGINAL LOGIC
        double bankBalance = UserService.getBalanceAccount(loggedUser.getAccount(), mongoTemplate);
        model.addAttribute("bankBalance", bankBalance);

        return "redirect:/ebanking";
    }

    /**
     * Redirect to add money
     * Original: addMoney() method
     */
    @GetMapping("/ebanking/add-money")
    public String addMoney() {
        return "redirect:/add-money";
    }

    /**
     * Redirect to home
     * Original: changeToHome() method
     */
    @GetMapping("/ebanking/to-home")
    public String changeToHome() {
        return "redirect:/home";
    }
}
