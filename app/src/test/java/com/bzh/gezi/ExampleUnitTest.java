package com.bzh.gezi;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        System.out.println(Fibonacci(new BigInteger("1")));
    }

    public static BigInteger Fibonacci(BigInteger target) {
        if (target.compareTo(new BigInteger("0")) == 0 || target.compareTo(new BigInteger("1")) == 0) {
            return target;
        }
//        int x, y, z;
//        x = 0;
//        y = 1;
//        for (int i = 2; i <= n; i++) {
//            z = y;
//            y = y + x;
//            x = z;
//
//        }
//        return y;
        return new BigInteger("0");
    }
}