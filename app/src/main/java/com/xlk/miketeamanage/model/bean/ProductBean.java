package com.xlk.miketeamanage.model.bean;

/**
 * @author Created by xlk on 2021/8/7.
 * @desc 产品
 */
public class ProductBean {
    String name;
    private int capacityA;
    private int capacityB;
    private int capacityC;
    private int capacityD;
    private int waterAmount;

    public ProductBean(String name, int capacityA, int capacityB, int capacityC, int capacityD, int waterAmount) {
        this.name = name;
        this.capacityA = capacityA;
        this.capacityB = capacityB;
        this.capacityC = capacityC;
        this.capacityD = capacityD;
        this.waterAmount = waterAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacityA() {
        return capacityA;
    }

    public void setCapacityA(int capacityA) {
        this.capacityA = capacityA;
    }

    public int getCapacityB() {
        return capacityB;
    }

    public void setCapacityB(int capacityB) {
        this.capacityB = capacityB;
    }

    public int getCapacityC() {
        return capacityC;
    }

    public void setCapacityC(int capacityC) {
        this.capacityC = capacityC;
    }

    public int getCapacityD() {
        return capacityD;
    }

    public void setCapacityD(int capacityD) {
        this.capacityD = capacityD;
    }

    public int getWaterAmount() {
        return waterAmount;
    }

    public void setWaterAmount(int waterAmount) {
        this.waterAmount = waterAmount;
    }

    @Override
    public String toString() {
        return "ProductBean{" +
                "name='" + name + '\'' +
                ", capacityA=" + capacityA +
                ", capacityB=" + capacityB +
                ", capacityC=" + capacityC +
                ", capacityD=" + capacityD +
                ", waterAmount=" + waterAmount +
                '}';
    }
}
