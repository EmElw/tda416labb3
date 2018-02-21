
import java.util.*;

public class DirectedGraph<E extends Edge> {


    /**
     * List of "nodes", where the position in the list is the node's index
     * and the entry is the follower list (i.e. outgoing edges)
     */
    private List<List<E>> graph;

    public DirectedGraph(int noOfNodes) {
        graph = new ArrayList<>(noOfNodes);
        for (int i = 0; i < noOfNodes; i++)
            graph.add(new LinkedList<>());
    }

    public void addEdge(E e) {
        int from = e.from;

        List<E> followers = graph.get(from);

        followers.add(e);
    }

    public Iterator<E> shortestPath(int from, int to) {

        return null; // TODO
    }

    public Iterator<E> minimumSpanningTree() {
        return null; // TODO
    }

}
  
