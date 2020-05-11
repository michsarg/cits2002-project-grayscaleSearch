/*
* CITS2200 Project
* @author Michael Sargeant
* */

public class MyProject implements Project {

    // FIELDS
    int image[][];
    int row;
    int col;
    int changedPixels;
    int colourSelected;
    boolean checked[][];
    int rowLength;
    int colLength;
    
    int size;

    int k;
    int totalBrightness;

    // CONSTRUCTOR
    // no parameter method required
    public MyProject(){
        //System.out.println("PixelAnalyser");

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
    
    
    //problem: assumes square when rectangle is input
    
    public int floodFillCount(int[][] image, int row, int col){
        
    	//test for black pixel selection
    	if (image[row][col] == 0) return 0;
    	    	
    	
    	changedPixels = 1;
        this.image = image;
        this.colourSelected = image[row][col];     
        this.rowLength = image.length;
        this.colLength = image[0].length;
        
//        System.out.println(rowLength);
//        System.out.println(colLength);
        
        this.checked = new boolean[rowLength][colLength];
        this.checked[row][col] = true;
        adjacencyCheck(row, col);
        return changedPixels;
    }

    private void adjacencyCheck(int row, int col) {
        pixelCheck(row+1, col);
        pixelCheck(row-1, col);
        pixelCheck(row, col+1);
        pixelCheck(row, col-1);
    }

    private void pixelCheck(int row, int col) {
        if (row >= 0 && row < rowLength     &&
            col >= 0 && col < colLength     &&
            this.checked[row][col] == false &&
            colourSelected == image[row][col]) {
                changedPixels++;
                checked[row][col] = true;
                adjacencyCheck(row, col);
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
    public int brightestSquare(int[][] image, int k){
        // do this with a binary search, dividing up the grid recursively
        // at the lower level do a more fine grained search
        //      use window iterator???

        this.k = k;
        this.image = image;
        this.size = image.length;
        this.totalBrightness = 0;

        // divide up the image into k sized squares and find the brightest
        //then search a range of 4 for the fine grained location of brightest
       // System.out.println("k: " + k);
       // System.out.println("size: " + size);
        //problem is k doesnt neatly fit into size !!!

        int newSize = (size/k);
        //System.out.println("newSize: " + newSize);


        int[][] imageSquared = new int[newSize][newSize];
        for (int i = 0; i<newSize; i++) {
            for (int j = 0; j<newSize; j++) {
                imageSquared[i][j] = -1;
            }
        }
      // System.out.println("imageSquared[0][0]: " + imageSquared[0][0]);

        for (int r = 0; r<(size/k); r++) {
            for (int c = 0; c<(size/k); c++) {

                int sumCount = 0;
                for (int h = 0; h<k; h++){
                    for (int v = 0; v<k; v++){
                        sumCount += this.image[(r*k)+h][(c*k)+v];
                        //System.out.println("sumCount: " + sumCount);
                    }
                }
                imageSquared[r][c] = sumCount;

            }
        }

        //System.out.println(Arrays.deepToString(imageSquared));
        //System.out.println(imageSquared[0][0]);

        return sumSquare(0, 0, size);

    }



    private int sumSquare(int rowStart, int colStart, int size) {
        int sum = 0;
        for (int r = 0; r<size; r++){
            for (int c = 0; c<size; c++) {
             sum += this.image[rowStart+r][colStart+c];
            }
        }
        return sum;
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
    	return 0;
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
    	int[] test = { 0, 1, 2};
    	return test;
    }




}