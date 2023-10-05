package com.devsuperior.bds02.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.bds02.entities.Event;

@Repository // permite injeção de dependência para implementar os métodos de acesso ao banco de dados
public interface EventRepository extends JpaRepository<Event, Long>{

}