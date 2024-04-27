package org.alexdev.http.dao.housekeeping;

import org.alexdev.havana.dao.Storage;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.player.PlayerDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HousekeepingPlayerDao {

    public static List<PlayerDetails> getPlayers(int page, boolean zeroCoinsFlag, String sortBy) {
        List<PlayerDetails> players = new ArrayList<>();

        int rows = 25;
        int nextOffset = page * rows;

        if (nextOffset >= 0) {
            Connection sqlConnection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try {
                sqlConnection = Storage.getStorage().getConnection();

                String statement = "";

                if (zeroCoinsFlag) {
                    statement += " AND credits = 0 ";
                }

                preparedStatement = Storage.getStorage().prepare("SELECT * FROM users WHERE username <> '' " + statement + "ORDER BY " + sortBy + " DESC LIMIT ? OFFSET ?", sqlConnection);
                preparedStatement.setInt(1, rows);
                preparedStatement.setInt(2, nextOffset);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    PlayerDetails playerDetails = new PlayerDetails();
                    PlayerDao.fill(playerDetails, resultSet);

                    players.add(playerDetails);
                }

            } catch (Exception e) {
                Storage.logError(e);
            } finally {
                Storage.closeSilently(resultSet);
                Storage.closeSilently(preparedStatement);
                Storage.closeSilently(sqlConnection);
            }
        }

        return players;
    }

    public static List<PlayerDetails> search(String type, String field, String input) {
        List<PlayerDetails> players = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Storage.getStorage().getConnection();

            if (type.equals("contains")) {
                preparedStatement = Storage.getStorage().prepare("SELECT * FROM users WHERE " + field + " LIKE ?", sqlConnection);
                preparedStatement.setString(1, "%" + input + "%");
            } else if (type.equals("starts_with")) {
                preparedStatement = Storage.getStorage().prepare("SELECT * FROM users WHERE " + field + " LIKE ?", sqlConnection);
                preparedStatement.setString(1, input + "%");
            } else if (type.equals("ends_with")) {
                preparedStatement = Storage.getStorage().prepare("SELECT * FROM users WHERE " + field + " LIKE ?", sqlConnection);
                preparedStatement.setString(1, "%" + input);
            } else {
                preparedStatement = Storage.getStorage().prepare("SELECT * FROM users WHERE " + field + " = ?", sqlConnection);
                preparedStatement.setString(1, input);
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                PlayerDetails playerDetails = new PlayerDetails();
                PlayerDao.fill(playerDetails, resultSet);

                players.add(playerDetails);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return players;
    }

    public static void logLogin(int userId, String username, String ipAddress) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = sqlConnection.prepareStatement("INSERT INTO housekeeping_login_log (user_id, username, login_time, ip_address) VALUES (?, ?, ?, ?)");
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, username);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String formattedTimestamp = dateFormat.format(new Timestamp(System.currentTimeMillis()));

            preparedStatement.setString(3, formattedTimestamp);
            preparedStatement.setString(4, ipAddress);

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static void unbanUser(int userId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = sqlConnection.prepareStatement("UPDATE users_bans SET is_active = 0 WHERE banned_value = CONVERT(?, CHAR)");
            preparedStatement.setInt(1, userId);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                // Éxito: El registro se marcó como "Picked Up"
            } else {
                // Error: El registro no se actualizó, maneja el error apropiadamente
            }
        } catch (Exception e) {
            // Maneja los errores adecuadamente
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static List<Map<String, Object>> getAllRanks() {
        List<Map<String, Object>> allRanksList = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare(
                    "SELECT * FROM ranks ORDER BY id ASC", sqlConnection);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Map<String, Object> ranks = new HashMap<>();
                ranks.put("id", resultSet.getInt("id"));
                ranks.put("name", resultSet.getString("name"));
                ranks.put("badge", resultSet.getString("badge"));
                ranks.put("description", resultSet.getString("description"));

                allRanksList.add(ranks);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return allRanksList;
    }
}
