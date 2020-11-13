package com.skeletor.plugin.javascript.communication;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.skeletor.plugin.javascript.communication.incoming.IncomingWebMessage;
import com.skeletor.plugin.javascript.communication.incoming.audio.*;
import com.skeletor.plugin.javascript.communication.incoming.common.MoveAvatarEvent;
import com.skeletor.plugin.javascript.communication.incoming.common.RequestCreditsEvent;
import com.skeletor.plugin.javascript.communication.incoming.common.RequestSpinSlotMachineEvent;
import com.skeletor.plugin.javascript.utils.JsonFactory;
import gnu.trove.map.hash.THashMap;

public class CommunicationManager {
    private final THashMap<String, Class<? extends IncomingWebMessage>> incomingMessages;

    public CommunicationManager() {
        this.incomingMessages = new THashMap<>();
        initializeMessages();
    }

    public void initializeMessages() {
        this.registerMessage("move_avatar", MoveAvatarEvent.class);
        this.registerMessage("request_credits", RequestCreditsEvent.class);
        this.registerMessage("spin_slot_machine", RequestSpinSlotMachineEvent.class);
        this.registerMessage("add_song", AddSongEvent.class);
        this.registerMessage("next_song", NextSongEvent.class);
        this.registerMessage("prev_song", PreviousSongEvent.class);
        this.registerMessage("play_stop", PlayStopEvent.class);
        this.registerMessage("remove_song", RemoveSongEvent.class);
        this.registerMessage("song_ended", SongEndedEvent.class);
    }

    public void registerMessage(String key, Class<? extends IncomingWebMessage> message) {
        this.incomingMessages.put(key, message);
    }

    public THashMap<String, Class<? extends IncomingWebMessage>> getIncomingMessages() {
        return this.incomingMessages;
    }

    public void onMessage(String jsonPayload, GameClient sender) {
        try {
            IncomingWebMessage.JSONIncomingEvent heading = JsonFactory.getInstance().fromJson(jsonPayload, IncomingWebMessage.JSONIncomingEvent.class);
            Class<? extends IncomingWebMessage> message = this.getIncomingMessages().get(heading.header);
            IncomingWebMessage webEvent = message.getDeclaredConstructor().newInstance();
            webEvent.handle(sender, JsonFactory.getInstance().fromJson(heading.data.toString(), webEvent.type));
        } catch(Exception e) {
            Emulator.getLogging().logUndefinedPacketLine("unknown message: " + jsonPayload);
        }
    }

    public void dispose() {
        incomingMessages.clear();
    }
}
