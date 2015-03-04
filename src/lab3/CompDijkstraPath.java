
package lab3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;


public class CompDijkstraPath<E extends Edge> {
    private final int size;
    private final PriorityQueue<QueueElement> queue;
    private final List<E> edges;
    private final List<Integer> visited;
    
    public CompDijkstraPath(List<E> edges){
        size = edges.size();
        queue = new PriorityQueue(size-1);
        this.edges = edges;
        visited = new ArrayList<>();
        
    }
    
    public Iterator<E> getShortest(int from, int to){
        for (E edge:edges){
            if (edge.getSource() == from){
                List<E> path = new ArrayList<>();
                path.add(edge);
                queue.add(new QueueElement(edge, path));
            }
        }
        visited.add(from);
        
        while (!queue.isEmpty()){
            QueueElement next = queue.poll();
            final int dest = next.getDest();
            if (!visited.contains(dest)){
                if (next.getDest() == to){
                    return next.getPath().iterator();
                } else {
                    visited.add(dest);
                    for (E edge:edges){
                        if (!visited.contains(edge.getDest())){
                            List<E> oldPath = (List<E>)next.getPath();
                            oldPath.add(edge);
                            queue.add(new QueueElement(edge, oldPath));
                        }
                    }
                }   
            }   
        }
        return null;
    }
}

class QueueElement<E extends Edge> implements Comparable<QueueElement>{
    private final E edge;
    private final List<E> path;
    
    public QueueElement(E e, List<E> path){
        this.edge = e;
        this.path = new ArrayList<>(path);
    }

    @Override
    public int compareTo(QueueElement toCompare) {
        double compareWeight = toCompare.getWeight();
        if (edge.getWeight() < compareWeight){
            return -1;
        } else if (edge.getWeight() == compareWeight){
            return 0;
        } else {
            return 1;
        }
    }
    
    public double getWeight(){
        return edge.getWeight();
    }
    
    public int getSource(){
        return edge.getSource();
    }

    public int getDest(){
        return edge.getDest();
    }
    
    public List<E> getPath(){
        return new ArrayList<E>(path);
    }
    
    public E getEdge(){
        return edge;
    }
}


