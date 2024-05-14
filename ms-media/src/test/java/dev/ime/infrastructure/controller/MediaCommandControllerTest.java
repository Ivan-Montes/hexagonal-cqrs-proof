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
import dev.ime.config.MediaMapper;
import dev.ime.domain.model.Genre;
import dev.ime.domain.model.Media;
import dev.ime.domain.model.MediaClass;
import dev.ime.domain.port.inbound.MediaCommandServicePort;
import dev.ime.infrastructure.dto.MediaDto;


@WebMvcTest(MediaCommandController.class)
@AutoConfigureMockMvc(addFilters = false)
class MediaCommandControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private MediaCommandServicePort mediaCommandServicePort;
	
	@MockBean
	private MediaMapper mediaMapper;

	@MockBean
	private Logger logger;

	@Autowired
    private ObjectMapper objectMapper;
	
	private final String PATH = "/api/medias";
	private Media mediaTest;
	private final Long id = 9L;
	private final String name = "Always";
	private final Genre genre = Genre.ROCK;
	private final MediaClass mediaClass = MediaClass.LIVE;
	private final Long artistId = 18L;	
	private MediaDto mediaDtoTest;
	
	@BeforeEach
	private void createObjects() {
		
		mediaTest = new Media.MediaBuilder()
				.setId(id)
				.setName(name)
				.setGenre(genre)
				.setMediaClass(mediaClass)
				.setArtistId(artistId)
				.build();
		
		mediaDtoTest = new MediaDto(id, name, genre.name(),mediaClass.name(),artistId);
		
	}	
	
	@Test
	void MediaCommandController_create_ReturnResponseEntityMediaDto() throws Exception {
		
		Mockito.when(mediaMapper.fromDtoToDomain(Mockito.any(MediaDto.class))).thenReturn(mediaTest);
		Mockito.when(mediaCommandServicePort.create(Mockito.any(Media.class))).thenReturn(Optional.ofNullable(mediaTest));
		Mockito.when(mediaMapper.fromDomainToDto(Mockito.any(Media.class))).thenReturn(mediaDtoTest);
		
		mockMvc.perform(MockMvcRequestBuilders.post(PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(mediaDtoTest))
				)
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.notNullValue()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.mediaId", org.hamcrest.Matchers.equalTo(id.intValue())))
		.andExpect(MockMvcResultMatchers.jsonPath("$.mediaName", org.hamcrest.Matchers.equalTo(name)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.mediaGenre", org.hamcrest.Matchers.equalTo(genre.name())))
		.andExpect(MockMvcResultMatchers.jsonPath("$.mediaClass", org.hamcrest.Matchers.equalTo(mediaClass.name())))
		.andExpect(MockMvcResultMatchers.jsonPath("$.artistId", org.hamcrest.Matchers.equalTo(artistId.intValue())))
		;
	}

	@Test
	void MediaCommandController_create_ReturnResponseEntityMediaDtoVoid() throws Exception {
		
		Mockito.when(mediaMapper.fromDtoToDomain(Mockito.any(MediaDto.class))).thenReturn(mediaTest);
		Mockito.when(mediaCommandServicePort.create(Mockito.any(Media.class))).thenReturn(Optional.ofNullable(null));
		
		mockMvc.perform(MockMvcRequestBuilders.post(PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(mediaDtoTest))
				)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.notNullValue()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.mediaId", org.hamcrest.Matchers.equalTo(0)))
		;
	}

	@Test
	void MediaCommandController_update_ReturnResponseEntityMediaDto() throws Exception  {
		
		Mockito.when(mediaMapper.fromDtoToDomain(Mockito.any(MediaDto.class))).thenReturn(mediaTest);
		Mockito.when(mediaCommandServicePort.update(Mockito.anyLong(), Mockito.any(Media.class))).thenReturn(Optional.ofNullable(mediaTest));
		Mockito.when(mediaMapper.fromDomainToDto(Mockito.any(Media.class))).thenReturn(mediaDtoTest);
		
		mockMvc.perform(MockMvcRequestBuilders.put(PATH + "/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(mediaDtoTest))
				)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.notNullValue()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.mediaId", org.hamcrest.Matchers.equalTo(id.intValue())))
		.andExpect(MockMvcResultMatchers.jsonPath("$.mediaName", org.hamcrest.Matchers.equalTo(name)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.mediaGenre", org.hamcrest.Matchers.equalTo(genre.name())))
		.andExpect(MockMvcResultMatchers.jsonPath("$.mediaClass", org.hamcrest.Matchers.equalTo(mediaClass.name())))
		.andExpect(MockMvcResultMatchers.jsonPath("$.artistId", org.hamcrest.Matchers.equalTo(artistId.intValue())))
		;		
	}

	@Test
	void MediaCommandController_deleteById_ReturnResponseEntityBoolTrue() throws Exception  {
		
		Mockito.when(mediaCommandServicePort.deleteById(Mockito.anyLong())).thenReturn(true);
		
		mockMvc.perform(MockMvcRequestBuilders.delete(PATH + "/{id}", Mockito.anyLong()))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$").isBoolean())
		.andExpect(MockMvcResultMatchers.jsonPath("$").value(true))
		;
	}

	@Test
	void MediaCommandController_deleteById_ReturnResponseEntityBoolFalse() throws Exception  {
		
		Mockito.when(mediaCommandServicePort.deleteById(Mockito.anyLong())).thenReturn(false);
		
		mockMvc.perform(MockMvcRequestBuilders.delete(PATH + "/{id}", Mockito.anyLong()))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$").isBoolean())
		.andExpect(MockMvcResultMatchers.jsonPath("$").value(false))
		;
	}

}
