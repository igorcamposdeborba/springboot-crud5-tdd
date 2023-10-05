package com.devsuperior.bds02.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.bds02.entities.City;

@Repository // permite injeção de dependência para implementar os métodos de acesso ao banco de dados
public interface CityRepository extends JpaRepository<City, Long>{

}
