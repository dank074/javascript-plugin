package com.skeletor.plugin.communication.outgoing.audio;

import com.google.gson.JsonPrimitive;
import com.skeletor.plugin.communication.outgoing.OutgoingWebMessage;

public class PlayStopComposer extends OutgoingWebMessage {
    public PlayStopComposer(boolean isPlaying) {
        super("play_stop");
        this.data.add("playing", new JsonPrimitive(isPlaying));
    }
}
