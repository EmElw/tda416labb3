import java.util.*;

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

        queue.add(new PathToNode(from, 0, new Path()));   // initial point

        while (!queue.isEmpty()) {
            PathToNode queueElement = queue.remove();
            int node = queueElement.node;
            Path path = queueElement.path;

            if (!visitedNodes.contains(node)) {

                if (node == to) {
                    return path.iterator();
                } else {
                    visitedNodes.add(node);

                    for (E edge : graph.get(node)) {
                        int toNode = edge.to;

                        if (!visitedNodes.contains(toNode)) {
                            Path newPath = new Path();
                            newPath.addAll(path);
                            newPath.add(edge);

                            queue.add(new PathToNode(toNode,
                                    queueElement.weight + edge.getWeight(),
                                    newPath));
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
     */
    private class PathToNode implements Comparable<PathToNode> {
        Path path;      // a list of edges to traverse to reach node
        int node;       // the node to reach
        double weight;  // the cost of reaching that note

        private PathToNode(int node, double weight, Path path) {
            this.node = node;
            this.weight = weight;
            this.path = path;
        }

        @Override
        public int compareTo(PathToNode o) {
            return Double.compare(this.weight, o.weight);
        }
    }

    private class Path extends ArrayList<E> {
    }
}
