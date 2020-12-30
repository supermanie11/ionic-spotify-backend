package com.spotify.backend.utils;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.spotify.backend.entities.Song;

@Component
public class Util {

	@Value("${spotify.config.music.path}")
	private String musicPath;

	public String buildMusicPath(Song song) {
		return musicPath + File.separator + song.getAlbum().getArtist().getName() + File.separator
				+ song.getAlbum().getName() + File.separator + song.getName() + ".mp3";
	}
}
