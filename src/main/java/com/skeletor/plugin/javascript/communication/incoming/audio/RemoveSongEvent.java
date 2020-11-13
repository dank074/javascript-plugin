package com.skeletor.plugin.javascript.communication.incoming.audio;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.skeletor.plugin.javascript.JSPlugin;
import com.skeletor.plugin.javascript.audio.RoomPlaylist;
import com.skeletor.plugin.javascript.communication.incoming.IncomingWebMessage;
import com.skeletor.plugin.javascript.communication.outgoing.audio.RemoveSongComposer;
import com.skeletor.plugin.javascript.override_packets.outgoing.JavascriptCallbackComposer;

public class RemoveSongEvent extends IncomingWebMessage<RemoveSongEvent.JSONRemoveSongEvent> {

    public RemoveSongEvent() {
        super(JSONRemoveSongEvent.class);
    }

    @Override
    public void handle(GameClient client, JSONRemoveSongEvent message) {
        Room room = client.getHabbo().getHabboInfo().getCurrentRoom();
        if(room == null)
            return;

        if(room.hasRights(client.getHabbo())) {
            RoomPlaylist roomPlaylist = JSPlugin.getInstance().getRoomAudioManager().getPlaylistForRoom(room.getId());
            roomPlaylist.removeSong(message.index);
            room.sendComposer(new JavascriptCallbackComposer(new RemoveSongComposer(message.index)).compose());
        }
    }

    public static class JSONRemoveSongEvent {
        public int index;
    }
}
