package com.itqf.shopingcar.test;

import java.util.Random;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/519:12
 * description:
 */
public class TestRandom {
    public static void main(String[] args) {
        Random random = new Random();
        double v = Math.floor( random.nextDouble()*1000);
        System.out.println(v);
    }
}
