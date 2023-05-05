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
public class GenerateTxt{
    /**
    * The nodeNames list is used to add all the names of the vertixes.
    */
    private static ArrayList<String> nodeNames, nodePairs;
    
    /**
    * This method is used to generate the text file with the graph inside.
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
            writer.close();
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
            int cost = random.nextInt(21);
            while(true){
                String firstNode = nodeNames.get(random.nextInt(vNum-1));
                String secondNode = nodeNames.get(random.nextInt(vNum-1));
                String pair = pairUp(firstNode, secondNode);
                if( pair.equals("-1"))
                    continue;
                else if( pair.equals("0")){
                    pair = firstNode+" "+firstNode;
                    cost = 0;
                }
                else{
                    nodePairs.add(pair);
                    
                    // Since this string is going to be a file, the hasNextLine() method of the 
                    // Scanner might be affected if the last line has a "nextLine" or ends with "\n"
                    if(pairNum != eNum-1)
                        result.append(pair+" "+cost+"\n");
                    else 
                        result.append(pair+" "+cost);
                    break;
                }
                
            }   
        
        }
        return result.toString();
    }
    
    /**
    * Used to pair up two node. Can also detetect if that pair aldready exists or the nodes are the same.
    * @param firstNode pass in the first node to add.
    * @param secondNode pass in the secong node to add.
    * @return String represenation of the node pair up. "-1" is the pair was not created succesfully and 0 if there is a self connection
    */
    private String pairUp(String firstNode, String secondNode) {
        String result = "-1";
        if(firstNode.equals(secondNode)){ // referes to self connected node
            result = "-1";
        }
        else if(!nodePairs.contains(firstNode+ " "+ secondNode)){
            result = firstNode+ " "+secondNode;
        }
        else if(!nodePairs.contains(secondNode+ " "+firstNode)){
            result = secondNode+" "+firstNode;
        }
        return result;
    }
    
    /**
     * Given any integer the method convects it to a string represenstaion of a node.
     * @param nodeNum pass in the integer of the node     
     * @return String of the node creted in the form NodeXXX
     */      
    public String intToNode(int nodeNum) {
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