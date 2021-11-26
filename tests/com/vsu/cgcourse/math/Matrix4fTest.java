package com.vsu.cgcourse.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Matrix4fTest {

    @Test
    void testAdd() {
        Matrix4f matrix1 = new Matrix4f(new float[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}});
        Matrix4f matrix2 = new Matrix4f(1);
        matrix1.add(matrix2);
        Matrix4f expectedResult = new Matrix4f(new float[][]{
                {2, 3, 4, 5},
                {6, 7, 8, 9},
                {10, 11, 12, 13},
                {14, 15, 16, 17}});
        Assertions.assertTrue(matrix1.equals(expectedResult));
    }

    @Test
    void testAddFloat() {
        Matrix4f matrix1 = new Matrix4f(new float[][]{
                {1.5f, 2.5f, 3.5f, 4.5f},
                {5.5f, 6.5f, 7.5f, 8.5f},
                {9.5f, 10.5f, 11.5f, 12.5f},
                {13.5f, 14.5f, 15.5f, 16.5f},});
        Matrix4f matrix2 = new Matrix4f(1.25f);
        matrix1.add(matrix2);
        Matrix4f expectedResult = new Matrix4f(new float[][]{
                {2.75f, 3.75f, 4.75f, 5.75f},
                {6.75f, 7.75f, 8.75f, 9.75f},
                {10.75f, 11.75f, 12.75f, 13.75f},
                {14.75f, 15.75f, 16.75f, 17.75f}});
        Assertions.assertTrue(matrix1.equals(expectedResult));
    }

    @Test
    void testMinus() {
        Matrix4f matrix1 = new Matrix4f(new float[][]{
                {2, 3, 4, 5},
                {6, 7, 8, 9},
                {10, 11, 12, 13},
                {14, 15, 16, 17}});
        Matrix4f matrix2 = new Matrix4f(1);
        matrix1.minus(matrix2);
        Matrix4f expectedResult = new Matrix4f(new float[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}});
        Assertions.assertTrue(matrix1.equals(expectedResult));
    }

    @Test
    void testMinusFloat() {
        Matrix4f matrix1 = new Matrix4f(new float[][]{
                {2.75f, 3.75f, 4.75f, 5.75f},
                {6.75f, 7.75f, 8.75f, 9.75f},
                {10.75f, 11.75f, 12.75f, 13.75f},
                {14.75f, 15.75f, 16.75f, 17.75f}});
        Matrix4f matrix2 = new Matrix4f(1.25f);
        matrix1.minus(matrix2);
        Matrix4f expectedResult = new Matrix4f(new float[][]{
                {1.5f, 2.5f, 3.5f, 4.5f},
                {5.5f, 6.5f, 7.5f, 8.5f},
                {9.5f, 10.5f, 11.5f, 12.5f},
                {13.5f, 14.5f, 15.5f, 16.5f},});
        Assertions.assertTrue(matrix1.equals(expectedResult));
    }

    @Test
    void testMultiplyValue() {
        Matrix4f matrix = new Matrix4f(new float[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}});
        float k = 3;
        matrix.multiply(k);
        Matrix4f expectedResult = new Matrix4f(new float[][]{
                {3, 6, 9, 12},
                {15, 18, 21, 24},
                {27, 30, 33, 36},
                {39, 42, 45, 48}});
        Assertions.assertTrue(matrix.equals(expectedResult));
    }

    @Test
    void testMultiplyFloatValue() {
        Matrix4f matrix = new Matrix4f(new float[][]{
                {1.5f, 2.5f, 3.5f, 4.5f},
                {5.5f, 6.5f, 7.5f, 8.5f},
                {9.5f, 10.5f, 11.5f, 12.5f},
                {13.5f, 14.5f, 15.5f, 16.5f},});
        float k = 3;
        matrix.multiply(k);
        Matrix4f expectedResult = new Matrix4f(new float[][]{
                {4.5f, 7.5f, 10.5f, 13.5f},
                {16.5f, 19.5f, 22.5f, 25.5f},
                {28.5f, 31.5f, 34.5f, 37.5f},
                {40.5f, 43.5f, 46.5f, 49.5f}});
        Assertions.assertTrue(matrix.equals(expectedResult));
    }

    @Test
    void testMultiplyZeroValue() {
        Matrix4f matrix = new Matrix4f(1);
        float k = 0;
        matrix.multiply(k);
        Matrix4f expectedResult = new Matrix4f();
        Assertions.assertTrue(matrix.equals(expectedResult));
    }

    @Test
    void testMultiplyVector() {
        Matrix4f matrix = new Matrix4f(new float[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}});
        Vector4f vector = new Vector4f(1, 2, 3, 4);
        Vector4f result = matrix.multiply(vector);
        Vector4f expectedResult = new Vector4f(30, 70, 110, 150);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    void testMultiplyFloatVector() {
        Matrix4f matrix = new Matrix4f(new float[][]{
                {1.5f, 2.5f, 3.5f, 4.5f},
                {5.5f, 6.5f, 7.5f, 8.5f},
                {9.5f, 10.5f, 11.5f, 12.5f},
                {13.5f, 14.5f, 15.5f, 16.5f},});
        Vector4f vector = new Vector4f(1, 2, 3, 4);
        Vector4f result = matrix.multiply(vector);
        Vector4f expectedResult = new Vector4f(35, 75, 115, 155);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    void testMultiplyZeroVector() {
        Matrix4f matrix = new Matrix4f(1);
        Vector4f vector = new Vector4f(0, 0, 0, 0);
        Vector4f result = matrix.multiply(vector);
        Vector4f expectedResult = new Vector4f(0, 0, 0 ,0);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    void testMultiplyIdentityMatrix() {
        Matrix4f matrix1 = new Matrix4f(1);
        Matrix4f matrix2 = new Matrix4f(true);
        matrix1.multiply(matrix2);
        Matrix4f expectedResult = new Matrix4f(1);
        Assertions.assertTrue(matrix1.equals(expectedResult));
    }

    @Test
    void testMultiplyZeroMatrix() {
        Matrix4f matrix1 = new Matrix4f(1);
        Matrix4f matrix2 = new Matrix4f();
        matrix1.multiply(matrix2);
        Matrix4f expectedResult = new Matrix4f();
        Assertions.assertTrue(matrix1.equals(expectedResult));
    }

    @Test
    void testMultiplyMatrix() {
        Matrix4f matrix1 = new Matrix4f(new float[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}});
        Matrix4f matrix2 = new Matrix4f(new float[][]{
                {1, 0, -2, 4},
                {3, 4, 2, -2},
                {2, -1, 0, -3},
                {0, 5, 2, 1}});
        matrix1.multiply(matrix2);
        Matrix4f expectedResult = new Matrix4f(new float[][]{
                {13, 25, 10, -5},
                {37, 57, 18, -5},
                {61, 89, 26, -5},
                {85, 121, 34, -5}});
        Assertions.assertTrue(matrix1.equals(expectedResult));
    }

    @Test
    void testDivide() {
        Matrix4f matrix = new Matrix4f(new float[][]{
                {3, 6, 9, 12},
                {15, 18, 21, 24},
                {27, 30, 33, 36},
                {39, 42, 45, 48}});
        float k = 3;
        matrix.divide(k);
        Matrix4f expectedResult = new Matrix4f(new float[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}});
        Assertions.assertTrue(matrix.equals(expectedResult));
    }

    @Test
    void testDivideFloat() {
        Matrix4f matrix = new Matrix4f(new float[][]{
                {4.5f, 7.5f, 10.5f, 13.5f},
                {16.5f, 19.5f, 22.5f, 25.5f},
                {28.5f, 31.5f, 34.5f, 37.5f},
                {40.5f, 43.5f, 46.5f, 49.5f}});
        float k = 3;
        matrix.divide(k);
        Matrix4f expectedResult = new Matrix4f(new float[][]{
                {1.5f, 2.5f, 3.5f, 4.5f},
                {5.5f, 6.5f, 7.5f, 8.5f},
                {9.5f, 10.5f, 11.5f, 12.5f},
                {13.5f, 14.5f, 15.5f, 16.5f},});
        Assertions.assertTrue(matrix.equals(expectedResult));
    }

    @Test
    void testDivideZero() {
        Matrix4f matrix = new Matrix4f(1);
        float k = 0;
        Assertions.assertThrows(AssertionError.class, () -> matrix.divide(k));
    }

    @Test
    void testTranspose() {
        Matrix4f matrix = new Matrix4f(new float[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}});
        matrix.transpose();
        Matrix4f expectedResult = new Matrix4f(new float[][]{
                {1, 5, 9, 13},
                {2, 6, 10, 14},
                {3, 7, 11, 15},
                {4, 8, 12, 16}});
        Assertions.assertTrue(matrix.equals(expectedResult));
    }

    @Test
    public void testDeterminant() {
        Matrix4f matrix = new Matrix4f(new float[][]{
                {3, -2, 1, 1},
                {5, 1, 2, 0},
                {-1, 1, -1, 1},
                {2, -1, 6, -3}});
        float result = matrix.determinant();
        float expectedResult = -69;
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void testInverse() {
        Matrix4f matrix = new Matrix4f(new float[][]{
                {1, 2, 3, 4},
                {0, 1, -3, 0},
                {-2, 2, 0, 3},
                {1, 3, -1, 2}});
        matrix.inverse();
        Matrix4f expectedResult = new Matrix4f(new float[][]{
                {12f / 37, 13f / 37, -14f / 37, -3f / 37},
                {-21f / 37, -32f / 37, 6f / 37, 33f / 37},
                {-7f / 37, -23f / 37, 2f / 37, 11f / 37},
                {22 / 37f, 30f / 37, -1f / 37, -24f / 37}});
        Assertions.assertTrue(matrix.equals(expectedResult));
    }

    @Test
    public void testInverseMatrixMultiplication() {
        Matrix4f matrix = new Matrix4f(new float[][]{
                {1, 2, 3, 4},
                {0, 1, -3, 0},
                {-2, 2, 0, 3},
                {1, 3, -1, 2}});
        Matrix4f copy = matrix.copy();
        copy.inverse();
        matrix.multiply(copy);
        Matrix4f expectedResult = new Matrix4f(true);
        Assertions.assertTrue(matrix.equals(expectedResult));
    }

    @Test
    public void testSolveGaussianOneSolution() {
        Matrix4f matrix = new Matrix4f(new float[][]{
                {2, 5, 4, 1},
                {1, 3, 2, 1},
                {2, 10, 9, 7},
                {3, 8, 9, 2}});
        Vector4f vector = new Vector4f(20, 11, 40, 37);
        Vector4f result = matrix.solveGaussian(vector);
        Vector4f expectedResult = new Vector4f(1, 2, 2, 0);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    public void testSolveGaussianZeroSolution() {
        Matrix4f matrix = new Matrix4f(new float[][]{
                {4, -3, 2, -1},
                {3, -2, 1, -3},
                {5, -3, 1, -8},
                {2, 5, -3, 6}});
        Vector4f vector = new Vector4f(8, 7, 1, 4);
        Assertions.assertThrows(AssertionError.class, () -> matrix.solveGaussian(vector));
    }

    @Test
    public void testSolveGaussianManySolution() {
        Matrix4f matrix = new Matrix4f(new float[][]{
                {2, 3, -1, 1},
                {8, 12, -9, 8},
                {4, 6, 3, -2},
                {2, 3, 9, -7}});
        Vector4f vector = new Vector4f(1, 3, 3, 3);
        Vector4f result = matrix.solveGaussian(vector);
        Vector4f expectedResult = new Vector4f(0.6f, 0, 0.2f, 0);
        Assertions.assertTrue(result.equals(expectedResult));
    }
}