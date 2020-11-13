package com.skeletor.plugin.javascript.communication.incoming.common;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomTile;
import com.skeletor.plugin.javascript.communication.incoming.IncomingWebMessage;

public class MoveAvatarEvent extends IncomingWebMessage<MoveAvatarEvent.JSONMoveAvatarEvent> {
    private static final short DEFAULT_WALK_AMOUNT = 1;

    public MoveAvatarEvent() {
        super(JSONMoveAvatarEvent.class);
    }
    @Override
    public void handle(GameClient client, JSONMoveAvatarEvent message) {
        Room room = client.getHabbo().getRoomUnit().getRoom();
        if(room == null)
            return;
        short x = client.getHabbo().getRoomUnit().getGoal().x;
        short y = client.getHabbo().getRoomUnit().getGoal().y;

        switch (message.direction) {
            case "stop":
                //client.getHabbo().getRoomUnit().stopWalking();
                //client.getHabbo().getRoomUnit().removeStatus(RoomUnitStatus.MOVE);
                return;
            case "left":
                y = (short) (y + DEFAULT_WALK_AMOUNT);
                break;
            case "right":
                y = (short) (y - DEFAULT_WALK_AMOUNT);
                break;
            case "up":
                x = (short) (x - DEFAULT_WALK_AMOUNT);
                break;
            case "down":
                x = (short) (x + DEFAULT_WALK_AMOUNT);
                break;
            default: return;
        }

        try {
            RoomTile goal = room.getLayout().getTile(x, y);
            if(goal == null)
                return;
            if (goal.isWalkable() || client.getHabbo().getHabboInfo().getCurrentRoom().canSitOrLayAt(goal.x, goal.y)) {
                if (client.getHabbo().getRoomUnit().getMoveBlockingTask() != null) client.getHabbo().getRoomUnit().getMoveBlockingTask().get();

                client.getHabbo().getRoomUnit().setGoalLocation(goal);
            }
        } catch(Exception e) {
            // do nothing, i guess we can't move there :(
        }
    }

    static class JSONMoveAvatarEvent {
        String direction;
    }
}
