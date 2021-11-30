package com.vsu.cgcourse.obj_reader;

import com.vsu.cgcourse.math.Vector2f;
import com.vsu.cgcourse.math.Vector3f;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class ObjReaderTest {

    @Test
    public void testParseVertex() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.03f);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    public void testParseVertexFalse() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.10f);
        Assertions.assertFalse(result.equals(expectedResult));
    }

    @Test
    public void testParseVertexStringValues() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("zx", "яч", ".-"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseVertexFewArguments() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseVertexManyArguments() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0", "4.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too many vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseTextureVertex() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("0.01", "0.02"));
        Vector2f result = ObjReader.parseTextureVertex(wordsInLineWithoutToken, 5);
        Vector2f expectedResult = new Vector2f(0.01f, 0.02f);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    public void testParseTextureVertexFalse() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("0.01", "0.02"));
        Vector2f result = ObjReader.parseTextureVertex(wordsInLineWithoutToken, 5);
        Vector2f expectedResult = new Vector2f(0.01f, 0.10f);
        Assertions.assertFalse(result.equals(expectedResult));
    }

    @Test
    public void testParseTextureVertexStringArguments() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("zx", ".-"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseTextureVertexFewArguments() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Collections.singletonList("0.0"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few texture vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseTextureVertexManyArguments() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("0.0", "0.1", "0.2"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too many texture vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseTextureVertexInvalidValue() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("-1.0", "2.0"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Invalid value for texture vertex.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseNormal() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjReader.parseNormal(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.03f);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    public void testParseNormalFalse() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjReader.parseNormal(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.10f);
        Assertions.assertFalse(result.equals(expectedResult));
    }

    @Test
    public void testParseNormalStringArguments() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("zx", "яч", ".-"));
        try {
            ObjReader.parseNormal(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseNormalFewArguments() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0"));
        try {
            ObjReader.parseNormal(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few normal arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseNormalManyArguments() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0", "4.0"));
        try {
            ObjReader.parseNormal(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too many normal arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFaceOneArgument() {
        ArrayList<String> wordInLine = new ArrayList<>();
        wordInLine.add("1");
        ArrayList<ArrayList<Integer>> polygonIndexes = ObjReader.parseFace(wordInLine, 5, 1, 0, 0, true, true);
        Integer result = polygonIndexes.get(0).get(0);
        Integer expectedResult = 0;
        Assertions.assertEquals(expectedResult, result);
    }
    @Test
    public void testParseFaceOneArgumentFalse() {
        ArrayList<String> wordInLine = new ArrayList<>();
        wordInLine.add("1");
        ArrayList<ArrayList<Integer>> polygonIndexes = ObjReader.parseFace(wordInLine, 5, 1, 0, 0, true, true);
        Integer result = polygonIndexes.get(0).get(0);
        Integer expectedResult = 1;
        Assertions.assertNotEquals(expectedResult, result);
    }

    @Test
    public void testParseFaceTwoArguments() {
        ArrayList<String> wordInLine = new ArrayList<>();
        wordInLine.add("1/2");
        ArrayList<ArrayList<Integer>> polygonIndexes = ObjReader.parseFace(wordInLine, 5, 2, 2, 0, true, true);
        Integer result1 = polygonIndexes.get(0).get(0);
        Integer expectedResult1 = 0;
        Assertions.assertEquals(expectedResult1, result1);
        Integer result2 = polygonIndexes.get(1).get(0);
        Integer expectedResult2 = 1;
        Assertions.assertEquals(expectedResult2, result2);
    }

    @Test
    public void testParseFaceTwoArgumentsFalse() {
        ArrayList<String> wordInLine = new ArrayList<>();
        wordInLine.add("1/2");
        ArrayList<ArrayList<Integer>> polygonIndexes = ObjReader.parseFace(wordInLine, 5, 2, 2, 0, true, true);
        Integer result1 = polygonIndexes.get(0).get(0);
        Integer expectedResult1 = 1;
        Assertions.assertNotEquals(expectedResult1, result1);
        Integer result2 = polygonIndexes.get(1).get(0);
        Integer expectedResult2 = 2;
        Assertions.assertNotEquals(expectedResult2, result2);
    }

    @Test
    public void testParseFaceThreeArguments() {
        ArrayList<String> wordInLine = new ArrayList<>();
        wordInLine.add("1/2/3");
        ArrayList<ArrayList<Integer>> polygonIndexes = ObjReader.parseFace(wordInLine, 5, 3, 3, 3, true, true);
        Integer result1 = polygonIndexes.get(0).get(0);
        Integer expectedResult1 = 0;
        Assertions.assertEquals(expectedResult1, result1);
        Integer result2 = polygonIndexes.get(1).get(0);
        Integer expectedResult2 = 1;
        Assertions.assertEquals(expectedResult2, result2);
        Integer result3 = polygonIndexes.get(2).get(0);
        Integer expectedResult3 = 2;
        Assertions.assertEquals(expectedResult3, result3);
    }

    @Test
    public void testParseFaceThreeArgumentsFalse() {
        ArrayList<String> wordInLine = new ArrayList<>();
        wordInLine.add("1/2/3");
        ArrayList<ArrayList<Integer>> polygonIndexes = ObjReader.parseFace(wordInLine, 5, 3, 3, 3, true, true);
        Integer result1 = polygonIndexes.get(0).get(0);
        Integer expectedResult1 = 1;
        Assertions.assertNotEquals(expectedResult1, result1);
        Integer result2 = polygonIndexes.get(1).get(0);
        Integer expectedResult2 = 2;
        Assertions.assertNotEquals(expectedResult2, result2);
        Integer result3 = polygonIndexes.get(2).get(0);
        Integer expectedResult3 = 3;
        Assertions.assertNotEquals(expectedResult3, result3);
    }

    @Test
    public void testParseFaceStringArguments() {
        ArrayList<String> wordInLine = new ArrayList<>();
        wordInLine.add("zx/яч/.-");
        try {
            ObjReader.parseFace(wordInLine, 5, 1, 1, 1, true, true);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 5. Failed to parse int value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFaceEmptyString() {
        ArrayList<String> wordInLine = new ArrayList<>();
        wordInLine.add("");
        try {
            ObjReader.parseFace(wordInLine, 5, 1, 1, 1, true, true);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 5. Invalid element size.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFaceZeroArguments() {
        ArrayList<String> wordInLine = new ArrayList<>();
        wordInLine.add("/");
        try {
            ObjReader.parseFace(wordInLine, 5, 1, 1, 1, true, true);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 5. Too few arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFaceFourArguments() {
        ArrayList<String> wordInLine = new ArrayList<>();
        wordInLine.add("1/2/3/4");
        try {
            ObjReader.parseFace(wordInLine, 5, 4, 4, 4, true, true);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 5. Invalid element size.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFaceThreeArgumentsWithoutSecond() {
        ArrayList<String> wordInLine = new ArrayList<>();
        wordInLine.add("1//3");
        ArrayList<ArrayList<Integer>> polygonIndexes = ObjReader.parseFace(wordInLine, 5, 3, 0, 3, true, true);
        Integer result1 = polygonIndexes.get(0).get(0);
        Integer expectedResult1 = 0;
        Assertions.assertEquals(expectedResult1, result1);
        Integer result2 = polygonIndexes.get(2).get(0);
        Integer expectedResult2 = 2;
        Assertions.assertEquals(expectedResult2, result2);
    }

    @Test
    public void testParseFaceLargeIndexVertex() {
        ArrayList<String> wordInLine = new ArrayList<>();
        wordInLine.add("1/2/3");
        try {
            ObjReader.parseFace(wordInLine, 5, 0, 1, 1, true, true);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 5. No vertex with index 1.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFaceLargeIndexTextureVertex() {
        ArrayList<String> wordInLine = new ArrayList<>();
        wordInLine.add("1/2/3");
        try {
            ObjReader.parseFace(wordInLine, 5, 1, 1, 1, true, true);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 5. No texture vertex with index 2.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFaceLargeIndexNormal() {
        ArrayList<String> wordInLine = new ArrayList<>();
        wordInLine.add("1/2/3");
        try {
            ObjReader.parseFace(wordInLine, 5, 1, 2, 2, true, true);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 5. No normal with index 3.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFaceNoTextureVertices() {
        ArrayList<String> wordInLine = new ArrayList<>();
        wordInLine.add("1/2/3");
        try {
            ObjReader.parseFace(wordInLine, 5, 3, 3, 3, false, true);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 5. There are texture vertices, but they shouldn't be.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFaceNoNormals() {
        ArrayList<String> wordInLine = new ArrayList<>();
        wordInLine.add("1/2/3");
        try {
            ObjReader.parseFace(wordInLine, 5, 3, 3, 3, true, false);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 5. There are normals, but they shouldn't be.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }
}