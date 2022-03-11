package com.daka.restservice;

import com.daka.restservice.entity.Persona;
import com.daka.restservice.service.PersonaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class MySqlRestServiceApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	PersonaService personaService;

	ObjectMapper mapper = new ObjectMapper();

	@Test
	void testListaPersonas() throws Exception {
		mvc.perform(get("/persona/lista")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$",
						hasSize(personaService.listaPersona().size())));
	}

	@Test
	void testPersonaById() throws Exception {
		int idPersona=1;
		Persona p = personaService.getPersona(idPersona).get();

		mvc.perform(get("/persona/detallePersona/"+idPersona)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.idpersona",is(p.getIdpersona())))
				.andExpect(jsonPath("$.nombre",is(p.getNombre())))
				.andExpect(jsonPath("$.apellidoPaterno",is(p.getApellidoPaterno())))
				.andExpect(jsonPath("$.apellidoMaterno",is(p.getApellidoMaterno())));
	}

	@Test
	void testFindByNombre() throws Exception {
		String nombre="Daniel";
		List<Persona> personas = personaService.findPersonaByNombre(nombre).get();

		mvc.perform(get("/persona/findByNombre/"+nombre)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(personas.size())))
				.andExpect(jsonPath("$[0].idpersona",is(personas.get(0).getIdpersona())))
				.andExpect(jsonPath("$[0].nombre",is(personas.get(0).getNombre())))
				.andExpect(jsonPath("$[0].apellidoPaterno",is(personas.get(0).getApellidoPaterno())))
				.andExpect(jsonPath("$[0].apellidoMaterno",is(personas.get(0).getApellidoMaterno())));
	}

	@Test
	void testCreaPersona() throws Exception {
		Persona p = new Persona();
		p.setNombre("CrearP");
		p.setApellidoPaterno("APaterno");
		p.setApellidoMaterno("AMaterno");

		mvc.perform(post("/persona/crearPersona/")
				.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(p)))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

		Persona p2 = personaService.findPersonaByNombre("CrearP").get().get(0);

		assert(p2.getNombre().equals(p.getNombre()));
		assert(p2.getApellidoPaterno().equals(p.getApellidoPaterno()));
		assert(p2.getApellidoMaterno().equals(p.getApellidoMaterno()));

		personaService.deletePersona(
				personaService.findPersonaByNombre("CrearP").get().get(0).getIdpersona()
		);

	}

	@Test
	void testActualizarPersona() throws Exception {
		Persona p = personaService.findPersonaByNombre("David").get().get(0);
		p.setApellidoPaterno("AP_Modificado");
		p.setApellidoMaterno("AM_Modificado");

		mvc.perform(put("/persona/actualizarPersona/"+p.getIdpersona())
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(p)))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

		Persona p2 = personaService.getPersona(p.getIdpersona()).get();

		assert(p2.getNombre().equals(p.getNombre()));
		assert(p2.getApellidoPaterno().equals(p.getApellidoPaterno()));
		assert(p2.getApellidoMaterno().equals(p.getApellidoMaterno()));

		p.setApellidoPaterno("aPaterno");
		p.setApellidoMaterno("aMaterno");
		personaService.savePersona(p);
	}

	@Test
	void testBorrarPersona() throws Exception {
		Persona p = new Persona();
		p.setNombre("Borrar");
		p.setApellidoPaterno("Borrar");
		p.setApellidoMaterno("Borrar");

		personaService.savePersona(p);
		p = personaService.findPersonaByNombre("Borrar").get().get(0);

		mvc.perform(delete("/persona/borrarPersona/"+p.getIdpersona())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status", is("ok")));
		assert(personaService.findPersonaByNombre("Borrar").get().isEmpty());

	}

}
