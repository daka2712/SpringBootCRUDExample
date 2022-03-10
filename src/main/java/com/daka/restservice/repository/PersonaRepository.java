package com.daka.restservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daka.restservice.entity.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {
	
    Optional<Persona> findByNombre(String nombre);
    
}
