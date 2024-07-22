package com.ritika.service;

import com.ritika.dto.RestaurantDto;
import com.ritika.model.Address;
import com.ritika.model.Restaurant;
import com.ritika.model.User;
import com.ritika.repository.AddressRepository;
import com.ritika.repository.RestaurantRepository;
import com.ritika.repository.UserRepository;
import com.ritika.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Address address = addressRepository.save(req.getAddress());
        System.out.println(address.toString());
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);
        restaurant.setOpen(req.isOpen());

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRedRestaurant) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);

        if(restaurant.getCuisineType() != null){
            restaurant.setCuisineType(updateRedRestaurant.getCuisineType());
        }
        if(restaurant.getDescription() != null){
            restaurant.setDescription(updateRedRestaurant.getDescription());
        }
        if(restaurant.getName() != null){
            restaurant.setName(updateRedRestaurant.getName());
        }
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);

    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);

        if(restaurant.isEmpty()){
            throw new Exception("Restaurant not found with id: " + id);
        }
        return restaurant.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
        if(restaurant == null){
            throw new Exception("Restaurant not found with owner id: " + userId);
        }
        return restaurant;
    }

    @Override
    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);

        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setDescription(restaurant.getDescription());
        restaurantDto.setImages(restaurant.getImages());
        restaurantDto.setImages(restaurant.getImages());
        restaurantDto.setTitle(restaurant.getName());
        restaurantDto.setId(restaurantId);

      boolean isFavorite = false;
      List<RestaurantDto> favorites = user.getFavorites();
      for(RestaurantDto favorite : favorites){
          if(favorite.getId().equals(restaurantId)){
              isFavorite = true;
              break;
          }
      }

      if(isFavorite){
          favorites.removeIf(favorite -> favorite.getId().equals(restaurantId));
      }else{
          favorites.add(restaurantDto);
      }

        userRepository.save(user);

        return restaurantDto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {

        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }
}
