import java.util.*;

public class DijkstraHelper<E extends Edge> {


    /**
     * Returns the path from "from" to "to" as a sequence of edges to traverse
     * or null if there is no path
     */
    public Iterator<E> shortestPath(List<List<E>> graph, int from, int to) {

        Set<Integer> visitedNodes = new HashSet<>();
        Queue<QueueElement> queue = new PriorityQueue<>();  // sorted by weight to evaluate shortest paths first

        queue.add(new QueueElement(from, 0, new Path()));   // initial point

        while (!queue.isEmpty()) {
            QueueElement queueElement = queue.remove();
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

                            queue.add(new QueueElement(toNode,
                                    queueElement.weight + edge.getWeight(),
                                    newPath));
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * QueueElement
     * Implements Comparable to other QueueElements, comparing using the weight field
     */
    private class QueueElement implements Comparable<QueueElement> {
        Path path;      // a list of edges to traverse to reach node
        int node;       // the node to reach
        double weight;  // the cost of reaching that note

        private QueueElement(int node, double weight, Path path) {
            this.node = node;
            this.weight = weight;
            this.path = path;
        }

        @Override
        public int compareTo(QueueElement o) {
            return Double.compare(this.weight, o.weight);
        }
    }

    private class Path extends ArrayList<E> {
    }
}
