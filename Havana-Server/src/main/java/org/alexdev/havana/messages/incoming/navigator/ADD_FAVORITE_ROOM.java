package org.alexdev.havana.messages.incoming.navigator;

import org.alexdev.havana.dao.mysql.RoomFavouritesDao;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.outgoing.rooms.FAVOURITE_ROOM_EVENT_FLASH;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.List;

public class ADD_FAVORITE_ROOM implements MessageEvent {
    public static final int MAX_FAVOURITES = 30;

    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        int roomType = reader.readInt();
        int roomId = reader.readInt();

        if (roomType == 1) {
            roomId = (roomId - RoomManager.PUBLIC_ROOM_OFFSET);
        }

        Room room = RoomManager.getInstance().getRoomById(roomId);

        if (room == null) {
            return; // Room was null, ignore request
        }

        List<Room> favouritesList = RoomManager.getInstance().getFavouriteRooms(player.getDetails().getId(), false);

        for (Room favroom : favouritesList) {
            if (favroom.getId() == roomId) {
                return; // Room already added, ignore request
            }
        }

        if (RoomManager.getInstance().getRoomById(roomId) == null) {
            return;
        }

        // Only count private rooms since there's a limited number of public rooms
        int finalRoomId = roomId;

        var privateFavouriteRooms = RoomManager.getInstance().getFavouriteRooms(player.getDetails().getId(), false);
        if (privateFavouriteRooms.size() >= MAX_FAVOURITES || privateFavouriteRooms.stream().anyMatch(r -> r.getId() == finalRoomId)) {
            System.out.println("Has too many favourite rooms");
            //player.send(new FLASH_ADD_FAVOURITE_FAILED());
            return;
        }

        if(player.flash) {
            player.send(new FAVOURITE_ROOM_EVENT_FLASH(room.getId(), true));
        }

        RoomFavouritesDao.addFavouriteRoom(player.getDetails().getId(), roomId);
    }
}
