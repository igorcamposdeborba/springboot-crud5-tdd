package com.devsuperior.bds02.controllers;



import java.net.URI;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.services.EventService;

@RestController // annotation que declara que este é um controlador REST. Um controlador controla os recursos das entity (objetos que podem representar a tabela do banco de dados)
@RequestMapping(value = "/events") // rota
public class EventController {

	@Autowired
	private EventService eventService;
	
	@PutMapping(value = "{id}")
	public ResponseEntity<EventDTO> update(@PathVariable Long id, @RequestBody EventDTO eventDto) throws EntityNotFoundException {
		EventDTO event = eventService.update(id, eventDto);
		event.getCity().getEvents().size();
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(eventDto.getId()).toUri();
		
		return ResponseEntity.ok().body(eventDto);
	}
	
}
