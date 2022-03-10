package com.daka.restservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.daka.restservice.entity.Persona;
import com.daka.restservice.service.PersonaService;

import javax.xml.bind.annotation.XmlRootElement;

@RestController
@RequestMapping("/persona")
public class PersonaController {

    @Autowired
    PersonaService personaService;

    @GetMapping("/lista")
    public ResponseEntity<List<Persona>> listaPersonas() {
        List<Persona> personas = personaService.listaPersona();
        return new ResponseEntity<List<Persona>>(personas, HttpStatus.OK);
    }

    @RequestMapping(value = "/listaXml", method = RequestMethod.GET, produces = {"application/xml", "text/xml"})
    public ResponseEntity<List<Persona>> listaPersonasXml() {
        List<Persona> personas = personaService.listaPersona();
        return new ResponseEntity<List<Persona>>(personas, HttpStatus.OK);
    }

    @GetMapping("/detallePersona/{idPersona}")
    public ResponseEntity<Object> personaById(@PathVariable("idPersona") int idPersona) {

        if (!personaService.existsById(idPersona))
            return new ResponseEntity<>("No existe la persona", HttpStatus.NOT_FOUND);

        Persona p = personaService.getPersona(idPersona).get();
        return new ResponseEntity<>(p, HttpStatus.OK);
    }	
    
    @RequestMapping(value = "/crearPersonaXml", method = RequestMethod.POST, 
            produces = {"application/xml", "text/xml"}, consumes = {"application/xml", "text/xml"})
    public ResponseEntity<Object> crearPersonaXML(@RequestBody Persona persona) {

        if (persona.getNombre().isEmpty())
            return new ResponseEntity<>("El nombre es obligatorio", HttpStatus.BAD_REQUEST);

        personaService.savePersona(persona);
        return new ResponseEntity<>("Persona creada", HttpStatus.OK);
    }

    @PostMapping("/crearPersona")
    public ResponseEntity<Object> creaPersona(@RequestBody Persona persona) {

        if (persona.getNombre().isEmpty())
            return new ResponseEntity<>("El nombre es obligatorio", HttpStatus.BAD_REQUEST);

        personaService.savePersona(persona);
        return new ResponseEntity<>("Persona creada", HttpStatus.OK);
    }

    @PutMapping("/actualizarPersona/{idPersona}")
    public ResponseEntity<Object> actualizarPersona(@PathVariable("idPersona") int idPersona, @RequestBody Persona persona) {

        if (!personaService.existsById(idPersona))
            return new ResponseEntity<>("No existe la persona", HttpStatus.NOT_FOUND);

        persona.setIdpersona(idPersona);
        personaService.savePersona(persona);
        return new ResponseEntity<>("Persona actualizada", HttpStatus.OK);
    }

    @DeleteMapping("/borrarPersona/{idPersona}")
    public ResponseEntity<Object> borrarPersona(@PathVariable("idPersona") int idPersona) {
        if (!personaService.existsById(idPersona))
            return new ResponseEntity<>("No existe la persona", HttpStatus.NOT_FOUND);
        personaService.deletePersona(idPersona);
        return new ResponseEntity<>("Persona eliminada", HttpStatus.OK);
    }

}
