package com.education.project.cars.manager.carsmanager.IOServce;

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
    public CarList carListReader(String fileName) {
        String query = String.format(
                "SELECT Idc, Year, Brand, Model, Cost FROM %s;", fileName);
        source.readDB(query);
        return carListGetFromRS(source.getRs());
    }

    public Car carReader(Long idc, String fileName) {
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
