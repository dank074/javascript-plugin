package com.skeletor.plugin.communication.outgoing.common;

import com.google.gson.JsonPrimitive;
import com.skeletor.plugin.communication.outgoing.OutgoingWebMessage;

public class MentionComposer extends OutgoingWebMessage {
    public MentionComposer(String sender, String message) {
        super("mention");
        this.data.add("sender", new JsonPrimitive(sender));
        this.data.add("message", new JsonPrimitive(message));
    }
}
