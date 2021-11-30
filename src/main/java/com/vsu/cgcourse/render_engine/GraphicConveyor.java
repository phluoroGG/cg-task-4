package com.vsu.cgcourse.render_engine;
import com.vsu.cgcourse.math.*;

public class GraphicConveyor {

    public static Matrix4f rotateScaleTranslate() {
        float[] matrix = new float[]{
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1};
        return new Matrix4f(matrix);
    }

    public static Matrix4f lookAt(Vector3f eye, Vector3f target) {
        return lookAt(eye, target, new Vector3f(0F, 1.0F, 0F));
    }

    public static Matrix4f lookAt(Vector3f eye, Vector3f target, Vector3f up) {
        Vector3f resultX = up.copy();
        Vector3f resultZ = target.copy();

        resultZ.minus(eye);
        resultX = resultX.crossProduct(resultZ);

        Vector3f resultY = resultZ.crossProduct(resultX);

        resultX.normalize();
        resultY.normalize();
        resultZ.normalize();

        float[] resultXArray = resultX.toArray();
        float[] resultYArray = resultY.toArray();
        float[] resultZArray = resultZ.toArray();

        float[] matrix = new float[]{
                resultXArray[0], resultYArray[0], resultZArray[0], 0,
                resultXArray[1], resultYArray[1], resultZArray[1], 0,
                resultXArray[2], resultYArray[2], resultZArray[2], 0,
                -resultX.dotProduct(eye), -resultY.dotProduct(eye), -resultZ.dotProduct(eye), 1};
        return new Matrix4f(matrix);
    }

    public static Matrix4f perspective(
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        float tangentMinusOnDegree = (float) (1.0F / (Math.tan(fov * 0.5F)));
        float[][] result = new float[4][4];
        result[0][0] = tangentMinusOnDegree / aspectRatio;
        result[1][1] = tangentMinusOnDegree;
        result[2][2] = (farPlane + nearPlane) / (farPlane - nearPlane);
        result[2][3] = 1.0F;
        result[3][2] = 2 * (nearPlane * farPlane) / (nearPlane - farPlane);
        return new Matrix4f(result);
    }

    public static Vector3f multiplyMatrix4ByVector3(final Matrix4f matrix, final Vector3f vertex) {
        float[] vectorArray = vertex.toArray();
        float[][] matrixArray = matrix.toMatrix();
        final float x = (matrixArray[0][0] * vectorArray[0]) +
                (matrixArray[0][1] * vectorArray[1]) +
                (matrixArray[0][2] * vectorArray[2]) +
                matrixArray[0][3];
        final float y = (matrixArray[1][0] * vectorArray[0]) +
                (matrixArray[1][1] * vectorArray[1]) +
                (matrixArray[1][2] * vectorArray[2]) +
                matrixArray[1][3];
        final float z = (matrixArray[2][0] * vectorArray[0]) +
                (matrixArray[2][1] * vectorArray[1]) +
                (matrixArray[2][2] * vectorArray[2]) +
                matrixArray[2][3];
        final float w = (matrixArray[3][0] * vectorArray[0]) +
                (matrixArray[3][1] * vectorArray[1]) +
                (matrixArray[3][2] * vectorArray[2]) +
                matrixArray[3][3];
        return new Vector3f(x / w, y / w, z / w);
    }

    public static Vector2f vertexToPoint(final Vector3f vertex, final int width, final int height) {
        float[] vectorArray = vertex.toArray();
        return new Vector2f(vectorArray[0] * width + width / 2.0F, -vectorArray[1] * height + height / 2.0F);
    }
}
