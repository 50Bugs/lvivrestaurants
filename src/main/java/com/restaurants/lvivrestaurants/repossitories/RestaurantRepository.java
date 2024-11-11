package com.restaurants.lvivrestaurants.repossitories;

import com.restaurants.lvivrestaurants.domain.Restaurant;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
}