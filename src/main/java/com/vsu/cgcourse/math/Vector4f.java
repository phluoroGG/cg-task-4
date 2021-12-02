package com.vsu.cgcourse.math;

import static com.vsu.cgcourse.math.Constants.EPS;

public class Vector4f {

    private final int length = 4;
    public final float[] vector;

    public Vector4f(float x, float y, float z, float w) {
        vector = new float[length];
        vector[0] = x;
        vector[1] = y;
        vector[2] = z;
        vector[3] = w;
    }

    public Vector4f(final float[] values) {
        vector = new float[length];
        System.arraycopy(values, 0, vector, 0, vector.length);
    }

    public boolean equals(Vector4f other) {
        return Math.abs(vector[0] - other.vector[0]) <= EPS &&
                Math.abs(vector[1] - other.vector[1]) <= EPS &&
                Math.abs(vector[2] - other.vector[2]) <= EPS &&
                Math.abs(vector[3] - other.vector[3]) <= EPS;
    }

    public Vector4f copy() {
        return new Vector4f(vector);
    }

    public void add(final Vector4f other) {
        for (int i = 0; i < vector.length; i++) {
            vector[i] += other.vector[i];
        }
    }

    public void minus(final Vector4f other) {
        for (int i = 0; i < vector.length; i++) {
            vector[i] -= other.vector[i];
        }
    }

    public void multiply(float k) {
        for (int i = 0; i < vector.length; i++) {
            vector[i] *= k;
        }
    }

    public void divide(float k) {
        assert (k != 0);
        for (int i = 0; i < vector.length; i++) {
            vector[i] /= k;
        }
    }

    public float length() {
        return (float) Math.sqrt(vector[0] * vector[0] +
                vector[1] * vector[1] +
                vector[2] * vector[2] +
                vector[3] * vector[3]);
    }

    public void normalize() {
        divide(length());
    }

    public float dotProduct(final Vector4f other) {
        return vector[0] * other.vector[0] +
                vector[1] * other.vector[1] +
                vector[2] * other.vector[2] +
                vector[3] * other.vector[3];
    }

    public float[] toArray() {
        return new float[]{vector[0], vector[1], vector[2], vector[3]};
    }
}
