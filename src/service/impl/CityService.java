package service.impl;

import entity.City;
import repository.CityRepository;

import java.util.List;

public class CityService {
    private CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getAll() {
        return cityRepository.findAll();
    }

    public void insertMultiple(List<City> cityList) {
        cityRepository.insertMultiple(cityList);
    }

    public void insert(City city) {
       cityRepository.insert(city);
    }


    public void update(String name, int id) {
        cityRepository.update(name, id);
    }

    public void delete(int id) {
        cityRepository.delete(id);
    }
}
