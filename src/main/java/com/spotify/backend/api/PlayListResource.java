package com.spotify.backend.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.util.Date;
import java.util.Random;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spotify.backend.entities.PlayList;
import com.spotify.backend.entities.Song;
import com.spotify.backend.service.PlayListService;

@Component
@Path("/playlists")
@PermitAll
public class PlayListResource {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	final int chunkSize = 1024 * 1024;

	@Autowired
	private com.spotify.backend.utils.Util util;

	@Autowired
	private PlayListService playListService;

	@RolesAllowed({ "CLIENT" })
	@GET
	@Path("/top-ten")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findTop10() throws Exception {

		PlayList playList = playListService.listTop10();

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(" retrieving entities {}", playList.toString());
		}

		return Response.ok().entity(playList).build();
	}

	@RolesAllowed({ "CLIENT" })
	@GET
	@Path("/{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findPlaylistByUser(@PathParam("userId") int userId) throws Exception {

		PlayList playList = playListService.listPlayListByUserId(userId);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(" retrieving entities {}", playList.toString());
		}

		return Response.ok().entity(playList).build();
	}

	@RolesAllowed({ "CLIENT" })
	@GET
	@Path("/{userId}/listened-recently")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findListenedRecently(@PathParam("userId") int userId) throws Exception {

		PlayList playList = playListService.listPlayListByUserId(userId);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(" retrieving entities {}", playList.toString());
		}

		return Response.ok().entity(playList).build();
	}

	@RolesAllowed({ "CLIENT" })
	@GET
	@Path("/{playlistId}/song/{songId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findPlaylistSongDetail(@PathParam("playlistId") int playlistId, @PathParam("songId") int songId)
			throws Exception {

		Song songDetail = playListService.findSongById(songId);

		return Response.ok().entity(songDetail).build();

	}

	@RolesAllowed({ "CLIENT" })
	@GET
	@Path("/{playlistId}/song/{songId}/play")
	@Produces("audio/mp3")
	public Response playSongDetail(@PathParam("playlistId") int playlistId, @PathParam("songId") int songId,
			@HeaderParam("Range") String range) throws Exception {
		
		
		Song songDetail = playListService.findSongByPlayListIdAndSongId(playlistId, songId);

		return buildStream(new File(	
										util.buildMusicPath(songDetail)
														), 
									range);

	}

	@RolesAllowed({ "CLIENT" })
	@GET
	@Path("/song/{songId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findSongDetail(@PathParam("songId") int songId) throws Exception {

		Song songDetail = playListService.findSongById(songId);

		return Response.ok().entity(songDetail).build();

	}

	@RolesAllowed({ "CLIENT" })
	@GET
	@Path("/song/{songId}/play")
	@Produces("audio/mp3")
	public Response playSong(@PathParam("songId") int songId, @HeaderParam("Range") String range) throws Exception {

		Song songDetail = playListService.findSongById(songId);

		return buildStream(new File(	
				util.buildMusicPath(songDetail)
								), 
			range);

	}

	private Response buildStream(final File asset, final String range) throws Exception {
		// range not requested : Firefox, Opera, IE do not send range headers
		if (range == null) {
			StreamingOutput streamer = new StreamingOutput() {
				@Override
				public void write(final OutputStream output) throws IOException, WebApplicationException {

					final FileInputStream inputStream = new FileInputStream(asset);
					final FileChannel inputChannel = inputStream.getChannel();
					final WritableByteChannel outputChannel = Channels.newChannel(output);
					try {
						inputChannel.transferTo(0, inputChannel.size(), outputChannel);
					} finally {
						// closing the channels
						inputStream.close();
						inputChannel.close();
						outputChannel.close();
					}
				}
			};
			return Response.ok(streamer).header(HttpHeaders.CONTENT_LENGTH, asset.length()).build();
		}

		String[] ranges = range.split("=")[1].split("-");
		final int from = Integer.parseInt(ranges[0]);
		/**
		 * Chunk media if the range upper bound is unspecified. Chrome sends "bytes=0-"
		 */
		int to = chunkSize + from;
		if (to >= asset.length()) {
			to = (int) (asset.length() - 1);
		}
		if (ranges.length == 2) {
			to = Integer.parseInt(ranges[1]);
		}

		final String responseRange = String.format("bytes %d-%d/%d", from, to, asset.length());
		final RandomAccessFile raf = new RandomAccessFile(asset, "r");
		raf.seek(from);

		final int len = to - from + 1;
		final MediaStreamer streamer = new MediaStreamer(len, raf);
		Response.ResponseBuilder res = Response.status(Status.PARTIAL_CONTENT).entity(streamer)
				.header("Accept-Ranges", "bytes").header("Content-Range", responseRange)
				.header(HttpHeaders.CONTENT_LENGTH, streamer.getLenth())
				.header(HttpHeaders.LAST_MODIFIED, new Date(asset.lastModified()));
		return res.build();
	}

	public class MediaStreamer implements StreamingOutput {

		private int length;
		private RandomAccessFile raf;
		final byte[] buf = new byte[4096];

		public MediaStreamer(int length, RandomAccessFile raf) {
			this.length = length;
			this.raf = raf;
		}

		@Override
		public void write(OutputStream outputStream) throws IOException, WebApplicationException {
			try {
				while (length != 0) {
					int read = raf.read(buf, 0, buf.length > length ? length : buf.length);
					outputStream.write(buf, 0, read);
					length -= read;
				}
			} finally {
				raf.close();
			}
		}

		public int getLenth() {
			return length;
		}
	}

}
