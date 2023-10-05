package com.devsuperior.bds02.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;

@Service // annotation registra que essa classe faz parte do sistema automatizado do spring. Spring gerencia a injeção de dependência
public class EventService {

	@Autowired // permite injetar uma instância do EventRepository
	private EventRepository eventRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	
	@Transactional
	public EventDTO update(Long id, EventDTO eventDto) {
		try {
			Event eventEntity = eventRepository.getOne(id); // getOne instancia um objeto temporario e evita de acessar o banco
			cotyDtoToEntity(eventDto, eventEntity); // deixar igual o banco de dados com o que está no DTO
			eventEntity = eventRepository.save(eventEntity); // salvar no banco de dados
			
			return new EventDTO(eventEntity);
			
		} catch (EntityNotFoundException e){
			throw new ResourceNotFoundException("Id não encontrado: " + id);
		}
	}
	
	public void cotyDtoToEntity(EventDTO eventDto, Event eventEntity) {
		eventEntity.setId(eventDto.getId());
		eventEntity.setName(eventDto.getName());
		eventEntity.setDate(eventDto.getDate());
		eventEntity.setUrl(eventDto.getUrl());
		
		City city = cityRepository.getOne(eventDto.getCityId()); // instanciar categoria sem acessar o banco de dados
		
		eventEntity.setCity(city); // reatribuir city ao dto em execução baseado no que está no banco de dados
		
	}
	
}
