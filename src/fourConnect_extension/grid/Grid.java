package fourConnect_extension.grid;

public class Grid<T> {
    private GridNode[][] nodes;
    private GridNode pointer;
    int columns;
    int rows;

    public Grid(int columns, int rows){
        this.columns = columns;
        this.rows = rows;
        initGridNodes(columns, rows);
        initPointer();
    }


    private void initPointer() {
        pointer = (isInBounds(0,0)) ? getNode(0, 0) : null;
    }


    private void initGridNodes(int columns, int rows) {
        nodes = new GridNode[rows][columns];
        for(int y=0; y<rows; y++){
            for(int x=0; x<columns; x++){
                nodes[y][x] = new GridNode(x, y, this, null);
            }
        }
    }


    GridNode getNode(int x, int y){
        return nodes[y][x];
    }


    //------------------------------- public methods ----------------------------------------

    public GridNode<T> peek(Direction dir){
        return switch(dir){
            case UP -> pointer.getUp();
            case DOWN -> pointer.getDown();
            case RIGHT -> pointer.getRight();
            case LEFT -> pointer.getLeft();
            case UP_LEFT -> (pointer.getLeft() != null) ? pointer.getLeft().getUp() : null;
            case UP_RIGHT -> (pointer.getRight() != null) ? pointer.getRight().getUp() : null;
            case DOWN_LEFT -> (pointer.getLeft() != null) ? pointer.getLeft().getDown() : null;
            case DOWN_RIGHT -> (pointer.getRight() != null) ? pointer.getRight().getDown() : null;
        };
    }


    public GridNode<T> get(){
        return pointer;
    }


    public void move(Direction dir){
        GridNode<T> next = peek(dir);
        pointer = next;
    }


    public void moveTo(int x, int y){
        pointer = getNode(x,y);
    }


    public boolean isInBounds(int x, int y){
        if(x<0 || y<0)
            return false;
        return x<columns && y<rows;
    }


    public int getColumns(){
        return  columns;
    }

    public int getRows(){
        return  rows;
    }

    //--------------------------------------- printing ------------------------------------------------------------

    public Object[][] toMatrix(){
        Object[][] obj = new Object[rows][columns];
        for(int y=0; y<getRows(); y++){
            for(int x=0; x<getColumns(); x++){
                obj[y][x] = getNode(x, y).getContent();
            }
        }
        return obj;
    }
}
