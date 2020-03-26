package com.skeletor.plugin.audio;

import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import gnu.trove.map.hash.THashMap;

import java.util.ArrayList;

public class RoomPlaylist {
    private ArrayList<YoutubeVideo> playlist;
    private int current;
    private boolean playing;

    RoomPlaylist() {
        playlist = new ArrayList<>();
        current = 0;
        playing = false;
    }

    public YoutubeVideo nextSong() {
        if(current < playlist.size() - 1)
            this.current++;
        else
            this.current = 0;
        return playlist.get(current);
    }

    public YoutubeVideo prevSong() {
        if(current > 0) {
            current--;
        }
        return playlist.get(current);
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public void addSong(YoutubeVideo song) {
        playlist.add(song);
    }

    public YoutubeVideo removeSong(int index) {
        YoutubeVideo res = null;
        if(playlist.size() - 1 >= index)
            res = this.playlist.remove(index);
        if(playlist.size() == 0) this.setPlaying(false);
        if(index == this.getCurrentIndex()) {
            if(index > this.playlist.size() - 1 && this.playlist.size() > 0) {
                this.current = this.playlist.size() - 1;
            }
        }
        else if(index < this.getCurrentIndex() && this.getCurrentIndex() > 0) {
            this.current--;
        }
        return res;
    }

    public YoutubeVideo getCurrentSong() {
        return playlist.get(current);
    }

    public int getCurrentIndex() {
        return current;
    }

    public ArrayList<YoutubeVideo> getPlaylist() {
        return playlist;
    }

    public static class YoutubeVideo {
        public String name;
        public String videoId;
        public String channel;

        public YoutubeVideo(String name, String videoId, String channel) {
            this.name = name;
            this.videoId = videoId;
            this.channel = channel;
        }
    }

    public MessageComposer getNowPlayingBubbleAlert() {
        final THashMap<String, String> keys = (THashMap<String, String>)new THashMap();
        keys.put("display", "BUBBLE");
        keys.put("image", ("${image.library.url}notifications/music.png"));
        keys.put("message", "Now playing " + this.getCurrentSong().name);
       return new BubbleAlertComposer("", (THashMap)keys);
    }
}
