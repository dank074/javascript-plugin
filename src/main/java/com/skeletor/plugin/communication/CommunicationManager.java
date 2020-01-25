package com.skeletor.plugin.communication;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.skeletor.plugin.communication.incoming.IncomingWebMessage;
import com.skeletor.plugin.communication.incoming.common.MoveAvatarEvent;
import com.skeletor.plugin.communication.incoming.common.RequestCreditsEvent;
import com.skeletor.plugin.communication.incoming.common.RequestSpinSlotMachineEvent;
import com.skeletor.plugin.utils.JsonFactory;
import gnu.trove.map.hash.THashMap;

public class CommunicationManager {
    private static CommunicationManager instance;
    static {
        try {
            instance = new CommunicationManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    };
    private final THashMap<String, Class<? extends IncomingWebMessage>> _incomingMessages;

    public CommunicationManager() {
        this._incomingMessages = new THashMap<>();
        initializeMessages();
    }

    public void initializeMessages() {
        this.registerMessage("move_avatar", MoveAvatarEvent.class);
        this.registerMessage("request_credits", RequestCreditsEvent.class);
        this.registerMessage("spin_slot_machine", RequestSpinSlotMachineEvent.class);
    }

    public void registerMessage(String key, Class<? extends IncomingWebMessage> message) {
        this._incomingMessages.put(key, message);
    }

    public THashMap<String, Class<? extends IncomingWebMessage>> getIncomingMessages() {
        return this._incomingMessages;
    }

    public static CommunicationManager getInstance(){
        if (instance == null) {
            try {
                instance = new CommunicationManager();
            } catch (Exception e) {
                Emulator.getLogging().logErrorLine(e.getMessage());
            }
        }
        return instance;
    }

    public void OnMessage(String jsonPayload, GameClient sender) {
        try {
            IncomingWebMessage.JSONIncomingEvent heading = JsonFactory.getInstance().fromJson(jsonPayload, IncomingWebMessage.JSONIncomingEvent.class);
            Class<? extends IncomingWebMessage> message = CommunicationManager.getInstance().getIncomingMessages().get(heading.header);
            IncomingWebMessage webEvent = message.getDeclaredConstructor().newInstance();
            webEvent.handle(sender, JsonFactory.getInstance().fromJson(heading.data.toString(), webEvent.type));
        } catch(Exception e) {
            Emulator.getLogging().logUndefinedPacketLine("unknown message: " + jsonPayload);
        }
    }

    public void Dispose() {
        _incomingMessages.clear();
        instance = null;
    }
}
