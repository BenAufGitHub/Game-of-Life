package tools;

import java.io.IOException;
import java.util.List;
import java.awt.Point;
import java.util.ArrayList;


/**
 text files are provisionally structured as the following:
 Point(1,2) , Point(5,3)

 x: 1
 y: 2
 x: 5
 y: 3
 */
public class CoordinateSaver extends SaveManagement<Point>{


    public CoordinateSaver(){
        try {
            setFormat("coords");
        } catch (FileFormatException e) {
            e.printStackTrace(); //should never happen
        }
    }

    //------------------------------------ loading --------------------------------------------------------------

    /**
     * @return awt.Point List with all the active cells
     * @throws IOException
     */
    @Override
    protected List<Point> getObjects(String[] content) throws FaultyFileException {
        try {
            return linesToPoints(content);
        } catch(Exception e){
            throw new FaultyFileException("", e);
        }
    }


    private List<Point> linesToPoints(String[] content) {
        ArrayList<Point> list = new ArrayList<>();
        for(int i=0; i< content.length; i++){
            if(content[i].trim().length() == 0)
                break;
            String xLine = content[i++];
            String yLine = content[i];
            int x = Integer.parseInt(xLine.substring(3));
            int y = Integer.parseInt(yLine.substring(3));
            list.add(new Point(x,y));
        }
        return list;
    }

    //---------------------------------------- saving ------------------------------------------------------


    /**
     * @param points all coordinates that should be saved as active cells
     */
    @Override
    public List<String> elementsToFileText(List<Point> points) {
        List<String> list = new ArrayList<>();
        if(points == null || points.size() == 0)
            return list;
        for(Point p : points){
            list.add("x: " + p.x);
            list.add("y: " + p.y);
        }
        return list;
    }
}
