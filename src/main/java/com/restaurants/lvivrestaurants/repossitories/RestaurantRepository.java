package com.restaurants.lvivrestaurants.repossitories;

import com.restaurants.lvivrestaurants.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    /*@Query("SELECT r FROM Restaurant r " +
            "WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(r.address) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(r.rating) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(r.cuisine) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Restaurant> searchInFields(@Param("query") String query);*/
    @Query("SELECT r FROM Restaurant r " +
            "WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(r.address) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(CAST(r.rating AS string)) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(r.cuisine) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Restaurant> searchInFields(@Param("query") String query);
}