public class ArrayPQK<P extends Comparable<P>, T> implements PQK<P, T> {

    private int maxsize;
    private int size;
    private int tail;
    private Pair<P,T>[] nodes;

    public ArrayPQK(int k) {
            maxsize = k;
            size = 0;
            tail = 0;
            nodes = new Pair[k];
    }

    // Return the length of the queue
    public int length()
    {
        return size;
    }

    // Enqueue a new element . The queue keeps the k elements with the
    // highest priority 
    public void enqueue(P pr, T e){
        if ( size < maxsize)
        {
            Pair<P ,T> pair = new Pair<P,T>(pr , e);
            int pos = tail-1;
            while ( pos >= 0 && pair.first.compareTo(nodes[pos].first) > 0 )
            {
                //move
                nodes[pos+1] = nodes[pos];
                pos--;
            }
            // add new pair            
            nodes[++pos] = pair;
            tail++;
            size++;
           }
    }

    // Serve the element with the highest priority . In case of a tie apply FIFO .
    public Pair<P,T> serve(){
        Pair<P ,T> e = null;
        if ( size > 0)
        {
            e = nodes[0];
            for ( int pos = 1 ; pos < size ; pos ++)
                nodes [ pos -1] = nodes[pos];
           tail--;
            size--;
        }
        return e;
    }
}