package com.devsuperior.bds02.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devsuperior.bds02.entities.City;

@Repository // permite injeção de dependência para implementar os métodos de acesso ao banco de dados
public interface CityRepository extends JpaRepository<City, Long>{

	@Query("SELECT e.id FROM Event e "
			+ "WHERE e.city.id = :cityId "
			+ "ORDER BY e.id DESC")
    Long findLastEventIdByCityId(@Param("cityId") Long idPivot);
}
