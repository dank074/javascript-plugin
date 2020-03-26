package com.skeletor.plugin.communication.outgoing.audio;

import com.google.gson.JsonPrimitive;
import com.skeletor.plugin.communication.outgoing.OutgoingWebMessage;

public class PlaySongComposer extends OutgoingWebMessage {
    public PlaySongComposer(int index) {
        super("play_song");
        this.data.add("index", new JsonPrimitive(index));
    }
}
