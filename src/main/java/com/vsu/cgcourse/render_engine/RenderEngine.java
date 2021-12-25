package com.vsu.cgcourse.render_engine;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import com.vsu.cgcourse.math.*;
import com.vsu.cgcourse.model.Mesh;

import static com.vsu.cgcourse.render_engine.GraphicConveyor.*;

public class RenderEngine {

    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Mesh mesh,
            final int width,
            final int height,
            final boolean drawLine,
            final boolean draw) {
        final Matrix4f modelMatrix = mesh.getModelMatrix();
        final Matrix4f viewMatrix = camera.getViewMatrix();
        final Matrix4f projectionMatrix = camera.getProjectionMatrix();

        viewMatrix.multiply(modelMatrix);
        projectionMatrix.multiply(viewMatrix);
        final Matrix4f modelViewProjectionMatrix = projectionMatrix.copy();

        final int nPolygons = mesh.polygonVertexIndices.size();
        /*int[] depth = new int[nPolygons];

        Thread[] threads = new Thread[Runtime.getRuntime().availableProcessors()];
        for (int k = 0; k < threads.length; k++) {
            threads[k] = new Thread(() -> {
                for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
                    final int nVerticesInPolygon = mesh.polygonVertexIndices.get(polygonInd).size();
                    for (int i = 0; i < nVerticesInPolygon; i++) {
                        for (int j = polygonInd + 1; j < nPolygons; j++) {
                            ArrayList<Vector3f> triangle = new ArrayList<>();
                            triangle.add(mesh.vertices.get(mesh.polygonVertexIndices.get(j).get(0)).copy());
                            triangle.add(mesh.vertices.get(mesh.polygonVertexIndices.get(j).get(1)).copy());
                            triangle.add(mesh.vertices.get(mesh.polygonVertexIndices.get(j).get(2)).copy());
                            Vector3f position = camera.getPosition().copy();
                            position.minus(mesh.vertices.get(mesh.polygonVertexIndices.get(polygonInd).get(i)));
                            Vector3f vector3f = position.copy();
                            if (rayIntersectsTriangle(camera.getPosition().copy(), position, triangle)) {
                                Vector3f vector3f1 = camera.getPosition().copy();
                                vector3f1.minus(position);
                                if (vector3f.length() < vector3f1.length()) {
                                    depth[j]++;
                                } else if (vector3f.length() > vector3f1.length()) {
                                    depth[polygonInd]++;
                                }
                            }
                        }
                    }
                }
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }*/

        ArrayList<ArrayList<Integer>> polygons = new ArrayList<>(mesh.polygonVertexIndices);
        //sortByFirst(depth, polygons);

        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = polygons.get(polygonInd).size();

            ArrayList<Vector2f> resultPoints = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                Vector3f vertex = mesh.vertices.get(polygons.get(polygonInd).get(vertexInPolygonInd));
                Vector2f resultPoint = vertexToPoint(modelViewProjectionMatrix.multiplyMatrix4ByVector3(vertex), width, height);
                resultPoints.add(resultPoint);
            }

            if (drawLine) {
                for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                    float[] startPoint = resultPoints.get(vertexInPolygonInd - 1).toArray();
                    float[] endPoint = resultPoints.get(vertexInPolygonInd).toArray();

                    graphicsContext.strokeLine(
                            startPoint[0],
                            startPoint[1],
                            endPoint[0],
                            endPoint[1]);
                }

                if (nVerticesInPolygon > 0) {
                    float[] startPoint = resultPoints.get(nVerticesInPolygon - 1).toArray();
                    float[] endPoint = resultPoints.get(0).toArray();
                    graphicsContext.strokeLine(
                            startPoint[0],
                            startPoint[1],
                            endPoint[0],
                            endPoint[1]);
                }
            }

            if (draw) {
                double[] xArray = new double[nVerticesInPolygon];
                double[] yArray = new double[nVerticesInPolygon];
                for (int i = 0; i < nVerticesInPolygon; i++) {
                    float[] point = resultPoints.get(i).toArray();
                    xArray[i] = point[0];
                    yArray[i] = point[1];
                }
                graphicsContext.fillPolygon(xArray, yArray, nVerticesInPolygon);
            }
        }
    }

    /*private static final double EPSILON = 0.0000001;

    public static boolean rayIntersectsTriangle(Vector3f rayOrigin,
                                                Vector3f rayVector,
                                                ArrayList<Vector3f> inTriangle) {
        Vector3f vertex0 = inTriangle.get(0);
        Vector3f vertex1 = inTriangle.get(1);
        Vector3f vertex2 = inTriangle.get(2);
        double a, f, u, v;
        vertex1.minus(vertex0);
        vertex2.minus(vertex0);
        rayVector.crossProduct(vertex2);
        a = vertex1.dotProduct(rayVector);
        if (a > -EPSILON && a < EPSILON) {
            return false;    // This ray is parallel to this triangle.
        }
        f = 1.0 / a;
        rayOrigin.minus(vertex0);
        u = f * (rayOrigin.dotProduct(rayVector));
        if (u < 0.0 || u > 1.0) {
            return false;
        }
        rayOrigin.crossProduct(vertex1);
        v = f * rayVector.dotProduct(rayOrigin);
        if (v < 0.0 || u + v > 1.0) {
            return false;
        }
        // At this stage we can compute t to find out where the intersection point is on the line.
        double t = f * vertex2.dotProduct(rayOrigin);
        if (t > EPSILON) // ray intersection
        {
            rayVector.multiply((float) t);
            rayVector.add(rayOrigin);
            return true;
        } else // This means that there is a line intersection but not a ray intersection.
        {
            return false;
        }
    }

    private static void sortByFirst(int[] arr, ArrayList<ArrayList<Integer>> list) {
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            ArrayList<Integer> temp1 = list.get(i);
            int j = i - 1;
            while (j >= 0 && arr[j] > temp) {
                arr[j + 1] = arr[j];
                list.set(j + 1, list.get(j));
                j--;
            }
            arr[j + 1] = temp;
            list.set(j + 1, temp1);
        }
    }

    void line(HDC hdc, int x1, int y1, int x2, int y2)
    {
        int dx = (x2 - x1 >= 0 ? 1 : -1);
        int dy = (y2 - y1 >= 0 ? 1 : -1);

        int lengthX = abs(x2 - x1);
        int lengthY = abs(y2 - y1);

        int length = max(lengthX, lengthY);

        if (length == 0)
        {
            SetPixel(hdc, x1, y1, 0);
        }

        if (lengthY <= lengthX)
        {
            // Начальные значения
            int x = x1;
            int y = y1;
            int d = -lengthX;

            // Основной цикл
            length++;
            while(length--)
            {
                SetPixel(hdc, x, y, 0);
                x += dx;
                d += 2 * lengthY;
                if (d > 0) {
                    d -= 2 * lengthX;
                    y += dy;
                }
            }
        }
        else
        {
            // Начальные значения
            int x = x1;
            int y = y1;
            int d = - lengthY;

            // Основной цикл
            length++;
            while(length--)
            {
                SetPixel(hdc, x, y, 0);
                y += dy;
                d += 2 * lengthX;
                if (d > 0) {
                    d -= 2 * lengthY;
                    x += dx;
                }
            }
        }
    }*/
}