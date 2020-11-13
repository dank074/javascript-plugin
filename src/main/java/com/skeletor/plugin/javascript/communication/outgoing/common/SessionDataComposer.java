package com.skeletor.plugin.javascript.communication.outgoing.common;

import com.google.gson.JsonPrimitive;
import com.skeletor.plugin.javascript.communication.outgoing.OutgoingWebMessage;

public class SessionDataComposer extends OutgoingWebMessage {
    public SessionDataComposer(int id, String username, String look, int credits) {
        super("session_data");
        this.data.add("id", new JsonPrimitive(id));
        this.data.add("username", new JsonPrimitive(username));
        this.data.add("look", new JsonPrimitive(look));
        this.data.add("credits", new JsonPrimitive(credits));
    }
}
