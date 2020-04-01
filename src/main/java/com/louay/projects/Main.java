package com.louay.projects;

import com.louay.projects.chains.Cars;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac2 = new AnnotationConfigApplicationContext();
        ac2.scan("com.louay.projects");
        ac2.refresh();


        Cars cars = ac2.getBean(Cars.class);
        cars.setIdCar((long)1);
        cars.setType("sedan");
        cars.setVersion("Audi R8");
        cars.setModel(2019);
        cars.setOwner("audi company");
        cars.setColor("Silver Metallic");
        cars.setNumberOfSeats(2);
        cars.setLicenseExpiry(java.sql.Date.valueOf("2020-04-01"));
        cars.setTrafficViolationTotalAmount(1.0);





    }
}
