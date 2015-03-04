
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
    
    /**
     * This algorithm assumes that the list of edges given in constructor spans a complete spanning tree
     * @return 
     */
    public Iterator<E> getMinimumSpanningTree(){
        //Aslong as we still have edges in the queue and we do not have a list containing all nodes
        while (!queue.isEmpty() && nodes[0].size() < size-1){
            //Get next edge
            E nextEdge = queue.poll();
            //Store the source and destination number
            final int source = nextEdge.getSource();
            final int dest = nextEdge.getDest();
            //If the source and destination does not point to the same list
            if (nodes[source] != nodes[dest]){
                //If the source list is smaller than the dest list
                if (nodes[source].size() < nodes[dest].size()){
                    //Loop over all the edges in source list
                    for (E edge:nodes[source]){
                        //Add the edge to the dest
                        nodes[dest].add(edge);
                        //Update what list the edges nodes point to
                        nodes[edge.getSource()] = nodes[dest];
                        nodes[edge.getDest()] = nodes[dest];
                    }
                    //Add the new edge to the list found at dest
                    nodes[dest].add(nextEdge);
                    //Set the source node to point to the dest edge list
                    nodes[source] = nodes[dest];
                //If the source list is bigger than the dest list
                } else {
                    //Loop over all the edges in the dest list
                    for (E edge:nodes[dest]){
                        //Add the edge to the source
                        nodes[source].add(edge);
                        //Update what list the edges nodes point to
                        nodes[edge.getSource()] = nodes[source];
                        nodes[edge.getDest()] = nodes[source];
                    }
                    //Add the new edge to the list found at source
                    nodes[source].add(nextEdge);
                    //Set the dest node to point to the source edge list
                    nodes[dest] = nodes[source];
                }
            }
        }
        //Return an iterator from the list of edges that node 0 points to.
        return nodes[0].iterator();
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
