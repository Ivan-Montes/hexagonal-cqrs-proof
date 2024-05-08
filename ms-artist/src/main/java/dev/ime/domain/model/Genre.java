package dev.ime.domain.model;

import java.util.Arrays;

public enum Genre {
	
	OTHER(1,"Other Genre"),
	HIPHOP(2,"Hip Hop rap"),
	ELECTRONIC(3,"Electronic and Dance"),
	INDIE(4,"Indie music"),
	ROCK(5,"Rock & Roll"),
	SAD(5,"Lofi and Sad");
	
	private Genre(Integer id, String description) {
		this.id = id;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}

	private Integer id;
	private String description;
	
	public static Genre findByName(String name) {
		
		return Arrays.asList(Genre.values())
				.stream()
				.filter( g -> g.name().equalsIgnoreCase(name))
				.findFirst()
				.orElse(OTHER);		
	}
	
}
