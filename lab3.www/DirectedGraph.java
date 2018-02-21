
import java.util.*;

public class DirectedGraph<E extends Edge> {


    /**
     * List of "nodes", where the position in the list is the node's index
     * and the entry is the follower list (i.e. outgoing edges)
     */
    private List<List<E>> graph;

    public DirectedGraph(int noOfNodes) {
        graph = new ArrayList<>(noOfNodes);
    }

    public void addEdge(E e) {
        int from = e.from;

        List<E> followers = graph.get(from);
        if (followers == null) {
            graph.add(from,                 // index
                    new LinkedList<>());    // linked list for easy adding
            followers = graph.get(from);
        }

        followers.add(e);
    }

    public Iterator<E> shortestPath(int from, int to) {

        return null;
    }

    public Iterator<E> minimumSpanningTree() {
        return null;
    }

}
  
