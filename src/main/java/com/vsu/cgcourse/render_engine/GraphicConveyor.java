package com.vsu.cgcourse.render_engine;
import com.vsu.cgcourse.math.*;

public class GraphicConveyor {

    public static Matrix4f rotateScaleTranslate(final Vector3f scale,
                                                final Vector3f rotation,
                                                final Vector3f translation) {
        Matrix4f scaleMatrix = scaleMatrix(scale);
        Matrix4f rotationXMatrix = rotationXMatrix(rotation);
        Matrix4f rotationYMatrix = rotationYMatrix(rotation);
        Matrix4f rotationZMatrix = rotationZMatrix(rotation);
        Matrix4f translationMatrix = translationMatrix(translation);
        rotationXMatrix.multiply(scaleMatrix);
        rotationYMatrix.multiply(rotationXMatrix);
        rotationZMatrix.multiply(rotationYMatrix);
        translationMatrix.multiply(rotationZMatrix);
        return translationMatrix;
    }

    private static Matrix4f scaleMatrix(final Vector3f scale) {
        final Matrix4f scaleMatrix = new Matrix4f(true);
        for (int row = 0; row < scale.vector.length; row++) {
            scaleMatrix.matrix[row][row] = scale.vector[row];
        }
        return scaleMatrix;
    }

    private static Matrix4f rotationXMatrix(final Vector3f rotation) {
        final Matrix4f rotationXMatrix = new Matrix4f(true);
        rotationXMatrix.matrix[1][1] = (float) Math.cos(rotation.vector[0]);
        rotationXMatrix.matrix[1][2] = (float) - Math.sin(rotation.vector[0]);
        rotationXMatrix.matrix[2][1] = (float) Math.sin(rotation.vector[0]);
        rotationXMatrix.matrix[2][2] = (float) Math.cos(rotation.vector[0]);
        return rotationXMatrix;
    }

    private static Matrix4f rotationYMatrix(final Vector3f rotation) {
        final Matrix4f rotationYMatrix = new Matrix4f(true);
        rotationYMatrix.matrix[0][0] = (float) Math.cos(rotation.vector[1]);
        rotationYMatrix.matrix[0][2] = (float) Math.sin(rotation.vector[1]);
        rotationYMatrix.matrix[2][0] = (float) - Math.sin(rotation.vector[1]);
        rotationYMatrix.matrix[2][2] = (float) Math.cos(rotation.vector[1]);
        return rotationYMatrix;

    }

    private static Matrix4f rotationZMatrix(final Vector3f rotation) {
        final Matrix4f rotationZMatrix = new Matrix4f(true);
        rotationZMatrix.matrix[0][0] = (float) Math.cos(rotation.vector[2]);
        rotationZMatrix.matrix[0][1] = (float) - Math.sin(rotation.vector[2]);
        rotationZMatrix.matrix[1][0] = (float) Math.sin(rotation.vector[2]);
        rotationZMatrix.matrix[1][1] = (float) Math.cos(rotation.vector[2]);
        return rotationZMatrix;
    }

    private static Matrix4f translationMatrix(final Vector3f translation) {
        final Matrix4f translationMatrix = new Matrix4f(true);
        for (int i = 0; i < translation.vector.length; i++) {
            translationMatrix.matrix[i][3] = translation.vector[i];
        }
        return translationMatrix;
    }

    public static Matrix4f lookAt(Vector3f eye, Vector3f target) {
        return lookAt(eye, target, new Vector3f(0F, 1.0F, 0F));
    }

    public static Matrix4f lookAt(final Vector3f eye, final Vector3f target, final Vector3f up) {
        Vector3f resultX = up.copy();
        Vector3f resultZ = target.copy();

        resultZ.minus(eye);
        resultX = resultX.crossProduct(resultZ);

        Vector3f resultY = resultZ.crossProduct(resultX);

        resultX.normalize();
        resultY.normalize();
        resultZ.normalize();

        float[] matrix = new float[]{
                resultX.vector[0], resultX.vector[1], resultX.vector[2], -resultX.dotProduct(eye),
                resultY.vector[0], resultY.vector[1], resultY.vector[2], -resultY.dotProduct(eye),
                resultZ.vector[0], resultZ.vector[1], resultZ.vector[2], -resultZ.dotProduct(eye),
                0, 0, 0, 1};
        return new Matrix4f(matrix);
    }

    public static Matrix4f perspective(
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        final float tangentMinusOnDegree = (float) (1.0F / (Math.tan(fov * 0.5F)));
        final float[][] result = new float[4][4];
        result[0][0] = tangentMinusOnDegree / aspectRatio;
        result[1][1] = tangentMinusOnDegree;
        result[2][2] = (farPlane + nearPlane) / (farPlane - nearPlane);
        result[2][3] = 2 * (nearPlane * farPlane) / (nearPlane - farPlane);
        result[3][2] = 1.0F;
        return new Matrix4f(result);
    }

    public static Vector2f vertexToPoint(final Vector3f vertex, final int width, final int height) {
        return new Vector2f(vertex.vector[0] * width + width / 2.0F, -vertex.vector[1] * height + height / 2.0F);
    }
}
