 import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/* *****************************************************************************
 *  Name: Arjoban Singh
 *  Date: December 31, 2019
 *  Description: I used WeightedQuickUnionUF to make this Percolation class.
 **************************************************************************** */
public class Percolation {

    private boolean[][] matrix;
    private int matrixLength;
    private WeightedQuickUnionUF weightQuickUnion;
    private int testUpperBox;
    private int testLowerBox;
    private int openedBoxes;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        if (n <= 0)
        {
            throw new IllegalArgumentException("N must be greater than 0");
        }
        matrix = new boolean[n][n];

        // matrix intially blocked, so setting each element of matrix to be false
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                matrix[i][j] = false;
            }
        }

        this.matrixLength = n;
        this.weightQuickUnion = new WeightedQuickUnionUF(n*n + 2);
        this.testUpperBox = 0;
        this.testLowerBox = n*n + 1;
        this.openedBoxes = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        if (!isOpen(row, col)) {
            // index of element having this row and column in union array
            int indexInUnionArray = numberForRowAndCol(row, col);

            // open it by changing it to true in matrix
            matrix[row - 1][col - 1] = true;

            this.openedBoxes++; // incrementing opened boxes, everytime new box opens.

            // if row is equal to 1, than open this and union it with testUpperBox
            if (row == 1) {
                weightQuickUnion.union(indexInUnionArray, testUpperBox);
            }

            // if row is equal to length of matrix or n, than open this and union it with testLowerBox
            if (row == this.matrixLength) {
                weightQuickUnion.union(indexInUnionArray, testLowerBox);
            }

            // if this box has upper neighbour and is open union with it
            if (row - 1 != 0) {
                if (isOpen(row - 1, col)) {
                    int indexUpperNeigh = numberForRowAndCol(row - 1, col);
                    weightQuickUnion.union(indexInUnionArray, indexUpperNeigh);
                }
            }

            // if this box has lower neighbour and is open union with it
            if (!((row + 1) > this.matrixLength)) {
                if (isOpen(row + 1, col)) {
                    int indexBottomNeigh = numberForRowAndCol(row + 1, col);
                    weightQuickUnion.union(indexInUnionArray, indexBottomNeigh);
                }
            }

            // if this box has left neighbour and is open union with it
            if (col - 1 != 0) {
                if (isOpen(row, col - 1)) {
                    int indexLeftNeigh = numberForRowAndCol(row, col - 1);
                    weightQuickUnion.union(indexInUnionArray, indexLeftNeigh);
                }
            }

            // if this box has right neighbour and is open union with it
            if (!(col + 1 > this.matrixLength))
            {
                if (isOpen(row, col + 1)) {
                    int indexRightNeigh = numberForRowAndCol(row, col + 1);
                    weightQuickUnion.union(indexInUnionArray, indexRightNeigh);
                }
            }
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        if (row <= 0 || row > matrixLength || col <= 0 || col > matrixLength)
        {
            throw new IllegalArgumentException("row or column is out of index");
        }
        return matrix[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        if (row <= 0 || row > matrixLength || col <= 0 || col > matrixLength)
        {
            throw new IllegalArgumentException("row or column is out of index");
        }

        // if number or box having this row and column is connected to testUpperBox, than it is Full
        int thisBox = numberForRowAndCol(row, col);
        // return this.weightQuickUnion.connected(this.testUpperBox, thisBox); // beacuse connected method is deprceated

        return this.weightQuickUnion.find(this.testUpperBox) == this.weightQuickUnion.find(thisBox);
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return this.openedBoxes;
    }

    // does the system percolate?
    public boolean percolates()
    {
        // if testUpperBox and testLowerBox are connected or simply if their canonical element is same, than system percolates
        return this.weightQuickUnion.find(this.testUpperBox) == this.weightQuickUnion.find(this.testLowerBox);
    }

    // test client (optional)
     public static void main(String[] args)
     {

     }


    private int numberForRowAndCol(int row, int col)
    {
        return ((row - 1) * this.matrixLength) + col;
    }


}
