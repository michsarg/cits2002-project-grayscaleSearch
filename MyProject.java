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
    int brightestSquare;

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
    
    
    public int floodFillCount(int[][] image, int row, int col){
        
    	//test for black pixel selection
    	if (image[row][col] == 0) return 0;
    	    	
    	changedPixels = 1;
        this.image = image;
        this.colourSelected = image[row][col];     
        this.rowLength = image.length;
        this.colLength = image[0].length;
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

        this.k = k;
        this.image = image;
        this.colourSelected = image[row][col];     
        this.rowLength = image.length;
        this.colLength = image[0].length;
        
        //System.out.println("rowLength = " + rowLength);
        //System.out.println("colLength = " + colLength);
        
        this.brightestSquare = 0;
        
        for (int r = 0; r < (rowLength-k+1); r++) {
        	for (int c = 0; c < (colLength-k+1); c++) {
        		
        		//System.out.println("r = " + r + "  c = " + c );
        		
        		int checkSquare = 0; 
        		
        		for (int i = r; i<(r+k); i++ ) {
        			for (int j = c; j<(c+k); j++ ) {
        				
        				
        				//System.out.println("image["+i+"]["+j+"] = " + image[i][j]);
                        checkSquare += image[i][j];
        			
        			}
        		}
        		
        		if (checkSquare>brightestSquare) brightestSquare = checkSquare;
        		//System.out.println(checkSquare);
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
        
        this.image = image;
        this.row = ur;
        this.col = uc;

        this.rowLength = image.length;
        this.colLength = image[0].length;


        int[] darkestAdjacent = findDarkestAdjacent(ur, uc);

        return darkestAdjacent[2];
    
    }

    public int[] findDarkestAdjacent(int focusRow, int focusCol){

        // what do i want this to return??? 
        //  the row col and brightness of darkest adjacent
        // need to check within valid range

        // darkest[0] = row
        // darkest[1] = col
        // darkest[2] = brightness
        int[] darkest = {-1, -1, 300};

        int north = (pixelBrightness(focusRow-1, focusCol));
        int east  = (pixelBrightness(focusRow,   focusCol+1));
        int south = (pixelBrightness(focusRow+1, focusCol));
        int west  = (pixelBrightness(focusRow,   focusCol-1));

        if  (north < darkest[2]) {
            darkest[0] = focusRow+1;
            darkest[1] = focusCol;
            darkest[2] = north;
        }
        if  (east < darkest[2]) {
            darkest[0] = focusRow;
            darkest[1] = focusCol+1;
            darkest[2] = east;
        }
        if  (south < darkest[2]) {
            darkest[0] = focusRow-1;
            darkest[1] = focusCol;
            darkest[2] = south;
        }
        if  (west < darkest[2]) {
            darkest[0] = focusRow;
            darkest[1] = focusCol-1;
            darkest[2] = west;
        }

        return darkest;

    }

    public int pixelBrightness (int focusRow, int focusCol){
        
        int pixelBrightness = 300; // sentinal value

        if (focusRow >=0 && focusRow < rowLength &&
            focusCol >=0 && focusCol < colLength)

            pixelBrightness = image[focusRow][focusCol];

        return pixelBrightness;
    
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