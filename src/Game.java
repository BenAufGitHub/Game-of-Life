public class Game {
    public static void main(String args[]){
        Grid grid = new Grid(10,5);
        for(int i=0; i<grid.getWidth(); i++){
            for(int j=0; j<grid.getHeight(); j++){
                Cell cell = grid.getCell(i,j);
                System.out.println(cell.isAlive());
            }
        }
    }
}
