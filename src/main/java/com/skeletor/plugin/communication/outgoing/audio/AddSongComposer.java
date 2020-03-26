package com.skeletor.plugin.communication.outgoing.audio;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.skeletor.plugin.audio.RoomPlaylist;
import com.skeletor.plugin.communication.outgoing.OutgoingWebMessage;

public class AddSongComposer extends OutgoingWebMessage {
    public AddSongComposer(RoomPlaylist.YoutubeVideo video) {
        super("add_song");
        JsonObject song = new JsonObject();
        song.add("name", new JsonPrimitive(video.name));
        song.add("videoId", new JsonPrimitive(video.videoId));
        song.add("channel", new JsonPrimitive(video.channel));
        this.data.add("song", song);
    }
}
