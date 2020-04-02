package com.louay.projects.dao;

import com.louay.projects.chains.Cars;

import java.util.Collection;

public interface SelectCarDAO {

    Collection <Cars> findByID(Cars car);

    Collection <Cars> findByType(Cars car);

    Collection <Cars> findByColor(Cars car);

    Collection <Cars> findByOwner(Cars car);

    Collection <Cars> findByTrafficViolationTotalAmount(Cars car);

    Collection <Cars> findColorByID(Cars car);

    Collection <Cars> findColorAndIDByType(Cars car);

    Collection <Cars> findViolationsByID(Cars car);

    Collection <Cars> findViolationsByOwner(Cars car);

}
