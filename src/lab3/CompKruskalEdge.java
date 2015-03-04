
package lab3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;


public class CompKruskalEdge<E extends Edge> {
    private final int size;
    private final List<E>[] nodes;
    private final PriorityQueue<E> queue;
    
    public CompKruskalEdge(int size, List<E> edges){
        this.size = size;
        queue = new PriorityQueue<>(edges.size(), new edgeComparator());
        nodes = new List[size];
        for (int i = 0; i < size; i++){
            nodes[i] = new ArrayList<>();
        }
        for (E edge:edges){
            queue.add(edge);
        }
    }
    
    public Iterator<E> getMinimumSpanningTree(){
        while (!queue.isEmpty() || nodes[0].size() < size){
            E nextEdge = queue.poll();
            final int source = nextEdge.getSource();
            final int dest = nextEdge.getDest();
            if (nodes[source] != nodes[dest]){
                if (nodes[source].size() < nodes[dest].size()){
                    for (E edge:nodes[source]){
                        nodes[dest].add(edge);
                        nodes[edge.getSource()] = nodes[dest];
                        nodes[edge.getDest()] = nodes[dest];
                    }
                    
                } else {
                    for (E edge:nodes[dest]){
                        nodes[source].add(edge);
                        nodes[edge.getSource()] = nodes[source];
                        nodes[edge.getDest()] = nodes[source];
                    }
                }
                nodes[dest].add(nextEdge);
            }
        }
        return queue.iterator();
    }

}

class edgeComparator<E extends Edge> implements Comparator<E> {
    @Override
    public int compare(E o1, E o2) {
        final double first = o1.getWeight();
        final double second = o2.getWeight();
        if (first < second){
            return -1;
        } else if (first == second) {
            return 0;
        } else {
            return 1;
        }
    }
    
}
