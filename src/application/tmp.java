/*
 * tmp.java                                      10 Nov 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft" 
 */
package application;

import java.math.BigInteger;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

import modele.Modele;

/** TODO comment class responsibility (SRP)
 * @author benji
 *
 */
public class tmp {

    /** TODO comment method role
     * @param args
     */
    public static void main(String[] args) {
        // Example: Calculate 2^1000
        BigInteger base = new BigInteger("2");
        int exponent = 100000;

        // Exponentiation by squaring
        long startTime = System.nanoTime();
        BigInteger resultSquaring = fastExponentiation(base, exponent);
        long endTime = System.nanoTime();
        long squaringTime = endTime - startTime;

        // Classic exponentiation
        startTime = System.nanoTime();
        BigInteger resultClassic = classicExponentiation(base, exponent);
        endTime = System.nanoTime();
        long classicTime = endTime - startTime;

        // Output results and comparison
        System.out.println("Exponentiation by Squaring: " + resultSquaring);
        System.out.println("Classic Exponentiation: " + resultClassic);

        System.out.println("Time taken for Exponentiation by Squaring: " + squaringTime + " nanoseconds");
        System.out.println("Time taken for Classic Exponentiation: " + classicTime + " nanoseconds");

        // Compare results
        if (resultSquaring.equals(resultClassic)) {
            System.out.println("Results are equal.");
        } else {
            System.out.println("Results are not equal.");
        }
    }

    static BigInteger fastExponentiation(BigInteger base, int exponent) {
        if (exponent == 0) {
            return BigInteger.ONE;
        } else if (exponent % 2 == 0) {
            BigInteger halfPower = fastExponentiation(base, exponent / 2);
            return halfPower.multiply(halfPower);
        } else {
            BigInteger halfPower = fastExponentiation(base, (exponent - 1) / 2);
            return base.multiply(halfPower).multiply(halfPower);
        }
    }

    static BigInteger classicExponentiation(BigInteger base, int exponent) {
        BigInteger result = BigInteger.ONE;

        for (int i = 0; i < exponent; i++) {
            result = result.multiply(base);
        }

        return result;
    }
}
