import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.ArrayList;

public class DijkstraHelper<E extends Edge> {


    /**
     * Returns the path from "from" to "to" as a sequence of edges to traverse
     * or null if there is no path
     * <p>
     * The algorithm used is a modified version of djikstra's which stops upon
     * reaching the "to" node
     * <p>
     * It works by adding "suggested" paths to a queue sorted by weight, and
     * successively adds and removes segments that locks the shortest
     * path to a point. Once the "to" node has been locked, the algorithm is done
     */
    public Iterator<E> shortestPath(List<List<E>> graph, int from, int to) {
        Set<Integer> visitedNodes = new HashSet<>();
        Queue<PathToNode> queue = new PriorityQueue<>();

        queue.add(new PathToNode(null, null, from, 0));   // initial point

        while (!queue.isEmpty()) {
            PathToNode queueElement = queue.remove();
            int node = queueElement.node;


            if (!visitedNodes.contains(node)) {

                if (node == to) {
                    return queueElement.iterator();
                } else {
                    visitedNodes.add(node);

                    for (E edge : graph.get(node)) {
                        int toNode = edge.to;

                        if (!visitedNodes.contains(toNode)) {
                            queue.add(new PathToNode(edge,
                                    queueElement,
                                    toNode,
                                    queueElement.weight + edge.getWeight()));
                        }
                    }
                }
            }
        }

        // arrive here if there is no path between "from" and "to"
        return null;
    }

    /**
     * PathToNode
     * Represents multiple edges and their summed weight to a given node from
     * the starting node (i.e. the "from" node)
     * <p>
     * It is essentially "some number of edges that is a short path" plus the edge from
     * the last node in that sequence, to this node "node"
     */
    private class PathToNode implements Comparable<PathToNode> {
        E edge;             // edge to previous PathToNode
        PathToNode prev;    // previous path to node
        int node;           // the node to reach
        double weight;      // the cost of reaching that note

        PathToNode(E edge, PathToNode prev, int node, double weight) {
            this.edge = edge;
            this.prev = prev;
            this.node = node;
            this.weight = weight;
        }


        @Override
        public int compareTo(PathToNode o) {
            return Double.compare(this.weight, o.weight);
        }

        private Iterator<E> iterator() {
            return makePath().iterator();
        }

        private List<E> makePath() {
            if (prev == null) {
                return new LinkedList<>();
            } else {
                List<E> l;
                l = prev.makePath();
                l.add(edge);
                return l;
            }
        }
    }

}
