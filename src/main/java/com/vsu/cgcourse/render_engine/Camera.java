package com.vsu.cgcourse.render_engine;


import com.vsu.cgcourse.math.Matrix4f;
import com.vsu.cgcourse.math.Vector3f;

public class Camera {

    private Vector3f position;
    private Vector3f target;
    private float fov;
    private float aspectRatio;
    private float nearPlane;
    private float farPlane;
    private float radius;
    public boolean isBack = false;

    public Camera(
            final Vector3f position,
            final Vector3f target,
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        this.position = position;
        this.target = target;
        this.fov = fov;
        this.aspectRatio = aspectRatio;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
        radius = new Vector3f(
                position.vector[0] - target.vector[0],
                position.vector[1] - target.vector[1],
                position.vector[2] - target.vector[2]).length();
    }



    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getTarget() {
        return target;
    }


    public void setPosition(final Vector3f position) {
        this.position = position;
        radius = position.length();
    }

    public void setTarget(final Vector3f target) {
        this.target = target;
    }

    public void setAspectRatio(final float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public void movePosition(final Vector3f translation) {
        if (isBack) {
            this.position.minus(translation);
        } else {
            this.position.add(translation);
        }
        radius = position.length();
    }

    public void moveTarget(final Vector3f translation) {
        this.target.add(translation);
    }

    public Matrix4f getViewMatrix() {
        return GraphicConveyor.lookAt(position, target);
    }

    public Matrix4f getProjectionMatrix() {
        return GraphicConveyor.perspective(fov, aspectRatio, nearPlane, farPlane);
    }

    public float getRadius() {
        return radius;
    }

    public Vector3f getAngles() {
        Vector3f vector2 = position.copy();
        Vector3f vector1 = target.copy();
        vector1.minus(vector2);
        vector1.normalize();
        vector1.vector[0] = (float) Math.asin(vector1.vector[0]);
        vector1.vector[2] = (float) Math.asin(vector1.vector[2]);
        return vector1;
    }

    public float findZ(float x, float y) {
        if (isBack) {
            x = position.vector[0] - x;
            y = position.vector[1] - y;
        } else {
            x = position.vector[0] + x;
            y = position.vector[1] + y;
        }
        if (x * x + y * y > radius * radius) {
            x = 2 * position.vector[0] - x;
            y = 2 * position.vector[1] - y;
            isBack = !isBack;
        }
        if (isBack) {
            return (float) Math.sqrt(radius * radius - x * x - y * y) + position.vector[2];
        }
        return (float) Math.sqrt(radius * radius - x * x - y * y) - position.vector[2];
    }
}