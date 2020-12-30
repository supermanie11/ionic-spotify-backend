package com.spotify.backend.entities;

public class PlayListSong {

	private PlayList playListId;
	private Song songId;

	public PlayListSong(PlayList playListId, Song songId) {
		super();
		this.playListId = playListId;
		this.songId = songId;
	}

	public PlayList getPlayListId() {
		return playListId;
	}

	public void setPlayListId(PlayList playListId) {
		this.playListId = playListId;
	}

	public Song getSongId() {
		return songId;
	}

	public void setSongId(Song songId) {
		this.songId = songId;
	}

	@Override
	public String toString() {
		return "PlayListSong [playListId=" + playListId + ", songId=" + songId + "]";
	}

}
