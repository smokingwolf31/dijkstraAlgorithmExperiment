// Represents an edge in the graph.
class Edge
{
    public Vertex     dest;   // Second vertex in Edge
    public double     cost;   // Edge cost
    
    /*
    * Only used during testing 
    */
    private Edge(){}
    /**
    * Represents an edge formed by two vertexes 
    *@param d pass in the vertex you which to go to
    *@param c pass in the cost of the edge
    */
    public Edge( Vertex d, double c )
    {
        dest = d;
        cost = c;
    }
}
