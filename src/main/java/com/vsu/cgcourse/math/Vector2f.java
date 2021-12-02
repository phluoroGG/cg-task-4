package com.vsu.cgcourse.math;

import static com.vsu.cgcourse.math.Constants.EPS;

public class Vector2f {

    private final int length = 2;
    public final float[] vector;

    public Vector2f(float x, float y) {
        vector = new float[length];
        vector[0] = x;
        vector[1] = y;
    }

    public Vector2f(final float[] values) {
        vector = new float[length];
        System.arraycopy(values, 0, vector, 0, vector.length);
    }

    public boolean equals(final Vector2f other) {
        return Math.abs(vector[0] - other.vector[0]) <= EPS &&
                Math.abs(vector[1] - other.vector[1]) <= EPS;
    }

    public Vector2f copy() {
        return new Vector2f(vector);
    }

    public void add(final Vector2f other) {
        for (int i = 0; i < vector.length; i++) {
            vector[i] += other.vector[i];
        }
    }

    public void minus(final Vector2f other) {
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
                vector[1] * vector[1]);
    }

    public void normalize() {
        divide(length());
    }

    public float dotProduct(final Vector2f other) {
        return vector[0] * other.vector[0] +
                vector[1] * other.vector[1];
    }

    public float[] toArray() {
        return new float[]{vector[0], vector[1]};
    }
}
