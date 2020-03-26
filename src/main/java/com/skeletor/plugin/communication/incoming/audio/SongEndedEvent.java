package com.skeletor.plugin.communication.incoming.audio;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.skeletor.plugin.audio.RoomAudioManager;
import com.skeletor.plugin.audio.RoomPlaylist;
import com.skeletor.plugin.communication.incoming.IncomingWebMessage;
import com.skeletor.plugin.communication.outgoing.audio.PlaySongComposer;
import com.skeletor.plugin.override_packets.outgoing.JavascriptCallbackComposer;

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
            RoomPlaylist playlist = RoomAudioManager.getInstance().getPlaylistForRoom(room.getId());
            if(playlist.getCurrentIndex() == message.currentIndex) {
                playlist.nextSong();
                playlist.setPlaying(true);
                PlaySongComposer playSongComposer = new PlaySongComposer(playlist.getCurrentIndex());
                room.sendComposer(new JavascriptCallbackComposer(playSongComposer).compose());
                room.sendComposer(playlist.getNowPlayingBubbleAlert().compose());
            }
        }
    }

    class JSONSongEndedEvent {
        public int currentIndex;
    }
}
