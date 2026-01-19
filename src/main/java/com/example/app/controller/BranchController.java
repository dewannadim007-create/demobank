package com.example.app.controller;

import com.example.app.model.Branch;
import com.example.app.services.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

/**
 * BranchController - Converted from JavaFX to Spring Boot
 * All original business logic preserved
 */
@Controller
public class BranchController {

    @Autowired
    private BranchService branchService;

    /**
     * Show branch list page
     * Original: initialize() method - ALL LOGIC PRESERVED
     */
    @GetMapping("/branch")
    public String showBranchList(Model model) {
        // ORIGINAL LOGIC - Get all branches
        List<Branch> branchList = branchService.getBranchList();

        model.addAttribute("branches", branchList);
        return "branch";
    }

    /**
     * Search branches by name, type, or location
     * Original: startSearch() method - EXACT SAME FILTERING LOGIC
     */
    @GetMapping("/branch/search")
    public String startSearch(@RequestParam("search") String search, Model model) {

        // ORIGINAL LOGIC - EXACT SAME
        String searchLower = search.toLowerCase();
        List<Branch> branchList = branchService.getBranchList();

        // EXACT SAME FILTER LOGIC
        List<Branch> branchFilteredList = branchList.stream()
                .filter(branch -> branch.getName().toLowerCase().contains(searchLower) ||
                        branch.getType().toLowerCase().contains(searchLower) ||
                        branch.getLocation().toLowerCase().contains(searchLower))
                .collect(Collectors.toList());

        model.addAttribute("branches", branchFilteredList);
        model.addAttribute("searchTerm", search);
        return "branch";
    }

    /**
     * Redirect to first page
     * Original: changeToFirst() method
     */
    @GetMapping("/branch/to-first")
    public String changeToFirst() {
        return "redirect:/first";
    }
}
