package com.daka.restservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daka.restservice.entity.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {
	
    Optional<List<Persona>> findByNombre(String nombre);
    Optional<List<Persona>> findByApellidoPaterno(String apellidoPaterno);
    
}
