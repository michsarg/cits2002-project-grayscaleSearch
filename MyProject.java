/*
* CITS2200 Project S1 2020
* @author Michael Sargeant
* */

import java.util.Arrays;


public class MyProject implements Project {

    // FIELDS
    int changedPixels;
    int colourSelected;
    boolean checked[][];
    int[] darkestAdjacent;

    // CONSTRUCTOR
    public MyProject(){
    }

    // METHODS
    /**
     * Compute the number of pixels that change when performing a black flood-fill from the pixel at (row, col) in the given image.
     * A flood-fill operation changes the selected pixel and all contiguous pixels of the same colour to the specified colour.
     * A pixel is considered part of a contiguous region of the same colour if it is exactly one pixel up/down/left/right of another pixel in the region.
     *
     * Marks (4 total):
     * - Correctness: +2 marks
     * - Complexity:
     *   - O(P): +1 mark
     * - Quality: +1 mark
     *
     * @param image The greyscale image as defined above
     * @param row The row index of the pixel to flood-fill from
     * @param col The column index of the pixel to flood-fill from
     * @return The number of pixels that changed colour when performing this operation
     */

     // current complexity: O(P)
     // maximum number of iterations will be the number of pixels

    public int floodFillCount(int[][] image, int row, int col){
        //test for black pixel selection
        //too many variables?
    	if (image[row][col] == 0) return 0;
    	changedPixels = 1;
        colourSelected = image[row][col];     
        checked = new boolean[image.length][image[0].length];
        checked[row][col] = true;
        adjacencyCheck(image, row, col);
        return changedPixels;
    }

    // can be combined?
    private void adjacencyCheck(int[][] image, int row, int col) {
        pixelCheck(image, row+1, col  );
        pixelCheck(image, row-1, col  );
        pixelCheck(image, row  , col+1);
        pixelCheck(image, row  , col-1);
    }

    private void pixelCheck(int[][] image, int row, int col) {
        if (row >= 0 && row < image.length &&
            col >= 0 && col < image[0].length &&
            checked[row][col] == false &&
            colourSelected == image[row][col]) {
                changedPixels++;
                checked[row][col] = true;
                adjacencyCheck(image, row, col);
        }
    }

    /**
     * Compute the total brightness of the brightest exactly k*k square that appears in the given image.
     * The total brightness of a square is defined as the sum of its pixel values.
     * You may assume that k is positive, no greater than R or C, and no greater than 2048.
     *
     * Marks (5 total):
     * - Correctness: +2 marks
     * - Complexity:
     *   - O(Pk): +1 mark
     *   - O(P): +1 mark
     * - Quality: +1 mark
     *
     * @param image The greyscale image as defined above
     * @param k the dimension of the squares to consider
     * @return The total brightness of the brightest square
     */

     // current complexity O(Pk^2)
    public int brightestSquare(int[][] image, int k){
       
        int brightestSquare = 0;
        int rowCount = image.length;
        System.out.println("rowCount: " + rowCount);
        int colCount = image[0].length;
        System.out.println("colCount: " + colCount);
        int[] pixelArray = new int[rowCount*colCount];
        System.out.println("pixelArray.length: " + pixelArray.length);

        int pixelCount = 0;
        for (int row = 0; row<rowCount; row++){
            for ( int col = 0; col<colCount; col++){
                pixelArray[pixelCount] = image[row][col];
                pixelCount++;
            }
        }

        // this can't be made flexible for other sizes without another for loop
        for (int focusPixel = 0; (focusPixel+12)<pixelArray.length; focusPixel++){
            int squareTotal = 0;
            squareTotal =   pixelArray[focusPixel]    + pixelArray[focusPixel+1]  + pixelArray[focusPixel+2] +
                            pixelArray[focusPixel+5]  + pixelArray[focusPixel+6]  + pixelArray[focusPixel+7] +
                            pixelArray[focusPixel+10] + pixelArray[focusPixel+11] + pixelArray[focusPixel+12];
            if (squareTotal>brightestSquare) brightestSquare = squareTotal;
            System.out.println("squareTotal: "+ squareTotal);  
        }

        int row = 0;
        int col = 4;
        System.out.println("Test: " + (pixelArray[((row*10)+(col*2))/2] == image[row][col]));      

        return brightestSquare;
    }

