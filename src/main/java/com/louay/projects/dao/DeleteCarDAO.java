package com.louay.projects.dao;

import com.louay.projects.chains.Cars;

import java.util.List;

public interface DeleteCarDAO {

    int deleteByID(Cars car);

    int deleteByType(Cars car);

    int deleteByOwner(Cars car);

}
