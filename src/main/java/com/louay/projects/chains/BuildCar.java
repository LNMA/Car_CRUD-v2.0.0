package com.louay.projects.chains;


import java.sql.ResultSet;
import java.util.List;

public interface BuildCar {

    Cars buildCar(ResultSet resultSet);

    Cars auxiliaryBuildSingleCar(ResultSet resultSet);

    List<Cars> buildCarsList(ResultSet resultSet, List<Cars> carsList);

}
