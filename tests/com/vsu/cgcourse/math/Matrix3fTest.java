package com.vsu.cgcourse.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Matrix3fTest {

    @Test
    void testAdd() {
        Matrix3f matrix1 = new Matrix3f(new float[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}});
        Matrix3f matrix2 = new Matrix3f(1);
        matrix1.add(matrix2);
        Matrix3f expectedResult = new Matrix3f(new float[][]{
                {2, 3, 4},
                {5, 6, 7},
                {8, 9, 10}});
        Assertions.assertTrue(matrix1.equals(expectedResult));
    }

    @Test
    void testAddFloat() {
        Matrix3f matrix1 = new Matrix3f(new float[][]{
                {1.5f, 2.5f, 3.5f},
                {4.5f, 5.5f, 6.5f},
                {7.5f, 8.5f, 9.5f}});
        Matrix3f matrix2 = new Matrix3f(1.25f);
        matrix1.add(matrix2);
        Matrix3f expectedResult = new Matrix3f(new float[][]{
                {2.75f, 3.75f, 4.75f},
                {5.75f, 6.75f, 7.75f},
                {8.75f, 9.75f, 10.75f}});
        Assertions.assertTrue(matrix1.equals(expectedResult));
    }

    @Test
    void testMinus() {
        Matrix3f matrix1 = new Matrix3f(new float[][]{
                {2, 3, 4},
                {5, 6, 7},
                {8, 9, 10}});
        Matrix3f matrix2 = new Matrix3f(1);
        matrix1.minus(matrix2);
        Matrix3f expectedResult = new Matrix3f(new float[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}});
        Assertions.assertTrue(matrix1.equals(expectedResult));
    }

    @Test
    void testMinusFloat() {
        Matrix3f matrix1 = new Matrix3f(new float[][]{
                {2.75f, 3.75f, 4.75f},
                {5.75f, 6.75f, 7.75f},
                {8.75f, 9.75f, 10.75f}});
        Matrix3f matrix2 = new Matrix3f(1.25f);
        matrix1.minus(matrix2);
        Matrix3f expectedResult = new Matrix3f(new float[][]{
                {1.5f, 2.5f, 3.5f},
                {4.5f, 5.5f, 6.5f},
                {7.5f, 8.5f, 9.5f}});
        Assertions.assertTrue(matrix1.equals(expectedResult));
    }

    @Test
    void testMultiplyValue() {
        Matrix3f matrix = new Matrix3f(new float[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}});
        float k = 3;
        matrix.multiply(k);
        Matrix3f expectedResult = new Matrix3f(new float[][]{
                {3, 6, 9},
                {12, 15, 18},
                {21, 24, 27}});
        Assertions.assertTrue(matrix.equals(expectedResult));
    }

    @Test
    void testMultiplyFloatValue() {
        Matrix3f matrix = new Matrix3f(new float[][]{
                {1.5f, 2.5f, 3.5f},
                {4.5f, 5.5f, 6.5f},
                {7.5f, 8.5f, 9.5f}});
        float k = 3;
        matrix.multiply(k);
        Matrix3f expectedResult = new Matrix3f(new float[][]{
                {4.5f, 7.5f, 10.5f},
                {13.5f, 16.5f, 19.5f},
                {22.5f, 25.5f, 28.5f}});
        Assertions.assertTrue(matrix.equals(expectedResult));
    }

    @Test
    void testMultiplyZeroValue() {
        Matrix3f matrix = new Matrix3f(1);
        float k = 0;
        matrix.multiply(k);
        Matrix3f expectedResult = new Matrix3f();
        Assertions.assertTrue(matrix.equals(expectedResult));
    }

    @Test
    void testMultiplyVector() {
        Matrix3f matrix = new Matrix3f(new float[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}});
        Vector3f vector = new Vector3f(1, 2, 3);
        Vector3f result = matrix.multiply(vector);
        Vector3f expectedResult = new Vector3f(14, 32, 50);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    void testMultiplyFloatVector() {
        Matrix3f matrix = new Matrix3f(new float[][]{
                {1.5f, 2.5f, 3.5f},
                {4.5f, 5.5f, 6.5f},
                {7.5f, 8.5f, 9.5f}});
        Vector3f vector = new Vector3f(1, 2, 3);
        Vector3f result = matrix.multiply(vector);
        Vector3f expectedResult = new Vector3f(17, 35, 53);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    void testMultiplyZeroVector() {
        Matrix3f matrix = new Matrix3f(1);
        Vector3f vector = new Vector3f(0, 0, 0);
        Vector3f result = matrix.multiply(vector);
        Vector3f expectedResult = new Vector3f(0, 0, 0);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    void testMultiplyIdentityMatrix() {
        Matrix3f matrix1 = new Matrix3f(1);
        Matrix3f matrix2 = new Matrix3f(true);
        matrix1.multiply(matrix2);
        Matrix3f expectedResult = new Matrix3f(1);
        Assertions.assertTrue(matrix1.equals(expectedResult));
    }

    @Test
    void testMultiplyZeroMatrix() {
        Matrix3f matrix1 = new Matrix3f(1);
        Matrix3f matrix2 = new Matrix3f();
        matrix1.multiply(matrix2);
        Matrix3f expectedResult = new Matrix3f();
        Assertions.assertTrue(matrix1.equals(expectedResult));
    }

    @Test
    void testMultiplyMatrix() {
        Matrix3f matrix1 = new Matrix3f(new float[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}});
        Matrix3f matrix2 = new Matrix3f(new float[][]{
                {1, 0, -2},
                {0, -3, 1},
                {-1, 2, 4}});
        matrix1.multiply(matrix2);
        Matrix3f expectedResult = new Matrix3f(new float[][]{
                {-2, 0, 12},
                {-2, -3, 21},
                {-2, -6, 30}});
        Assertions.assertTrue(matrix1.equals(expectedResult));
    }

    @Test
    void testDivide() {
        Matrix3f matrix = new Matrix3f(new float[][]{
                {3, 6, 9},
                {12, 15, 18},
                {21, 24, 27}});
        float k = 3;
        matrix.divide(k);
        Matrix3f expectedResult = new Matrix3f(new float[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}});
        Assertions.assertTrue(matrix.equals(expectedResult));
    }

    @Test
    void testDivideFloat() {
        Matrix3f matrix = new Matrix3f(new float[][]{
                {4.5f, 7.5f, 10.5f},
                {13.5f, 16.5f, 19.5f},
                {22.5f, 25.5f, 28.5f}});
        float k = 3;
        matrix.divide(k);
        Matrix3f expectedResult = new Matrix3f(new float[][]{
                {1.5f, 2.5f, 3.5f},
                {4.5f, 5.5f, 6.5f},
                {7.5f, 8.5f, 9.5f}});
        Assertions.assertTrue(matrix.equals(expectedResult));
    }

    @Test
    void testDivideZero() {
        Matrix3f matrix = new Matrix3f(1);
        float k = 0;
        Assertions.assertThrows(AssertionError.class, () -> matrix.divide(k));
    }

    @Test
    void testTranspose() {
        Matrix3f matrix = new Matrix3f(new float[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}});
        matrix.transpose();
        Matrix3f expectedResult = new Matrix3f(new float[][]{
                {1, 4, 7},
                {2, 5, 8},
                {3, 6, 9}});
        Assertions.assertTrue(matrix.equals(expectedResult));
    }

    @Test
    public void testDeterminant() {
        Matrix3f matrix = new Matrix3f(new float[][]{
                {1, 2, 3},
                {-2, 0, -2},
                {-3, 2, 1}});
        float result = matrix.determinant();
        float expectedResult = 8;
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void testInverse() {
        Matrix3f matrix = new Matrix3f(new float[][]{
                {2, 5, 7},
                {6, 3, 4},
                {5, -2, -3}});
        matrix.inverse();
        Matrix3f expectedResult = new Matrix3f(new float[][]{
                {1, -1, 1},
                {-38, 41, -34},
                {27, -29, 24}});
        Assertions.assertTrue(matrix.equals(expectedResult));
    }

    @Test
    public void testInverseMatrixMultiplication () {
        Matrix3f matrix = new Matrix3f(new float[][]{
                {2, 5, 7},
                {6, 3, 4},
                {5, -2, -3}});
        Matrix3f copy = matrix.copy();
        copy.inverse();
        matrix.multiply(copy);
        Matrix3f expectedResult = new Matrix3f(true);
        Assertions.assertTrue(matrix.equals(expectedResult));
    }

    @Test
    public void testSolveGaussianOneSolution() {
        Matrix3f matrix = new Matrix3f(new float[][]{
                {3, 2, -5},
                {2, -1, 3},
                {1, 2, -1}});
        Vector3f vector = new Vector3f(-1, 13, 9);
        Vector3f result = matrix.solveGaussian(vector);
        Vector3f expectedResult = new Vector3f(3, 5, 4);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    public void testSolveGaussianZeroSolution() {
        Matrix3f matrix = new Matrix3f(new float[][]{
                {4, -3, 2},
                {3, -2, 1},
                {5, -3, 1}});
        Vector3f vector = new Vector3f(8, 7, 1);
        Assertions.assertThrows(AssertionError.class, () -> matrix.solveGaussian(vector));
    }

    @Test
    public void testSolveGaussianManySolutions() {
        Matrix3f matrix = new Matrix3f(new float[][]{
                {2, 3, -1},
                {8, 12, -9},
                {4, 6, 3}});
        Vector3f vector = new Vector3f(1, 3, 3);
        Vector3f result = matrix.solveGaussian(vector);
        Vector3f expectedResult = new Vector3f(0.6f, 0, 0.2f);
        Assertions.assertTrue(result.equals(expectedResult));
    }
}