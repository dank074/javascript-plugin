package com.skeletor.plugin.javascript.communication.incoming.common;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.messages.outgoing.users.UserCreditsComposer;
import com.skeletor.plugin.javascript.communication.incoming.IncomingWebMessage;
import com.skeletor.plugin.javascript.communication.outgoing.common.UpdateCreditsComposer;
import com.skeletor.plugin.javascript.communication.outgoing.slotmachine.SpinResultComposer;
import com.skeletor.plugin.javascript.override_packets.outgoing.JavascriptCallbackComposer;

public class RequestSpinSlotMachineEvent extends IncomingWebMessage<RequestSpinSlotMachineEvent.JSONRequestSpinSlotMachineEvent> {
    private static final int maxNumber = 5;
    private static final int LEMON = 0;
    private static final int MELON = 1;
    private static final int GRAPES = 2;
    private static final int CHERRY = 3;
    private static final int BAR = 4;

    public RequestSpinSlotMachineEvent() {
        super(JSONRequestSpinSlotMachineEvent.class);
    }

    @Override
    public void handle(GameClient client, JSONRequestSpinSlotMachineEvent message) {
        HabboItem item = client.getHabbo().getRoomUnit().getRoom().getHabboItem(message.itemId);
        if(item == null)
            return;
        if(message.bet <= 0 || message.bet > client.getHabbo().getHabboInfo().getCredits())
            return;

        client.getHabbo().getHabboInfo().addCredits(-message.bet);
        client.sendResponse(new UserCreditsComposer(client.getHabbo()));
        client.getHabbo().talk(Emulator.getTexts().getValue("slot.machines.spin", "* Bets %amount% on Slots Machine *").replace("%amount%", message.bet + ""), RoomChatMessageBubbles.ALERT);
        client.sendResponse(new JavascriptCallbackComposer(new UpdateCreditsComposer(client.getHabbo().getHabboInfo().getCredits())));
        int result1 = Emulator.getRandom().nextInt(maxNumber);
        int result2 = Emulator.getRandom().nextInt(maxNumber);
        int result3 = Emulator.getRandom().nextInt(maxNumber);
        /*
        Lemon 3x	0.008	5	0.04
        Melon 3x	0.008	6	0.048
        Grapes 3x	0.008	10	0.08
        Cherry 3x	0.008	15	0.12
        Bar 3x	    0.008	20	0.16
        Bar 2x	    0.032	4	0.128
        Cherry 2x	0.032	3	0.096
        Cherry 1x	0.128	2	0.256

			0.928


         */
        int amountWon = 0;
        boolean won = false;
        if(result1 == result2 && result2 == result3) {
            won = true;
            switch (result1) {
                case LEMON:
                    amountWon = 5 * message.bet;
                    break;
                case MELON:
                    amountWon = 6 * message.bet;
                    break;
                case GRAPES:
                    amountWon = 10 * message.bet;
                    break;
                case CHERRY:
                    amountWon = 15 * message.bet;
                    break;
                case BAR:
                    amountWon = 20 * message.bet;
                    break;
            }
            client.getHabbo().getHabboInfo().addCredits(amountWon);
        }
        else if(result1 == BAR && result2 == BAR) {
            won = true;
            amountWon = 4 * message.bet;
            client.getHabbo().getHabboInfo().addCredits(amountWon);
        }
        else if(result1 == CHERRY && result2 == CHERRY) {
            won = true;
            amountWon = 3 * message.bet;
            client.getHabbo().getHabboInfo().addCredits(amountWon);
        }
        else if(result1 == CHERRY) {
            won = true;
            amountWon = 2 * message.bet;
            client.getHabbo().getHabboInfo().addCredits(amountWon);
        }
        SpinResultComposer resultComposer = new SpinResultComposer(result1, result2, result3, won, amountWon);
        client.sendResponse(new JavascriptCallbackComposer(resultComposer));
        final int finalAmount = amountWon;
        Emulator.getThreading().run(() -> client.getHabbo().talk(Emulator.getTexts().getValue("slot.machines.won", "* Won %amount% in Slots Machine *").replace("%amount%", finalAmount + ""), RoomChatMessageBubbles.ALERT), 5000);
    }

    static class JSONRequestSpinSlotMachineEvent {
        int itemId;
        int bet;
    }
}
