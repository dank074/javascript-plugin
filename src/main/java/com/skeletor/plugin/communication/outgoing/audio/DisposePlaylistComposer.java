package com.skeletor.plugin.communication.outgoing.audio;

import com.skeletor.plugin.communication.outgoing.OutgoingWebMessage;

public class DisposePlaylistComposer extends OutgoingWebMessage {
    public DisposePlaylistComposer() {
        super("dispose_playlist");
    }
}
