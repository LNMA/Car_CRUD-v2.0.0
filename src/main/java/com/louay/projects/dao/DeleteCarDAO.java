package com.louay.projects.dao;

import com.louay.projects.chains.Cars;


public interface DeleteCarDAO {

    int deleteByID(Cars car);

    int deleteByType(Cars car);

    int deleteByOwner(Cars car);

}
