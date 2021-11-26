package com.vsu.cgcourse.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Vector3fTest {

    @Test
    void testAdd() {
        Vector3f vector1 = new Vector3f(1, 2, 3);
        Vector3f vector2 = new Vector3f(3, 2, 1);
        vector1.add(vector2);
        Vector3f expectedResult = new Vector3f(4, 4, 4);
        Assertions.assertTrue(vector1.equals(expectedResult));
    }

    @Test
    void testAddFloat() {
        Vector3f vector1 = new Vector3f(1.25f, 2.5f, 3.75f);
        Vector3f vector2 = new Vector3f(2.5f, 3.25f, 3.75f);
        vector1.add(vector2);
        Vector3f expectedResult = new Vector3f(3.75f, 5.75f, 7.5f);
        Assertions.assertTrue(vector1.equals(expectedResult));
    }

    @Test
    void testMinus() {
        Vector3f vector1 = new Vector3f(4, 4, 4);
        Vector3f vector2 = new Vector3f(3, 2, 1);
        vector1.minus(vector2);
        Vector3f expectedResult = new Vector3f(1, 2, 3);
        Assertions.assertTrue(vector1.equals(expectedResult));
    }

    @Test
    void testMinusFloat() {
        Vector3f vector1 = new Vector3f(3.75f, 5.75f, 7.5f);
        Vector3f vector2 = new Vector3f(2.5f, 3.25f, 3.75f);
        vector1.minus(vector2);
        Vector3f expectedResult = new Vector3f(1.25f, 2.5f, 3.75f);
        Assertions.assertTrue(vector1.equals(expectedResult));
    }

    @Test
    void testMultiply() {
        Vector3f vector = new Vector3f(1, 2, 3);
        float k = 3;
        vector.multiply(k);
        Vector3f expectedResult = new Vector3f(3, 6, 9);
        Assertions.assertTrue(vector.equals(expectedResult));
    }

    @Test
    void testMultiplyFloat() {
        Vector3f vector = new Vector3f(1.25f, 2.5f, 3.75f);
        float k = 3;
        vector.multiply(k);
        Vector3f expectedResult = new Vector3f(3.75f, 7.5f, 11.25f);
        Assertions.assertTrue(vector.equals(expectedResult));
    }

    @Test
    void testDivide() {
        Vector3f vector = new Vector3f(3, 6, 9);
        float k = 3;
        vector.divide(k);
        Vector3f expectedResult = new Vector3f(1, 2, 3);
        Assertions.assertTrue(vector.equals(expectedResult));
    }

    @Test
    void testDivideFloat() {
        Vector3f vector = new Vector3f(3.75f, 7.5f, 11.25f);
        float k = 3;
        vector.divide(k);
        Vector3f expectedResult = new Vector3f(1.25f, 2.5f, 3.75f);
        Assertions.assertTrue(vector.equals(expectedResult));
    }

    @Test
    void testDivideZero() {
        Vector3f vector = new Vector3f(1, 1, 1);
        float k = 0;
        Assertions.assertThrows(AssertionError.class, () -> vector.divide(k));
    }

    @Test
    void testLength() {
        Vector3f vector = new Vector3f(1, 2, 3);
        float result = vector.length();
        float expectedResult = (float) Math.sqrt(14);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void testLengthFloat() {
        Vector3f vector = new Vector3f(-1.5f, -2.5f, -3.5f);
        float result = vector.length();
        float expectedResult = (float) Math.sqrt(20.75f);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void testDotProduct() {
        Vector3f vector1 = new Vector3f(1, 2, 3);
        Vector3f vector2 = new Vector3f(3, 2, 1);
        float result = vector1.dotProduct(vector2);
        float expectedResult = 10;
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void testDotProductFloat() {
        Vector3f vector1 = new Vector3f(-1.5f, -2.5f, -3.5f);
        Vector3f vector2 = new Vector3f(-1.5f, -2.5f, 3.5f);
        float result = vector1.dotProduct(vector2);
        float expectedResult = -3.75f;
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void testCrossProduct() {
        Vector3f vector1 = new Vector3f(1, 2, 3);
        Vector3f vector2 = new Vector3f(3, 2, 1);
        Vector3f result = vector1.crossProduct(vector2);
        Vector3f expectedResult = new Vector3f(-4, 8, -4);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    void testCrossProductFloat() {
        Vector3f vector1 = new Vector3f(-1.5f, -2.5f, -3.5f);
        Vector3f vector2 = new Vector3f(-1.5f, -2.5f, 3.5f);
        Vector3f result = vector1.crossProduct(vector2);
        Vector3f expectedResult = new Vector3f(-17.5f, 10.5f, 0);
        Assertions.assertTrue(result.equals(expectedResult));
    }
}