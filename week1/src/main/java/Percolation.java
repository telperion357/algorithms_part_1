import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private enum Status {
        OPENED,
        CLOSED
    }

    private final WeightedQuickUnionUF quickUnion;
    private final Status[][] grid;
    private final int n;
    private int openSitesNumber = 0;

    // Imagined top element index in quickUnion data structure;
    private final int TOP = 0;
    // Imagined bottom element index in quickUnion data structure (n*n + 1);
    private final int BOTTOM;

    /**
     * Creates n-by-n grid, with all sites initially blocked.
     * @param n The size of the grid. Should be integer greater than zero.
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should be > 0");
        }
        this.n = n;
        grid = new Status[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = Status.CLOSED;
            }
        }

        // quickUnion data structure to hold n*n grid elements plus imagined top and bottom element.
        // It will hold imagined top site under index 0, then n*n grid sites,
        // and imagined bottom element under index n*n + 1.
        quickUnion = new WeightedQuickUnionUF(n*n + 2);
        BOTTOM = n*n + 1;
    }

    /**
     * Opens the site (row, col) if it is not open already.
     * @param row Row index starting with 1: 1 <= row <= n;
     * @param col Column index starting with 1: 1 <= row <= n;
     */
    public void open(int row, int col) {
        checkBoundaries(row, col);
        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = Status.OPENED;
            openSitesNumber++;

            int quickUnionIndex = convertToQuickUnionIndex(row, col);

            // connect with site to the top
            if (isUpperRow(row)) {
                quickUnion.union(TOP, quickUnionIndex);
            } else {
                if (isOpen(row - 1, col)) {
                    int quickUnionIndexTop = convertToQuickUnionIndex(row - 1, col);
                    quickUnion.union(quickUnionIndexTop, quickUnionIndex);
                }
            }

            // connect with site to the bottom
            if (isLowerRow(row)) {
                quickUnion.union(BOTTOM, quickUnionIndex);
            } else {
                if (isOpen(row + 1, col)) {
                    int quickUnionIndexBottom = convertToQuickUnionIndex(row + 1, col);
                    quickUnion.union(quickUnionIndexBottom, quickUnionIndex);
                }
            }

            // connect with site to the left
            if (!isLeftColumn(col) && isOpen(row, col - 1)) {
                int quickUnionIndexLeft = convertToQuickUnionIndex(row, col - 1);
                quickUnion.union(quickUnionIndexLeft, quickUnionIndex);
            }

            // connect with site to the right
            if (!isRightColumn(col) && isOpen(row, col + 1)) {
                int quickUnionIndexRight = convertToQuickUnionIndex(row, col + 1);
                quickUnion.union(quickUnionIndexRight, quickUnionIndex);
            }
        }
    }

    /**
     * Is the site (row, col) open?
     * @param row Row index starting with 1: 1 <= row <= n;
     * @param col Column index starting with 1: 1 <= row <= n;
     * @return true if opened, false otherwise;
     */
    public boolean isOpen(int row, int col) {
        checkBoundaries(row, col);
        return grid[row - 1][col - 1] == Status.OPENED;
    }

    /**
     * Is the site (row, col) full?
     * @param row Row index starting with 1: 1 <= row <= n;
     * @param col Column index starting with 1: 1 <= row <= n;
     * @return true if the site is connected to any opened site in the upper row, false otherwise;
     */
    public boolean isFull(int row, int col) {
        checkBoundaries(row, col);
        return quickUnion.find(TOP) == quickUnion.find(convertToQuickUnionIndex(row, col));
    }

    /**
     * Returns the number of open sites
     * @return the number of open sites
     */
    public int numberOfOpenSites() {
        return openSitesNumber;
    }

    /**
     * Does the system percolate?
     * @return true if any opened site in the upper row is connected to any opened site in the lower row.
     */
    public boolean percolates() {
        return quickUnion.find(TOP) == quickUnion.find(BOTTOM);
    }

    private void checkBoundaries(int row, int col) {
        if (row < 1 || row > n) {
            throw new IllegalArgumentException("row should be >= 1 and <= n");
        }
        if (col < 1 || col > n) {
            throw new IllegalArgumentException("col should be >= 1 and <= n");
        }
    }

    /**
     * Converts grid coordinates (row, col) into one dimensional index of quick union data structure.
     * returns (row - 1)*n + col;
     */
    private int convertToQuickUnionIndex(int row, int col) {
        return (row - 1)*n + col;
    }

    private boolean isUpperRow(int row) {
        return row == 1;
    }

    private boolean isLowerRow(int row) {
        return row == n;
    }

    private boolean isLeftColumn(int col) {
        return col == 1;
    }

    private boolean isRightColumn(int col) {
        return col == n;
    }
}
