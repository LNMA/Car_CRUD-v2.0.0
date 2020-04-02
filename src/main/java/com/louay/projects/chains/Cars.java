package com.louay.projects.chains;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
@Scope("prototype")
public class Cars {

    private Long idCar;
    private String type;
    private String version;
    private Integer model;
    private String owner;
    private java.sql.Date purchaseDate;
    private String color;
    private java.sql.Date colorDate;
    private Integer numberOfSeats;
    private java.sql.Date licenseExpiry;
    private Double trafficViolationTotalAmount;
    private java.sql.Date violationsDate;

    public Long getIdCar() {
        return idCar;
    }

    public void setIdCar(Long idCar) {
        this.idCar = idCar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date getColorDate() {
        return colorDate;
    }

    public void setColorDate(Date colorDate) {
        this.colorDate = colorDate;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Date getLicenseExpiry() {
        return licenseExpiry;
    }

    public void setLicenseExpiry(Date licenseExpiry) {
        this.licenseExpiry = licenseExpiry;
    }

    public Double getTrafficViolationTotalAmount() {
        return trafficViolationTotalAmount;
    }

    public void setTrafficViolationTotalAmount(Double trafficViolationTotalAmount) {
        this.trafficViolationTotalAmount = trafficViolationTotalAmount;
    }

    public Date getViolationsDate() {
        return violationsDate;
    }

    public void setViolationsDate(Date violationsDate) {
        this.violationsDate = violationsDate;
    }

    @Override
    public String toString() {
        return "Cars{" +
                "idCar=" + idCar +
                ", type='" + type + '\'' +
                ", version='" + version + '\'' +
                ", model=" + model +
                ", owner='" + owner + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", color='" + color + '\'' +
                ", colorDate=" + colorDate +
                ", numberOfSeats=" + numberOfSeats +
                ", licenseExpiry=" + licenseExpiry +
                ", trafficViolationTotalAmount=" + trafficViolationTotalAmount + " $" +
                ", violationsDate=" + violationsDate +
                '}';
    }
}
