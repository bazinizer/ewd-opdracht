package service;

import java.util.List;

import domain.Sport;

public interface SportService {
	void save(Sport sport);
	void saveAll(Iterable<Sport> sports);
	List<Sport> findAll();
	Sport findById(Long sportId);

	
	
	

}
