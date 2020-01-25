package com.skeletor.plugin;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.CommandHandler;
import com.eu.habbo.habbohotel.items.ItemInteraction;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.HabboPlugin;
import com.eu.habbo.plugin.events.emulator.EmulatorLoadItemsManagerEvent;
import com.eu.habbo.plugin.events.emulator.EmulatorLoadedEvent;
import com.eu.habbo.plugin.events.users.UserCreditsEvent;
import com.eu.habbo.plugin.events.users.UserLoginEvent;
import com.eu.habbo.plugin.events.users.UserTalkEvent;
import com.skeletor.plugin.commands.YoutubeCommand;
import com.skeletor.plugin.communication.CommunicationManager;
import com.skeletor.plugin.communication.outgoing.common.MentionComposer;
import com.skeletor.plugin.communication.outgoing.common.SessionDataComposer;
import com.skeletor.plugin.communication.outgoing.common.UpdateCreditsComposer;
import com.skeletor.plugin.interactions.InteractionSlotMachine;
import com.skeletor.plugin.override_packets.incoming.JavascriptCallbackEvent;
import com.skeletor.plugin.override_packets.outgoing.JavascriptCallbackComposer;
import com.skeletor.plugin.utils.RegexUtility;

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
    }

    @EventHandler
    public void onLoadItemsManager(EmulatorLoadItemsManagerEvent e) {
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("slots_machine", InteractionSlotMachine.class));
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
    }

    public boolean hasPermission(Habbo habbo, String s) {
        return false;
    }
}
