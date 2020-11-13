package com.skeletor.plugin.javascript.communication.outgoing.common;

import com.google.gson.JsonPrimitive;
import com.skeletor.plugin.javascript.communication.outgoing.OutgoingWebMessage;

public class MentionComposer extends OutgoingWebMessage {
    public MentionComposer(String sender, String message) {
        super("mention");
        this.data.add("sender", new JsonPrimitive(sender));
        this.data.add("message", new JsonPrimitive(message));
    }
}
