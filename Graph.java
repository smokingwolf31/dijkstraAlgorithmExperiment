/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.io.FileWriter;
import java.util.List;
import java.util.Queue;
import java.util.Map;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.StringTokenizer;


/**Graph class: evaluate shortest paths.
*
* CONSTRUCTION: with no parameters.
*
* ******************PUBLIC OPERATIONS**********************
* void addEdge( String v, String w, double cvw )
*                              --> Add additional edge
* void printPath( String w )   --> Print path after alg is run
* void unweighted( String s )  --> Single-source unweighted
* void dijkstra( String s )    --> Single-source weighted
* void negative( String s )    --> Single-source negative weighted
* void acyclic( String s )     --> Single-source acyclic
* ******************ERRORS*********************************
* Some error checking is performed to make sure graph is ok,
* and to make sure graph satisfies properties needed by each
* algorithm.  Exceptions are thrown if errors are detected.
*/
public class Graph
{   
    /**
     * Used to represents unprocessed nodes
     */
    public static final double INFINITY = Double.MAX_VALUE;
    private Map<String,Vertex> vertexMap = new HashMap<String,Vertex>( );
    private static StringBuilder resultData = new StringBuilder("");//will be used to append all the results from each graph 
    
    /**
     * Add edge to a graph
     * @param sourceName pass in the souce name
     * @param destName pass in the destination node name
     * @param cost pass in the cost
     */
    public void addEdge( String sourceName, String destName, double cost )
    {
        Vertex v = getVertex( sourceName );
        Vertex w = getVertex( destName );
        v.adj.add( new Edge( w, cost ) );
    }

    /**
     * Driver routine to handle unreachables and print total cost.
     * It calls recursive routine to print shortest path to
     * destNode after a shortest path algorithm has run.
     * @param destName pass in the destination node
     */
    public void printPath( String destName )
    {
        Vertex w = vertexMap.get( destName );
        if( w == null )
            throw new NoSuchElementException( "Destination vertex not found" );
        else if( w.dist == INFINITY )
            System.out.println( destName + " is unreachable" );
        else
        {
            System.out.print( "(Cost is: " + w.dist + ") " );
            printPath( w );
            System.out.println( );
        }
    }

    /**
     * If vertexName is not present, add it to vertexMap.
     * In either case, return the Vertex.
     * @param vertexName pass in the vertex name
     */
    private Vertex getVertex( String vertexName )
    {
        Vertex v = vertexMap.get( vertexName );
        if( v == null )
        {
            v = new Vertex( vertexName );
            vertexMap.put( vertexName, v );
        }
        return v;
    }

    /**
     * Recursive routine to print shortest path to dest
     * after running shortest path algorithm. The path
     * is known to exist.
     * @param dest pass in the destination vertex
     */
    private void printPath( Vertex dest )
    {
        if( dest.prev != null )
        {
            printPath( dest.prev );
            System.out.print( " to " );
        }
        System.out.print( dest.name );
    }
    
    /**
     * Initializes the vertex output info prior to running
     * any shortest path algorithm.
     */
    private void clearAll( )
    {
        for( Vertex v : vertexMap.values( ) )
            v.reset( );
    }

    /**
     * Single-source weighted shortest-path algorithm. (Dijkstra) 
     * using priority queues based on the binary heap
     * @param startName pass in the start node name
     */
    public int dijkstra( String startName )
    {   
        int oppcount_v = 0;
        System.out.println(oppcount_v);
        PriorityQueue<Path> pq = new PriorityQueue<Path>( );

        Vertex start = vertexMap.get( startName );
        if( start == null )
            throw new NoSuchElementException( "Start vertex not found" );

        clearAll( );
        pq.add( new Path( start, 0 ) ); start.dist = 0;
        
        int nodesSeen = 0;
        while( !pq.isEmpty( ) && nodesSeen < vertexMap.size( ) )
        {   
            System.out.println(pq.size()+" Before ="+oppcount_v);
            oppcount_v ++;
            System.out.println("After ="+oppcount_v);
            Path vrec = pq.remove( );
            Vertex v = vrec.dest;
            if( v.scratch != 0 )  // already processed v
                continue;
                
            v.scratch = 1;
            nodesSeen++;

            for( Edge e : v.adj )
            {   
                Vertex w = e.dest;
                double cvw = e.cost;
                
                if( cvw < 0 )
                    throw new GraphException( "Graph has negative edges" );
                
                oppcount_v++;
                if( w.dist > v.dist + cvw )
                {   
                    
                    w.dist = v.dist +cvw;
                    w.prev = v;
                    pq.add( new Path( w, w.dist ) );
                    oppcount_v ++;
                }
            }
        
        }
    }

    

   

