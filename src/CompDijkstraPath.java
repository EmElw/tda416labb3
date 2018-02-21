import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompDijkstraPath<E extends Edge> {


    /**
     * Finds the shortest path in a directed graph
     *
     * @param graph
     * @param from
     * @param to
     * @return the path, as a list, where the index represents the node and the contents represents
     * the node that should be travelled to return to from TODO
     */
    public List<E> shortestPath(List<List<E>> graph, int from, int to) {
        List<Double> ssf = new ArrayList<>(graph.size());       // shortest path so far, as seen to each edge.getWeight()
        List<Integer> path = new ArrayList<>(graph.size());     // path to traverse back to from
        Set<Integer> known = new HashSet<>();                   // set of nodes represented by their integer id
        Set<Integer> notKnown = new HashSet<>();

        // fill notKnown
        for (int i = 0; i < graph.size(); i++) {
            notKnown.add(i);
        }

        known.add(from);
        notKnown.remove(from);

        // initialize, fill all values
        for (int node : notKnown) {
            double cost = cost(graph, from, node);

            ssf.set(node, cost);
            path.set(node, from);
        }

        // modified dijkstra, only care about to node
        while (!known.contains(to)) {

            // find smallest ssf
            int w = -1;             // node to search from
            double minWeight = -1;  // weight of that node
            for (Integer node : notKnown) {
                if (minWeight == -1) minWeight = 0;
                if (ssf.get(node) < minWeight) {
                    minWeight = ssf.get(node);
                    w = node;
                }
            }

            if (minWeight == -1) throw new ShitIsFuckedException();

            // add smallest ssf node to known
            notKnown.remove(w);
            known.add(w);

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

                // update shortest so far
                double val;
                if (ssf.get(v) == -1)  // ensure that -1 < is treated as inf
                    val = ssf.get(w) + cost;
                else
                    val = Math.min(ssf.get(v), ssf.get(w) + cost);

                ssf.set(v, val);
            }
        }
        return null;    // TODO
    }

    /**
     * Calculates the cost of the edge between two nodes if any
     *
     * @param graph
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
