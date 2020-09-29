package com.skeletor.plugin.audio;

import java.util.concurrent.ConcurrentHashMap;

public class RoomAudioManager {
    private static RoomAudioManager _instance;
    private ConcurrentHashMap<Integer, RoomPlaylist> roomAudio;

    RoomAudioManager() {
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

    public static void Init() {
        _instance = new RoomAudioManager();
    }

    public void Dispose() {
        this.roomAudio.clear();
    }

    public static RoomAudioManager getInstance() {
        return _instance;
    }
}
