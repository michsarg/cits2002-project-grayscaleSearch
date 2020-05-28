/*
* CITS2200 Project S1 2020
* @author Michael Sargeant
* */

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.*;



public class MyProject implements Project {

    // FIELDS
    int changedPixels;
    int colourSelected;
    int image[][];
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
    
    // FIRST SOLUTION (WORKING)
    /* 
    public int floodFillCount(int[][] image, int row, int col){
        //test for black pixel selection
        //too many variables?
    	if (image[row][col] == 0) return 0;
    	changedPixels = 1; // EXTERNAL
        colourSelected = image[row][col]; // EXTERNAL     
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
    */


    // SECOND SOLUTION USING QUEUE
    public int floodFillCount(int[][] image, int row, int col){

        if (image[row][col] == 0) return 0; // black first check
        
        boolean[][] checked = new boolean[image.length][image[0].length];
        Queue<PixelFF> queue = new LinkedList<PixelFF>();
        queue.add(new PixelFF(row, col));
        checked[row][col] = true;
        int changedPixels = 1;

        while (!queue.isEmpty()){
            PixelFF pixel = queue.poll();

            if (pixel.row+1 < image.length) {
                if (image[pixel.row+1][pixel.col] == image[row][col] && 
                    checked[pixel.row+1][pixel.col] == false ) {
                        checked[pixel.row+1][pixel.col] = true;
                        changedPixels++;                
                        queue.add(new PixelFF(pixel.row+1, pixel.col));
                }
            }
            if (pixel.row-1 >= 0) {
                if (image[pixel.row-1][pixel.col] == image[row][col] && 
                    checked[pixel.row-1][pixel.col] == false ) {
                        checked[pixel.row-1][pixel.col] = true;
                        changedPixels++;                
                        queue.add(new PixelFF(pixel.row-1, pixel.col));
                }
            }
            if (pixel.col+1 < image[0].length) {
                if (image[pixel.row][pixel.col+1] == image[row][col] && 
                    checked[pixel.row][pixel.col+1] == false ) {
                        checked[pixel.row][pixel.col+1] = true;
                        changedPixels++;    
                        queue.add(new PixelFF(pixel.row, pixel.col+1));
                }
            }
            if (pixel.col-1 > 0) {
                if (image[pixel.row][pixel.col-1] == image[row][col] && 
                    checked[pixel.row][pixel.col-1] == false ) {
                        checked[pixel.row][pixel.col-1] = true;
                        changedPixels++;    
                        queue.add(new PixelFF(pixel.row, pixel.col-1));
                }
            }
        }
        return changedPixels;
    }

