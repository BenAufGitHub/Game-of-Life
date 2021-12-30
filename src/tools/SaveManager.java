package tools;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
public class SaveManager {


    /**
     *
     * @param filename that you want to name your save: without paths, without ".txt" endings etc.
     * @param points all coordinates that should be saved as active cells
     */
    public static void save(String filename, Point[] points){
        if(points == null || points.length <2)
            return;
        filename = format(filename);
        if(filename.length() < 4)
            filename += "xx" + filename;

        List<String> lines = new ArrayList();
        for(Point point : points){
            if(point == null)
                continue;
            lines.add("x: " + point.x);
            lines.add("y: " + point.y);
        }
        write(filename, lines);
    }


    /**
     *
     * @param name -> Filename of SaveData, all Saves should be placed into the resources folder in order to load them
     *             with this message.
     * @return awt.Point Array with all the active cells
     * @throws IOException
     */
    public static Point[] get(String name) throws IOException {
        ArrayList<Point> points = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("resources/saves/"+name));
        try {
            String x = br.readLine();
            String y = br.readLine();
            while (x != null && y != null && !x.trim().isEmpty()) {
                int h = Integer.parseInt(x.substring(3));
                int v = Integer.parseInt(y.substring(3));
                points.add(new Point(h, v));
                x = br.readLine();
                y = br.readLine();
            }
        } finally { br.close(); }

        Point[] array = new Point[points.size()];
        for(int i=0; i<points.size(); i++)
            array[i] = points.get(i);

        return array;
    }


    private static void write(String path, List<String> lines) {
        try {
            Path p = Paths.get(path);
            Files.write(p, lines, StandardCharsets.UTF_8);
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
     * formates strings in a way that they do not contain a ".txt" or spaces
     * @param unformated
     * @return
     */
    private static String format(String unformated){
        String result = unformated.trim();
        int ind = result.indexOf(".");
        while(ind != -1){String front = result.substring(0,ind);
            String end = (++ind == result.length()) ? "" : result.substring(ind, result.length());
            result = front + end;
            ind = result.indexOf(".");
        }
        return "./resources/saves/"+ result + ".txt";
    }


    public static String[] getSaveNames(){
        File folder = new File("./resources/saves/");
        File[] listOfFiles = folder.listFiles();
        String[] names = new String[listOfFiles.length];
        for(int i=0; i< listOfFiles.length; i++){
            names[i] = listOfFiles[i].toString().substring(18);
        }
        return names;
    }


}
