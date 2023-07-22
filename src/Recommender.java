import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Recommender {

        // Read graph from file. The file is a text file where each line contains an edge. 
        //The end and start of the edge are separated by space(s) or tabs (see graph.txt).
        public static Graph<Integer> read(String fileName) {
            Graph<Integer> graph = new MGraph<Integer>();
            try {
                File F = new File(fileName);
                Scanner Count = new Scanner(F);
                //read from file and write to the array A
                while(Count.hasNext()){
                    String str = Count.nextLine();
                    String [] Data = str.split(" ");
                    
                    Integer S = Integer.parseInt(Data[0]);
                    Integer E = Integer.parseInt(Data[1]);
      
                    if ( ! graph.isNode(S))
                            graph.addNode(S);
                    
                    if ( ! graph.isNode(E))
                            graph.addNode(E);
                   
                    if (! graph.isEdge(S, E))
                            graph.addEdge(S, E);
                    
                }
                Count.close();
                
            } catch (Exception ex) {
                Logger.getLogger(Recommender.class.getName()).log(Level.SEVERE, null, ex);
            }
            return graph;
        }

// Return the top k recommended friends for user i using the popular users method. 
//If i does not exist, return null. In case of a tie, users with the lowest id are selected.
/*
Popular users: In this method, we recommend the most popular users in the graph,
that is nodes with the highest degrees (number of neighbors).
Example 1. If we want to recommend 4 new friends for user 3 using the popular users
method, we recommend:
(a) User 8, which has degree 7.
(b) User 12, which has degree 5.
(c) User 4, which has degree 3.
(d) User 6, which has degree 3 (we break ties according to user ID).
*/
    public static <K extends Comparable<K>> PQK<Double, K> recommendDeg(Graph<K> g, K i, int k) 
    {
        if (! g.isNode(i))
            return null;
        
        Set<K> i_neigh = g.neighb(i);
        Iterator<K> it = g.nodesIt();
        PQK<Double, K> pqk = new ArrayPQK<Double, K> (((MGraph)g).nodes.size());
        
        while (it.isValid() ) {
                    K name =  it.next();
                    if ( name.compareTo(i) != 0 && ! i_neigh.exists(name)) 
                    {
                        int degree = g.deg(name);
                        pqk.enqueue( (double) degree, name);
                    }
                    
        }
        
        PQK<Double, K> pqk_new = new ArrayPQK<Double, K> (k);
        for ( int index = 1 ; index <= k; index ++)
        {
            Pair <Double, K> pair = pqk.serve();
            pqk_new.enqueue(pair.first,pair.second);
        }
        return pqk_new;
    }

    // Return the top k recommended friends for user i using common neighbors method. 
    //If i does not exist, return null. In case of a tie, users with the lowest id are selected.
    /*
    2. Common neighbors: In this method, we recommend users who have the most common
    friends with the user.

    Example 2. If we want to recommend 4 new friends for user 3 using the common neigh bors method, we recommend:
    (a) User 4, which has 2 common neighbors with 3, nodes 1 and 5.
    (b) User 6, which has 2 common neighbors with 3, nodes 2 and 5.
    (c) User 12, which has 1 common neighbor with 3, node 1.
    (d) User 8, which has 0 common neighbors with 3 (we break ties according to user ID).

    */
    public static <K extends Comparable<K>> PQK<Double, K> recommendCN(Graph<K> g, K i, int k) 
    {
        if (! g.isNode(i))
            return null;
    
        PQK<Double, K> pqk = new ArrayPQK<Double, K> (((MGraph)g).nodes.size());
        
        Set<K> i_neighrs = g.neighb(i);
         Iterator<K> it = g.nodesIt();
         while (it.isValid() ) {
                    K name =  it.next();
                    if ( ! i_neighrs.exists(name) && name.compareTo(i) != 0 )
                    {
                        Set<K> j_neighrs = g.neighb(name);
                        int count = 0 ;
                        Iterator<K> j_neighrs_it = j_neighrs.minIt();
                        while (j_neighrs_it.isValid())
                        {
                            K ne_name = j_neighrs_it.next();
                            if (ne_name.compareTo(i) != 0 && i_neighrs.exists(ne_name) )
                                count ++ ;
                        }
                        pqk.enqueue((double) count, name);
                    }
         }
        
        PQK<Double, K> pqk_new = new ArrayPQK<Double, K> (k);
        for ( int index = 1 ; index <= k; index ++)
        {
            Pair <Double, K> pair = pqk.serve();
            pqk_new.enqueue(pair.first,pair.second);
        }
        return pqk_new;
    }

    // Return the top k recommended friends for user i using weighted common neighbors method. 
                //If i does not exist, return null. In case of a tie, users with the lowest id are selected.
    public static <K extends Comparable<K>> PQK<Double, K> recommendWCN(Graph<K> g, K i, int k) {
if (! g.isNode(i))
            return null;
    
        PQK<Double, K> pqk = new ArrayPQK<Double, K> (((MGraph)g).nodes.size());
        
        Set<K> i_neighrs = g.neighb(i);
         Iterator<K> it = g.nodesIt();
         while (it.isValid() ) {
                    K name =  it.next();
                    if ( ! i_neighrs.exists(name) && name.compareTo(i) != 0 )
                    {
                        Set<K> j_neighrs = g.neighb(name);
                        double count = 0 ;
                        Iterator<K> j_neighrs_it = j_neighrs.minIt();
                        while (j_neighrs_it.isValid())
                        {
                            K ne_name = j_neighrs_it.next();
                            if (ne_name.compareTo(i) != 0 && i_neighrs.exists(ne_name) )
                            {
                                int degree = g.deg(ne_name);
                                count += (1.0 / degree);
                            }
                        }
                        pqk.enqueue( count, name);
                    }
         }
        
        PQK<Double, K> pqk_new = new ArrayPQK<Double, K> (k);
        for ( int index = 1 ; index <= k; index ++)
        {
            Pair<Double, K> pair = pqk.serve();
            pqk_new.enqueue(pair.first,pair.second);
        }
        return pqk_new;    
    }

}
