package service;

import java.util.List;

import domain.Stadium;

public interface StadiumService {
    void save(Stadium stadium);
    void saveAll(List<Stadium> stadiums);
    List<Stadium> findAll();
}
