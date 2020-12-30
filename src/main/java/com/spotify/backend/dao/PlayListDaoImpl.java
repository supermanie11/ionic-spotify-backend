package com.spotify.backend.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spotify.backend.entities.Album;
import com.spotify.backend.entities.Artist;
import com.spotify.backend.entities.PlayList;
import com.spotify.backend.entities.Song;

@Repository
public class PlayListDaoImpl implements GenericDao<PlayList>, PlayListDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Optional<PlayList> get(int id) {
		String query = "select id, name, user_id from playlist where id = ?";

		PlayList playlist = jdbcTemplate.queryForObject(query,
				(rs, rowNum) -> new PlayList(rs.getInt("id"), rs.getString("name")), new Object[] { id });

		return Optional.ofNullable(playlist);
	}

	@Override
	public Collection<PlayList> getAll() {
		String query = "select id, name, user_id from playlist";

		List<PlayList> playlists = jdbcTemplate.query(query,
				(rs, rowNum) -> new PlayList(rs.getInt("id"), rs.getString("name")));

		return playlists;

	}

	@Transactional
	@Override
	public PlayList save(PlayList model) {

		String insertPlayList = "INSERT INTO playlist(id, name, user_id) select ifnull(max(id), 0) + 1 , ?,  ? from playlist";

		jdbcTemplate.update(insertPlayList, new Object[] { model.getName(), model.getUser().getId() });

		String insertPlayListSong = "INSERT INTO playlist_song(playlist_id, song_id) select max(id) , ? from playlist";

		int[][] insertPlayListSongCount = jdbcTemplate.batchUpdate(insertPlayListSong, model.getSongs(),
				model.getSongs().size(), new ParameterizedPreparedStatementSetter<Song>() {
					public void setValues(PreparedStatement ps, Song row) throws SQLException {
						ps.setInt(1, row.getId());
					}
				});

		return model;
	}

	@Transactional
	@Override
	public PlayList update(PlayList model) {
		return model;

	}

	@Transactional
	@Override
	public void delete(PlayList model) {
		String queryDelete = "delete from playlist where id = ?";
		jdbcTemplate.update(queryDelete, new Object[] { model.getId() });
	}

	@Override
	public Collection<Song> getTop10() {
		String query = "select artista.name as artist_name, cancion.id as id, cancion.name as name, cancion.duration as duration , album.id as album_id, album.name as album_name, album.url_image as album_image, album.year as album_year from song cancion inner join song_reproduction reproduction on reproduction.song_id = cancion.id inner join album album on album.id = cancion.album_id inner join artist artista on artista.id = album.artist_id order by reproduction.total_reproduction desc limit 10";

		List<Song> top10 = jdbcTemplate.query(query,
				(rs, rowNum) -> new Song(
						rs.getInt("id"), new Album(rs.getInt("album_id"), new Artist(0, rs.getString("artist_name"), null), rs.getString("album_name"),
								rs.getString("album_year"), rs.getString("album_image")),
						rs.getString("name"), rs.getFloat("duration")));

		return top10;
	}

	@Override
	public Collection<Song> getTop10ByCountry(String country) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<PlayList> addPlayList(PlayList playList) {
		return Optional.ofNullable(save(playList));
	}

	@Override
	public PlayList getPlayListByAlbumId(int albumId) {

		String query = "select cancion.id as id, cancion.name as name, cancion.duration as duration , album.id as album_id, album.name as album_name, album.url_image as album_image, album.year as album_year from song cancion inner join song_reproduction reproduction on reproduction.song_id = cancion.id inner join album album on album.id = cancion.album_id where album.id = ? order by cancion.id desc ";

		List<Song> songs = jdbcTemplate.query(query,
				(rs, rowNum) -> new Song(rs.getInt("id"),
						new Album(rs.getInt("album_id"), null, rs.getString("album_name"), rs.getString("album_year"),
								rs.getString("album_image")),
						rs.getString("name"), rs.getFloat("duration")),
				new Object[] { albumId });

		PlayList playList = new PlayList();

		playList.setSongs(songs);

		return playList;
	}

	@Override
	public PlayList getPlayListByUserId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Album> getTop10Album() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Album> getTop10AlbumByCountry(String country) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Album> getTop10MoreListenedByUserId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Album> getTop10RecentlyListenedByUserId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Song fingSongById(int songId) {
		String query = "select distinct artista.name as artist_name, cancion.id as id, cancion.name as name, cancion.duration as duration , album.id as album_id, album.name as album_name, album.url_image as album_image, album.year as album_year from song cancion inner join song_reproduction reproduction on reproduction.song_id = cancion.id inner join album album on album.id = cancion.album_id inner join artist artista on artista.id = album.artist_id where cancion.id = ?";

		Song song = jdbcTemplate.queryForObject(query,
				(rs, rowNum) -> new Song(rs.getInt("id"),
						new Album(rs.getInt("album_id"), new Artist(0, rs.getString("artist_name"), null),
								rs.getString("album_name"), rs.getString("album_year"), rs.getString("album_image")),
						rs.getString("name"), rs.getFloat("duration")),
				new Object[] { songId });

		return song;
	}

	@Override
	public Song findSongByPlayListIdAndSongId(int playlistId, int songId) {
		String query = "select artista.name as artist_name, cancion.id as id, cancion.name as name, cancion.duration as duration , album.id as album_id, album.name as album_name, album.url_image as album_image, album.year as album_year from song cancion inner join song_reproduction reproduction on reproduction.song_id = cancion.id inner join album album on album.id = cancion.album_id inner join artist artista on artista.id = album.artist_id where cancion.id = ?";

		Song song = jdbcTemplate.queryForObject(query,
				(rs, rowNum) -> new Song(rs.getInt("id"),
						new Album(rs.getInt("album_id"), new Artist(0, rs.getString("artist_name"), null),
								rs.getString("album_name"), rs.getString("album_year"), rs.getString("album_image")),
						rs.getString("name"), rs.getFloat("duration")),
				new Object[] { songId });

		return song;
	}

}
