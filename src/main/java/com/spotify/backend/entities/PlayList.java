package com.spotify.backend.entities;

import java.util.List;

public class PlayList {

	private int id;
	private String name;
	private User user;

	private List<Song> songs;

	
	public PlayList() {}
	
	public PlayList(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public PlayList(int id, String name, User user) {
		super();
		this.id = id;
		this.name = name;
		this.user = user;
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "PlayList [id=" + id + ", name=" + name + ", user=" + user + "]";
	}

}
