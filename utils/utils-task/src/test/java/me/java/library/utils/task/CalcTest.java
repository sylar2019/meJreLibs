package me.java.library.utils.task;

import org.junit.Test;

import java.text.DecimalFormat;

/**
 * File Name             :  DemoTaskServiceTest
 *
 * @author :  sylar
 * Create                :  2019-11-22
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) allthings.vip  All Rights Reserved
 * *******************************************************************************************
 */
public class CalcTest {

    @Test
    public void calcTest() {

        double[] A = new double[]{0.48, 0.4, 0.32, 0.24};
        double[] B = new double[]{0.45, 0.36, 0.3, 0.24, 0.15, 0.06};
        double[] C = new double[]{0.45, 0.36, 0.3, 0.24, 0.15, 0.06};

        DecimalFormat df = new DecimalFormat("0.00");

        double sum;
        for (int x = 0; x < A.length; x++) {
            for (int y = 0; y < B.length; y++) {
                for (int z = 0; z < C.length; z++) {
                    sum = A[x] + B[y] + C[z];
                    System.out.println(String.format("%s+%s+%s=%s",
                            df.format(A[x]),
                            df.format(B[y]),
                            df.format(C[z]),
                            df.format(sum))
                    );
                }
            }
        }

        System.out.println("总计个数:" + (A.length * B.length * C.length));

    }
}
