package com.skeletor.plugin.javascript.commands;

import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.skeletor.plugin.javascript.communication.outgoing.OutgoingWebMessage;
import com.skeletor.plugin.javascript.communication.outgoing.common.YoutubeTVComposer;
import com.skeletor.plugin.javascript.override_packets.outgoing.JavascriptCallbackComposer;
import com.skeletor.plugin.javascript.utils.RegexUtility;

public class YoutubeCommand extends Command {
    public YoutubeCommand(){
        super("cmd_youtube", new String[] {"youtube"});
    }
    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception {
        Room room = gameClient.getHabbo().getHabboInfo().getCurrentRoom();

        if(room.hasRights(gameClient.getHabbo()) || gameClient.getHabbo().getHabboInfo().getRank().getName().equals("VIP")) {
            if (strings.length > 1) {
                String videoId = RegexUtility.getYouTubeId(strings[1]);
                if(videoId.isEmpty()) {
                    gameClient.getHabbo().whisper("Invalid youtube url");
                    return true;
                }
                OutgoingWebMessage webMsg = new YoutubeTVComposer(videoId);
                room.sendComposer(new JavascriptCallbackComposer(webMsg).compose());
                return true;
            }
        }
        gameClient.getHabbo().whisper("You do not have permission to use this command in this room");
        return true;
    }
}
