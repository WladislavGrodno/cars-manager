package com.education.project.cars.manager.carsmanager.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
@Getter
@Service
public class DBPoolService {
    private PGPoolingDataSource ds = new PGPoolingDataSource();
    private Connection connection;
    private Statement statement;
    private ResultSet rs;

    private String serverName = "localhost";
    private String databaseName = "postgres";
    private String userName  = "postgres";
    private String password = "password";
    private int maxConnections = 100;
    private int initialConnections = 20;

    private int status = 1;
    //private String tableName;

    public DBPoolService(){
         initConnection();
     }

    public DBPoolService(String serverName, String databaseName,
                         String userName, String password,
                         int maxConnections, int initialConnections) {
        this.serverName = serverName;
        this.databaseName = databaseName;
        this.userName = userName;
        this.password = password;
        this.maxConnections = maxConnections;
        this.initialConnections = initialConnections;
        initConnection();
    }

    private void initConnection(){
        setupPGPoolingDataSource();
        status = setConnection();
    }

    private void setupPGPoolingDataSource(){
         ds.setServerName(serverName);
         ds.setDatabaseName(databaseName);
         ds.setUser(userName);
         ds.setPassword(password);
         ds.setMaxConnections(maxConnections);
         ds.setInitialConnections(initialConnections);
     }

    public boolean checkStatus(){
        return status == 0;
    }

    /**
     * Set connection to database
     * @return
     * 0    :   It's all Ok<br>
     * -1   :   No driver<br>
     * -2   :   No database<br>
     * -3   :   No statement
     */
    public int setConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException e){
            log.error("{\"error\": \"{}\"}", e.getMessage());
            return -1;//неподключен драйвер
        }
        try {
            connection = ds.getConnection();
        }
        catch (SQLException e){
            log.error("{\"error\": \"{}\", \"state\": \"{}\"}",
                    e.getMessage(), e.getSQLState());
            return -2;//нет подключения к базе данных
        }
        try {
            statement = connection.createStatement();
        }
        catch (SQLException e){
            log.error("{\"error\": \"{}\", \"state\": \"{}\"}",
                    e.getMessage(), e.getSQLState());
            return  -3;//не смог сотворить statement
        }
        return 0;
    }

    /**
     *Create table Garage
     */
    public void createDB(String tableName){
        //ToDo: add indexes for optimal work?
        try {
            statement.execute(String.format(
                    "CREATE TABLE if not exists %s (" +
                            "Id SERIAL PRIMARY KEY NOT NULL, " +
                            "Idc BIGINT UNIQUE NOT NULL, " +
                            "Year INT NOT NULL, " +
                            "Brand varchar(30) NOT NULL " +
                            "CHECK(trim(brand)!=''), " +
                            "Model varchar(30) NOT NULL " +
                            "CHECK(trim(model)!=''), " +
                            "Cost INT NOT NULL);", tableName));
        } catch (SQLException e) {
            switch (e.getSQLState()){
                case "42P07" -> status = 1;// already exist
                default -> {
                    log.error("{\"error\": \"{}\", \"state\": \"{}\"}",
                            e.getMessage(), e.getSQLState());
                    status = -4;
                }
            }
        }
    }

    /**
     * remove table Garage
     */
    public void removeDB(String tableName) {
        try {
            ResultSet rsNew = statement.executeQuery(
                    "DROP TABLE " + tableName);
        } catch (SQLException e) {
            switch (e.getSQLState()) {
                case "42P01" -> System.out.printf(
                        "таблица %s уже не существует", tableName);
                default -> log.error("{\"error\": \"{}\", \"state\": \"{}\"}",
                        e.getMessage(), e.getSQLState());
            }
        }
    }


    public void writeDB(String query){
        try {
            statement.execute(query);
            status = 0;
        } catch (SQLException e) {
            switch (e.getSQLState()) {
                case "23502" -> {   //Null value error
                    log.error("{\"error\": \"{}\", \"state\": \"{}\"}",
                            e.getMessage(), e.getSQLState());
                    status = -7;
                }
                case "23505" -> {   //Use another key
                    status = -8;
                }
                default -> {    //unknown error
                    log.error("{\"error\": \"{}\", \"state\": \"{}\"}",
                            e.getMessage(), e.getSQLState());
                    status = -5;
                }
            }
        }
    }

    /**
     * Query executor
     * @param query query body
     */
    public void readDB(String query){
        // ToDo: насколько секурно давать публичный доступ к выполнению
        // произвольного SQL-запроса?
        try {
            rs = statement.executeQuery(query);
        } catch (SQLException e) {
            log.error("{\"error\": \"{}\", \"state\": \"{}\"}",
                    e.getMessage(), e.getSQLState());
            status = -6;
        }
    }

    /**
     * Close the Connection after all
     */
    public void closeConnection() {
        try {
            if (statement != null) statement.close();
        }
        catch (Exception e) {
            log.error("{\"error\": \"{}\"}", e.getMessage());
        }
        try {
            if (connection != null) connection.close();
        }
        catch (Exception e) {
            log.error("{\"error\": \"{}\"}", e.getMessage());
        }
        try {
            if(rs != null) rs.close();
        }
        catch (Exception e) {
            log.error("{\"error\": \"{}\"}", e.getMessage());
        }
        System.out.println("\nDatabase connections were closed\n");
    }

    public void showStatusMessage(){
        switch (status) {
            case -1 -> System.out.println("No driver");
            case -2 -> System.out.println("No database");
            case -3 -> System.out.println("No statement");
            case -4 -> System.out.println("Unknown status of table 'Garage'");
            case -5 -> System.out.println("writeDB error");
            case -6 -> System.out.println("readDB error");
            case -7 -> System.out.println("Trying empty value insertion");
            case -8 -> System.out.println("Already existing key insertion");
        }
    }
}
