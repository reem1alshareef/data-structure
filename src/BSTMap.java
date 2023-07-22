public class BSTMap<K extends Comparable<K>, T> implements Map<K, T> {
    
    public BSTMapNode<K, T> root; // Do not change this
    public BSTMapNode<K, T > curr;
    public BSTMapNode<K, T > head ;
    public BSTMapNode<K, T > tail ;
    
    private int count;
    
    public BSTMap() {
        curr= root = null;
        head = tail = null;
        count = 0 ;
    }
    
    // Return the size of the map .
    public int size () 
    {
       return count;
    }
    
    // Return true if the map is full .
    public boolean full () 
    {
        return false;
    }
    
    // Remove all elements from the map .
    public void clear () 
    {
        curr = root = null;
        head = tail = null;
        count = 0 ;
    }
    
    // Update the data of the key k if it exists and return true . If k does
    //not exist , the method returns false .
    public boolean update ( K k , T e ) 
    {
            BSTMapNode<K, T> pBstMap = root;
            while (pBstMap != null) 
            {
                    if (k.compareTo(pBstMap.key) == 0) 
                    {
                        pBstMap.data = e;
                        return true;
                    }
                    else if (k.compareTo(pBstMap.key) < 0) 
                        pBstMap = pBstMap.left;
                    else
                        pBstMap = pBstMap.right;
            }
            return false;
    }
    
    // Search for element with key k and returns a pair containing true and
    //its data if it exists . If k does not exist , the method returns false and null .
    public Pair < Boolean , T > retrieve ( K k )  
    {
        Pair < Boolean , T > pair = new Pair < Boolean , T >(false, null );
        
        BSTMapNode<K,T> pBstNode = root;
        
        while (pBstNode != null) 
        {
            if (k.compareTo(pBstNode.key) == 0) 
            {
                pair.first = true;
                pair.second = pBstNode.data; 
                break;
            }
            else if (k.compareTo(pBstNode.key) <0 )
                   pBstNode = pBstNode.left;
            else       
                    pBstNode = pBstNode.right;
        }
        return pair; 
    }
    
    // Insert a new element if does not exist and return true . If k already exists , return false .
    public boolean insert ( K k , T e )  
    {
        if (root == null) 
        {
            root = new BSTMapNode<K, T>(k, e);
            root.prev = root.next = null;
            
            //=================== insert in DLL
            head = tail = root;
            count ++ ;
            
            return true;
        }
        
        // check if it exist before
        Pair < Boolean , T > pair =  retrieve ( k )  ;
        if ( pair.first == true)
            return false;
        
        // insert in BST
        BSTMapNode<K, T> pBstNode = root;
        BSTMapNode<K, T> qBSTNode = null ;

        // serach for key
        while (pBstNode != null ) 
        {
            int rest = k.compareTo(pBstNode.key);
            if (rest == 0) 
                return false;
            else 
            {
                qBSTNode = pBstNode ;
                if (rest < 0) 
                    pBstNode = pBstNode.left;
                else
                    pBstNode = pBstNode.right;
           }
        }
        
        BSTMapNode<K, T> tempBstNode = new BSTMapNode<K, T> (k, e);
        
        if (k.compareTo(qBSTNode.key) < 0) 
            qBSTNode.left = tempBstNode;
        else 
            qBSTNode.right = tempBstNode;
       
        //Insert in DLL
        //==========================================================
        //Before head
        if (tempBstNode.key.compareTo(head.key) < 0) 
        {
            tempBstNode.prev = null;
            tempBstNode.next = head;
            head.prev = tempBstNode;
            head = tempBstNode;
        }    
        //after tail
        else if (tempBstNode.key.compareTo(tail.key) > 0) 
        {
            tempBstNode.prev = tail;
            tempBstNode.next = null;
            tail.next = tempBstNode;                
            tail = tempBstNode;
        }    
        // add in middle
        else
        {
            BSTMapNode<K, T> c1 =null;
            BSTMapNode<K, T> c2 =head;
            while (c2 != null && tempBstNode.key.compareTo(c2.key) > 0)
            {
                c1 = c2;
                c2 = c2.next;
            }
            tempBstNode.prev = c1;
            tempBstNode.next = c2;
            if ( c1 != null ) c1.next = tempBstNode;
            if ( c2 != null ) c2.prev = tempBstNode;
            while ( tail.next != null)
                tail = tail.next;
        }           
        //=======================================================================
        
        count++ ;
        return true;
    }
    
    // Remove the element with key k if it exists and return true . If the
    // element does not exist return false .
    public boolean remove ( K k )
    {
            // Search for k      
            K k1 = k;
            BSTMapNode <K, T> p = root;
            BSTMapNode <K, T> q = null;  // Parent of p
            while (p != null) 
            {
                if  (k1.compareTo(p.key) < 0) 
                {
                    q =p;
                    p = p.left;
                } 
                else if (k1.compareTo(p.key) > 0) 
                {
                    q = p;
                    p = p.right;
                } 
                else { 
                    // Found the key
                    // Check the three cases
                    if ((p.left != null) && (p.right != null)) 
                    { 
                        // Case 3: two children                
                        // Search for the min in the right subtree
                        BSTMapNode <K, T> min = p.right;
                        q = p;
                        while (min.left != null) 
                        {
                            q = min;
                            min = min.left;
                        }
                        p.key = min.key;
                        p.data = min.data;
                        k1 = min.key;
                        p = min;
                        // Now fall back to either case 1 or 2
                    }
                    // The subtree rooted at p will change here
                    if (p.left != null) 
                    { // One child
                        p = p.left;
                    } 
                    else 
                    { 
                        // One or no children
                        p = p.right;
                    }
                    if (q == null) 
                    { 
                        // No parent for p, root must change
                            root = p;
                    } 
                    else 
                    {
                        if (k1.compareTo(q.key) <0) 
                        {
                            q.left = p;
                        } 
                        else 
                        {
                            q.right = p;
                        }
                    }
                    // remove from DLL
                    //=========================================================================
                        if(k1.compareTo(head.key) == 0) 
                        {
                            head = head.next;
                            if(head != null)
                                head.prev = null;
                        }
                        if(k1.compareTo(tail.key) == 0) 
                        {
                            tail = tail.prev;
                            if ( tail != null)
                                tail.next = null;
                        }
                        else 
                        {
                        BSTMapNode<K, T> c1 = tail.prev;
                        BSTMapNode<K, T> c2 = tail;
                            while ( c1 != null && c2.key.compareTo(k1) != 0)
                            {
                                c2 = c1 ;
                                c1 = c1.prev;
                            }
                           if ( c2.key.compareTo(k1) == 0) 
                           {
                               c1.next = c2.next;
                               if(c2.next != null)
                                    c2.next.prev = c2.prev;
                            }
                        }
                    //=========================================================================
                    count --;
                    return true;
                }
            }
            return false; // Not found
    }

    // Return an iterator that points to the minimum key . The iterator
    //moves according to the order of the keys .
    public Iterator < Pair <K , T > > minIt ()  
    {
            curr = head;
            Iterator < Pair <K , T > > itmin = new BSTMAPIterator < Pair <K , T > >();
            return itmin;
    }
    
    // Return an iterator that points to the maximum key . The iterator
    //moves according to the order of the keys .
    public Iterator < Pair <K , T > > maxIt () 
    {
            curr = tail;
            Iterator < Pair <K , T > > itmax = new BSTMAPIterator < Pair <K , T > >();
            return itmax;
    }

    
    //----------------------------------------------------
    public class  BSTMAPIterator<X> implements Iterator<X> {
        
            // Returns true if the iterator is valid (points to an element), false otherwise.
            public boolean isValid()
            {
                if ( curr != null)
                    return true;
                return false;
            }

            // Returns the current element and moves forward. This method can only be called if the iterator is valid. 
            //If the iterator points to the last element, it becomes invalid after the call.
            public X next()
            {
                Pair<K,T> pair = new Pair<K,T> ( curr.key , curr.data);
                curr = curr.next;                           
                return  (X) pair;  
            }

            // Returns the current element and moves backwards. 
            //This method can only be called if the iterator is valid.
            // If the iterator points to the first element, it becomes invalid after the call.
            public X prev()
            {
                Pair<K,T> pair = new Pair<K,T> ( curr.key , curr.data);
                curr = curr.prev;                           
                return  (X) pair;  
            }
        }
   
}