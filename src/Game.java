public class Game {
    public static void main(String args[]){

        Grid grid = new Grid(10,11);
        for(int i=0; i<grid.getHeight(); i++){
            for(int j=0; j<grid.getWidth(); j++){
                Cell cell = grid.getCell(j,i);
                System.out.print("-");
            }
            System.out.println();
        }
    }

    public static void test(){
        int[][] m = new int[10][5];

        for(int i=0; i<m.length; i++){
            for(int j=0; j<m[i].length; j++){
                m[i][j] = 0;
                System.out.print(m[i][j]);
            }
            System.out.println("");
        }

    }
}
