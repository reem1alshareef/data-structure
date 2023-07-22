public class BSTSet<K extends Comparable<K>> implements Set<K> {
    public BSTSetNode<K> root; // Do not change this
    public BSTSetNode<K> curr;
    public BSTSetNode<K> head ;
    public BSTSetNode<K> tail ;
    
    private int count;

    public BSTSet()
    {
        curr= root = null;
        head = tail = null;
        count = 0 ;
    }

    // Return the size of the set.
    public int size()
    {
       return count;
    }

    // Return true if the set is full.
    public boolean full()
    {
        return false;
    }

    // Remove all keys from the set.
    public void clear () 
    {
        curr = root = null;
        head = tail = null;
        count = 0 ;
    }

    // Search for the key k and return true if it exists, false otherwise.
    public boolean exists(K k)
    {
        boolean found  = false;
        
        BSTSetNode<K> pBstNode = root;
        
        while (pBstNode != null) 
        {
            if (k.compareTo(pBstNode.key) == 0) 
            {
                found  = true;
                break;
            }
            else if (k.compareTo(pBstNode.key) <0 )
                   pBstNode = pBstNode.left;
            else       
                    pBstNode = pBstNode.right;
        }
        return found; 
    }
    
    // Insert a new key if does not exist and return true. If k already exists, return false.
    public boolean insert(K k)
    {
        if (root == null) 
        {
            root = new BSTSetNode<K> (k);
            root.prev = root.next = null;
            
            //=================== insert in DLL
            head = tail = root;
            count ++ ;
            
            return true;
        }
        
        // check if it exist before
        boolean pair =  this.exists( k )  ;
        if ( pair == true)
            return false;
        
        // insert in BST
        BSTSetNode<K>  pBstNode = root;
        BSTSetNode<K>  qBSTNode = null ;

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
        
        BSTSetNode<K> tempBstNode = new BSTSetNode<K>  (k);
        
        if (k.compareTo(qBSTNode.key) < 0) 
            qBSTNode.left = tempBstNode;
        else 
            qBSTNode.right = tempBstNode;
       
        // Insert into DLL;
        //==========================================================
        // Insert Before head
        if (tempBstNode.key.compareTo(head.key) < 0) 
        {
            tempBstNode.prev = null;
            tempBstNode.next = head;
            head.prev = tempBstNode;
            head = tempBstNode;
        }    
        else if (tempBstNode.key.compareTo(tail.key) > 0) 
        {
            tempBstNode.prev = tail;
            tempBstNode.next = null;
            tail.next = tempBstNode;                
            tail = tempBstNode;
        }    
        
        else
        {
            BSTSetNode<K> c1 =null;
            BSTSetNode<K> c2 =head;
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

    // Remove the key k if it exists and return true. If the key does not exist return false.
    public boolean remove(K k)
    {
            // Search for k      
            K k1 = k;
            BSTSetNode<K> p = root;
            BSTSetNode<K> q = null;  // Parent of p
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
                        BSTSetNode<K> min = p.right;
                        q = p;
                        while (min.left != null) 
                        {
                            q = min;
                            min = min.left;
                        }
                        p.key = min.key;
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
                        BSTSetNode<K> c1 = tail.prev;
                        BSTSetNode<K> c2 = tail;
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
    
    // Return an iterator that points to the minimum key. The iterator moves according to the order of the keys.
    public Iterator<K> minIt()
    {
            curr = head;
            Iterator < K > itmin = new BSTSetIterator<K>();
            return itmin;
    }

    // Return an iterator that points to the minimum key. The iterator moves according to the order of the keys.
    public Iterator<K> maxIt()
    {
            curr = tail;
            Iterator < K > itmax = new BSTSetIterator<K>();
            return itmax;
    }
    
    //----------------------------------------------------
    public class  BSTSetIterator<K> implements Iterator<K> {
        
            // Returns true if the iterator is valid (points to an element), false otherwise.
            public boolean isValid()
            {
                if ( curr != null)
                    return true;
                return false;
            }

            // Returns the current element and moves forward. This method can only be called if the iterator is valid. 
            //If the iterator points to the last element, it becomes invalid after the call.
            public K next()
            {
                K pair = (K) curr.key;
                curr = curr.next;                           
                return  pair;  
            }

            // Returns the current element and moves backwards. 
            //This method can only be called if the iterator is valid.
            // If the iterator points to the first element, it becomes invalid after the call.
            public K prev()
            {
                K pair = (K)curr.key;
                curr = curr.prev;                           
                return  pair;  
            }
        }
}