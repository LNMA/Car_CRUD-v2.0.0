package com.louay.projects.dao;

import com.louay.projects.chains.Cars;

import java.util.List;

public interface SelectCarDAO {

    Cars findByID(Cars car);

    List <Cars> findByType(Cars car);

    List <Cars> findByColor(Cars car);

    List <Cars> findByOwner(Cars car);

    List <Cars> findByTrafficViolationTotalAmount(Cars car);

}
