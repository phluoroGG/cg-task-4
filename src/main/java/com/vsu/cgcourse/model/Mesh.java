package com.vsu.cgcourse.model;

import com.vsu.cgcourse.math.Vector2f;
import com.vsu.cgcourse.math.Vector3f;

import java.util.*;


public class Mesh {

    public Mesh() {}

    public ArrayList<Vector3f> vertices = new ArrayList<>();
    public ArrayList<Vector2f> textureVertices = new ArrayList<>();
    public ArrayList<Vector3f> normals = new ArrayList<>();

    public ArrayList<ArrayList<Integer>> polygonVertexIndices = new ArrayList<>();
    public ArrayList<ArrayList<Integer>> polygonTextureVertexIndices = new ArrayList<>();
    public ArrayList<ArrayList<Integer>> polygonNormalIndices = new ArrayList<>();

    public Mesh copy() {
        Mesh mesh = new Mesh();
        mesh.vertices.addAll(vertices);
        mesh.textureVertices.addAll(textureVertices);
        mesh.normals.addAll(normals);
        mesh.polygonVertexIndices.addAll(polygonVertexIndices);
        mesh.polygonTextureVertexIndices.addAll(polygonTextureVertexIndices);
        mesh.polygonNormalIndices.addAll(polygonNormalIndices);
        return mesh;
    }
}
