package com.vsu.cgcourse.math;

import java.util.Arrays;

import static com.vsu.cgcourse.math.Constants.EPS;

public class Matrix4f {

    private static final int length = 4;
    private final float[][] matrix;

    public Matrix4f() {
        matrix = new float[length][length];
    }

    public Matrix4f(float m) {
        matrix = new float[length][length];
        for (float[] arr : matrix) {
            Arrays.fill(arr, m);
        }
    }

    public Matrix4f(boolean isIdentity) {
        matrix = new float[length][length];
        if (isIdentity)
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][i] = 1;
            }
    }

    public Matrix4f(final float[][] values) {
        matrix = new float[length][length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(values[i], 0, matrix[i], 0, matrix[0].length);
        }
    }

    public Matrix4f(final float[] values) {
        matrix = new float[length][length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(values, i * matrix.length, matrix[i], 0, matrix[0].length);
        }
    }

    public boolean equals(final Matrix4f other) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                if (Math.abs(matrix[row][col] - other.matrix[row][col]) > EPS) {
                    return false;
                }
            }
        }
        return true;
    }

    public Matrix4f copy() {
        return new Matrix4f(matrix);
    }

    public void add(final Matrix4f other) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                matrix[row][col] += other.matrix[row][col];
            }
        }
    }

    public void minus(final Matrix4f other) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                matrix[row][col] -= other.matrix[row][col];
            }
        }
    }

    public void multiply(float k) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                matrix[row][col] *= k;
            }
        }
    }

    public Vector4f multiply(final Vector4f vector) {
        float[] values = new float[matrix.length];
        float[] vectorValues = vector.toArray();
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                values[row] += matrix[row][col] * vectorValues[col];
            }
        }
        return new Vector4f(values);
    }

    public void multiply(final Matrix4f other) {
        float[][] values = new float[matrix.length][matrix[0].length];
        for (int row = 0; row < matrix.length; row++) {
            int m = 0;
            for (int col = 0; col < matrix[0].length; col++) {
                values[row][col] = matrix[row][0] * other.matrix[0][m] +
                                matrix[row][1] * other.matrix[1][m] +
                                matrix[row][2] * other.matrix[2][m] +
                                matrix[row][3] * other.matrix[3][m];
                m++;
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(values[i], 0, matrix[i], 0, matrix[0].length);
        }
    }

    public void divide(float k) {
        assert (k != 0);
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                matrix[row][col] /= k;
            }
        }
    }

    public void transpose() {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                if (row < col) {
                    float temp = matrix[row][col];
                    matrix[row][col] = matrix[col][row];
                    matrix[col][row] = temp;
                }
            }
        }
    }

    private float determinant(final float[][] matrix) {
        if (matrix.length == 1) {
            return matrix[0][0];
        }
        float[] values = new float[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            float[][] minorMatrix = new float[matrix.length - 1][matrix[0].length - 1];
            for (int row = 1; row < matrix.length; row++) {
                int minorCol = 0;
                for (int col = 0; col < matrix[0].length; col++) {
                    if (i != col) {
                        minorMatrix[row - 1][minorCol] = matrix[row][col];
                        minorCol++;
                    }
                }
            }
            values[i] = matrix[0][i] * determinant(minorMatrix);
        }
        float sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum += values[i] * Math.pow(-1, i);
        }
        return sum;
    }

    public float determinant() {
        return determinant(matrix);
    }

    public void inverse() {
        float determinant = determinant();
        assert (determinant != 0);
        float[] algebraicComplementValues = new float[matrix.length * matrix[0].length];
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                float[][] minorMatrix = new float[matrix.length - 1][matrix[0].length - 1];
                int i = 0;
                for (int minorRow = 0; minorRow < matrix.length; minorRow++) {
                    int j = 0;
                    for (int minorCol = 0; minorCol < matrix[0].length; minorCol++) {
                        if (row != minorRow && col != minorCol) {
                            minorMatrix[i][j] = matrix[minorRow][minorCol];
                            j++;
                        }
                    }
                    if (row != minorRow) {
                        i++;
                    }
                }
                algebraicComplementValues[row * matrix.length + col] = determinant(minorMatrix);
            }
        }
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                if ((row + col) % 2 != 0) {
                    algebraicComplementValues[row * matrix.length  + col] *= -1;
                }
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(algebraicComplementValues, i * matrix.length, matrix[i], 0, matrix[0].length);
        }
        transpose();
        divide(determinant);
    }

    public Vector4f solveGaussian(final Vector4f vector) {
        float[][] matrix = copy().matrix;
        float[] values = vector.toArray();
        boolean[] emptyCols = new boolean[matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            int swapped = i + 1;
            float divider;
            while (true) {
                divider = matrix[i][i];
                if (divider == 0) {
                    if (swapped == matrix.length) {
                        emptyCols[i] = true;
                        for (int j = 0; j < i; j++) {
                            matrix[j][i] = 0;
                        }
                        break;
                    } else {
                        float[] tempArr = new float[matrix.length];
                        System.arraycopy(matrix[i], 0, tempArr, 0, matrix[0].length);
                        System.arraycopy(matrix[swapped], 0, matrix[i], 0, matrix[0].length);
                        System.arraycopy(tempArr, 0, matrix[swapped], 0, matrix[0].length);
                        float temp = values[i];
                        values[i] = values[swapped];
                        values[swapped] = temp;
                        swapped++;
                    }
                } else {
                    break;
                }
            }
            if (emptyCols[i]) {
                continue;
            }
            for (int j = i; j < matrix[0].length; j++) {
                matrix[i][j] /= divider;
            }
            values[i] /= divider;
            for (int j = 0; j < matrix.length; j++) {
                if (j == i) {
                    continue;
                }
                float factor = matrix[j][i];
                for (int k = i; k < matrix[0].length; k++) {
                    matrix[j][k] -= factor * matrix[i][k];
                }
                values[j] -= factor * values[i];
            }
        }
        for (int i = 0; i < emptyCols.length; i++) {
            assert !emptyCols[i] || (values[i] == 0);
        }
        return new Vector4f(values);
    }

    public float[][] toMatrix() {
        return matrix;
    }
}