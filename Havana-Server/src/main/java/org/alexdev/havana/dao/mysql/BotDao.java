package org.alexdev.havana.dao.mysql;

import org.alexdev.havana.dao.Storage;
import org.alexdev.havana.game.bot.BotData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BotDao {

    public static String getFlashFigure(String botName) {
        String result = null;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT figure_flash FROM rooms_bots WHERE name = ?", sqlConnection);
            preparedStatement.setString(1, botName);
            resultSet =  preparedStatement.executeQuery();

            while (resultSet.next()) {
                result = resultSet.getString("figure_flash");
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
            Storage.closeSilently(resultSet);
        }

        return result;
    }

    public static List<BotData> getBotData(int roomId) {
        List<BotData> botData = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM rooms_bots WHERE room_id = ?", sqlConnection);
            preparedStatement.setInt(1, roomId);
            resultSet =  preparedStatement.executeQuery();

            while (resultSet.next()) {
                //a(String name, String mission, int x, int y, String headRotation, String bodyRotation, String figure, String walkspace) {
                botData.add(new BotData(
                        resultSet.getString("name"),
                        resultSet.getString("mission"),
                        resultSet.getInt("x"),
                        resultSet.getInt("y"),
                        Integer.parseInt(resultSet.getString("start_look").split(",")[0]),
                        Integer.parseInt(resultSet.getString("start_look").split(",")[1]),
                        resultSet.getString("figure"),
                        resultSet.getString("walkspace"),
                        resultSet.getString("speech"),
                        resultSet.getString("response"),
                        resultSet.getString("unrecognised_response"),
                        resultSet.getString("hand_items")
                ));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
            Storage.closeSilently(resultSet);
        }

        return botData;
    }
}
