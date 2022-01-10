package tools;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.List;

public class JSONSaver extends SaveManagement<JSONObject> {

    public JSONSaver(){
        try {
            setFormat("json");
        } catch (FileFormatException e) {
            e.printStackTrace();  //should never happen
        }
    }


    @Override
    protected List<JSONObject> getObjects(String[] file_content) throws FaultyFileException {
        List<JSONObject> list = new ArrayList<>();
        try {

            JSONArray array = (JSONArray) new JSONParser().parse(file_content[0]);
            for (Object o : array)
                list.add((JSONObject) o);
            return list;

        } catch (Exception e){
            throw new FaultyFileException("File is faulty.", e);
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<String> elementsToFileText(List<JSONObject> list) {
        JSONArray array = new JSONArray();
            array.addAll(list);
        List<String> text = new ArrayList<>();
        text.add(array.toJSONString());
        return text;
    }
}
