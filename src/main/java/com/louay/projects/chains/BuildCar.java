package com.louay.projects.chains;


import java.sql.ResultSet;
import java.util.Collection;

public interface BuildCar {

    Cars buildCar(ResultSet resultSet);

    Collection<Cars> buildCarsList(ResultSet resultSet, Collection <Cars> carsContainer);

}
