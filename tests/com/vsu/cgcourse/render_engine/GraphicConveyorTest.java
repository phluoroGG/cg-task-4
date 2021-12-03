package com.vsu.cgcourse.render_engine;


import com.vsu.cgcourse.math.Matrix4f;
import com.vsu.cgcourse.math.Vector3f;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GraphicConveyorTest {

    @Test
    public void testRotateScaleTranslateDefault() {
        Vector3f scale = new Vector3f(1, 1, 1);
        Vector3f rotation = new Vector3f(0, 0, 0);
        Vector3f translation = new Vector3f(0, 0, 0);
        Matrix4f result = GraphicConveyor.rotateScaleTranslate(scale, rotation, translation);
        Matrix4f expectedResult = new Matrix4f(true);
        Assertions.assertTrue(expectedResult.equals(result));
    }

    @Test
    public void testRotateScaleTranslate() {
        Vector3f scale = new Vector3f(5, 1, 0.5f);
        Vector3f rotation = new Vector3f((float) Math.PI / 4, (float) Math.PI / 3, (float) Math.PI / 2);
        Vector3f translation = new Vector3f(-10, 5, 10);
        Matrix4f result = GraphicConveyor.rotateScaleTranslate(scale, rotation, translation);
        Matrix4f expectedResult = new Matrix4f(new float[]{
                0, -0.70710677f, 0.35355338f, -10,
                2.5f, 0.6123724f, 0.30618626f, 5,
                -4.3301272f, 0.35355335f, 0.17677668f, 10,
                0, 0, 0, 1});
        Assertions.assertTrue(expectedResult.equals(result));
    }

}