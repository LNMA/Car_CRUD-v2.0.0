package com.louay.projects.dao;

import com.louay.projects.chains.Cars;

public interface UpdateCarDAO {

    int updateCarByID(Cars cars);

    int updateColorByIDAndDate(Cars cars);

    int updateOwnerNameByIDAndDate(Cars cars);

    int updateTrafficViolationsByIDAndDate(Cars cars);

}
