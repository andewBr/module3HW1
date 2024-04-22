package by.javaguru.je.jdbc.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Ticket {

    private Integer id;
    private String passportNo;
    private String passengerName;
    private long flightId;
    private String seatNo;
    private Long cost;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public long getFlightId() {
        return flightId;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Tickt{" +
                "id=" + id +
                ", passportNo='" + passportNo + '\'' +
                ", passengerName='" + passengerName + '\'' +
                ", flightId=" + flightId +
                ", seatNo='" + seatNo + '\'' +
                ", cost=" + cost +
                '}';
    }

    public Ticket set(ResultSet result) throws SQLException {
        Ticket tickt = new Ticket();
        tickt.setId(result.getInt("id"));
        tickt.setPassportNo(result.getString("passport_no"));
        tickt.setPassengerName(result.getString("passenger_name"));
        tickt.setFlightId(result.getLong("flight_id"));
        tickt.setSeatNo(result.getString("seat_no"));
        tickt.setCost(result.getLong("cost"));
        return tickt;
    }
}
