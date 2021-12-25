package com.vsu.cgcourse.model;

import com.vsu.cgcourse.math.Matrix4f;
import com.vsu.cgcourse.math.Vector2f;
import com.vsu.cgcourse.math.Vector3f;
import com.vsu.cgcourse.render_engine.GraphicConveyor;

import java.nio.file.Path;
import java.util.*;


public class Mesh {

    private static int counter = 1;

    public Mesh() {
        name = String.format("Model %d", counter);
        counter++;
        scale = new Vector3f(1, 1, 1);
        rotation = new Vector3f(0, 0, 0);
        translation = new Vector3f(0, 0, 0);
    }

    public ArrayList<Vector3f> vertices = new ArrayList<>();
    public ArrayList<Vector2f> textureVertices = new ArrayList<>();
    public ArrayList<Vector3f> normals = new ArrayList<>();

    public ArrayList<ArrayList<Integer>> polygonVertexIndices = new ArrayList<>();
    public ArrayList<ArrayList<Integer>> polygonTextureVertexIndices = new ArrayList<>();
    public ArrayList<ArrayList<Integer>> polygonNormalIndices = new ArrayList<>();

    public Path textureFile;

    public Mesh copy() {
        Mesh mesh = new Mesh();
        mesh.vertices.addAll(vertices);
        mesh.textureVertices.addAll(textureVertices);
        mesh.normals.addAll(normals);
        mesh.polygonVertexIndices.addAll(polygonVertexIndices);
        mesh.polygonTextureVertexIndices.addAll(polygonTextureVertexIndices);
        mesh.polygonNormalIndices.addAll(polygonNormalIndices);
        mesh.textureFile = textureFile;
        return mesh;
    }

    private final String name;
    private Vector3f scale;
    private Vector3f rotation;
    private Vector3f translation;

    public String getName() {
        return name;
    }

    public Vector3f getScale() {
        return scale;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void setTranslation(Vector3f translation) {
        this.translation = translation;
    }

    public Matrix4f getModelMatrix() {
        return GraphicConveyor.rotateScaleTranslate(scale, rotation, translation);
    }

    public void triangulate() {
        for (int i = 0; i < polygonVertexIndices.size(); i++) {
            ArrayList<Integer> vertices = polygonVertexIndices.get(i);
            if (vertices.size() > 3) {
                for (int j = 2; j < vertices.size(); j++) {
                    ArrayList<Integer> newPolygon = new ArrayList<>();
                    newPolygon.add(vertices.get(0));
                    newPolygon.add(vertices.get(j - 1));
                    newPolygon.add(vertices.get(j));
                    polygonVertexIndices.add(newPolygon);
                }
                polygonVertexIndices.remove(vertices);
                i--;
            }
        }
        for (int i = 0; i < polygonTextureVertexIndices.size(); i++) {
            ArrayList<Integer> textureVertices = polygonTextureVertexIndices.get(i);
            if (textureVertices.size() > 3) {
                for (int j = 2; j < textureVertices.size(); j++) {
                    ArrayList<Integer> newPolygon = new ArrayList<>();
                    newPolygon.add(textureVertices.get(0));
                    newPolygon.add(textureVertices.get(j - 1));
                    newPolygon.add(textureVertices.get(j));
                    polygonTextureVertexIndices.add(newPolygon);
                }
                polygonTextureVertexIndices.remove(textureVertices);
                i--;
            }
        }
        for (int i = 0; i < polygonNormalIndices.size(); i++) {
            ArrayList<Integer> normals = polygonNormalIndices.get(i);
            if (normals.size() > 3) {
                for (int j = 2; j < normals.size(); j++) {
                    ArrayList<Integer> newPolygon = new ArrayList<>();
                    newPolygon.add(normals.get(0));
                    newPolygon.add(normals.get(j - 1));
                    newPolygon.add(normals.get(j));
                    polygonNormalIndices.add(newPolygon);
                }
                polygonNormalIndices.remove(normals);
                i--;
            }
        }
    }
}
