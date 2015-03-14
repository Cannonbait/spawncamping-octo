
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
        //Put all edges that originate in the starting node in our list.
        for (E edge:edges){
            if (edge.getSource() == from){
                List<E> path = new ArrayList<>();
                path.add(edge);
                queue.add(new QueueElement(edge, path));
            }
        }
        visited.add(from); 
        //As long as we have edges to go through
        while (!queue.isEmpty()){
            QueueElement next = queue.poll();
            final int newNode = next.getDest(); //The node we originate our search from
            if (!visited.contains(newNode)){ //If we haven't already visited this node
                visited.add(newNode);
                if (next.getDest() == to){ //If this is the node we are traveling to we are done, return the path
                    return next.getPath().iterator();
                } else {  //Loop through all edges and add the ones that point to a node we haven't visited and originates in the node we originated from
                    for (E edge:edges){
                        if (!visited.contains(edge.getDest()) && edge.getSource() == newNode){
                            List<E> oldPath = (List<E>)next.getPath();
                            oldPath.add(edge);
                            queue.add(new QueueElement(edge, oldPath));
                        }
                    }
                }   
            }   
        }
        return null; //There is no path from the node 'from' to the node 'to'
    }
}

/**
 * Contains an edge and the path we took to get to this edge
 * @param <E>
 */
class QueueElement<E extends Edge> implements Comparable<QueueElement>{
    private final E edge;
    private final List<E> path;
    
    public QueueElement(E e, List<E> path){
        this.edge = e;
        this.path = new ArrayList<>(path);
    }

    /**
     * Compares the weight of of the paths of 2 queueelements
     * @param toCompare the queueelement to compare to
     */
    @Override
    public int compareTo(QueueElement toCompare) {
        if (this.getWeight() < toCompare.getWeight()){
            return -1;
        } else if (this.getWeight() == toCompare.getWeight()){
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * Returns the weight of the path we took to this edge
     */
    private double getWeight(){
        double totalWeight = 0;
        for (E edge:path){
            totalWeight += edge.getWeight();
        }
        return totalWeight;
    }

    public int getDest(){
        return edge.getDest();
    }
    
    public List<E> getPath(){
        return new ArrayList<E>(path);
    }
}


