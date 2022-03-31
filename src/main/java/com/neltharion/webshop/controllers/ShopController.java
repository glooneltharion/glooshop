package com.neltharion.webshop.controllers;

import com.neltharion.webshop.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShopController {

    private ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping(value = "/")
    public String homePage() {
        return "redirect:/webshop";
    }

    @GetMapping(value = "/webshop")
    public String webshop(Model model) {
        model.addAttribute("inventory", shopService.getItems());
        model.addAttribute("format", "table");
        model.addAttribute("currency", shopService.getCurrency());
        return "webshop";
    }

    @GetMapping(value ="/only-available")
    public String onlyAvailable(Model model) {
        model.addAttribute("inventory", shopService.getOnlyAvailable());
        model.addAttribute("format", "table");
        model.addAttribute("currency", shopService.getCurrency());
        return "webshop";
    }

    @GetMapping(value ="/cheapest-first")
    public String cheapestFirst(Model model) {
        model.addAttribute("inventory", shopService.getCheapestFirst());
        model.addAttribute("format", "table");
        model.addAttribute("currency", shopService.getCurrency());
        return "webshop";
    }

    @GetMapping(value ="/contains-nike")
    public String containsNike(Model model) {
        model.addAttribute("inventory", shopService.containsNike());
        model.addAttribute("format", "table");
        model.addAttribute("currency", shopService.getCurrency());
        return "webshop";
    }

    @GetMapping(value ="/average-stock")
    public String averageStock(Model model) {
        model.addAttribute("averageStock", shopService.getAverageStock());
        model.addAttribute("format", "averageStock");
        model.addAttribute("currency", shopService.getCurrency());
        return "webshop";
    }

    @GetMapping(value ="/most-expensive")
    public String mostExpensive(Model model) {
        model.addAttribute("inventory", shopService.getMostExpensive());
        model.addAttribute("format", "mostExpensive");
        model.addAttribute("currency", shopService.getCurrency());
        return "webshop";
    }

    @PostMapping(value ="/search")
    public String search(Model model, @RequestParam("string") String keyword) {
        model.addAttribute("inventory", shopService.search(keyword));
        model.addAttribute("format", "table");
        model.addAttribute("currency", shopService.getCurrency());
        return "webshop";
    }

    @GetMapping(value ="/more-filters")
    public String moreFilters(Model model) {
        model.addAttribute("inventory", shopService.getItems());
        model.addAttribute("format", "table");
        model.addAttribute("currency", shopService.getCurrency());
        return "more-filters";
    }

    @GetMapping(value ="/more-filters/{type}")
    public String moreFilters(Model model, @PathVariable(required = false) String type) {
        model.addAttribute("inventory", shopService.getType(type));
        model.addAttribute("format", "table");
        model.addAttribute("currency", shopService.getCurrency());
        return "more-filters";
    }

    @GetMapping(value ="/price-in-eur")
    public String priceInEur(Model model) {
        model.addAttribute("inventory", shopService.getPrice("eur"));
        model.addAttribute("format", "table");
        model.addAttribute("currency", shopService.getCurrency());
        return "more-filters";
    }

    @GetMapping(value ="/price-in-original")
    public String priceInCzk(Model model) {
        model.addAttribute("inventory", shopService.getPrice("czk"));
        model.addAttribute("format", "table");
        model.addAttribute("currency", shopService.getCurrency());
        return "more-filters";
    }

    @GetMapping(value ="/filter-by-price")
    public String searchPriceExactly(Model model,@RequestParam("price") String typeSearch, @RequestParam("value") Double value) {

        model.addAttribute("inventory", shopService.filterByPrice(value, typeSearch));
        model.addAttribute("format", "table");
        model.addAttribute("currency", shopService.getCurrency());
        return "more-filters";
    }


}
