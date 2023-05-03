import java.io.IOException;
import java.io.FileWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.lang.StringBuilder;

/**
* The GenerateTxt class is used to generate a txt file which is a representation of a graph
* 02 May 2023
* @author Nkabinde Mnqobi
*/
public class GenerateTxt
{
    /**
    * The nodeNames list is used to add all the names of the vertixes.
    */
    private static ArrayList<String> nodeNames, nodePairs;
    
    /**
    * This method is used to generate the text file withe the graph inside.
    * @param vNum pass in the number of node you what the graph to have.
    * @param eNum pass in the number of edges you what the graph to have.
    */
    public void generateTxtFile(int vNum, int eNum) {
        // initailise the two data stuctures each time a new graph is to be generated.
        nodeNames = new ArrayList<>();
        nodePairs = new ArrayList<>();
        
        //  First create and then write to the file
        try {
            String fileName = "data//Graph"+ vNum+ "-"+eNum+".txt";
            File file = new File(fileName);
            file.createNewFile();
            FileWriter writer = new FileWriter(fileName);
            writer.write(getGraph(vNum, eNum));
        }
        catch (IOException e) {
            System.out.println("An error occured while creating the file");
            System.exit(0);
        }
    
    }
    
    /**
    * This method is used to write the nodes and make the pairs and the distances associated with those pairs
    * @param vNum the vNum passed in on the generateTxtFile method is used here.
    * @param eNum the eNum passed in on the generateTxtFile method is used here.
    * @return a string representation of the graph.
    */
    private String getGraph(int vNum, int eNum) {
        StringBuilder result = new StringBuilder("");
        // Make all node names
        for (int nodeNum = 1; nodeNum<=vNum; nodeNum++){
            nodeNames.add(intToNode(nodeNum));
        }
        // Make and add pairs
        for (int pairNum = 0; pairNum<eNum; pairNum++){
            Random random = new Random();
            while(true){
                String firstNode = nodeNames.get(random.nextInt(vNum-1));
                String secondNode = nodeNames.get(random.nextInt(vNum-1));
                if(alreadyPaired(firstNode, secondNode) | firstNode.equals(secondNode))
                    continue;
                else{
                    String pair = firstNode+" "+secondNode;
                    nodePairs.add(pair);
                    
                    // Since this string is going to be a file, the hasNextLine() method of the 
                    // Scanner might be affected if the last line has a "nextLine" or ends with "\n"
                    if(pairNum == eNum-1)
                        result.append(pair+" "+random.nextInt(21)+"\n");
                    else 
                        result.append(pair+" "+random.nextInt(21));
                    break;
                }
                
            }   
        
        }
        return result.toString();
    }
    
    private boolean alreadyPaired(String firstNode, String secondNode) {
        return nodePairs.contains(firstNode +" "+ secondNode) | nodePairs.contains(secondNode +" "+ firstNode);
    }
    
    private String intToNode(int nodeNum) {
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
}