package com.spotify.backend.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spotify.backend.dao.PlayListDao;
import com.spotify.backend.entities.PlayList;
import com.spotify.backend.entities.Song;
import com.spotify.backend.exception.BusinessException;

@Transactional
@Service
public class PlayListServiceImpl implements PlayListService {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private PlayListDao playListDao;

	@Override
	public PlayList listPlayListByAlbumId(int albumId) throws BusinessException {

		try {

			return playListDao.getPlayListByAlbumId(albumId);
		} catch (Exception validationException) {
			LOGGER.error("Errores de validacion", validationException.getMessage().toString());

			throw new BusinessException(500, 0001, "listPlayListByAlbumId()");

		}
	}

	@Override
	public PlayList listPlayListByUserId(int userId) throws BusinessException {
		try {

			return playListDao.getPlayListByUserId(userId);
		} catch (Exception validationException) {
			LOGGER.error("Errores de validacion", validationException.getMessage().toString());

			throw new BusinessException(500, 0002, "listPlayListByUserId()");

		}
	}

	@Override
	public PlayList listTop10() throws BusinessException {

		PlayList list = new PlayList();
		try {

			list.setSongs((List<Song>) playListDao.getTop10());
			return list;
		} catch (Exception validationException) {
			LOGGER.error("Errores de validacion", validationException.getMessage().toString());

			throw new BusinessException(500, 0003, "listTop10()");

		}
	}

	@Override
	public Song findSongById(int songId) throws BusinessException {
		try {

			return playListDao.fingSongById(songId);
		} catch (Exception validationException) {
			LOGGER.error("Errores de validacion", validationException.getMessage().toString());

			throw new BusinessException(500, 0004, "findSongById()");

		}
	}

	@Override
	public Song findSongByPlayListIdAndSongId(int playlistId, int songId) throws BusinessException {
		try {

			return playListDao.findSongByPlayListIdAndSongId(playlistId, songId);
		} catch (Exception error) {
			LOGGER.error("Exception at findSongByPlayListIdAndSongId()", error.getMessage().toString());

			throw new BusinessException(500, 0005, "Exception at findSongByPlayListIdAndSongId()");

		}
	}

}
