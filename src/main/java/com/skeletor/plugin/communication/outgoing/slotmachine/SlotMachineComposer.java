package com.skeletor.plugin.communication.outgoing.slotmachine;

import com.google.gson.JsonPrimitive;
import com.skeletor.plugin.communication.outgoing.OutgoingWebMessage;

public class SlotMachineComposer extends OutgoingWebMessage {
    public SlotMachineComposer(int itemId, int credits) {
        super("slot_machine");
        this.data.add("itemId", new JsonPrimitive(itemId));
        this.data.add("credits", new JsonPrimitive(credits));
    }
}
