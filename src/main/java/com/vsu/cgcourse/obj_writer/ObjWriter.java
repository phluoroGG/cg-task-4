package com.vsu.cgcourse.obj_writer;

import com.vsu.cgcourse.math.Vector2f;
import com.vsu.cgcourse.math.Vector3f;
import com.vsu.cgcourse.model.Mesh;

import java.util.ArrayList;
import java.util.List;

public class ObjWriter {

    public static String write(Mesh model) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Vector3f> vertices = model.vertices;
        for (Vector3f vector3f : vertices) {
            float[] vector = vector3f.toArray();
            stringBuilder.append(String.format("v %f %f %f\n", vector[0], vector[1], vector[2]));
        }
        stringBuilder.append("\n");
        List<Vector2f> textureVertices = model.textureVertices;
        for (Vector2f vector2f : textureVertices) {
            float[] vector = vector2f.toArray();
            stringBuilder.append(String.format("vt %f %f\n", vector[0], vector[1]));
        }
        stringBuilder.append("\n");
        List<Vector3f> normals = model.normals;
        for (Vector3f vector3f : normals) {
            float[] vector = vector3f.toArray();
            stringBuilder.append(String.format("vn %f %f %f\n", vector[0], vector[1], vector[2]));
        }
        List<ArrayList<Integer>> polygonVertexIndices = model.polygonVertexIndices;
        List<ArrayList<Integer>> polygonTextureVertexIndices = model.polygonTextureVertexIndices;
        List<ArrayList<Integer>> polygonNormalIndices = model.polygonNormalIndices;
        for (int i = 0; i < polygonVertexIndices.size(); i++) {
            if (polygonTextureVertexIndices.isEmpty()) {
                if (polygonNormalIndices.isEmpty()) {
                    stringBuilder.append("f ");
                    for (Integer number : polygonVertexIndices.get(i)) {
                        stringBuilder.append(number + 1).append(" ");
                    }
                    stringBuilder.append("\n");
                } else {
                    stringBuilder.append("f ");
                    for (int j = 0; j < polygonVertexIndices.get(i).size(); j++) {
                        stringBuilder.append(polygonVertexIndices.get(i).get(j) + 1).append("//").
                                append(polygonNormalIndices.get(i).get(j) + 1).append(" ");
                    }
                    stringBuilder.append("\n");
                }
            } else {
                if (polygonNormalIndices.isEmpty()) {
                    stringBuilder.append("f ");
                    for (int j = 0; j < polygonVertexIndices.get(i).size(); j++) {
                        stringBuilder.append(polygonVertexIndices.get(i).get(j) + 1).append("/").
                                append(polygonTextureVertexIndices.get(i).get(j) + 1).append(" ");
                    }
                    stringBuilder.append("\n");
                } else {
                    stringBuilder.append("f ");
                    for (int j = 0; j < polygonVertexIndices.get(i).size(); j++) {
                        stringBuilder.append(polygonVertexIndices.get(i).get(j) + 1).append("/").
                                append(polygonTextureVertexIndices.get(i).get(j) + 1).append("/").
                                append(polygonNormalIndices.get(i).get(j) + 1).append(" ");
                    }
                    stringBuilder.append("\n");
                }
            }
        }

        return stringBuilder.toString();
    }
}
