package com.skeletor.plugin.override_packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.skeletor.plugin.communication.outgoing.OutgoingWebMessage;
import com.skeletor.plugin.utils.JsonFactory;

public class JavascriptCallbackComposer extends MessageComposer {
    private OutgoingWebMessage webMessage;

    public JavascriptCallbackComposer(OutgoingWebMessage message) {
        this.webMessage = message;
    }

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.NuxAlertComposer);
        //replace the / char so the string doesnt get cutoff by the swf
        String jsonMessage = JsonFactory.getInstance().toJson(webMessage).replace("/", "&#47;");
        this.response.appendString("habblet/open/" + jsonMessage);
        return this.response;
    }
}
