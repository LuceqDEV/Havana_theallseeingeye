package org.alexdev.havana.messages.incoming.rooms;

import org.alexdev.havana.game.ads.AdManager;
import org.alexdev.havana.game.ads.Advertisement;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.outgoing.rooms.ROOMAD;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.config.GameConfiguration;

public class GETROOMAD implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        player.send(new ROOMAD("", ""));

       /* if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        String image = null;
        String url = null;

        */

        //if (room.isPublicRoom()) {
                        /*image = GameConfiguration.getInstance().getString("advertisement.api");
            image = image.replace("{roomId}", String.valueOf(room.getId()));
            image = image.replace("{pictureName}", advertisement.getImage());*/
            /*
            Advertisement advertisement = AdManager.getInstance().getRandomAd(room.getId());

            if (advertisement != null) {


                if (advertisement.getImage() != null) {
                    image = GameConfiguration.getInstance().getString("site.path").replace("https", "http") + "/api/advertisement/get_img?ad=" + advertisement.getId();
                }

                if (advertisement.getUrl() != null) {
                    url = GameConfiguration.getInstance().getString("site.path").replace("https", "http") + "/api/advertisement/get_url?ad=" + advertisement.getId();
                }
            }
             */
        //}

        /*
        if (!GameConfiguration.getInstance().getBoolean("room.ads")) {
            image = null;
            url = null;
        }

        player.send(new ROOMAD(image, url));
         */

        /*player.send(new MessageComposer() {
            @Override
            public void compose(NettyResponse response) {
                response.writeString("http://localhost/api/get_ad?roomId=123&picture=ad_lido_L.gif");
                response.writeString("http://localhost/");
            }

            @Override
            public short getHeader() {
                return 208;
            }
        });*/

        /*          response.writeString("http://localhost/c_images/billboards/getad.php?picture=ad_lido_L.gif");
                response.writeString("http://localhost/");*/
    }
}
