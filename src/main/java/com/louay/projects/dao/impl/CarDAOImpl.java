package com.louay.projects.dao.impl;

import com.louay.projects.beans.BeansFactory;
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
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Collection;

@Component("carImpl")
@Scope("prototype")
public class CarDAOImpl implements CreateCarDAO, DeleteCarDAO, SelectCarDAO, UpdateCarDAO, BuildCar {

    @Autowired
    @Qualifier("pool")
    private MyConnectionPool pool;

    private ApplicationContext ac = new AnnotationConfigApplicationContext(BeansFactory.class);


    @Override
    public long createCar(Cars cars) {
        try {
            ConnectionWrapper wrapper = this.pool.getConnection();

            cars.setIdCar(auxiliaryInsertCar(wrapper, cars));

            auxiliaryInsertColor(wrapper, cars);

            auxiliaryInsertOwner(wrapper, cars);

            auxiliaryInsertTrafficViolation(wrapper, cars);

            this.pool.release(wrapper);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return cars.getIdCar();
    }

    private long auxiliaryInsertCar(ConnectionWrapper wrapper, Cars cars) throws SQLException{
        long idCar=0;
        PreparedStatement insert = wrapper.getConnection().prepareStatement("insert into `car`(`type`, `version`," +
                "`model`, `numberOfSeats`,  `licenseExpiry`) values (?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);

        insert.setString(1, cars.getType());
        insert.setString(2, cars.getVersion());
        insert.setInt(3, cars.getModel());
        insert.setInt(4, cars.getNumberOfSeats());
        insert.setDate(5, cars.getLicenseExpiry());
        insert.executeUpdate();

        ResultSet resultSet = insert.getGeneratedKeys();

        if (resultSet.next()) {
            idCar = resultSet.getLong(1);
        }
        return idCar;
    }

    private void auxiliaryInsertColor(ConnectionWrapper wrapper, Cars cars) throws SQLException{
        PreparedStatement insert = wrapper.getConnection().prepareStatement("insert into `color-history`(`idCar`, " +
                "`color`,`colorDate`) values (?,?,?);");
        insert.setLong(1, cars.getIdCar());
        insert.setString(2, cars.getColor());
        insert.setDate(3, cars.getColorDate());
        insert.executeUpdate();
    }

    private void auxiliaryInsertOwner(ConnectionWrapper wrapper,  Cars cars) throws SQLException{
        PreparedStatement insert = wrapper.getConnection().prepareStatement("insert into `owner-history`(`idCar`, " +
                "`ownerName`,`purchaseDate`) values (?,?,?);");
        insert.setLong(1, cars.getIdCar());
        insert.setString(2, cars.getOwner());
        insert.setDate(3, cars.getPurchaseDate());
        insert.executeUpdate();
    }

    private void auxiliaryInsertTrafficViolation(ConnectionWrapper wrapper,  Cars cars) throws SQLException{
        PreparedStatement insert = wrapper.getConnection().prepareStatement("insert into `traffic_violations-history`" +
                "(`idCar`, `trafficViolationTotalAmount`,`violationsDate`) values (?,?,?);");
        insert.setLong(1, cars.getIdCar());
        insert.setDouble(2, cars.getTrafficViolationTotalAmount());
        insert.setDate(3, cars.getViolationsDate());
        insert.executeUpdate();
    }

    @Override
    public int insertColor(Cars cars){
        int result = 0;
        try {
            result = this.pool.updateQuery("insert into `color-history`(`idCar`, `color`,`colorDate`) " +
                    "values (?,?,?);", cars.getIdCar(), cars.getColor(), cars.getColorDate());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public int insertOwner(Cars cars) {
        int result = 0;
        try {
            result = this.pool.updateQuery("insert into `owner-history`(`idCar`, `ownerName`,`purchaseDate`)" +
                    " values (?,?,?);", cars.getIdCar(), cars.getOwner(), cars.getPurchaseDate());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public int insertTrafficViolation(Cars cars) {
        int result = 0;
        try {
            result = this.pool.updateQuery("insert into `traffic_violations-history`(`idCar`, " +
                    "`trafficViolationTotalAmount`, `violationsDate`) values (?,?,?);", cars.getIdCar(),
                    cars.getTrafficViolationTotalAmount(), cars.getViolationsDate());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
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

            result = this.pool.updateQuery("DELETE `car` FROM `car` INNER join `owner-history` ON " +
                    "`car`.`idCar` = `owner-history`.`idCar` WHERE `owner-history`.`ownerName` = ? ; ", car.getOwner());

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public Collection <Cars> findByID(Cars car) {
        Collection<Cars> carsContainer = (Collection<Cars>) ac.getBean("carContainer");
        try {
            ResultSet resultSet = this.pool.selectResult("SELECT * FROM `car` INNER join `owner-history` ON " +
                    "`car`.`idCar` = `owner-history`.`idCar` INNER join `color-history` ON `car`.`idCar` = " +
                    "`color-history`.`idCar` INNER join `traffic_violations-history` ON `car`.`idCar` = " +
                    "`traffic_violations-history`.`idCar` WHERE `car`.`idCar`= ? ORDER BY `color-history`.`colorDate` DESC," +
                    "`owner-history`.`purchaseDate` DESC, `traffic_violations-history`.`violationsDate` DESC;  ", car.getIdCar());
            buildCarsList(resultSet, carsContainer);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return carsContainer;
    }

    @Override
    public Collection <Cars> findByType(Cars car) {
        Collection<Cars> carsContainer = (Collection<Cars>) ac.getBean("carContainer");
        try {
            ResultSet resultSet = this.pool.selectResult("SELECT * FROM `car` INNER join `owner-history` ON " +
                    "`car`.`idCar` = `owner-history`.`idCar` INNER join `color-history` ON `car`.`idCar` = " +
                    "`color-history`.`idCar` INNER join `traffic_violations-history` ON `car`.`idCar` = " +
                    "`traffic_violations-history`.`idCar` WHERE `car`.`type`= ? ORDER BY `color-history`.`colorDate` DESC," +
                    " `owner-history`.`purchaseDate` DESC, `traffic_violations-history`.`violationsDate` DESC;  ", car.getType());
            buildCarsList(resultSet, carsContainer);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return carsContainer;
    }

    @Override
    public Collection <Cars> findByColor(Cars car) {
        Collection<Cars> carsContainer = (Collection<Cars>) ac.getBean("carContainer");
        try {
            ResultSet resultSet = this.pool.selectResult("SELECT * FROM `car` INNER join `owner-history` ON " +
                    "`car`.`idCar` = `owner-history`.`idCar` INNER join `color-history` ON `car`.`idCar` = " +
                    "`color-history`.`idCar` INNER join `traffic_violations-history` ON `car`.`idCar` = " +
                    "`traffic_violations-history`.`idCar` WHERE `color-history`.`color` = ? ORDER BY " +
                    "`color-history`.`colorDate` DESC, `owner-history`.`purchaseDate` DESC, " +
                    "`traffic_violations-history`.`violationsDate` DESC;", car.getColor());
            buildCarsList(resultSet, carsContainer);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return carsContainer;
    }

    @Override
    public Collection <Cars> findByOwner(Cars car) {
        Collection<Cars> carsContainer = (Collection<Cars>) ac.getBean("carContainer");
        try {
            ResultSet resultSet = this.pool.selectResult("SELECT * FROM `car` INNER join `owner-history` ON " +
                    "`car`.`idCar` = `owner-history`.`idCar` INNER join `color-history` ON `car`.`idCar` = " +
                    "`color-history`.`idCar` INNER join `traffic_violations-history` ON `car`.`idCar` = " +
                    "`traffic_violations-history`.`idCar` WHERE `owner-history`.`ownerName` = ? ORDER BY " +
                    "`color-history`.`colorDate` DESC, `owner-history`.`purchaseDate` DESC, " +
                    "`traffic_violations-history`.`violationsDate` DESC;", car.getOwner());
            buildCarsList(resultSet, carsContainer);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return carsContainer;
    }

    @Override
    public Collection <Cars> findByTrafficViolationTotalAmount(Cars car) {
        Collection<Cars> carsContainer = (Collection<Cars>) ac.getBean("carContainer");
        try{
            ResultSet resultSet = this.pool.selectResult("SELECT * FROM `car` INNER join `owner-history` ON " +
                    "`car`.`idCar` = `owner-history`.`idCar` INNER join `color-history` ON `car`.`idCar` = " +
                    "`color-history`.`idCar` INNER join `traffic_violations-history` ON `car`.`idCar` = " +
                    "`traffic_violations-history`.`idCar` WHERE `traffic_violations-history`.`trafficViolationTotalAmount` = ? " +
                    "ORDER BY `color-history`.`colorDate` DESC, `owner-history`.`purchaseDate` DESC, " +
                    "`traffic_violations-history`.`violationsDate` DESC;", car.getTrafficViolationTotalAmount());
            buildCarsList(resultSet, carsContainer);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return carsContainer;
    }

    @Override
    public Collection <Cars> findColorByID(Cars car){
        Collection<Cars> carsContainer = (Collection<Cars>) ac.getBean("carContainer");
        try{
            ResultSet resultSet = this.pool.selectResult("SELECT `color-history`.`color` FROM `color-history`" +
                    " WHERE `color-history`.`idCar` = ?;", car.getIdCar());

            while (resultSet.next()){
                Cars cars = new Cars();
                cars.setColor(resultSet.getString(1));
                carsContainer.add(cars);
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return carsContainer;
    }

    @Override
    public Collection <Cars> findColorAndIDByType(Cars car){
        Collection<Cars> carsContainer = (Collection<Cars>) ac.getBean("carContainer");
        try{
            ResultSet resultSet = this.pool.selectResult("SELECT `color-history`.`idCar`, `color-history`.`color` " +
                    "From `color-history` INNER JOIN `car` ON `car`.`idCar` = `color-history`.`idCar`" +
                    " WHERE `car`.`type` = ? ;", car.getType());

            while (resultSet.next()){
                Cars cars = new Cars();
                cars.setIdCar(resultSet.getLong(1));
                cars.setColor(resultSet.getString(2));
                carsContainer.add(cars);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return carsContainer;
    }

    @Override
    public Collection <Cars> findViolationsByID(Cars car){
        Collection<Cars> carsContainer = (Collection<Cars>) ac.getBean("carContainer");
        try{
            ResultSet resultSet = this.pool.selectResult("SELECT `traffic_violations-history`.`trafficViolationTotalAmount` " +
                    "FROM `traffic_violations-history` WHERE `traffic_violations-history`.`idCar` = ?;", car.getIdCar());

            while (resultSet.next()){
                Cars cars = new Cars();
                cars.setTrafficViolationTotalAmount(resultSet.getDouble(1));
                carsContainer.add(cars);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return carsContainer;
    }

    @Override
    public Collection <Cars> findViolationsByOwner(Cars car){
        Collection<Cars> carsContainer = (Collection<Cars>) ac.getBean("carContainer");
        try{
            ResultSet resultSet = this.pool.selectResult("SELECT `traffic_violations-history`.`idCar`," +
                    " `traffic_violations-history`.`trafficViolationTotalAmount` From `traffic_violations-history` " +
                    "INNER JOIN `owner-history` ON `owner-history`.`idCar` = `traffic_violations-history`.`idCar`" +
                    " WHERE `owner-history`.`ownerName` = ?;", car.getOwner());

            while (resultSet.next()){
                Cars cars = new Cars();
                cars.setIdCar(resultSet.getLong(1));
                cars.setTrafficViolationTotalAmount(resultSet.getDouble(2));
                carsContainer.add(cars);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return carsContainer;
    }

    @Override
    public Cars buildCar(ResultSet resultSet) {
        Cars cars = new Cars();
        try {
            cars.setIdCar(resultSet.getLong(1));
            cars.setType(resultSet.getString(2));
            cars.setVersion(resultSet.getString(3));
            cars.setModel(resultSet.getInt(4));
            cars.setNumberOfSeats(resultSet.getInt(5));
            cars.setLicenseExpiry(resultSet.getDate(6));
            cars.setOwner(resultSet.getString(8));
            cars.setPurchaseDate(resultSet.getDate(9));
            cars.setColor(resultSet.getString(11));
            cars.setColorDate(resultSet.getDate(12));
            cars.setTrafficViolationTotalAmount(resultSet.getDouble(14));
            cars.setViolationsDate(resultSet.getDate(15));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cars;
    }

    @Override
    public Collection<Cars> buildCarsList(ResultSet resultSet,Collection <Cars> carsContainer) {
        try{
            while (resultSet.next()){
                carsContainer.add(buildCar(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return carsContainer;
    }

    @Override
    public int updateCarByID(Cars cars) {
        int result = 0;
        try{
            result = this.pool.updateQuery("UPDATE `car` SET `type` = ?, `version` = ?, `model` = ?, " +
                            "numberOfSeats = ?, licenseExpiry = ? WHERE `idCar` = ?", cars.getType(), cars.getVersion(),
                             cars.getModel(), cars.getNumberOfSeats(), cars.getLicenseExpiry(),  cars.getIdCar());
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public int updateColorByIDAndDate(Cars cars) {
        int result = 0;
        try{
            result = this.pool.updateQuery("UPDATE `color-history` SET `color` = ? WHERE `color-history`.`idCar` = ?" +
                    "AND `color-history`.`colorDate` = ?;", cars.getColor(), cars.getIdCar(), cars.getColorDate());
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public int updateOwnerNameByIDAndDate(Cars cars) {
        int result = 0;
        try{
            result = this.pool.updateQuery("UPDATE `owner-history` SET `owner-history`.`ownerName` = ? " +
                    "WHERE `owner-history`.`idCar` = ? AND `owner-history`.`purchaseDate` = ?;", cars.getOwner(),
                    cars.getIdCar(), cars.getPurchaseDate());
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public int updateTrafficViolationsByIDAndDate(Cars cars) {
        int result = 0;
        try{
            result = this.pool.updateQuery("UPDATE `traffic_violations-history` SET " +
                     "`traffic_violations-history`.`trafficViolationTotalAmount` = ? WHERE " +
                     "`traffic_violations-history`.`idCar` = ? AND `traffic_violations-history`.`violationsDate` = ?;"
                    , cars.getTrafficViolationTotalAmount(), cars.getIdCar(), cars.getViolationsDate());
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return result;
    }
}
