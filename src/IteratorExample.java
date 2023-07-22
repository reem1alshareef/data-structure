//Print in increasing order
class IteratorExample {
 public static void main(String[] args) {

         Map<Integer, Integer> map = new BSTMap<Integer, Integer>();
         for (int i = 100; i > 0; i--) {
                 map.insert(i % 13, i);
         }
     
         Iterator<Pair<Integer, Integer>> it1 = map.minIt();
         while (it1.isValid()) {
                 Pair<Integer, Integer> p = it1.next();
                 System.out.println(p.first + "\t" + p.second);
         }

         // Print in decreasing order
         Iterator<Pair<Integer, Integer>> it2 = map.maxIt();
         while (it2.isValid()) {
                 Pair<Integer, Integer> p = it2.prev();
                 System.out.println(p.first + "\t" + p.second);
         }
 }
}