    /**
     * Process a request; return false if end of file.
     * @param in pass in the scanner to read the file
     * @param g pass in the graph to process
     * @return boolean value true after the processReqest is done
     */
    public static int processRequest( Scanner in, Graph g )
    {   
        int oppcount_v = 0;
        String results="";
        try
        {
            String startName = "NodeXX1";
            String destName = "NodeXX2";
            oppcount_v = g.dijkstra( startName );
            g.printPath( destName );
        }
        catch( NoSuchElementException e )
          { return 0; }
        catch( GraphException e )
          { System.err.println( e ); }
        return oppcount_v;
    }

    /**
     *  * A main routine that:
     * 1. Reads a file containing edges (supplied as a command-line parameter);
     * 2. Forms the graph;
     * 3. Repeatedly prompts for two vertices and
     *    runs the shortest path algorithm.
     * The data file is a sequence of lines of the format
     *    source destination cost
     * 
     * @param args unused
     */
    public static void main( String [ ] args )
    {	
        Integer[][] vNums = new Integer[][] {{15,30,45,60,70,10,20,10}, {20, 35, 50, 65,80,100},{20, 35, 50, 65,80,100}
                                            ,{20, 35, 50, 65,80,100}, {20, 35, 50, 65,80,100,20,30}};
        GenerateTxt generateTxt = new GenerateTxt();
        int oppcount_v = 0;
        for(int vNumIndex=0 ; vNumIndex<vNums.length; vNumIndex++){
            Graph g = new Graph();
            int vNum = Integer.parseInt((int)(vNumIndex+1)+"0"); // we first add to the index then cast is as an 
            System.out.print(vNum);
            System.out.println("\nThe following results is for all the different graphs with "+vNum+ " nodes.");
            for(int eNum : vNums[vNumIndex]){
                
                
                try {   
                    generateTxt.generateTxtFile(vNum, eNum);
                    FileReader fin = new FileReader("data//Graph" +vNum+ "-"+eNum +".txt"); // result = "data//Graph10-15"
                    Scanner graphFile = new Scanner( fin );

                    // Read the edges and insert
                    String line;
                    while( graphFile.hasNextLine( ) )
                    {
                        line = graphFile.nextLine( );
                        StringTokenizer st = new StringTokenizer( line );

                        try {
                            if( st.countTokens( ) != 3 ){
                                System.err.println( "Skipping ill-formatted line " + line );
                                continue;
                            }
                            String source  = st.nextToken( );
                            String dest    = st.nextToken( );
                            int    cost    = Integer.parseInt( st.nextToken( ) );
                            g.addEdge( source, dest, cost );
                        }
                        catch( NumberFormatException e )
                            { System.err.println( "Skipping ill-formatted line " + line ); }
                    }
                }
                catch( IOException e )
                    { System.err.println( e ); }

                System.out.println( "File read..." );
                System.out.println( g.vertexMap.size( ) + " vertices" );

                Scanner in = new Scanner( System.in );
                oppcount_v = processRequest( in, g );
                resultData.append(vNum+ " "+eNum+ " "+oppcount_v+"\n");
            }
        }
        try{
            FileWriter writer = new FileWriter("data//resultData.txt");
            System.out.println("\nThe results are\nNodeNumber EdgeNumber NumOfOperations\n"+resultData.toString());
            writer.write(resultData.toString());
            writer.close();
        }
        catch (IOException ee){
            System.err.println(ee);
        
        }
        System.out.println("If you want a second look at the data you just created look at the data folder and look for resultData.txt");  
    }
}