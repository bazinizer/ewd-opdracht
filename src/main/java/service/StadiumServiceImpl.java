package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Stadium;
import repository.StadiumRepository;

@Service
public class StadiumServiceImpl implements StadiumService {
    @Autowired
    private StadiumRepository stadiumRepository;

    @Override
    public void save(Stadium stadium) {
        stadiumRepository.save(stadium);
    }

    @Override
    public void saveAll(List<Stadium> stadiums) {
        stadiumRepository.saveAll(stadiums);
    }

    @Override
    public List<Stadium> findAll() {
        return (List<Stadium>) stadiumRepository.findAll();
    }
}
