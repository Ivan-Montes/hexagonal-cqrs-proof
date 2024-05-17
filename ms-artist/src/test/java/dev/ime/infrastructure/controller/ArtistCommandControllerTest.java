package dev.ime.infrastructure.controller;


import java.util.Optional;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.ime.config.ArtistMapper;
import dev.ime.domain.model.Artist;
import dev.ime.domain.port.inbound.ArtistCommandServicePort;
import dev.ime.infrastructure.dto.ArtistDto;

@WebMvcTest(ArtistCommandController.class)
@AutoConfigureMockMvc(addFilters = false)
class ArtistCommandControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private ArtistCommandServicePort artistCommandService;

	@MockBean
	private ArtistMapper artistMapper;

	@MockBean
	private Logger logger;

	private final String PATH = "/api/artists";
	private Artist artistTest;
	private final Long id = 18L;
	private final String name = "John Francis";
	private final String surname = "Bongiovi";
	private final String artisticName = "Bon Jovi";
	private ArtistDto artistDtoTest;
	
	@BeforeEach
	private void createObjects() {		

		artistTest = new Artist.ArtistBuilder()
				.setId(id)
				.setName(name)
				.setSurname(surname)
				.setArtisticName(artisticName)
				.build();
		
		artistDtoTest = new ArtistDto(id, name, surname, artisticName);
		
	}
	
	@Test
	void ArtistCommandController_create_ReturnResponseArtistDto() throws Exception {
		
		Mockito.when(artistMapper.fromDtoToDomain(Mockito.any(ArtistDto.class))).thenReturn(artistTest);
		Mockito.when(artistCommandService.create(Mockito.any(Artist.class))).thenReturn(Optional.ofNullable(artistTest));
		Mockito.when(artistMapper.fromDomainToDto(Mockito.any(Artist.class))).thenReturn(artistDtoTest);

		mockMvc.perform(MockMvcRequestBuilders.post(PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(artistDtoTest)))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.notNullValue()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.artistId", org.hamcrest.Matchers.equalTo(id.intValue())))
		.andExpect(MockMvcResultMatchers.jsonPath("$.artistName", org.hamcrest.Matchers.equalTo(name)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.artistSurname", org.hamcrest.Matchers.equalTo(surname)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.artisticName", org.hamcrest.Matchers.equalTo(artisticName)))
		;		
	}

	@Test
	void ArtistCommandController_create_ReturnResponseArtistDtoEmpty() throws Exception {
		
		Mockito.when(artistMapper.fromDtoToDomain(Mockito.any(ArtistDto.class))).thenReturn(artistTest);
		Mockito.when(artistCommandService.create(Mockito.any(Artist.class))).thenReturn(Optional.ofNullable(null));

		mockMvc.perform(MockMvcRequestBuilders.post(PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(artistDtoTest)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.notNullValue()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.artistId", org.hamcrest.Matchers.equalTo(0)))
		;		
	}

	@Test
	void ArtistCommandController_update_ReturnResponseArtistDto() throws Exception {		

		Mockito.when(artistMapper.fromDtoToDomain(Mockito.any(ArtistDto.class))).thenReturn(artistTest);
		Mockito.when(artistCommandService.update(Mockito.anyLong(),Mockito.any(Artist.class))).thenReturn(Optional.ofNullable(artistTest));
		Mockito.when(artistMapper.fromDomainToDto(Mockito.any(Artist.class))).thenReturn(artistDtoTest);

		mockMvc.perform(MockMvcRequestBuilders.put(PATH + "/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(artistDtoTest)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.notNullValue()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.artistId", org.hamcrest.Matchers.equalTo(id.intValue())))
		.andExpect(MockMvcResultMatchers.jsonPath("$.artistName", org.hamcrest.Matchers.equalTo(name)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.artistSurname", org.hamcrest.Matchers.equalTo(surname)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.artisticName", org.hamcrest.Matchers.equalTo(artisticName)))
		;
	}

	@Test
	void ArtistCommandController_update_ReturnResponseArtistDtoEmpty() throws Exception {
		
		Mockito.when(artistMapper.fromDtoToDomain(Mockito.any(ArtistDto.class))).thenReturn(artistTest);
		Mockito.when(artistCommandService.update(Mockito.anyLong(), Mockito.any(Artist.class))).thenReturn(Optional.ofNullable(null));

		mockMvc.perform(MockMvcRequestBuilders.put(PATH + "/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(artistDtoTest)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.notNullValue()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.artistId", org.hamcrest.Matchers.equalTo(0)))
		;		
	}

	@Test
	void ArtistCommandController_deleteById_ReturnResponseBoolTrue() throws Exception {
		
		Mockito.when(artistCommandService.deleteById(Mockito.anyLong())).thenReturn(true);
		
		mockMvc.perform(MockMvcRequestBuilders.delete(PATH+ "/{id}", id))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$").isBoolean())
		.andExpect(MockMvcResultMatchers.jsonPath("$").value(true))
		;
	}

	@Test
	void ArtistCommandController_deleteById_ReturnResponseBoolFalse() throws Exception {
		
		Mockito.when(artistCommandService.deleteById(Mockito.anyLong())).thenReturn(false);
		
		mockMvc.perform(MockMvcRequestBuilders.delete(PATH+ "/{id}", id))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$").isBoolean())
		.andExpect(MockMvcResultMatchers.jsonPath("$").value(false))
		;
	}
	
}
