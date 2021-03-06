package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class SaveManagement<Elements> {
    private String format = "txt";


    /**
     * for this to work, the subclass needs to implement the method get()
     * @throws CouldNotLoadFileException
     */
    public List<Elements> load(String saveName) throws CouldNotLoadFileException, FileFormatException {
        assertCorrectFileFormat(saveName);
        String path = nameToRelativePath(saveName);
        String[] file_content = getPlainText(path);
        return getObjects(file_content);
    }


    /**
     * Translates file content into the save-objects.
     * This deciphering-method should use the same concept as the enciphering-method elementsToText.
     */
    protected abstract List<Elements> getObjects(String[] file_content) throws FaultyFileException;


    public void save(String saveName, List<Elements> elements){
        customizeSaveName(saveName);
        saveName = formatWithRelativePath(saveName);
        List<String> lines = elementsToFileText(elements);
        write(saveName, lines);
    }


    /**
     * Override method to customize the save name before it is formatted.
     */
    protected void customizeSaveName(String name){}


    /**
     *  This method defines how the elements are portrayed as text in the save-document. (encipher)
     *  This method has to be a partner of getObjects (decipher) as they should use the same System.
     */
    public abstract List<String> elementsToFileText(List<Elements> elements);


    public String[] getSaveNames(){
        File folder = new File("./resources/saves/");
        File[] listOfFiles = folder.listFiles();
        listOfFiles = (listOfFiles != null) ? listOfFiles : new File[0];

        String[] names = new String[listOfFiles.length];
        for(int i=0; i< listOfFiles.length; i++){
            // substring cuts the path away
            names[i] = listOfFiles[i].toString().substring(18);
        }
        return filterSaves(names);
    }


    protected void write(String path, List<String> lines) {
        try {
            Path p = Paths.get(path);
            Files.write(p, lines, StandardCharsets.UTF_8);
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    private String[] getPlainText(String path) throws CouldNotLoadFileException {
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(path));
            return read(br);
        } catch (IOException e) {
            throw new CouldNotLoadFileException("Path "+path+" is unable to load.", e);
        } finally {
            closeReader(br);
        }
    }


    /**
     * formats strings in a way that they do not contain dots or spaces
     * @param saveName
     * @return ./resources/saves/properSaveName.format
     */
    public String formatWithRelativePath(String saveName){
        String result = saveName.trim();
        int ind = result.indexOf(".");
        while(ind != -1){
            String front = result.substring(0,ind);
            String end = (++ind == result.length()) ? "" : result.substring(ind);
            result = front + end;
            ind = result.indexOf(".");
        }
        return "./resources/saves/"+ result + "." + getFormat();
    }


    /**
     * @param saveName a name of save in resources/saves folder, may or may not include format
     * @return the relative Path to be used when fetching the file
     */
    public String nameToRelativePath(String saveName){
        saveName = "./resources/saves/" + saveName;
        if(isASave(saveName))
            return saveName;
        return saveName + "." +getFormat();
    }


    /**
     * Sets what is appended to the save, like "savename.txt" or "savename.json".
     * @param format Accepted formats: 2-8 Characters after the dot.
     */
    protected void setFormat(String format) throws FileFormatException {
        this.format = toValidFormat(format);
    }


    public String getFormat(){
        return format;
    }


    public String determineFormatFromString(String save) throws NoFormatFoundException {
        StringBuilder sb = new StringBuilder();
        for(int i=save.length()-1; i >= 0; i--){
            if(save.charAt(i) == '.')
                break;
            sb.insert(0, save.charAt(i));
        }
        String result = sb.toString();
        if(result.equals(""))
            throw new NoFormatFoundException("The String does not contain a file extension.");
        return sb.toString();
    }



    //----------------------------- private Operations / background Operations --------------------

    //-------------------------------------> file format ------------------------------------------


    private void assertCorrectFileFormat(String saveName) throws FileFormatException {
        if(isASave(saveName) || saveName.indexOf('.') == -1)
            return;
        throw new FileFormatException("Cannot load the given file with a JSONSaver.");
    }


    /**
     * @param format like file.txt or file.json
     * @return if no exception is thrown, it returns a valid format
     * @throws FileFormatException format too short, too long or with special characters
     */
    private String toValidFormat(String format) throws FileFormatException {
        if(format == null || format.strip().length() < 2)
            throw new FileFormatException("Cannot use the given file-format " + format);
        // If someone writes the format (eg "txt") with a dot (".txt") the dot gets removed.
        if(format.strip().charAt(0) == '.')
            format = format.strip().substring(1);
        approveFormat(format.strip());
        return format;
    }


    /**
     * Checks for length and special characters and throws exception accordingly.
     */
    private void approveFormat(String format) throws FileFormatException {
        if(format.length() < 2 || format.length() > 8)
            throw new FileFormatException("File format " + format + " not between 2 and 8 characters long");
        if(!isLetters(format))
            throw new FileFormatException("File format " + format + " is not allowed to contain special letters");
    }


    private boolean isLetters(String s){
        for(char c : s.toCharArray()){
            if(!Character.isLetter(c))
                return false;
        }
        return true;
    }


    static class FileFormatException extends IOException {
        public FileFormatException(String message){
            super(message);
        }
    }


    //-----------------------------------------> filtering -----------------------------------------------


    /**
     * @return only those files that match with the format
     */
    public String[] filterSaves(String[] directoryFiles){
        ArrayList<String> accepted = new ArrayList<>();
        for(String name: directoryFiles){
            if(isASave(name))
                accepted.add(name);
        }
        String[] result = new String[accepted.size()];
        for(int i=0; i< result.length; i++)
            result[i] = accepted.get(i);
        return result;
    }


    /**
     * only files with the appropriate format ending while be classified as a save on load-time
     * to load from multiple formats, change the format and run "getSaveNames" again
     */
    private boolean isASave(String name) {
        String format = getFormat();
        if(name.length() < format.length()+1)
            return false;
        int endingIndex = name.length()-(format.length()+1);
        String ending = name.substring(endingIndex);
        return ending.equals("." + format);
    }


    //------------------------------------------> Exceptions ------------------------------------------


    static class NoFormatFoundException extends Exception {
        public NoFormatFoundException(String message){
            super(message);
        }
    }


    //------------------------------------------> I/O Exceptions --------------------------------------


    static class CouldNotLoadFileException extends IOException{
        public CouldNotLoadFileException(String message, Exception e){
            super(message, e);
        }
    }


    static class FaultyFileException extends CouldNotLoadFileException{
        public FaultyFileException(String message, Exception exc){
            super(message, exc);
        }
    }


    //------------------------------------------> Reading -------------------------------------------------


    /**
     * br: BufferedReader wired to a file
     * return: String[] with no null values guaranteed.
     */
    public String[] read(BufferedReader br) throws IOException {
        ArrayList<String> list = new ArrayList<>();

        String line = br.readLine();
        while(line != null){
            list.add(line);
            line = br.readLine();
        }

        String[] result = new String[list.size()];
        for(int i=0; i<list.size(); i++){
            result[i] = list.get(i);
        }
        return result;
    }


    private void closeReader(BufferedReader br) {
        try {
            if(br != null)
                br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
