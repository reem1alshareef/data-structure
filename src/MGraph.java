public class MGraph<K extends Comparable<K>> implements Graph<K> {
    public Set<K> nodes; // Do not change this
    public Map<K, Set<K>> adj; // Do not change this
    Iterator iteratorNodes;
    
    //Constructor
    public MGraph()
    {
        nodes = new BSTSet<K> ();
        adj = new BSTMap<K, Set<K>> ();
    }
    
    // Add a node to the graph if it does not exist and return true . If the
    //node already exists , return false .
    public boolean addNode ( K i )
    {
           boolean found = nodes.exists(i);
           if (! found )
           {
               nodes.insert(i);
               return true;
           }
           return false;
    }

    // Check if i is a node
    public boolean isNode ( K i )
    {
        return nodes.exists(i);
    }

    // Add an edge to the graph if it does not exist and return true . If i
    //or j do not exist or the edge (i , j ) already exists , return false .
    public boolean addEdge ( K i , K j )
    {
        if ((nodes.exists(i) == false ) || ( nodes.exists(j) == false)) 
            return false;
        
        if ((isEdge (i , j ) == true) || (isEdge (j,i) == true))
            return false;
 
        
        Pair <Boolean, Set<K>> pair1 = adj.retrieve(i);
        Pair <Boolean, Set<K>> pair2 = adj.retrieve(j);
        
        Set<K> set1 = new BSTSet<K> ();
        Set<K> set2 = new BSTSet<K> ();

        // from i to j    
        if ( pair1.first == false)
        {
            adj.insert(i, set1);
            pair1 = adj.retrieve(i);
        }
        
        // from j to i    
        if ( pair2.first == false)
        {
            adj.insert(j, set2);
            pair2 = adj.retrieve(j);
        }
        
        set1 = pair1.second;
        set2 = pair2.second;
        
        
        if (set1.insert(j)  &&  set2.insert(i))
        {
            boolean updated1 = adj.update(i, set1);
            boolean updated2 = adj.update(j, set2);
            return (updated1 && updated2);
        } 
        return false;
    }

    // Check if (i , j ) is an edge .
    public boolean isEdge ( K i , K j )
    {
        Pair <Boolean, Set<K>> pair = adj.retrieve(i);
        Boolean found = pair.first;
        Set<K> set = pair.second;

        if( pair.first == true)
            if (set.exists(j) == true )
                return true;
        
        return false;
    }

    // Return the set of neighbors of node i . If i does not exist , the
    //method returns null .
    public Set <K > neighb ( K i )
    {
        if ( nodes.exists(i) ==  false) 
            return null;
        
        Set<K> set = new BSTSet<K>();
        Pair <Boolean, Set<K>> pair = adj.retrieve(i);
        if ( pair.second != null)
            set = pair.second;
        return set;
    }

    // Return the degree ( the number of neighbors ) of node i . If i does not
    //exist , the method returns -1.
    public  int deg ( K i )
    {
        if ( nodes.exists(i) == false)
            return -1;
        
        Pair <Boolean, Set<K>> pair = adj.retrieve(i);
        if ( pair.first == false)
            return 0 ;
        
        return pair.second.size();
    }

    // Return an iterator over nodes in increasing order starting with the
    //smallest node .
    public Iterator <K > nodesIt ()
    {
        MGraphIterator<K> iterator = new MGraphIterator<K>();
        return iterator;
    }

//----------------------------------------------------
public class  MGraphIterator<K> implements Iterator<K> {
        
        
        public MGraphIterator()
        {
            iteratorNodes = nodes.minIt();
        }
        
        // Returns true if the iterator is valid (points to an element), false otherwise.
        public boolean isValid()
        {
            if (iteratorNodes.isValid())
                return true;
            return false;
        }

        // Returns the current element and moves forward. This method can only be called if the iterator is valid. 
        //If the iterator points to the last element, it becomes invalid after the call.
        public K next()
        {
            return (K) iteratorNodes.next();
        }

        // Returns the current element and moves backwards. 
        //This method can only be called if the iterator is valid.
        // If the iterator points to the first element, it becomes invalid after the call.
        public K prev()
        {
            return (K) iteratorNodes.prev();
        }
    }
}

