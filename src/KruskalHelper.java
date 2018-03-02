import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Comparator;
import java.util.Queue;
import java.util.PriorityQueue;

public class KruskalHelper<E extends Edge> {

    /**
     * Returns an Iterator that describes the smallest spanning tree
     *
     * @param graph a graph on the form List<List<Edge>> where the index identifies the node
     * @return an Iterator that contains all the edges necessary to the tree
     * or null if there is no single tree
     */
    public Iterator<E> smallestSpanningTree(List<List<E>> graph) {

        // list of nodes pointing to the group they are in
        List<List<E>> nodesToGroup = new ArrayList<>(graph.size());

        // fill with empty lists
        for (int i = 0; i < graph.size(); i++)
            nodesToGroup.add(i, new LinkedList<>());

        // queue of edges, lowest weight first
        Comparator<E> comparator = Comparator.comparingDouble(Edge::getWeight);
        Queue<E> edgeQueue = new PriorityQueue<>(comparator);

        // fill queue with all edges
        for (List<E> followerList : graph)
            edgeQueue.addAll(followerList);

        while (!edgeQueue.isEmpty() && nodesToGroup.size() > 1) {
            E edge = edgeQueue.remove();
            List<E> groupA = nodesToGroup.get(edge.from);
            List<E> groupB = nodesToGroup.get(edge.to);

            // if they are different groups, merge them (from smallest into largest)
            if (groupA != groupB) {
                if (groupA.size() > groupB.size()) {
                    mergeGroups(groupA, groupB, nodesToGroup, edge);
                } else {
                    mergeGroups(groupB, groupA, nodesToGroup, edge);
                }
            }
        }

        // if the algorithm worked, all nodes should be in the same group
        List<E> out = nodesToGroup.get(0);

        if (out.size() != graph.size() - 1) // condition to see if true MST
            return null;    // there is no MST

        return out.iterator();
    }

    /*
    Functional decomposition, merges the two lists together and replaces the
    ones in group b with group a (order is important for efficiency)
     */
    private void mergeGroups(List<E> a, List<E> b, List<List<E>> groups, E edge) {

        a.addAll(b);

        // update all in cc pointing to b to point to a
        groups.replaceAll(group -> group == b ? // is it in group b?
                                   a :          // change b to a
                                   group);      // otherwise, don't change

        a.add(edge);
    }
}
