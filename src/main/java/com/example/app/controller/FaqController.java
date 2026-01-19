package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * FaqController - Converted from JavaFX to Spring Boot
 * All original business logic preserved
 */
@Controller
public class FaqController {

    /**
     * Show FAQ page
     */
    @GetMapping("/faq")
    public String showFaqPage(Model model) {
        return "faq";
    }

    /**
     * Redirect to first page
     * Original: changeToFirst() method
     */
    @GetMapping("/faq/to-first")
    public String changeToFirst() {
        return "redirect:/first";
    }
}
