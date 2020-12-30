package com.spotify.backend.entities;

public class Song {

	private int id;
	private Album album;
	private String name;
	private float duration;
	
	public Song(int id, Album album, String name, float duration) {
		super();
		this.id = id;
		this.album = album;
		this.name = name;
		this.duration = duration;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "Song [id=" + id + ", album=" + album + ", name=" + name + ", duration=" + duration + "]";
	}

}
