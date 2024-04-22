package by.javaguru.je.jdbc.dao;

import by.javaguru.je.jdbc.entity.Ticket;

import java.sql.*;

import static by.javaguru.je.jdbc.utils.ConnectionManager.open;

public class TicketDao {

    private static String SELECT_PASS_BY_NAME = """
            SELECT * FROM ticket where passenger_name = ?;
            """;

    private static String SELECT_PASS_BY_ID = """
            SELECT * FROM ticket where id = ?;
            """;
    
    // 1) Возвращает имена встречающиеся чаще всего
    private static String PASSENGER_NAME = """
            SELECT distinct ticket.passenger_name from ticket order by passenger_name desc limit 10;
            """;
    // 2) Возвращает имена пассажиров и сколько билетов пассажир купил за все время
    private static String PASS_NAME_AND_COUNT_TICKET = """
            SELECT passenger_name, count(*) as pass_count
            from ticket
            group by passenger_name order by pass_count desc ;
            """;
    //    3) Обновляет данные в таблице ticket по id
    private static String UPDATE_DATA = """
            update ticket
            set passenger_name = ?
            where id = ?;
            """;


    //    4) Обновляет данные по flight_id в таблицах flight и ticket в одной транзакции (если происходит ошибка, то все операции должны откатиться)
    private static String UPDATE_TABLE_FLIGHT_AND_TICKET = """
        UPDATE flight SET departure_date = ?, arrival_date = ? WHERE id = ?;
        """;

    public void morePopularName() throws SQLException {
        try (Connection connection = open()) {
            PreparedStatement statement = connection.prepareStatement(PASSENGER_NAME);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                System.out.println(result.getString(1));
            }
        }
    }

    public void returnMovePopularTicket() throws SQLException {
        try (Connection connection = open()) {
            PreparedStatement statement = connection.prepareStatement(PASS_NAME_AND_COUNT_TICKET);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                System.out.println("1) name: " + result.getObject(1) + " " + "2) Frequency of purchases: " + result.getObject(2));
            }
        }
    }

    public void updatePassengerNameFromTableTicket(Integer passID, String passName) throws SQLException {
        try (Connection connection = open()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_DATA);
            statement.setString(1, passName);
            statement.setInt(2, passID);
            statement.execute();

            selectPassengerByName(passName);
        }
    }

    private void selectPassengerByName(String passName) {
        try (Connection connection = open()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_PASS_BY_NAME);
            statement.setString(1, passName);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Ticket tickt = new Ticket().set(result);
                System.out.println(tickt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void selectPassengerByID(Integer passId) {
        try (Connection connection = open()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_PASS_BY_ID);
            statement.setInt(1, passId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Ticket tickt = new Ticket().set(result);
                System.out.println(tickt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDataOrRollback(Integer id, String arrival_date, String departure_date) throws SQLException {
        Connection connection = null;
        try {
            connection = open();
            PreparedStatement statement = connection.prepareStatement(UPDATE_TABLE_FLIGHT_AND_TICKET);
            statement.setString(1, departure_date);
            statement.setString(2, arrival_date);
            statement.setInt(3, id);


            selectPassengerByID(id);
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

}
