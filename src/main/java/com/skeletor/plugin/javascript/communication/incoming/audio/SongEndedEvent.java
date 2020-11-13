package com.skeletor.plugin.javascript.communication.incoming.audio;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.skeletor.plugin.javascript.JSPlugin;
import com.skeletor.plugin.javascript.audio.RoomPlaylist;
import com.skeletor.plugin.javascript.communication.incoming.IncomingWebMessage;
import com.skeletor.plugin.javascript.communication.outgoing.audio.PlaySongComposer;
import com.skeletor.plugin.javascript.override_packets.outgoing.JavascriptCallbackComposer;

public class SongEndedEvent extends IncomingWebMessage<SongEndedEvent.JSONSongEndedEvent> {

    public SongEndedEvent() {
        super(JSONSongEndedEvent.class);
    }

    @Override
    public void handle(GameClient client, JSONSongEndedEvent message) {
        Room room = client.getHabbo().getHabboInfo().getCurrentRoom();
        if(room == null)
            return;

        if(room.hasRights(client.getHabbo())) {
            RoomPlaylist playlist = JSPlugin.getInstance().getRoomAudioManager().getPlaylistForRoom(room.getId());
            if(playlist.getCurrentIndex() == message.currentIndex) {
                playlist.nextSong();
                playlist.setPlaying(true);
                PlaySongComposer playSongComposer = new PlaySongComposer(playlist.getCurrentIndex());
                room.sendComposer(new JavascriptCallbackComposer(playSongComposer).compose());
                room.sendComposer(playlist.getNowPlayingBubbleAlert().compose());
            }
        }
    }

    static class JSONSongEndedEvent {
        public int currentIndex;
    }
}
