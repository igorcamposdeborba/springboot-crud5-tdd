package com.devsuperior.bds02.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
	
	public List<EventDTO> findAll(){
		List<Event> eventList = eventRepository.findAll(Sort.by("name")); // buscar no banco de dados e ordenar por nome
		
		// converter a lista de classe para DTO
		List<EventDTO> eventDTOList = new ArrayList<EventDTO>();
		for (Event i : eventList) {
			eventDTOList.add(new EventDTO(i));
		}
		
		return eventDTOList;
	}
	
	@Transactional // Transaction permite rollback ao estado anterior se algo falhar entre o dto e o banco de dados
	public EventDTO update(Long id, EventDTO eventDto) throws EntityNotFoundException {
		try {
			Event eventEntity = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException()); // getOne instancia um objeto temporario e evita de acessar o banco
			City newCityEntity = cityRepository.findById(eventDto.getCityId()).orElse(null); // buscar id da cidade no banco de dados baseado no novo id que o usuário está passando pelo dto pelo atributo cityId

			eventDto.setCity(newCityEntity); // mudar cidade do evento: atualizar id da cidade do evento
			copyDtoToEntity(eventDto, eventEntity); // deixar igual o banco de dados com o que está no DTO
			eventEntity = eventRepository.save(eventEntity); // salvar no banco de dados o evento
			

			newCityEntity.addEvent(eventEntity); // atualizar objeto cidade colocando o evento nela
			cityRepository.save(newCityEntity); // atualizar no banco de dados a cidade colocando o evento nela
			
			return new EventDTO(eventEntity); // sempre manipulo o dto e não a entity para separar camadas
			
		} catch (EntityNotFoundException e){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND); // exception 404 do ResponseEntity
		}
	}
	
	public void copyDtoToEntity(EventDTO eventDto, Event eventEntity) {
		eventEntity.setName(eventDto.getName());
		eventEntity.setDate(eventDto.getDate());
		eventEntity.setUrl(eventDto.getUrl());
		eventEntity.setCity(eventDto.getCity());
			
		City city = cityRepository.getOne(eventEntity.getCity().getId()); // instanciar categoria sem acessar o banco de dados
		eventDto.setCity(city);
		eventDto.setId(eventEntity.getId());

		eventEntity.setCity(city); // reatribuir city ao dto em execução baseado no que está no banco de dados
		
	}
	
}
