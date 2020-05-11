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

        MyProject newMyProject = new MyProject();

        //System.out.println(newMyProject.floodFillCount(testArray, 1, 4));
        //System.out.println(newMyProject.brightestSquare(testArray, 3));
        System.out.println(newMyProject.darkestPath(testArray2, 1, 0, 0, 1));



    }
}