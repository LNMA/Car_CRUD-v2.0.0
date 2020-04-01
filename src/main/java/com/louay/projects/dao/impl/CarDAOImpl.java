package com.louay.projects.dao.impl;

import com.louay.projects.chains.BuildCar;
import com.louay.projects.chains.Cars;
import com.louay.projects.dao.CreateCarDAO;
import com.louay.projects.dao.DeleteCarDAO;
import com.louay.projects.dao.SelectCarDAO;
import com.louay.projects.dao.UpdateCarDAO;
import com.louay.projects.util.pool.ConnectionWrapper;
import com.louay.projects.util.pool.MyConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component()
public class CarDAOImpl implements CreateCarDAO, DeleteCarDAO, SelectCarDAO, UpdateCarDAO, BuildCar {

    @Autowired
    @Qualifier("pool")
    private MyConnectionPool pool;


    @Override
    public long createCar(Cars cars) {
        long idCar = 0;
        try {
            ConnectionWrapper wrapper = this.pool.getConnection();
            PreparedStatement insert = wrapper.getConnection().prepareStatement("insert into `car`(`type`, `version`," +
                    "`model`, `owner`, `color`, `numberOfSeats`,  `licenseExpiry`, `trafficViolationTotalAmount`) " +
                    "values (?,?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);

            insert.setString(1, cars.getType());
            insert.setString(2, cars.getVersion());
            insert.setInt(3, cars.getModel());
            insert.setString(4, cars.getOwner());
            insert.setString(5, cars.getColor());
            insert.setInt(6, cars.getNumberOfSeats());
            insert.setDate(7, cars.getLicenseExpiry());
            insert.setDouble(8, cars.getTrafficViolationTotalAmount());
            insert.executeUpdate();

            ResultSet resultSet = insert.getGeneratedKeys();

            if (resultSet.next()) {
                idCar = resultSet.getLong(1);
            }

            this.pool.release(wrapper);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return idCar;
    }

    @Override
    public int deleteByID(Cars car) {
        int result = 0;
        try{
            result = this.pool.updateQuery("delete from `car` where `idCar`=? ", car.getIdCar());

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public int deleteByType(Cars car) {
        int result = 0;
        try{
            result = this.pool.updateQuery("delete from `car` where `type`=? ", car.getType());

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public int deleteByOwner(Cars car) {
        int result = 0;
        try{
            result = this.pool.updateQuery("delete from `car` where `owner`=? ", car.getOwner());

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public Cars findByID(Cars car) {
        Cars cars = null;
        try {
            ResultSet resultSet = this.pool.selectResult("SELECT * FROM `car` WHERE `idCar`= ?", car.getIdCar());
            cars = auxiliaryBuildSingleCar(resultSet);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return cars;
    }

    @Override
    public List<Cars> findByType(Cars car) {
        List<Cars> carsList = new ArrayList<>();
        try{
            ResultSet resultSet = this.pool.selectResult("SELECT * FROM `car` WHERE `type`= ?", car.getType());
            buildCarsList(resultSet, carsList);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return carsList;
    }

    @Override
    public List<Cars> findByColor(Cars car) {
        List<Cars> carsList = new ArrayList<>();
        try{
            ResultSet resultSet = this.pool.selectResult("SELECT * FROM `car` WHERE `color`= ?", car.getColor());
            buildCarsList(resultSet, carsList);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return carsList;    }

    @Override
    public List<Cars> findByOwner(Cars car) {
        List<Cars> carsList = new ArrayList<>();
        try{
            ResultSet resultSet = this.pool.selectResult("SELECT * FROM `car` WHERE `owner`= ?", car.getOwner());
            buildCarsList(resultSet, carsList);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return carsList;
    }

    @Override
    public List<Cars> findByTrafficViolationTotalAmount(Cars car) {
        List<Cars> carsList = new ArrayList<>();
        try{
            ResultSet resultSet = this.pool.selectResult("SELECT * FROM `car` WHERE `trafficViolationTotalAmount`= ?", car.getTrafficViolationTotalAmount());
            buildCarsList(resultSet, carsList);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return carsList;    }

    @Override
    public Cars buildCar(ResultSet resultSet) {
        Cars cars = new Cars();
        try {
            cars.setIdCar(resultSet.getLong(1));
            cars.setType(resultSet.getString(2));
            cars.setVersion(resultSet.getString(3));
            cars.setModel(resultSet.getInt(4));
            cars.setOwner(resultSet.getString(5));
            cars.setColor(resultSet.getString(6));
            cars.setNumberOfSeats(resultSet.getInt(7));
            cars.setLicenseExpiry(resultSet.getDate(8));
            cars.setTrafficViolationTotalAmount(resultSet.getDouble(9));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cars;
    }

    @Override
    public Cars auxiliaryBuildSingleCar(ResultSet resultSet) {
        Cars cars = null;
        try{
            if (resultSet.next()) {
                cars = buildCar(resultSet);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cars;
    }

    @Override
    public List<Cars> buildCarsList(ResultSet resultSet,List<Cars> carsList) {
        try{
            while (resultSet.next()){
                carsList.add(buildCar(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return carsList;
    }

    @Override
    public int updateCar(Cars cars) {
        int result = 0;
        try{
            result = this.pool.updateQuery("UPDATE `car` SET `type` = ?, `version` = ?, `model` = ?, `owner` = ?, " +
                    "color = ?, numberOfSeats = ?, licenseExpiry = ?, trafficViolationTotalAmount = ? WHERE `idCar` = ?",
                    cars.getType(), cars.getVersion(), cars.getModel(), cars.getOwner(), cars.getColor(), cars.getNumberOfSeats(),
                    cars.getLicenseExpiry(), cars.getTrafficViolationTotalAmount(), cars.getIdCar());

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return result;
    }

}
