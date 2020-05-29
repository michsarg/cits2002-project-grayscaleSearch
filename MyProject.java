/*
* CITS2200 Project S1 2020
* @author Michael Sargeant
* */

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.LinkedList;

public class MyProject implements Project {

    public MyProject(){
    }

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
    public int floodFillCount(int[][] image, int row, int col){

        //check if starting pixel is already black
        if (image[row][col] == 0) return 0;   
        
        //set up queue and tracking variables
        boolean[][] checked = new boolean[image.length][image[0].length];
        int changedPixels = 0;
        Queue<Pixel> queue = new LinkedList<Pixel>();
        
        //add the starting pixel to the queue and updates checks
        checked[row][col] = true;
        changedPixels++;
        queue.add(new Pixel(row, col));
        
        //takes pixel from queue and checks neighbouring pixels for enqueuing
        while (!queue.isEmpty()){
            Pixel current = queue.poll();

            if (current.row+1 < image.length) {
                if (image[current.row+1][current.col] == image[row][col] && 
                    checked[current.row+1][current.col] == false ) {
                        checked[current.row+1][current.col] = true;
                        changedPixels++;                
                        queue.add(new Pixel(current.row+1, current.col));
                }
            }
            if (current.row-1 >= 0) {
                if (image[current.row-1][current.col] == image[row][col] && 
                    checked[current.row-1][current.col] == false ) {
                        checked[current.row-1][current.col] = true;
                        changedPixels++;                
                        queue.add(new Pixel(current.row-1, current.col));
                }
            }
            if (current.col+1 < image[0].length) {
                if (image[current.row][current.col+1] == image[row][col] && 
                    checked[current.row][current.col+1] == false ) {
                        checked[current.row][current.col+1] = true;
                        changedPixels++;    
                        queue.add(new Pixel(current.row, current.col+1));
                }
            }
            if (current.col-1 > 0) {
                if (image[current.row][current.col-1] == image[row][col] && 
                    checked[current.row][current.col-1] == false ) {
                        checked[current.row][current.col-1] = true;
                        changedPixels++;    
                        queue.add(new Pixel(current.row, current.col-1));
                }
            }
        }
        return changedPixels;
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
    public int brightestSquare(int[][] image, int k){
        
        int brightestSquare = 0;
        int[][] subRowSum = new int[image.length][image[0].length-k+1];

        //outer loop moves down the rows
        for (int row=0; row<image.length; row++){
            
            //inner loop calculates the first subrow
            for (int col=0; col<k; col++) {
                subRowSum[row][0] += image[row][col];
            }

            //inner loop calculates remaining subrows
            for (int col=1; col+k<=image[0].length; col++) {
                subRowSum[row][col]  = subRowSum[row][col-1];
                subRowSum[row][col] -= image[row][col-1];
                subRowSum[row][col] += image[row][col+k-1];

                //calculates square brightness at last sub row
                if (row >= k-1) {
                    int square = 0;
                    for (int j=0; j<k; j++){
                        square += subRowSum[row-j][col];
                    }
                    if(square>brightestSquare) brightestSquare=square;
                }
            }
        }
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
    public int darkestPath(int[][] image, int ur, int uc, int vr, int vc){

        int brightest = 0;
        boolean[][] checked = new boolean[image.length][image[0].length];
        PriorityQueue<Pixel> queue = new PriorityQueue<Pixel>();

        //enqueue starting pixel
        checked[ur][uc] = true;
        queue.add(new Pixel(ur, uc, image[ur][uc]));

        //checks front of queue pixel and enqueues relevant neighbours
        while(!queue.isEmpty()){

            Pixel pixel = queue.poll();
            if (pixel.brightness > brightest) brightest = pixel.brightness; //brightness check
            if (pixel.row == vr && pixel.col == vc) break; //target destination check
        
            if (pixel.row+1 < image.length) {
                if (checked[pixel.row+1][pixel.col] == false) {
                    checked[pixel.row+1][pixel.col] = true;
                    queue.add(new Pixel(pixel.row+1, pixel.col, image[pixel.row+1][pixel.col]));
                }
            }
            if (pixel.row-1 >= 0) {
                if (checked[pixel.row-1][pixel.col] == false) {
                    checked[pixel.row-1][pixel.col] = true;
                    queue.add(new Pixel(pixel.row-1, pixel.col, image[pixel.row-1][pixel.col]));
                }
            }
            if (pixel.col+1 < image[0].length) {
                if (checked[pixel.row][pixel.col+1] == false) {
                    checked[pixel.row][pixel.col+1] = true;
                    queue.add(new Pixel(pixel.row, pixel.col+1, image[pixel.row][pixel.col+1]));
                }
            }
            if (pixel.col-1 >= 0) {
                if (checked[pixel.row][pixel.col-1] == false) {
                    checked[pixel.row][pixel.col-1] = true;
                    queue.add(new Pixel(pixel.row, pixel.col-1, image[pixel.row][pixel.col-1]));
                }
            }   
        }
        return brightest;
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
     public int[] brightestPixelsInRowSegments(int[][] image, int[][] queries) {
        
        int[] brightestList = new int[queries.length];      
        
        // traverses queries
        for (int i=0; i<queries.length; i++){
            int brightest = 0;
            // traverses pixel range for brightest
            for (int j=queries[i][1]; j<queries[i][2]; j++) {
                if (image[queries[i][0]][j] > brightest){
                    brightest = image[queries[i][0]][j];
                }
            }
            brightestList[i] = brightest;
        }
        return brightestList;
    }

    public class Pixel implements Comparable<Pixel> {
        int row;
        int col;
        int brightness;
        public Pixel(int row, int col){
            this.row = row;
            this.col = col;
        }
        public Pixel (int row, int col, int brightness){
            this.row = row;
            this.col = col;
            this.brightness = brightness;
        }
        @Override
        public int compareTo(Pixel o){
            if (brightness == o.brightness) return 0;
            if (brightness >  o.brightness) return 1;
            else return -1;
        }
    }
}