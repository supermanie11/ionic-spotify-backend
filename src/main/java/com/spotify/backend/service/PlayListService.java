package com.spotify.backend.service;

import com.spotify.backend.entities.PlayList;
import com.spotify.backend.entities.Song;
import com.spotify.backend.exception.BusinessException;

public interface PlayListService {

	public PlayList listPlayListByAlbumId(int albumId) throws BusinessException;
	public PlayList listPlayListByUserId(int userId) throws BusinessException;
	public PlayList listTop10() throws BusinessException;
	
	public Song findSongById(int songId) throws BusinessException;
	public Song findSongByPlayListIdAndSongId(int playlistId, int songId) throws BusinessException;

}
