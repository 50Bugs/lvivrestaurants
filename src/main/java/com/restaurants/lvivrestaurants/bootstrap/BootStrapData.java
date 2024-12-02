package com.restaurants.lvivrestaurants.bootstrap;

import com.restaurants.lvivrestaurants.formating.FormatingData;
import com.restaurants.lvivrestaurants.domain.Restaurant;
import com.restaurants.lvivrestaurants.parsing.ParsingData;
import com.restaurants.lvivrestaurants.repossitories.RestaurantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;

@Component
public class BootStrapData implements CommandLineRunner {

    private final RestaurantRepository restaurantRepository;

    public BootStrapData(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        ArrayList<Restaurant> restaurants = FormatingData.mergeRestaurantLists(
                ParsingData.parseFromDlabComUA(), ParsingData.parseFromGoogleMaps());

        restaurants = FormatingData.mergeRestaurantLists(restaurants, ParsingData.parseFromTomatoUA());
        for(Restaurant r: restaurants){
            System.out.println(r);
        }

        //ArrayList<Restaurant> restaurants = ParsingData.parseFromDlabComUA();
        System.out.println(restaurants.size());

        restaurants.sort(Comparator.comparing(Restaurant::getName));
        for (Restaurant r : restaurants){
            restaurantRepository.save(r);
        }

        System.out.println(restaurantRepository.count());
    }
}
