package com.skeletor.plugin.javascript.audio;

import java.util.concurrent.ConcurrentHashMap;

public class RoomAudioManager {
    private ConcurrentHashMap<Integer, RoomPlaylist> roomAudio;

    public RoomAudioManager() {
        this.roomAudio = new ConcurrentHashMap<>();
    }

    public RoomPlaylist getPlaylistForRoom(int roomId) {
        if(roomAudio.containsKey(roomId))
            return roomAudio.get(roomId);

        RoomPlaylist newPlaylist = new RoomPlaylist();
        this.roomAudio.put(roomId, newPlaylist);
        return newPlaylist;
    }

    public void dispose(int roomId) {
        this.roomAudio.remove(roomId);
    }

    public void dispose() {
        this.roomAudio.clear();
    }
}
