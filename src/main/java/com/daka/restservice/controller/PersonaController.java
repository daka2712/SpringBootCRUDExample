package com.daka.restservice.controller;

import java.util.List;

import com.daka.restservice.dto.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.daka.restservice.entity.Persona;
import com.daka.restservice.service.PersonaService;

@RestController
@RequestMapping("/persona")
public class PersonaController {

    @Autowired
    PersonaService personaService;

    @GetMapping("/lista")
    public ResponseEntity<?> listaPersonas() {
        List<Persona> personas = personaService.listaPersona();
        return new ResponseEntity<>(personas, HttpStatus.OK);
    }

    @RequestMapping(value = "/listaXml", method = RequestMethod.GET, produces = {"application/xml", "text/xml"})
    public ResponseEntity<?> listaPersonasXml() {
        List<Persona> personas = personaService.listaPersona();
        return new ResponseEntity<>(personas, HttpStatus.OK);
    }

    @GetMapping("/detallePersona/{idPersona}")
    public ResponseEntity<?> personaById(@PathVariable("idPersona") int idPersona) {

        if (!personaService.existsById(idPersona))
            return new ResponseEntity<>(new Mensaje("No existe la persona", "ok"), HttpStatus.NOT_FOUND);

        Persona p = personaService.getPersona(idPersona).get();
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @GetMapping("/findByNombre/{nombre}")
    public ResponseEntity<?> findByNombre(@PathVariable("nombre") String nombre) {
        return new ResponseEntity<>(personaService.findPersonaByNombre(nombre), HttpStatus.OK);
    }

    @GetMapping("/findByApellidoPaterno/{apellidoPaterno}")
    public ResponseEntity<?> findByApellidoPaterno(@PathVariable("apellidoPaterno") String apellidoPaterno) {
        return new ResponseEntity<>(personaService.findPersonaByApellidoPaterno(apellidoPaterno), HttpStatus.OK);
    }

    @RequestMapping(value = "/crearPersonaXml", method = RequestMethod.POST,
            produces = {"application/xml", "text/xml"}, consumes = {"application/xml", "text/xml"})
    public ResponseEntity<?> crearPersonaXML(@RequestBody Persona persona) {

        try {
            if (persona.getNombre().isEmpty())
                return new ResponseEntity<>(new Mensaje("El nombre es obligatorio", "error"), HttpStatus.BAD_REQUEST);

            personaService.savePersona(persona);
            return new ResponseEntity<>(new Mensaje("Persona creada", "ok"), HttpStatus.OK);
        } catch (Exception e)   {
            return new ResponseEntity<>(new Mensaje("Error al insertar a la persona", "error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/crearPersona")
    public ResponseEntity<?> creaPersona(@RequestBody Persona persona) {

        try {
            if (persona.getNombre().isEmpty())
                return new ResponseEntity<>(new Mensaje("El nombre es obligatorio", "error"), HttpStatus.BAD_REQUEST);

            personaService.savePersona(persona);
            return new ResponseEntity<>(new Mensaje("Persona creada", "ok"), HttpStatus.OK);
        } catch (Exception e)   {
            return new ResponseEntity<>(new Mensaje("Error al insertar a la persona", "error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/actualizarPersona/{idPersona}")
    public ResponseEntity<?> actualizarPersona(@PathVariable("idPersona") int idPersona, @RequestBody Persona persona) {

        if (!personaService.existsById(idPersona))
            return new ResponseEntity<>(new Mensaje("No existe la persona", "ok"), HttpStatus.NOT_FOUND);

        persona.setIdpersona(idPersona);
        personaService.savePersona(persona);
        return new ResponseEntity<>(new Mensaje("Persona actualizada", "ok"), HttpStatus.OK);
    }

    @DeleteMapping("/borrarPersona/{idPersona}")
    public ResponseEntity<?> borrarPersona(@PathVariable("idPersona") int idPersona) {
        if (!personaService.existsById(idPersona))
            return new ResponseEntity<>(new Mensaje("No existe la persona", "error"), HttpStatus.NOT_FOUND);
        personaService.deletePersona(idPersona);
        return new ResponseEntity<>(new Mensaje("Persona eliminada", "ok"), HttpStatus.OK);
    }
}