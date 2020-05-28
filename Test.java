import java.util.Arrays;

public class Test {

    public static void main(String[] args) {

        //System.out.println("Test");


        int[][] testArray = new int[][]{
                {  0,   0,  85,   85,  85},
                {  0,  85,  85,  170, 170},
                { 170, 255, 255,   0, 170},
                { 170, 170, 170, 170, 170}
        };

        int[][] testArray2 = new int[][] {
            { 0, 0, 1, 1, 1 },
            { 0, 1, 1, 2, 2 },
            { 2, 3, 3, 0, 2 },
            { 2, 2, 2, 2, 2 },
        };

        int[][] testArray3 = new int[][] {
            {   0,   0,  85,  85,  85 },
            {   0,  85,  85, 170, 170 },
            { 170, 255, 255,   0, 170 },
            { 170, 170, 170, 170, 170 },
        };

        int[][] testArray4 = new int[][] {
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } ,
            { 1, 1, 2, 2, 2, 2, 1, 1, 1, 1 } ,
            { 1, 1, 2, 2, 2, 2, 1, 1, 1, 1 } ,
            { 1, 1, 2, 2, 2, 2, 1, 1, 1, 1 } ,
            { 1, 1, 2, 2, 2, 2, 1, 1, 1, 1 } ,
            { 1, 1, 1, 9, 1, 1, 1, 1, 1, 1000 } ,
            { 1, 1, 2, 2, 2, 2, 1, 1, 1, 1 } ,
            { 1, 1, 2, 2, 2, 2, 1, 1, 1, 1 } ,
            { 1, 1, 2, 2, 2, 2, 1, 1, 1, 1 } ,
        };

        int[][] queries = {
            {0, 0, 3},
            {1, 1, 5},
            {2, 2, 5},
        };

        MyProject newMyProject = new MyProject();

        //FLOOD FILL COUNT
        System.out.println(newMyProject.floodFillCount(testArray2, 1, 2));
        
        //BRIGHTEST SQUARE
        //System.out.println("bS: " + newMyProject.brightestSquare(testArray3, 3));
        //System.out.println("bS: " + newMyProject.brightestSquare(testArray4, 4));
        //System.out.println("bS2: " + newMyProject.brightestSquare2(testArray3, 2));

        //DARKEST PATH
        //System.out.println("Dp: " + newMyProject.darkestPath(testArray3, 1, 0, 2, 3));
        //System.out.println(newMyProject.darkestPath(testArray2, 1, 0, 0, 1));

        //BRIGHTEST PIXEL IN ROW SEGMENT
        //System.out.println("bPIRS: " + Arrays.toString(newMyProject.brightestPixelsInRowSegments(testArray3, queries)));
        //System.out.println("bPIR: " + Arrays.toString(newMyProject.brightestPixelsInRowSegments(testArray3, queries)));


    }
}