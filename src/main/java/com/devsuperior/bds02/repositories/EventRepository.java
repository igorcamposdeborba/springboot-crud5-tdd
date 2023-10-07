package com.devsuperior.bds02.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devsuperior.bds02.entities.Event;

@Repository // permite injeção de dependência para implementar os métodos de acesso ao banco de dados
public interface EventRepository extends JpaRepository<Event, Long>{

	@Query("SELECT e FROM Event e WHERE e.name = :eventName")
    Event findByName(@Param("eventName") String eventName);
}
