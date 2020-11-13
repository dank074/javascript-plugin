package com.skeletor.plugin.javascript.communication.outgoing.audio;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.skeletor.plugin.javascript.audio.RoomPlaylist;
import com.skeletor.plugin.javascript.communication.outgoing.OutgoingWebMessage;

public class JukeboxComposer extends OutgoingWebMessage {
    public JukeboxComposer(RoomPlaylist playlist) {
        super("jukebox_player");
        JsonArray playlistJson = new JsonArray();
        for (RoomPlaylist.YoutubeVideo video:playlist.getPlaylist()) {
            JsonObject song = new JsonObject();
            song.add("name", new JsonPrimitive(video.name));
            song.add("videoId", new JsonPrimitive(video.videoId));
            song.add("channel", new JsonPrimitive(video.channel));
            playlistJson.add(song);
        }
        this.data.add("playlist", playlistJson);
    }
}
