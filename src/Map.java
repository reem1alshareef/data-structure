public interface Map < K extends Comparable <K > , T > {
	// Return the size of the map .
	int size () ;
	// Return true if the map is full .
	boolean full () ;
	// Remove all elements from the map .
	void clear () ;
	// Update the data of the key k if it exists and return true . If k doesnot exist , the method returns false .
	boolean update ( K k , T e ) ;
	// Search for element with key k and returns a pair containing true and its data if it exists . If k does not exist , the method returnsfalse and null .
	Pair < Boolean , T > retrieve ( K k ) ;
	// Insert a new element if does not exist and return true . If k alreadyexists , return false .
	boolean insert ( K k , T e ) ;
	// Remove the element with key k if it exists and return true . If the element does not exist return false .
	boolean remove ( K k ) ;
	// Return an iterator that points to the minimum key . The iterator moves according to the order of the keys .
	Iterator < Pair <K , T > > minIt() ;
	// Return an iterator that points to the maximum key . The iterator moves according to the order of the keys .
	Iterator < Pair <K , T > > maxIt() ;
	}
