package com.education.project.cars.manager.carsmanager.repository;

import com.education.project.cars.manager.carsmanager.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

}
