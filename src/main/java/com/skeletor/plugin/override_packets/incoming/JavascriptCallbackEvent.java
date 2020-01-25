package com.skeletor.plugin.override_packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.skeletor.plugin.communication.CommunicationManager;

public class JavascriptCallbackEvent extends MessageHandler {
    @Override
    public void handle(){
        String payload = this.packet.readString();
        CommunicationManager.getInstance().OnMessage(payload, this.client);
    }
}
