package com.daka.restservice.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import com.daka.restservice.entity.Persona;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daka.restservice.repository.PersonaRepository;

@Service
@Transactional
public class PersonaService {
	
	@Autowired
	PersonaRepository personaRepo;
	
	public List<Persona> listaPersona()	{
		return personaRepo.findAll();
	}
	
	public Optional<Persona> getPersona(int idPersona)	{
		return personaRepo.findById(idPersona);
	}
	
	public Optional<Persona> getPersonaByNombre(String nombre)	{
		return personaRepo.findByNombre(nombre);
	}
	
	public boolean existsById(int idPersona)	{
		return personaRepo.existsById(idPersona);		
	}
	
	public void savePersona(Persona persona)	{
		personaRepo.save(persona);
	}
	
	public void deletePersona(int idPersona)	{
		personaRepo.deleteById(idPersona);
	}
	
	
	
	

}
