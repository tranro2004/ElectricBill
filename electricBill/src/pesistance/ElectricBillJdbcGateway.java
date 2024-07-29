package pesistance;

import domain.model.ElectricBill;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElectricBillJdbcGateway implements ElectricBillGateway {

    private static Connection connection;

    public ElectricBillJdbcGateway() throws SQLException, ClassNotFoundException {

        String url = "jdbc:mysql://localhost:3306/electricbill";
        String user = "root";
        String password = "230324";
        Class.forName("com.mysql.cj.jdbc.Driver");

        connection = DriverManager.getConnection(url, user, password);

    }

    @Override
    public void addElectricBill(ElectricBill electricBill) {
        String query = "INSERT INTO ElectricBill (idClient, fullName, person, monthlyElectricly, time, qty, unitPrice, quota, total) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, electricBill.getIdClient());
            stmt.setString(2, electricBill.getFullName());
            stmt.setString(3, electricBill.getPerson());
            stmt.setString(4, electricBill.getMonthlyElectricly());
            stmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));
            stmt.setInt(6, electricBill.getQty());
            stmt.setDouble(7, electricBill.getUnitPrice());
            stmt.setDouble(8, electricBill.getQuota());
            stmt.setDouble(9, electricBill.getTotal());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateElectricBill(ElectricBill electricBill) {
        String query = "UPDATE ElectricBill SET fullName=?, person=?, monthlyElectricly=?, time=?, qty=?, unitPrice=?, quota=?, total=? WHERE idClient=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, electricBill.getFullName());
            stmt.setString(2, electricBill.getPerson());
            stmt.setString(3, electricBill.getMonthlyElectricly());
            stmt.setDate(4, new java.sql.Date(electricBill.getTime().getTime()));
            stmt.setInt(5, electricBill.getQty());
            stmt.setDouble(6, electricBill.getUnitPrice());
            stmt.setDouble(7, electricBill.getQuota());
            stmt.setDouble(8, electricBill.getTotal());
            stmt.setString(9, electricBill.getIdClient());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteElectricBill(String idClient) {
        String query = "DELETE FROM ElectricBill WHERE idClient=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, idClient);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ElectricBill> getElectricBillsByMonth(String month) {
        List<ElectricBill> bills = new ArrayList<>();
        String query = "SELECT * FROM ElectricBill WHERE monthlyElectricly=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, month);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bills.add(mapResultSetToElectricBill(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    @Override
    public ArrayList<ElectricBill> getAllElectricBills() {
        ArrayList<ElectricBill> bills = new ArrayList<>();
        String query = "SELECT * FROM ElectricBill";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                bills.add(mapResultSetToElectricBill(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    @Override
    public List<ElectricBill> findElectricBillsById(String searchIdClient) {
        List<ElectricBill> bills = new ArrayList<>();
        String query = "SELECT * FROM ElectricBill WHERE idClient=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, searchIdClient);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bills.add(mapResultSetToElectricBill(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    @Override
    public Map<String, Integer> calcTotalQuantityCustomer() {
        Map<String, Integer> customerQuantities = new HashMap<>();
        String query = "SELECT person, SUM(qty) as totalQty FROM ElectricBill GROUP BY person";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                customerQuantities.put(rs.getString("person"), rs.getInt("totalQty"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerQuantities;
    }

    @Override
    public Double avgTotalForeigner() {
        Double avgTotal = 0.0;
        String query = "SELECT AVG(total) as avgTotal FROM ElectricBill WHERE person='Foreigner'";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                avgTotal = rs.getDouble("avgTotal");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avgTotal;
    }

    private ElectricBill mapResultSetToElectricBill(ResultSet rs) throws SQLException {
        return new ElectricBill(
                rs.getString("idClient"),
                rs.getString("fullName"),
                rs.getString("person"),
                rs.getString("monthlyElectricly"),
                rs.getDate("time"),
                rs.getInt("qty"),
                rs.getDouble("unitPrice"),
                rs.getDouble("quota")
         
        );
    }

   
}
