package com.education.project.cars.manager.carsmanager.IOService;

import com.education.project.cars.manager.carsmanager.model.Car;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.education.project.cars.manager.carsmanager.service.CarList;
import com.education.project.cars.manager.carsmanager.service.DBPoolService;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Setter
@Getter
@Service
public class ReadServiceDBImp implements ReadService{
    @Autowired
    private DBPoolService source;

    @Override
    public CarList carListRead(String fileName) {
        String query = String.format(
                "SELECT Idc, Year, Brand, Model, Cost FROM %s;", fileName);
        source.readDB(query);
        return carListGetFromRS(source.getRs());
    }


    private String orderList(String[] list, int i){
        if (i >= list.length) return "";
        switch (list[i].toLowerCase()) {
            case "year", "brand", "model", "cost" -> {
                if (i + 1 >= list.length) return list[i];
                switch (list[i + 1].toLowerCase()) {
                    case "asc", "desc" -> {
                        String s = orderList(list, i + 2);
                        switch (s) {
                            case "b" -> {
                                return s;
                            }
                            case "" -> {
                                return list[i].toUpperCase()
                                        + " " + list[i + 1].toUpperCase();
                            }
                            default -> {
                                return list[i].toUpperCase()
                                        + " " + list[i + 1].toUpperCase()
                                        + ", " + s;
                            }
                        }
                    }
                    default -> {
                        String s = orderList(list, i + 1);
                        switch (s) {
                            case "b" -> {
                                return s;
                            }
                            case "" -> {
                                return list[i].toUpperCase();
                            }
                            default -> {
                                return list[i].toUpperCase() + ", " + s;
                            }
                        }
                    }
                }
            }
            default -> {return "b";}
        }
    }
    private String orderList(String[] list){
        return orderList(list, 0);
    }
    private String filterList(String[] list){
        switch (list.length) {
            case 0 -> {return "";}
            case 3 -> {
                String condition = list[0].toLowerCase();
                switch (condition) {
                    case "equal", "not_equal" -> {
                        String ethanol;
                        switch (list[1].toLowerCase()) {
                            case "brand", "model" ->
                                    ethanol = "'" + list[2] + "'";
                            case "year", "cost" ->
                                    ethanol = list[2];
                            default -> {return "b";}
                        }
                        return list[1] +
                                ((condition.equals("equal")) ? " = " : " <> ") +
                                ethanol;
                    }
            /*
            case "not_equal" -> {}
            case "between" -> {
                source.readDB("SELECT Year, Brand, Model, Cost FROM " + table +
                        " WHERE " + field + " BETWEEN " + min + " AND " + max + ";");
            }
            case "not_between" -> {}
             */
                    default -> {return "b";}
                }
            }
            default -> {return "b";}
        }
    }

    @Override
    public CarList carListCustomRead(
            String sortBy, String filter, String fileName) {

        StringBuilder query = new StringBuilder();
        query.append("SELECT Idc, Year, Brand, Model, Cost FROM ").append(fileName);

        String sortPhrase = orderList(sortBy.split("\\."));
        String filterPhrase = filterList(filter.split("\\."));

        switch (filterPhrase){
            case "b" -> {return null;}
            case "" -> {}
            default -> query.append(" WHERE ").append(filterPhrase);
        }

        switch (sortPhrase){
            case "b" -> {return null;}
            case "" -> {}
            default -> query.append(" ORDER BY ").append(sortPhrase);
        }

        query.append(";");

        source.readDB(query.toString());
        return carListGetFromRS(source.getRs());
    }


    @Override
    public Car carRead(Long idc, String fileName) {
        String query = String.format(
                "SELECT Idc, Year, Brand, Model, Cost " +
                        "FROM %s WHERE Idc=%d;", fileName, idc);
        source.readDB(query);
        CarList cars = carListGetFromRS(source.getRs());
        if (cars.size() == 1) return cars.get(0);
        return null;
    }

    private CarList carListGetFromRS(ResultSet rs){
        //ToDo: согласовать размер КарЛиста с количеством записей в резултсете
        CarList carArrayList = new CarList();
        try {
            while (rs.next()) {
                Car car = new Car();
                car.setIdCar(rs.getLong("Idc"));
                car.setYear(rs.getInt("Year"));
                car.setBrand(rs.getString("Brand"));
                car.setModel(rs.getString("Model"));
                car.setCost(rs.getInt("Cost"));
                carArrayList.add(car);
            }
        } catch (SQLException e) {
            log.error("{\"error\": \"{}\", \"state\": \"{}\"}",
                    e.getMessage(), e.getSQLState());
        }
        return carArrayList;
    }
}
