import java.io.IOException;
import java.io.FileWriter;
import java.io.File;
import java.util.ArrayList;

// 
// Decompiled by Procyon v0.5.36
// 

public class GenerateTxt
{
    public static ArrayList<String> nodeNames;
    public static ArrayList<String> nodePairs;
    
    public void main(final String[] args) {
        try {
            final File file = new File("fileName.txt");
            file.createNewFile();
            final FileWriter writer = new FileWriter("filename.txt");
            writer.write(getGraph(20, 1));
            writer.close();
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            System.exit(0);
        }
    }
    
    public static String getGraph(final int vNum, final int eNum) {
        return " ad";
    }
    
    public static boolean alreadyPaired(final String pair) {
        return GenerateTxt.nodePairs.contains(pair) | GenerateTxt.nodePairs.contains(pair.substring(8) +" "+ pair.substring(0, 7));
    }
    
    public static String intToNode(final int nodeNum) {
        String result;
        if (nodeNum < 10) {
            result = "NodeXX"+nodeNum;
        }
        else if (nodeNum < 100) {
            result = "NodeX"+nodeNum;
        }
        else {
            result = "Node"+nodeNum;
        }
        return result;
    }
    
    static {
        GenerateTxt.nodeNames = new ArrayList<String>();
        GenerateTxt.nodePairs = new ArrayList<String>();
    }
}