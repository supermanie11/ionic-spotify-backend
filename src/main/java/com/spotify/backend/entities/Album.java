package com.spotify.backend.entities;

import java.util.ArrayList;
import java.util.Collection;

public class Album {

	private int id;
	private Artist artist;
	private String name;
	private String year;
	private String urlImage;

	private Collection<Song> songs = new ArrayList<Song>();

	public Album(int id, Artist artist, String name, String year, String urlImage) {
		super();
		this.id = id;
		this.artist = artist;
		this.name = name;
		this.year = year;
		this.urlImage = urlImage;
	}
	
	public Album(int id, String name, String year, String urlImage) {
		super();
		this.id = id;
		this.name = name;
		this.year = year;
		this.urlImage = urlImage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public Collection<Song> getSongs() {
		return songs;
	}

	public void setSongs(Collection<Song> songs) {
		this.songs = songs;
	}

	@Override
	public String toString() {
		return "Album [id=" + id + ", artist=" + artist + ", name=" + name + ", year=" + year + ", urlImage=" + urlImage
				+ "]";
	}

}
