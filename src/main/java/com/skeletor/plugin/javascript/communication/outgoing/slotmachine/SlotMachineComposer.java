package com.skeletor.plugin.javascript.communication.outgoing.slotmachine;

import com.google.gson.JsonPrimitive;
import com.skeletor.plugin.javascript.communication.outgoing.OutgoingWebMessage;

public class SlotMachineComposer extends OutgoingWebMessage {
    public SlotMachineComposer(int itemId, int credits) {
        super("slot_machine");
        this.data.add("itemId", new JsonPrimitive(itemId));
        this.data.add("credits", new JsonPrimitive(credits));
    }
}
