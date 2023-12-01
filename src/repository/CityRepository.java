package repository;

import entity.City;

import java.util.List;

public interface CityRepository {
    List<City> findAll();
    City findCityById(int id);
    void insertMultiple(List<City> cityList);
    void insert(City city);
    void update(String name, int id);
    void delete(int id);
}
