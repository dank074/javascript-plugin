package com.skeletor.plugin;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.CommandHandler;
import com.eu.habbo.habbohotel.items.ItemInteraction;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.HabboPlugin;
import com.eu.habbo.plugin.events.emulator.EmulatorLoadItemsManagerEvent;
import com.eu.habbo.plugin.events.emulator.EmulatorLoadedEvent;
import com.eu.habbo.plugin.events.rooms.RoomUncachedEvent;
import com.eu.habbo.plugin.events.users.*;
import com.skeletor.plugin.audio.RoomAudioManager;
import com.skeletor.plugin.audio.RoomPlaylist;
import com.skeletor.plugin.commands.CmdCommand;
import com.skeletor.plugin.commands.YoutubeCommand;
import com.skeletor.plugin.communication.CommunicationManager;
import com.skeletor.plugin.communication.outgoing.audio.DisposePlaylistComposer;
import com.skeletor.plugin.communication.outgoing.audio.PlaySongComposer;
import com.skeletor.plugin.communication.outgoing.audio.PlaylistComposer;
import com.skeletor.plugin.communication.outgoing.audio.PlayStopComposer;
import com.skeletor.plugin.communication.outgoing.common.SessionDataComposer;
import com.skeletor.plugin.communication.outgoing.common.UpdateCreditsComposer;
import com.skeletor.plugin.interactions.InteractionSlotMachine;
import com.skeletor.plugin.interactions.InteractionYoutubeJukebox;
import com.skeletor.plugin.override_packets.incoming.JavascriptCallbackEvent;
import com.skeletor.plugin.override_packets.outgoing.JavascriptCallbackComposer;

public class main extends HabboPlugin implements EventListener {
    public static final int JSCALLBACKEVENTHEADER = 314;

    public void onEnable () throws Exception {
        Emulator.getPluginManager().registerEvents(this, this);
        if(Emulator.isReady && !Emulator.isShuttingDown) {
            this.onEmulatorLoadedEvent(null);
        }
    }

    @EventHandler
    public void onEmulatorLoadedEvent ( EmulatorLoadedEvent e ) throws Exception {
        //register incoming message
        Emulator.getGameServer().getPacketManager().registerHandler(JSCALLBACKEVENTHEADER, JavascriptCallbackEvent.class);

        //register commands
        CommandHandler.addCommand(new YoutubeCommand());
        CommandHandler.addCommand(new CmdCommand());

        // youtube audio player
        RoomAudioManager.Init();
    }

    @EventHandler
    public void onLoadItemsManager(EmulatorLoadItemsManagerEvent e) {
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("slots_machine", InteractionSlotMachine.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("yt_jukebox", InteractionYoutubeJukebox.class));
    }

    @EventHandler
    public void onUserEnterRoomEvent(UserEnterRoomEvent e) {
        RoomPlaylist playlist = RoomAudioManager.getInstance().getPlaylistForRoom(e.room.getId());
        // here send the playlist to the user
        if(playlist.getPlaylist().size() > 0) {
            e.habbo.getClient().sendResponse(new JavascriptCallbackComposer(new PlaylistComposer(playlist)));
            if(playlist.isPlaying()) {
                e.habbo.getClient().sendResponse(new JavascriptCallbackComposer(new PlaySongComposer(playlist.getCurrentIndex())));
                e.habbo.getClient().sendResponse(playlist.getNowPlayingBubbleAlert());
            }
        }
    }

    @EventHandler
    public void onUserExitRoomEvent(UserExitRoomEvent e) {
        // here empty the playlist
        e.habbo.getClient().sendResponse(new JavascriptCallbackComposer(new PlayStopComposer(false)));
        e.habbo.getClient().sendResponse(new JavascriptCallbackComposer(new DisposePlaylistComposer()));
    }

    @EventHandler
    public void onRoomUncachedEvent(RoomUncachedEvent e) {
        RoomAudioManager.getInstance().dispose(e.room.getId());
    }

    /* Trash
    @EventHandler
    public void onUserTalkEvent(UserTalkEvent e) {
        String userMentioned = RegexUtility.getUserMentionedFromChat(e.chatMessage.getMessage());
        if(!userMentioned.isEmpty()) {
            Habbo habbo = Emulator.getGameEnvironment().getHabboManager().getHabbo(userMentioned);

            if (habbo != null) {
                MentionComposer msg = new MentionComposer(e.habbo.getHabboInfo().getUsername(), e.chatMessage.getMessage());
                habbo.getClient().sendResponse(new JavascriptCallbackComposer(msg));
                e.habbo.whisper("You mentioned " + userMentioned, RoomChatMessageBubbles.ALERT);
            }
        }
    }
    */
    @EventHandler
    public void onUserLoginEvent(UserLoginEvent e) {
        SessionDataComposer sessionDataComposer = new SessionDataComposer(e.habbo.getHabboInfo().getId(), e.habbo.getHabboInfo().getUsername(), e.habbo.getHabboInfo().getLook(), e.habbo.getHabboInfo().getCredits());
        e.habbo.getClient().sendResponse(new JavascriptCallbackComposer(sessionDataComposer));
    }

    @EventHandler
    public void onUserCreditsEvent(UserCreditsEvent e) {
        UpdateCreditsComposer creditsComposer = new UpdateCreditsComposer(e.habbo.getHabboInfo().getCredits());
        e.habbo.getClient().sendResponse(new JavascriptCallbackComposer(creditsComposer));
    }

    @Override
    public void onDisable() throws Exception {
        CommunicationManager.getInstance().Dispose();
        RoomAudioManager.getInstance().Dispose();
    }

    public boolean hasPermission(Habbo habbo, String s) {
        return false;
    }
}
