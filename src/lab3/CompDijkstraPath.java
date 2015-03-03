
package lab3;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;


public class CompDijkstraPath<E extends Edge> {
    private final int size;
    private final PriorityQueue<E> queue;
    
    public CompDijkstraPath(List<E> edges){
        size = edges.size();
        queue = new PriorityQueue(size, new EdgeComparator());
        for(E e:edges){
            queue.add(e);
        }
    }
    
    public Iterator<E> getShortest(int from, int to){
        return null;
    }
    
    
    
    
}

class EdgeComparator<E extends Edge> implements Comparator{
    @Override
    public int compare(Object o1, Object o2) {
        final double first, second;
        first = ((E)o1).getWeight();
        second = ((E)o2).getWeight();
        if (first < second){
            return -1;
        } else if (first == second){
            return 0;
        } else {
            return 1;
        }
    }
}
