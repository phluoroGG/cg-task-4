package com.vsu.cgcourse.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Vector2fTest {

    @Test
    void testAdd() {
        Vector2f vector1 = new Vector2f(1, 2);
        Vector2f vector2 = new Vector2f(2, 1);
        vector1.add(vector2);
        Vector2f expectedResult = new Vector2f(3, 3);
        Assertions.assertTrue(vector1.equals(expectedResult));
    }

    @Test
    void testAddFloat() {
        Vector2f vector1 = new Vector2f(1.25f, 2.5f);
        Vector2f vector2 = new Vector2f(2.5f, 3.25f);
        vector1.add(vector2);
        Vector2f expectedResult = new Vector2f(3.75f, 5.75f);
        Assertions.assertTrue(vector1.equals(expectedResult));
    }

    @Test
    void testMinus() {
        Vector2f vector1 = new Vector2f(3, 3);
        Vector2f vector2 = new Vector2f(2, 1);
        vector1.minus(vector2);
        Vector2f expectedResult = new Vector2f(1, 2);
        Assertions.assertTrue(vector1.equals(expectedResult));
    }

    @Test
    void testMinusFloat() {
        Vector2f vector1 = new Vector2f(3.75f, 5.75f);
        Vector2f vector2 = new Vector2f(2.5f, 3.25f);
        vector1.minus(vector2);
        Vector2f expectedResult = new Vector2f(1.25f, 2.5f);
        Assertions.assertTrue(vector1.equals(expectedResult));
    }

    @Test
    void testMultiply() {
        Vector2f vector = new Vector2f(1, 2);
        float k = 3;
        vector.multiply(k);
        Vector2f expectedResult = new Vector2f(3, 6);
        Assertions.assertTrue(vector.equals(expectedResult));
    }

    @Test
    void testMultiplyFloat() {
        Vector2f vector = new Vector2f(1.25f, 2.5f);
        float k = 3;
        vector.multiply(k);
        Vector2f expectedResult = new Vector2f(3.75f, 7.5f);
        Assertions.assertTrue(vector.equals(expectedResult));
    }

    @Test
    void testDivide() {
        Vector2f vector = new Vector2f(3, 6);
        float k = 3;
        vector.divide(k);
        Vector2f expectedResult = new Vector2f(1, 2);
        Assertions.assertTrue(vector.equals(expectedResult));
    }

    @Test
    void testDivideFloat() {
        Vector2f vector = new Vector2f(3.75f, 7.5f);
        float k = 3;
        vector.divide(k);
        Vector2f expectedResult = new Vector2f(1.25f, 2.5f);
        Assertions.assertTrue(vector.equals(expectedResult));
    }

    @Test
    void testDivideZero() {
        Vector2f vector = new Vector2f(1, 1);
        float k = 0;
        Assertions.assertThrows(AssertionError.class, () -> vector.divide(k));
    }

    @Test
    void testLength() {
        Vector2f vector = new Vector2f(1, 2);
        float result = vector.length();
        float expectedResult = (float) Math.sqrt(5);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void testLengthFloat() {
        Vector2f vector = new Vector2f(-1.5f, -2.5f);
        float result = vector.length();
        float expectedResult = (float) Math.sqrt(8.5);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void testDotProduct() {
        Vector2f vector1 = new Vector2f(1, 2);
        Vector2f vector2 = new Vector2f(2, 1);
        float result = vector1.dotProduct(vector2);
        float expectedResult = 4;
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void testDotProductFloat() {
        Vector2f vector1 = new Vector2f(-1.5f, -2.5f);
        Vector2f vector2 = new Vector2f(-2.5f, 3.5f);
        float result = vector1.dotProduct(vector2);
        float expectedResult = -5;
        Assertions.assertEquals(expectedResult, result);
    }
}