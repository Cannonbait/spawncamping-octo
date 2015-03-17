
package lab3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;


public class CompDijkstraPath<E extends Edge> {
    private final PriorityQueue<QueueElement> queue;
    private final List<E> edges;
    private final Boolean[] visited;
    private List<E>[] EL;

    public CompDijkstraPath(List<E> edges, int numberOfNodes){
        this.edges = edges;
        queue = new PriorityQueue(edges.size() - 1);
        visited = new Boolean[numberOfNodes];
        EL = new List[numberOfNodes];

        for(int i = 0; i < numberOfNodes; ++i) {
            visited[i] = false;
            EL[i] = new ArrayList();
        }
        //Construct EL
        for(E edge : edges) {
            EL[edge.from].add(edge);
        }
    }
    
    public Iterator<E> getShortest(int from, int to){
        //Put all edges that originate in the starting node in our list.
        for (E edge:EL[from]){
            List<E> path = new ArrayList<>();
            path.add(edge);
            queue.add(new QueueElement(edge, path));
        }
        visited[from] = true;
        //As long as we have edges to go through
        while (!queue.isEmpty()){
            QueueElement next = queue.poll();
            final int newNode = next.getDest(); //The node we originate our search from
            if (!visited[newNode]){ //If we haven't already visited this node
                visited[newNode] = true;
                if (next.getDest() == to){ //If this is the node we are traveling to we are done, return the path
                    return next.getPath().iterator();
                } else {  //Loop through all edges that originate in this node and add the ones that point to a node we haven't visited.
                    for (E edge:EL[newNode]){
                        if (!visited[edge.getDest()] && edge.getSource() == newNode){
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


