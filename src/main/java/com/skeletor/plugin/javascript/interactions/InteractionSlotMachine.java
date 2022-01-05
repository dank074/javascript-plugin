package com.skeletor.plugin.javascript.interactions;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.InteractionDefault;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomTile;
import com.eu.habbo.habbohotel.rooms.RoomUnit;
import com.skeletor.plugin.javascript.communication.outgoing.slotmachine.SlotMachineComposer;
import com.skeletor.plugin.javascript.override_packets.outgoing.JavascriptCallbackComposer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InteractionSlotMachine extends InteractionDefault {
    public InteractionSlotMachine(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public InteractionSlotMachine(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, userId, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public boolean canWalkOn(RoomUnit roomUnit, Room room, Object[] objects) {
        return false;
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public void onWalk(RoomUnit roomUnit, Room room, Object[] objects) throws Exception {

    }

    @Override
    public void onClick(GameClient client, Room room, Object[] objects) throws Exception {
        RoomTile tile = room.getLayout().getTile(this.getX(), this.getY());
        RoomTile tileInFront = room.getLayout().getTileInFront(tile, this.getRotation());

        if(client.getHabbo().getRoomUnit().getCurrentLocation().is(tileInFront.x, tileInFront.y)) {
            super.onClick(client, room, objects);
            SlotMachineComposer webComposer = new SlotMachineComposer(this.getId(), client.getHabbo().getHabboInfo().getCredits());
            client.sendResponse(new JavascriptCallbackComposer(webComposer));
        }

    }
}
