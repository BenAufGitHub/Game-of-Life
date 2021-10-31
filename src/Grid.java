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
        for(int y=0; y<getHeight(); y++){
            for(int x=0; x<getWidth(); x++){
                // from topLeft -> topmid -> topright -> midleft -> midright -> midleft -> ... neighbours
                List<Cell> neighbours = new ArrayList<Cell>();
                for(int k=-1; k<2; k++){
                    for(int l=-1; k<2; k++){

                        if((k==0 && l == 0) || !inBounds(k+y, getHeight()) || !inBounds(l+x, getWidth()))
                            continue;
                        neighbours.add(getCell(l+x, k+y));
                    }
                }
                Cell[] arr= new Cell[neighbours.size()];
                for(int s=0; s<neighbours.size(); s++){
                    arr[s] = neighbours.get(s);
                }
                getCell(x,y).setNeighbours(arr);
            }
        }
    }

    public boolean inBounds(int num, int bounds){
        return (num>=0) && (num < bounds);
    }

    public void fill(Cell[][] matrix){
        for(int y=0; y< matrix.length; y++){
            for(int x=0; x<matrix[y].length; x++){
                matrix[y][x] = new Cell(false, x, y);
            }
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder("");
        for(int i=0; i<getHeight(); i++){
            sb.append("[");
            for(int j=0; j<getWidth(); j++){
                Cell cell = getCell(j, i);
                if(cell.isAlive())
                    sb.append("T ");
                else
                    sb.append("F ");
            }
            sb.append("]\n");
        }
        return sb.toString();
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
