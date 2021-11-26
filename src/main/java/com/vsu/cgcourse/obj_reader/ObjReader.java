package com.vsu.cgcourse.obj_reader;

import com.vsu.cgcourse.math.Vector2f;
import com.vsu.cgcourse.math.Vector3f;
import com.vsu.cgcourse.model.Mesh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ObjReader {

    private static final String OBJ_VERTEX_TOKEN = "v";
    private static final String OBJ_TEXTURE_TOKEN = "vt";
    private static final String OBJ_NORMAL_TOKEN = "vn";
    private static final String OBJ_FACE_TOKEN = "f";

    public static Mesh read(String fileContent) {
        Mesh result = new Mesh();

        int lineInd = 0;
        Scanner scanner = new Scanner(fileContent);
        while (scanner.hasNextLine()) {
            final String line = scanner.nextLine();
            ArrayList<String> wordsInLine = new ArrayList<>(Arrays.asList(line.split("\\s+")));
            if (wordsInLine.isEmpty())
                continue;

            final String token = wordsInLine.get(0);
            wordsInLine.remove(0);

            ++lineInd;
            switch (token) {
                case OBJ_VERTEX_TOKEN -> result.vertices.add(parseVertex(wordsInLine, lineInd));
                case OBJ_TEXTURE_TOKEN -> result.textureVertices.add(parseTextureVertex(wordsInLine, lineInd));
                case OBJ_NORMAL_TOKEN -> result.normals.add(parseNormal(wordsInLine, lineInd));
                case OBJ_FACE_TOKEN -> {
                    ArrayList<ArrayList<Integer>> polygonIndexes = parseFace(wordsInLine, lineInd);
                    result.polygonVertexIndices.add(polygonIndexes.get(0));
                    if (!polygonIndexes.get(1).isEmpty()) {
                        result.polygonTextureVertexIndices.add(polygonIndexes.get(1));
                    }
                    if (!polygonIndexes.get(2).isEmpty()) {
                        result.polygonNormalIndices.add(polygonIndexes.remove(2));
                    }
                }
                default -> {}
            }
        }
        return result;
    }

    protected static Vector3f parseVertex(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        try {
            return new Vector3f(
                    Float.parseFloat(wordsInLineWithoutToken.get(0)),
                    Float.parseFloat(wordsInLineWithoutToken.get(1)),
                    Float.parseFloat(wordsInLineWithoutToken.get(2)));

        } catch(NumberFormatException e) {
            throw new ObjReaderException("Failed to parse float value.", lineInd);

        } catch(IndexOutOfBoundsException e) {
            throw new ObjReaderException("Too few vertex arguments.", lineInd);
        }
    }

    protected static Vector2f parseTextureVertex(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        try {
            return new Vector2f(
                    Float.parseFloat(wordsInLineWithoutToken.get(0)),
                    Float.parseFloat(wordsInLineWithoutToken.get(1)));

        } catch(NumberFormatException e) {
            throw new ObjReaderException("Failed to parse float value.", lineInd);

        } catch(IndexOutOfBoundsException e) {
            throw new ObjReaderException("Too few texture vertex arguments.", lineInd);
        }
    }

    protected static Vector3f parseNormal(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        try {
            return new Vector3f(
                    Float.parseFloat(wordsInLineWithoutToken.get(0)),
                    Float.parseFloat(wordsInLineWithoutToken.get(1)),
                    Float.parseFloat(wordsInLineWithoutToken.get(2)));

        } catch(NumberFormatException e) {
            throw new ObjReaderException("Failed to parse float value.", lineInd);

        } catch(IndexOutOfBoundsException e) {
            throw new ObjReaderException("Too few normal arguments.", lineInd);
        }
    }

    protected static ArrayList<ArrayList<Integer>> parseFace(
            ArrayList<String> wordInLine,
            int lineInd) {
        ArrayList<Integer> polygonVertexIndices = new ArrayList<>();
        ArrayList<Integer> polygonTextureVertexIndices = new ArrayList<>();
        ArrayList<Integer> polygonNormalIndices = new ArrayList<>();
        for (String s : wordInLine) {
            try {
                String[] wordIndices = s.split("/");
                if (wordIndices[0].equals("")) {
                    throw new ObjReaderException("Invalid element size.", lineInd);
                }
                switch (wordIndices.length) {
                    case 1 -> polygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
                    case 2 -> {
                        polygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
                        polygonTextureVertexIndices.add(Integer.parseInt(wordIndices[1]) - 1);
                    }
                    case 3 -> {
                        polygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
                        polygonNormalIndices.add(Integer.parseInt(wordIndices[2]) - 1);
                        if (!wordIndices[1].equals("")) {
                            polygonTextureVertexIndices.add(Integer.parseInt(wordIndices[1]) - 1);
                        }
                    }
                    default -> throw new ObjReaderException("Invalid element size.", lineInd);
                }

            } catch(NumberFormatException e) {
                throw new ObjReaderException("Failed to parse int value.", lineInd);

            } catch(IndexOutOfBoundsException e) {
                throw new ObjReaderException("Too few arguments.", lineInd);
            }
        }
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        result.add(polygonVertexIndices);
        result.add(polygonTextureVertexIndices);
        result.add(polygonNormalIndices);
        return result;
    }
}