    /**
     * Compute the maximum brightness that MUST be encountered when drawing a path from the pixel at (ur, uc) to the pixel at (vr, vc).
     * The path must start at (ur, uc) and end at (vr, vc), and may only move one pixel up/down/left/right at a time in between.
     * The brightness of a path is considered to be the value of the brightest pixel that the path ever touches.
     * This includes the start and end pixels of the path.
     *
     * Marks (5 total):
     * - Correctness: +2 marks
     * - Complexity:
     *   - O(P log P): +1 mark
     * - Quality: +2 mark
     *
     * @param image The greyscale image as defined above
     * @param ur The row index of the start pixel for the path
     * @param uc The column index of the start pixel for the path
     * @param vr The row index of the end pixel for the path
     * @param vc The column index of the end pixel for the path
     * @return The minimum brightness of any path from (ur, uc) to (vr, vc)
     */

     //@TODO review ways of finding paths
    public int darkestPath(int[][] image, int ur, int uc, int vr, int vc){
        int brightest = 0;
        //for checking not repeating
        this.checked = new boolean[image.length][image[0].length];
        this.checked[ur][uc] = true;
        //start darkest path
        darkestAdjacent = findDarkestAdjacent(image, ur, uc);
        //termination condition
        while(checked[vr][vc] == false) {
            darkestAdjacent = findDarkestAdjacent(image, darkestAdjacent[0], darkestAdjacent[1]);
            if (darkestAdjacent[2] > brightest) brightest = darkestAdjacent[2];
        }
        return brightest;
    }

    public int[] findDarkestAdjacent(int[][] image, int focusRow, int focusCol){
        // darkest[] = {row, col, brightness}
        int[] darkest = {-1, -1, 300};
        //set up array of co-ordinates and brightness
        int[][] nextPos = { {focusRow-1, focusCol  , 300},    //north
                            {focusRow  , focusCol+1, 300},    //east
                            {focusRow+1, focusCol ,  300},    //south
                            {focusRow  , focusCol-1, 300}};   //west
        for (int i = 0; i < 4; i++) {
            // check for range
            if (nextPos[i][0] >= 0  &&  nextPos[i][0] < image.length   &&
                nextPos[i][1] >= 0  &&  nextPos[i][1] < image[0].length){
                    //check for checked
                    if (checked[nextPos[i][0]][nextPos[i][1]] == false ) {
                        nextPos[i][2] = image[nextPos[i][0]][nextPos[i][1]];       
                        //check for checked and highest
                        if (nextPos[i][2] < darkest[2]) {
                            darkest[0] = nextPos[i][0];
                            darkest[1] = nextPos[i][1];
                            darkest[2] = nextPos[i][2];
                        }
                    }
            }
        }
        checked[darkest[0]][darkest[1]] = true;
        return darkest;
    }

    /**
     * Compute the results of a list of queries on the given image.
     * Each query will be a three-element int array {r, l, u} defining a row segment. You may assume l < u.
     * A row segment is a set of pixels (r, c) such that r is as defined, l <= c, and c < u.
     * For each query, find the value of the brightest pixel in the specified row segment.
     * Return the query results in the same order as the queries are given.
     *
     * Marks (6 total):
     * - Correctness: +2 marks
     * - Complexity: (where Q is the number of queries)
     *   - O(PC + Q): +1 mark
     *   - O(P log C + Q log C): +1 mark
     *   - Faster is possible but will not receive additional marks
     * - Quality: +2 marks
     *
     * @param image The greyscale image as defined above
     * @param queries The list of query row segments
     * @return The list of brightest pixels for each query row segment
     */
    public int[] brightestPixelsInRowSegments(int[][] image, int[][] queries){
        int[] brightestList = new int[queries.length];
        for (int i=0; i<queries.length; i++){
            int brightest = 0;
            for (int j=queries[i][1]; j<queries[i][2]; j++) {
                if (image[queries[i][0]][j] > brightest){
                    brightest = image[queries[i][0]][j];
                }
            }
            brightestList[i] = brightest;
        }
        return brightestList;
    }
}