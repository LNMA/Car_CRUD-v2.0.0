package com.louay.projects.dao;

import com.louay.projects.chains.Cars;

public interface CreateCarDAO {

    long createCar(Cars cars);

    int insertTrafficViolation(Cars cars);

    int insertOwner(Cars cars);

    int insertColor(Cars cars);
}
