package com.skeletor.plugin.communication.incoming.audio;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.skeletor.plugin.audio.RoomAudioManager;
import com.skeletor.plugin.audio.RoomPlaylist;
import com.skeletor.plugin.communication.incoming.IncomingWebMessage;
import com.skeletor.plugin.communication.outgoing.audio.AddSongComposer;
import com.skeletor.plugin.override_packets.outgoing.JavascriptCallbackComposer;

public class AddSongEvent extends IncomingWebMessage<AddSongEvent.JSONAddSong> {

    public AddSongEvent() {
        super(JSONAddSong.class);
    }

    @Override
    public void handle(GameClient client, JSONAddSong message) {
        Room currentRoom = client.getHabbo().getHabboInfo().getCurrentRoom();
        if(currentRoom == null)
            return;
        if(currentRoom.hasRights(client.getHabbo())) {
            RoomPlaylist playlist= RoomAudioManager.getInstance().getPlaylistForRoom(currentRoom.getId());
            RoomPlaylist.YoutubeVideo song = new RoomPlaylist.YoutubeVideo(message.name, message.videoId, message.channel);
            playlist.addSong(song);
            AddSongComposer addSongComposer = new AddSongComposer(song);
            currentRoom.sendComposer(new JavascriptCallbackComposer(addSongComposer).compose());
        }
    }

    public static class JSONAddSong {
        public String name;
        public String videoId;
        public String channel;
    }
}
