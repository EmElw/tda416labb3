import java.util.*;

public class DijkstraHelper<E extends Edge> {


    /**
     * Finds the shortest path in a directed graph
     */
    public Iterator<E> shortestPath(List<List<E>> graph, int from, int to) {
        List<Double> ssf = new ArrayList<>(graph.size());       // shortest path so far, as seen to each edge.getWeight()
        List<Integer> path = new ArrayList<>(graph.size());     // path to traverse back to from
        for (int i = 0; i < graph.size(); i++) {
            ssf.add(i, -1.0);
            path.add(i, -1);
        }

        //Set<Integer> known = new HashSet<>();                   // set of nodes represented by their integer id
        Set<Integer> notKnown = new HashSet<>();

        // fill notKnown
        for (int i = 0; i < graph.size(); i++) {
            notKnown.add(i);
        }

        //known.add(from);
        notKnown.remove(from);

        // initialize, fill all values
        for (int node : notKnown) {
            double cost = cost(graph, from, node);
            ssf.set(node, cost);
            path.set(node, from);
        }

        // modified dijkstra, only care about to node
        while (!notKnown.isEmpty()) {
            dijkstraIteration(path, ssf, notKnown, graph);
        }
        // returns an iterator containing edges from "from"-node to "to"-node
        return pathToIterator(path, graph, from, to);
    }

    /**
     * function decomposition: converts the given data to an iterator
     */
    private Iterator<E> pathToIterator(List<Integer> path, List<List<E>> graph, int from, int to) {
        Deque<E> edges = new ArrayDeque<>();

        for (int current = to; current != from; current = path.get(current)) {
            // find the shortest edge that goes from current to path.get(current)
            E shortEdge = null;
            for (E edge : graph.get(current))
                if (edge.to == path.get(current)) {
                    if (shortEdge == null || shortEdge.getWeight() > edge.getWeight())
                        shortEdge = edge;
                }
            if (shortEdge == null) throw new ShitIsFuckedException();
            edges.addLast(shortEdge);
        }

        return edges.iterator();
    }

    /**
     * Updates the path and ssf lists using the data in notKnown and graph
     */
    private void dijkstraIteration(List<Integer> path, List<Double> ssf, Set<Integer> notKnown, List<List<E>> graph) {
        // find smallest ssf
        int w = -1;             // node to search from
        double minWeight = -1;  // weight of that node
        for (Integer node : notKnown) {
            double weight = ssf.get(node);
            if (weight == -1) continue; // treat -1 as inf
            if (weight < minWeight || minWeight == -1) {
                minWeight = weight;
                w = node;
            }
        }

        if (minWeight == -1) throw new ShitIsFuckedException();

        // add smallest ssf node to known
        notKnown.remove(w);

        // make a set of follower nodes from w
        Set<Integer> followers = new HashSet<>();
        for (E edge : graph.get(w)) followers.add(edge.to);

        // make a set of nodes to search for potential candidates
        Set<Integer> toSearch = new HashSet<>();
        for (int node : followers) {
            if (notKnown.contains(node))
                toSearch.add(node);
        }

        // search those nodes for the shortest path
        for (int v : toSearch) {
            // find cost from w to v if any (otherwise the cost is treated as infinite)
            double cost = cost(graph, w, v);

            if (cost == -1) // should never arrive here, but
                throw new ShitIsFuckedException();

            // update shortest so far if necessary
            double oldWeight = ssf.get(v);
            double newWeight = ssf.get(w) + cost;
            if (newWeight < oldWeight || oldWeight == -1) {
                ssf.set(v, newWeight);
                path.set(v, w);
            }
        }
    }

    /**
     * Calculates the cost of the edge between two nodes if any
     *
     * @param graph the graph to retrieve data in
     * @param from
     * @param to
     * @return the weight, using edge.getWeight, or -1 if there is no edge from from to to
     */
    private double cost(List<List<E>> graph, int from, int to) {
        E edge = null;
        for (E e : graph.get(from))
            if (e.to == to)
                edge = e;
        return edge == null ? -1 : edge.getWeight();
    }
}
