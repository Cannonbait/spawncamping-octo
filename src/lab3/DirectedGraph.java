package lab3;

import java.util.*;

public class DirectedGraph<E extends Edge> {
        private List<E> edges;
        private final int number;

	public DirectedGraph(int noOfNodes) {
            number = noOfNodes;
            edges = new ArrayList<>();
	}

	public void addEdge(E e) {
            edges.add(e);
	}

	public Iterator<E> shortestPath(int from, int to) {
            CompDijkstraPath dijkstra = new CompDijkstraPath(edges, number);
            return dijkstra.getShortest(from, to);
	}
		
	public Iterator<E> minimumSpanningTree() {
            CompKruskalEdge kruskal = new CompKruskalEdge(number, edges);
            return kruskal.getMinimumSpanningTree();
	}

}
  
