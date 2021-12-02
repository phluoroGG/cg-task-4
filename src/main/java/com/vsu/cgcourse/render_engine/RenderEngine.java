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
            final int height)
    {
        Matrix4f modelMatrix = camera.getModelMatrix();
        Matrix4f viewMatrix = camera.getViewMatrix();
        Matrix4f projectionMatrix = camera.getProjectionMatrix();

        viewMatrix.multiply(modelMatrix);
        projectionMatrix.multiply(viewMatrix);
        Matrix4f modelViewProjectionMatrix = projectionMatrix.copy();

        final int nPolygons = mesh.polygonVertexIndices.size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = mesh.polygonVertexIndices.get(polygonInd).size();

            ArrayList<Vector2f> resultPoints = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                Vector3f vertex = mesh.vertices.get(mesh.polygonVertexIndices.get(polygonInd).get(vertexInPolygonInd));
                Vector2f resultPoint = vertexToPoint(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex), width, height);
                resultPoints.add(resultPoint);
            }

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
    }
}