package com.restaurants.lvivrestaurants.controllers;

import com.restaurants.lvivrestaurants.domain.Restaurant;
import com.restaurants.lvivrestaurants.repossitories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping
    public String getAllRestaurants(Model model) {
        List<Restaurant> restaurants = (List<Restaurant>) restaurantRepository.findAll();
        model.addAttribute("restaurants", restaurants);
        return "restaurants"; // ім'я шаблону, який буде відображений (наприклад, "restaurants.html")
    }
}