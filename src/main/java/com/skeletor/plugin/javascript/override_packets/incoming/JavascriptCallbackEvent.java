package com.skeletor.plugin.javascript.override_packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.skeletor.plugin.javascript.JSPlugin;

public class JavascriptCallbackEvent extends MessageHandler {
    @Override
    public void handle(){
        String payload = this.packet.readString();
        JSPlugin.getInstance().getCommunicationManager().onMessage(payload, this.client);
    }
}
