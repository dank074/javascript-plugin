package com.skeletor.plugin.javascript.communication.outgoing.common;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.google.gson.JsonArray;
import com.skeletor.plugin.javascript.communication.outgoing.OutgoingWebMessage;

import java.util.List;

public class CommandsComposer extends OutgoingWebMessage {
    public CommandsComposer(List<Command> commands) {
        super("commands");
        JsonArray json_cmd = new JsonArray();
        for (Command c : commands) {
            json_cmd.add(Emulator.getTexts().getValue("commands.description." + c.permission, "commands.description." + c.permission));
        }
        this.data.add("commands", json_cmd);
    }
}
