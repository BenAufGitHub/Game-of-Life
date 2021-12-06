import java.util.ArrayList;

public class Grid {
    private Cell[][] grid;
    private int width;
    private int height;
    
    public Grid(int x, int y){
        this.width = x;
        this.height = y;
        grid = new Cell[y][x];
        
        fill(grid);
        connect();
    }

    public Cell getCell(int x, int y){
        return grid[y][x];
    }


    /**
     * fills grid with cells, called by Constructor
     * @param grid
     */
    private void fill(Cell[][] grid) {
        for(int y=0; y< grid.length; y++){
            for(int x=0; x<grid[y].length; x++){
                grid[y][x] = new Cell(x, y);
            }
        }
    }


    /**
     * all Cells store their neighbours -> after initialization (fill()) , this method takes care of that
     */
    private void connect() {
        for(int y=0; y< height; y++){
            for(int x=0; x< width; x++){
                Cell[] neighbours = getSurroundingCells(x,y);
                getCell(x,y).setNeighbours(neighbours);
            }
        }
    }

    public int getWidth(){ return width; }
    public int getHeight(){ return height; }



    // ------------------------------- less relevant methods ------------------------------



    /**
     * given an offset, validate wether the coordinates are in bounds of the grid
     * @param row
     * @param column
     * @param xDiff
     * @param yDiff
     * @return
     */
    private boolean validNeighbour(int row, int column, int xDiff, int yDiff){
        if(xDiff == 0 && yDiff == 0)
            return false;
        int x = row + xDiff;
        int y = column + yDiff;

        if( x >= width || x < 0)
            return false;
        if (y >= height || y < 0)
            return false;
        return true;
    }


    /**
     * returns all existing Cell neighbours of a cell
     */
    private Cell[] getSurroundingCells(int x, int y){
        ArrayList<Cell> street = new ArrayList<>();
        for(int k=-1; k<2; k++){        //yDiff
            for(int l=-1; l<2; l++){           //xDiff

                if(!validNeighbour(x, y, l, k))      //in Bounds
                    continue;
                street.add(getCell(l+x, k+y));
            }
        }
        Cell[] arr = new Cell[street.size()];
        for(int k=0; k < street.size(); k++){          //packing into array
            arr[k] = street.get(k);
        }

        return arr;
    }
}
