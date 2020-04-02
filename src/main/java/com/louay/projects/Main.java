package com.louay.projects;

import com.louay.projects.chains.Cars;

import com.louay.projects.dao.CreateCarDAO;
import com.louay.projects.dao.DeleteCarDAO;
import com.louay.projects.dao.SelectCarDAO;
import com.louay.projects.dao.UpdateCarDAO;
import com.louay.projects.dao.impl.CarDAOImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.scan("com.louay.projects");
        ac.refresh();


        Cars cars = ac.getBean(Cars.class);

        cars.setIdCar((long)1);
        cars.setType("sedan");
        cars.setVersion("Audi R8");
        cars.setModel(2019);
        cars.setOwner("audi company");
        cars.setPurchaseDate(java.sql.Date.valueOf("2020-04-01"));
        cars.setColor("White Metallic");
        cars.setColorDate(java.sql.Date.valueOf("2021-05-01"));
        cars.setNumberOfSeats(2);
        cars.setLicenseExpiry(java.sql.Date.valueOf("2020-04-01"));
        cars.setTrafficViolationTotalAmount(25.5);
        cars.setViolationsDate(java.sql.Date.valueOf("2020-04-04"));

        CreateCarDAO createCarDAO = ac.getBean(CarDAOImpl.class);
        createCarDAO.createCar(cars);

        UpdateCarDAO updateCarDAO = ac.getBean(CarDAOImpl.class);
        updateCarDAO.updateOwnerNameByIDAndDate(cars);

        SelectCarDAO selectCarDAO = ac.getBean(CarDAOImpl.class);
        selectCarDAO.findColorAndIDByType(cars);

        DeleteCarDAO deleteCarDAO = ac.getBean(CarDAOImpl.class);
        deleteCarDAO.deleteByOwner(cars);
    }
}
