package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Sport;
import repository.SportRepository;

@Service
public class SportServiceImpl implements SportService{
	
	@Autowired
    private SportRepository sportRepository;

	@Override
	public void save(Sport sport) {
		if(sportRepository.findByNaam(sport.getNaam())==null){
			sportRepository.save(sport);
			
        }
		
	}

	@Override
	public void saveAll(Iterable<Sport> sports) {
		sportRepository.saveAll(sports);
		
	}
	@Override
    public List<Sport> findAll() {
        return (List<Sport>) sportRepository.findAll();
    }

}
