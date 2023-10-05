package com.devsuperior.bds02.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;

@Service // annotation registra que essa classe faz parte do sistema automatizado do spring. Spring gerencia a injeção de dependência
public class CityService {

	@Autowired
	private CityRepository repository;
	
	
	public List<CityDTO> findAll(){
		List<City> cityList = repository.findAll(Sort.by("name")); // buscar no banco de dados e ordenar por nome
		
		// converter a lista de classe para DTO
		List<CityDTO> cityDTOList = new ArrayList<CityDTO>();
		for (City i : cityList) {
			cityDTOList.add(new CityDTO(i));
		}
		
		return cityDTOList;
	}
	
	
	@Transactional
	public CityDTO insert(CityDTO cityDTO) {
		City cityEntity = new City();
		cityEntity.setId(cityDTO.getId());
		cityEntity.setName(cityDTO.getName());
		
		cityEntity = repository.save(cityEntity); // Salvar no banco de dados
		
		return new CityDTO(cityEntity); // retornar DTO para o usuário
	}
	
	
	public void delete(Long id) {
		try {
			repository.deleteById(id); // deletar no banco de dados
			
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id não encontrado: " + e);
		} catch (DataIntegrityViolationException e) {
			throw new ResourceNotFoundException("Violação de integridade do banco de dados");
		}
	}
}