package com.restaurants.lvivrestaurants.domain;

import com.restaurants.lvivrestaurants.repossitories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<Restaurant> searchByCriteria(String query) {
        return restaurantRepository.searchInFields(query);
    }
}