    public class PixelFF{
        int row;
        int col;
        public PixelFF (int row, int col){
            this.row = row;
            this.col = col;
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

     // current complexity O(Pk)?
    public int brightestSquare(int[][] image, int k){
       
        int brightestSquare = 0;
        int rowCount = image.length;
        int colCount = image[0].length;
        int colIterations = colCount-k+1;
        int[][] rowBarArray = new int[rowCount][colIterations];

        // move down the rows
        for (int row=0; row<rowCount; row++){
            // calculate first row bar of k length
            for (int col=0; col<k; col++) {
                rowBarArray[row][0] += image[row][col];
            }
            // calculate other row bars to the right, across columns
            for (int col=1; col+k<=colCount; col++){
                rowBarArray[row][col] = rowBarArray[row][col-1];
                rowBarArray[row][col] -= image[row][col-1];
                rowBarArray[row][col] += image[row][col+k-1];            
                // adds up preceding rowbars if square exists
                // could this be made more efficeint here?
                // instead of counting backwards it could go along with the calculations?
                // 
                if (row >= k-1) {
                    int thisSquare = 0;
                    for (int j=0; j<k; j++){
                        thisSquare += rowBarArray[row-j][col];
                    }
                    if(thisSquare>brightestSquare) brightestSquare=thisSquare;
                }
            }
        }
        return brightestSquare;
    }


    // BRIGHTEST SQUARE SECOND ATTEMPT
    // try again treating the image as a 1d array with a scaling framework over the top
    // DELETE THIS
    // I THINK ITS STILL PK^2
    /*
    public int brightestSquare2(int[][] image, int k){

        int brightestSquare2 = 0;
        int rowCount = image.length;
        int colCount = image[0].length;
        
        int length = (rowCount*colCount);
        int head = 0;

        int[] imageArray = new int[length];
        int counter = 0;
        for (int r=0; r<rowCount; r++){
            for (int c=0; c<colCount; c++){
            imageArray[counter] = image[r][c];
            counter++;
            }
        }
        System.out.println(Arrays.toString(imageArray));
        // traverses every pixel
        for (int i = 0; i<length; i++){
            int thisSquare = 0;
            //for every k number of rows
            for (int j=i; j<k ;j++)
                // for every k pixels within a row
                for (int m=0; m<k; m++){
                    thisSquare += imageArray[(j*colCount)+m];
                    System.out.println("[j][m]: " + j + " " + m);
                }
            System.out.println("thisSquare: " + thisSquare);
            if (thisSquare>brightestSquare2) brightestSquare2 = thisSquare;
        }
        return brightestSquare2;
    }
    */

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
     // greedy algorithms : 
     // this is written using Primms O(E log V) NO! this is for MST
     // target complexity is Kruskals O(E log E) NO! this is for MST
     // use Djikstras! NO! only the max brightness matters
     //
     // use an amended version of Kruskals because the value of a path
     // is determinined by the ONE brightest pixel in its path
     // BUT how to check a path is formed?
     // breadth first search for connection - queue data structure






    //FIRST SOLUTION (WORKING)
/* 
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
        
        //darkest[] = {row, col, brightness}
        int[] darkest = {-1, -1, 300};
        
        //set up array of co-ordinates and brightness
        int[][] nextPos = { {focusRow-1, focusCol  , 300},    //up
                            {focusRow  , focusCol+1, 300},    //right
                            {focusRow+1, focusCol ,  300},    //down
                            {focusRow  , focusCol-1, 300}};   //left
        
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
     */

    // SECOND ATTEMPT AT DARKEST PATH (UNIFINISHED)
    /*
    public int darkestPath(int[][] image, int ur, int uc, int vr, int vc) {
        int brightest = 0;
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
        boolean[][] linked = new boolean[image.length][image[0].length];
        boolean completed = false;

        // create queue of brightness values
        for(int row=0; row<image.length; row++){
            for(int col=0; col<image[0].length; col++){
                if (!queue.contains(image[row][col])) 
                queue.add(image[row][col]);
            }
        }
        while (completed = false){
            brightest = queue.poll();
            for(int row=0; row<image.length; row++){
                for(int col=0; col<image[0].length; col++){
      
                }
            }

        }
        return brightest;
    }
    */

    //THIRD ATTEMPT AT DARKEST PATH USING PRIORITY QUEUE (WORKING)
    public int darkestPath(int[][] image, int ur, int uc, int vr, int vc){

        int brightest = 0;
        boolean[][] checked = new boolean[image.length][image[0].length];
        PriorityQueue<pixelDP> queue = new PriorityQueue<pixelDP>();
        queue.add(new pixelDP(ur, uc, image[ur][uc]));
        checked[ur][uc] = true;

        while(!queue.isEmpty()){

            pixelDP thisPixelDP = queue.poll();
            if (thisPixelDP.brightness > brightest) brightest = thisPixelDP.brightness; //brightness check
            if (thisPixelDP.row == vr && thisPixelDP.col == vc) break; // destination check
        
            if (thisPixelDP.row+1 < image.length) {
                if (checked[thisPixelDP.row+1][thisPixelDP.col] == false) {
                    checked[thisPixelDP.row+1][thisPixelDP.col] = true;
                    queue.add(new pixelDP(thisPixelDP.row+1, thisPixelDP.col, 
                                        image[thisPixelDP.row+1][thisPixelDP.col]));
                }
            }
            if (thisPixelDP.row-1 >= 0) {
                if (checked[thisPixelDP.row-1][thisPixelDP.col] == false) {
                    checked[thisPixelDP.row-1][thisPixelDP.col] = true;
                    queue.add(new pixelDP(thisPixelDP.row-1, thisPixelDP.col, 
                                        image[thisPixelDP.row-1][thisPixelDP.col]));
                }
            }
            if (thisPixelDP.col+1 < image[0].length) {
                if (checked[thisPixelDP.row][thisPixelDP.col+1] == false) {
                    checked[thisPixelDP.row][thisPixelDP.col+1] = true;
                    queue.add(new pixelDP(thisPixelDP.row, thisPixelDP.col+1, 
                                        image[thisPixelDP.row][thisPixelDP.col+1]));
                }
            }
            if (thisPixelDP.col-1 >= 0) {
                if (checked[thisPixelDP.row][thisPixelDP.col-1] == false) {
                    checked[thisPixelDP.row][thisPixelDP.col-1] = true;
                    queue.add(new pixelDP(thisPixelDP.row, thisPixelDP.col-1, 
                                        image[thisPixelDP.row][thisPixelDP.col-1]));
                }
            }   
        }
        return brightest;
    }

    public class pixelDP implements Comparable<pixelDP> {
        int row;
        int col;
        int brightness;
        public pixelDP (int row, int col, int brightness){
            this.row = row;
            this.col = col;
            this.brightness = brightness;
        }
        @Override
        public int compareTo(pixelDP o){
            if (brightness == o.brightness) return 0;
            if (brightness >  o.brightness) return 1;
            else return -1;
        }

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
    
    // FIRST VERSION WORKING
     public int[] brightestPixelsInRowSegments(int[][] image, int[][] queries) {
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

/* 

    //REVISED VERSION
    // Goal: O(Pixels*Columns + Queries)
    // aka: O(Rows*Rows*Columns + Queries)
    // Can I sort the queries so they're addressed in sequential order?
    // NO because there may be overlap and more than one per row
    public int[] brightestPixelsInRowSegments(int[][] image, int[][] queries){
        
        int[] brightestList = new int[queries.length];
        PriorityQueue<RowSegment> queue = new PriorityQueue<RowSegment>();

        int queryCount = 0;
        for (int i=0; i<queries.length; i++){
            //System.out.println("queryCount: " + queryCount);
            queue.add(new RowSegment(queries[i][0], queries[i][1], queries[i][2], queryCount));
            queryCount++;
        }
        
        //   PROBLEM WITH THIS IMPLEMENTATION: CANT DEAL WITH OVERLAPPING QUERIES


        RowSegment thisRowSegment = queue.poll();
        //System.out.println(thisRowSegment.row + " " + thisRowSegment.colStart + " " +  thisRowSegment.colEnd);

        for(int row=0; row<image.length; row++){
            System.out.println("row: " + row);

            for (int col=0; col<image[0].length; col++){

                System.out.println(row + " " + col);

                if (row == thisRowSegment.row &&
                    col >= thisRowSegment.colStart) { 
                        //System.out.println("derp");
                        System.out.println("image[row][col]: " + image[row][col]);
                        if (thisRowSegment.brightest <= image[row][col]) {

                                thisRowSegment.brightest = image[row][col];
                                System.out.println("brightest: " + thisRowSegment.brightest);
                        }
                    
                        //System.out.println("thisRowSegment.queryCount: " + thisRowSegment.queryCount);
                        if (thisRowSegment.colEnd == col) {

                            brightestList[thisRowSegment.queryCount] = thisRowSegment.brightest;
                            System.out.println("brightestList[thisRowSegment.queryCount]: " + brightestList[thisRowSegment.queryCount]);
                            System.out.println("thisRowSegment.brightest: " + thisRowSegment.brightest);
                            
                            thisRowSegment = queue.poll();
                            System.out.println("thisRowSegment.queryCount: " + thisRowSegment.queryCount);
                        }

                    }


            }


        }

        /* 
        RowSegment thisRowSegment;
        thisRowSegment = queue.poll();
        System.out.println(thisRowSegment.row + " " + thisRowSegment.colStart + " " + thisRowSegment.colEnd);
        thisRowSegment = queue.poll();
        System.out.println(thisRowSegment.row + " " + thisRowSegment.colStart + " " + thisRowSegment.colEnd);
        thisRowSegment = queue.poll();
        System.out.println(thisRowSegment.row + " " + thisRowSegment.colStart + " " + thisRowSegment.colEnd);
         */
        /*
        return brightestList;
    }

    public class RowSegment implements Comparable<RowSegment> {

        private int row;
        private int colStart;
        private int colEnd;
        private int brightest = 0;
        private int queryCount;

        public RowSegment(int row, int colStart, int colEnd, int queryCount){
            this.row = row;
            this.colStart = colStart;
            this.colEnd = colEnd;
            this.queryCount = queryCount;
        }

        @Override
        public int compareTo(RowSegment o){
            if (row == o.row) return 0;
            if (this.row > o.row) return 1;
            else return -1;
        }

    }

 */



}