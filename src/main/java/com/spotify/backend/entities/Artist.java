package com.spotify.backend.entities;

import java.util.ArrayList;
import java.util.Collection;

public class Artist {

	private int id;
	private String name;
	private Gender gender;
	
	private Collection<Album> albums = new ArrayList<Album>();

	public Artist(int id, String name, Gender gender) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Collection<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(Collection<Album> albums) {
		this.albums = albums;
	}

	@Override
	public String toString() {
		return "Artist [id=" + id + ", name=" + name + ", gender=" + gender + "]";
	}
	
	
	
	
}
