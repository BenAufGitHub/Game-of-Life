package tools;

/*
textfiles are provisionally structured as the following:
Point(1,2) , Point(5,3)

x: 1
y: 2
x: 5
y: 3
 */


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.awt.Point;
import java.util.ArrayList;

public class SaveWriter {
    public static void main(String[] args){
        Point[] arr = new Point[2];
        arr[0] = new Point(2,5);
        arr[1] = new Point(1,0);
        SaveWriter.save("Marianne", arr);
    }

    public static void save(String filename, Point[] points){
        filename = format(filename);
        if(filename.length() < 4)
            filename += "xx" + filename;

        List<String> lines = new ArrayList();
        for(Point point : points){
            lines.add(new String("x: "+point.x));
            lines.add(new String("y: "+ point.y));
        }
        write(filename, lines);
    }

    public static Point[] get(String relativePath){
        return new Point[0];
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
    public static String format(String unformated){
        String result = unformated.trim();
        int ind = result.indexOf(".");
        while(ind != -1){String front = result.substring(0,ind);
            String end = (++ind == result.length()) ? "" : result.substring(ind, result.length());
            result = front + end;
            ind = result.indexOf(".");
        }
        return "./resources/"+ result + ".txt";
    }
}
