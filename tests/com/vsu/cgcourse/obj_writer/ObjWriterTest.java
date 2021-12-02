package com.vsu.cgcourse.obj_writer;

import com.vsu.cgcourse.math.Vector3f;
import com.vsu.cgcourse.model.Mesh;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


class ObjWriterTest {

    @Test
    public void testWrite() {
        Mesh mesh = new Mesh();
        mesh.vertices.add(new Vector3f(1, 2, 3));
        mesh.vertices.add(new Vector3f(2, 3, 4));
        mesh.vertices.add(new Vector3f(3, 2, 1));
        ArrayList<Integer> polygonVertexIndices = new ArrayList<>();
        polygonVertexIndices.add(0);
        polygonVertexIndices.add(1);
        polygonVertexIndices.add(2);
        mesh.polygonVertexIndices.add(polygonVertexIndices);
        String result = ObjWriter.write(mesh);
        String expectedResult = String.format("v %f %f %f\nv %f %f %f\nv %f %f %f\n\nf 1 2 3 \n",
                1f, 2f, 3f, 2f, 3f, 4f, 3f, 2f, 1f);
        Assertions.assertEquals(expectedResult, result);
    }

}