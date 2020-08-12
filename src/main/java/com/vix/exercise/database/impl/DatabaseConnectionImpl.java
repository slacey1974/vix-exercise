package com.vix.exercise.database.impl;

import com.vix.exercise.service.impl.Polling;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.time.*;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:db.properties")
public class DatabaseConnectionImpl {

    @Value( "url}" )
    private String url;
    @Value( "${user}" )
    private String user;
    @Value( "${password}" )
    private String password;
    @Value( "${jdbcDriver}" )
    private String jdbcDriver
            ;

    public List<Polling> getPollingRecords(String query) throws SQLException {
        List<Polling> pollingRecordList = new ArrayList<Polling>();

        Connection con = DriverManager.getConnection(url, user, password);
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(query);

        try (con; statement; rs) {
            Class.forName(jdbcDriver);
            while (rs.next()) {
                Polling pollingRs = new Polling();
                pollingRs.setId(rs.getInt("id"));
                pollingRs.setStatus(rs.getString("status"));
                pollingRs.setModifiedDate(rs.getDate("ModifiedDate").toLocalDate());

                pollingRecordList.add(pollingRs);
            }
        } catch (ClassNotFoundException | SQLException se) {
            se.printStackTrace();

        } finally {
            rs.close();
            statement.close();
            con.close();

        }

        return pollingRecordList;

    }

    public int createPollingRecords(String query, String status) throws SQLException {

        Connection con = DriverManager.getConnection(url, user, password);
        PreparedStatement preparedStatement = con.prepareStatement(query);
        int row = 0;

        try(con; preparedStatement) {
            preparedStatement.setString(1, status);
            row = preparedStatement.executeUpdate();

        } catch (SQLException se) {
            se.printStackTrace();

        } finally {
            preparedStatement.close();
            con.close();

        }


        return row;

    }

    public int updatePollingRecords(String query, Polling polling) throws SQLException {

        Connection con = DriverManager.getConnection(url, user, password);
        PreparedStatement preparedStatement = con.prepareStatement(query);
        int row = 0;

        try(con; preparedStatement) {
            preparedStatement.setString(1, polling.getStatus());
            preparedStatement.setInt(2, polling.getId());
            row = preparedStatement.executeUpdate();

        } catch (SQLException se) {
            se.printStackTrace();

        } finally {
            preparedStatement.close();
            con.close();

        }


        return row;

    }

    public boolean deletePollingRecord(String query, int id) throws SQLException {

        Connection con = DriverManager.getConnection(url, user, password);
        PreparedStatement preparedStatement = con.prepareStatement(query);
        boolean blnDelete = false;

        try(con; preparedStatement) {
            preparedStatement.setInt(1, id);
            blnDelete = preparedStatement.execute();

        } catch (SQLException se) {
            se.printStackTrace();

        } finally {
            preparedStatement.close();
            con.close();

        }


        return blnDelete;

    }
}
