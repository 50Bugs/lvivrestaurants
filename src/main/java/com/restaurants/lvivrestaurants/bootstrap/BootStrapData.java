package com.restaurants.lvivrestaurants.bootstrap;

import com.restaurants.lvivrestaurants.domain.Restaurant;
import com.restaurants.lvivrestaurants.parsing.ParsingData;
import com.restaurants.lvivrestaurants.repossitories.RestaurantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class BootStrapData implements CommandLineRunner {

    private final RestaurantRepository restaurantRepository;

    public BootStrapData(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        ArrayList<Restaurant> restaurants = ParsingData.parseFromDlabComUA();

        for (Restaurant r : restaurants){
            restaurantRepository.save(r);
        }

        System.out.println(restaurantRepository.count());
    }
}
