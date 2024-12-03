package com.restaurants.lvivrestaurants.controllers;

import com.restaurants.lvivrestaurants.domain.Restaurant;
import com.restaurants.lvivrestaurants.domain.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public String showSearchPage() {
        return "search";
    }

    @GetMapping("/results")
    public String searchRestaurants(@RequestParam("query") String query, Model model) {
        List<Restaurant> searchResults = restaurantService.searchByCriteria(query);
        model.addAttribute("searchResults", searchResults);
        return "search";
    }
}