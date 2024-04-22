package by.javaguru.je.jdbc;

import by.javaguru.je.jdbc.dao.TicketDao;
import by.javaguru.je.jdbc.utils.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcRunner {


    public static void main(String[] args) throws SQLException {
        TicketDao ticketDao = new TicketDao();
//        ticketDao.updateDataOrRollback(12, "2022-05-10 07:15:00.000000", "2023-09-09 19:15:00.000000");
        ticketDao.updatePassengerNameFromTableTicket(5, "Андрей Сидоров");
    }
}
