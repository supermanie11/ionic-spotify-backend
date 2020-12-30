package com.spotify.backend.dao;

import java.util.Collection;
import java.util.Optional;

import com.spotify.backend.entities.Album;
import com.spotify.backend.entities.PlayList;
import com.spotify.backend.entities.Song;

public interface PlayListDao {

	Song fingSongById(int songId);

	Song findSongByPlayListIdAndSongId(int playlistId, int songId);

	Collection<Song> getTop10();

	Collection<Song> getTop10ByCountry(String country);

	PlayList getPlayListByAlbumId(int albumId);

	PlayList getPlayListByUserId(int userId);

	Optional<PlayList> addPlayList(PlayList playList);

	Collection<Album> getTop10Album();

	Collection<Album> getTop10AlbumByCountry(String country);

	Collection<Album> getTop10MoreListenedByUserId(int userId);

	Collection<Album> getTop10RecentlyListenedByUserId(int userId);

}
