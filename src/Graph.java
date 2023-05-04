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


// Graph class: evaluate shortest paths.
//
// CONSTRUCTION: with no parameters.
//
// ******************PUBLIC OPERATIONS**********************
// void addEdge( String v, String w, double cvw )
//                              --> Add additional edge
// void printPath( String w )   --> Print path after alg is run
// void unweighted( String s )  --> Single-source unweighted
// void dijkstra( String s )    --> Single-source weighted
// void negative( String s )    --> Single-source negative weighted
// void acyclic( String s )     --> Single-source acyclic
// ******************ERRORS*********************************
// Some error checking is performed to make sure graph is ok,
// and to make sure graph satisfies properties needed by each
// algorithm.  Exceptions are thrown if errors are detected.

public class Graph
{
    public static final double INFINITY = Double.MAX_VALUE;
    private Map<String,Vertex> vertexMap = new HashMap<String,Vertex>( );
    private static int oppcount_v = 0;
    private static StringBuilder resultData = new StringBuilder("");//will be used to append all the results from each graph 

    /**
     * Add a new edge to the graph.
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
     */
    public void dijkstra( String startName )
    {   
        oppcount_v = 0;
        PriorityQueue<Path> pq = new PriorityQueue<Path>( );

        Vertex start = vertexMap.get( startName );
        if( start == null )
            throw new NoSuchElementException( "Start vertex not found" );

        clearAll( );
        pq.add( new Path( start, 0 ) ); start.dist = 0;
        
        int nodesSeen = 0;
        while( !pq.isEmpty( ) && nodesSeen < vertexMap.size( ) )
        {   
            oppcount_v += (int)(Math.log(pq.size())/Math.log(2));
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
                }
            }
        
        }
        System.out.println("Number of operations ="+oppcount_v);
    }

    

   

    /**
     * Process a request; return false if end of file.
     */
    public static boolean processRequest( Scanner in, Graph g )
    {   
        String results="";
        try
        {
            String startName = "NodeXX1";
            String destName = "NodeXX2";
            g.dijkstra( startName );
            g.printPath( destName );
            System.out.println("With "+oppcount_v+" operations");
        }
        catch( NoSuchElementException e )
          { return false; }
        catch( GraphException e )
          { System.err.println( e ); }
        return true;
    }

    /**
     * A main routine that:
     * 1. Reads a file containing edges (supplied as a command-line parameter);
     * 2. Forms the graph;
     * 3. Repeatedly prompts for two vertices and
     *    runs the shortest path algorithm.
     * The data file is a sequence of lines of the format
     *    source destination cost
     */
    public static void main( String [ ] args )
    {	
        Integer[] vNums = new Integer[]{10, 20, 30, 40, 50};
        Integer[] eNums = new Integer[]{20, 35, 50, 65, 80};
        GenerateTxt generateTxt = new GenerateTxt();
        Graph g = new Graph( );
        
        for(int vNum : vNums){
            for(int eNum : eNums){
                
                System.out.println("The following results is for all the different graphs with "+vNum+"nodes.");
                try {   
                    generateTxt.generateTxtFile(vNum, eNum);
                    System.out.println("Problem not in creating"); 
                    FileReader fin = new FileReader("data//Graph"+vNum+"-"+eNum+".txt");
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
                processRequest( in, g );
                resultData.append(vNum+ " "+eNum+ " "+oppcount_v+"\n");
            }
        }
        try{
            FileWriter writer = new FileWriter("data//resultData.txt");
            System.out.println("The resultts are\n"+resultData.toString());
            writer.write(resultData.toString());
            writer.close();
        }
        catch (IOException ee){
            System.err.println(ee);
        
        }
        System.out.println("If you want a second look at the data you just created look at the data folder and look for resultData.txt");   
    }
}