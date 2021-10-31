import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class Grid {
    private Dimension dimension;
    private Cell[][] matrix;

    public Grid(int width, int height){
        dimension = new Dimension(width,height);
        matrix    = new Cell[height][width];

        fill(matrix);
        connectNodes(matrix);
    }

    //Time = O(n)
    public void connectNodes(Cell[][] matrix){
        for(int i=0; i<dimension.height; i++){
            for(int j=0; j<dimension.width; j++){
                // from topLeft -> topmid -> topright -> midleft -> midright -> midleft -> ... neighbours
                List<Cell> neighbours = new ArrayList<Cell>();
                for(int k=-1; k<2; k++){
                    for(int l=-1; k<2; k++){

                        if((k==0 && l == 0) || !inBounds(k+i, getHeight()) || !inBounds(l+j, getWidth()))
                            continue;
                        neighbours.add(matrix[k+i][l+j]);
                    }
                }
                Cell[] arr= new Cell[neighbours.size()];
                for(int s=0; s<neighbours.size(); s++){
                    arr[s] = neighbours.get(s);
                }
                matrix[i][j].setNeighbours(arr);
            }
        }
    }

    public boolean inBounds(int num, int bounds){
        return (num>=0) && (num < bounds);
    }

    public void fill(Cell[][] matrix){
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[i].length; j++){
                matrix[i][j] = new Cell(false, j, i);
            }
        }
    }

    public Cell getCell(int x, int y){
        return matrix[y][x];
    }

    public int getWidth(){
        return dimension.width;
    }
    public int getHeight(){
        return dimension.height;
    }
}
