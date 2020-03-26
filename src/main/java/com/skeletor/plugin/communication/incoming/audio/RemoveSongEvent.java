package com.skeletor.plugin.communication.incoming.audio;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.skeletor.plugin.audio.RoomAudioManager;
import com.skeletor.plugin.audio.RoomPlaylist;
import com.skeletor.plugin.communication.incoming.IncomingWebMessage;
import com.skeletor.plugin.communication.outgoing.audio.RemoveSongComposer;
import com.skeletor.plugin.override_packets.outgoing.JavascriptCallbackComposer;

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
            RoomPlaylist roomPlaylist = RoomAudioManager.getInstance().getPlaylistForRoom(room.getId());
            roomPlaylist.removeSong(message.index);
            room.sendComposer(new JavascriptCallbackComposer(new RemoveSongComposer(message.index)).compose());
        }
    }

    public static class JSONRemoveSongEvent {
        public int index;
    }
}
