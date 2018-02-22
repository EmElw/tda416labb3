import java.util.*;

public class KruskalHelper<E extends Edge> {


    public Iterator<E> smallestSpanningTree(List<List<E>> graph) {

        if (graph.size() <= 1) {
            return null;
        }

        /* list of "nodes" represented by index
         * groups are represented by pointing to
         * the same linked list, updated in the
         * loop below
         * */
        List<List<E>> nodeToGroupList = new ArrayList<>(graph.size());

        // fill with empty lists
        for (int i = 0; i < graph.size(); i++)
            nodeToGroupList.add(i, new LinkedList<>());

        // queue of edges, lowest weight first
        Comparator<E> comparator = Comparator.comparingDouble(Edge::getWeight);
        Queue<E> edgeQueue = new PriorityQueue<>(comparator);

        // fill queue with all edges
        for (List<E> followerList : graph)
            edgeQueue.addAll(followerList);

        int noEdges = edgeQueue.size();

        while (!edgeQueue.isEmpty() && nodeToGroupList.size() > 1) {
            E edge = edgeQueue.remove();
            // check if from and to are in the same grouping
            List<E> a = nodeToGroupList.get(edge.from);
            List<E> b = nodeToGroupList.get(edge.to);

            // if they are different groups, merge them (from smallest into largest)
            if (a != b) {
                if (a.size() > b.size()) {
                    mergeGroups(a, b, nodeToGroupList, edge);
                } else {
                    mergeGroups(b, a, nodeToGroupList, edge);
                }
            }
        }

        // if the algorithm worked, all nodes should be in the same group
        List<E> out = nodeToGroupList.get(0);

        if (out.size() != graph.size() - 1) // condition to see if true MST
            return null;    // there is no MST

        return out.iterator();
    }

    /*
    Functional decomposition, merges the two lists together and replaces the
    ones in group b with group a
     */
    private void mergeGroups(List<E> a, List<E> b, List<List<E>> groups, E edge) {

        a.addAll(b);

        // update all in cc pointing to b to point to a
        groups.replaceAll(e -> e == b ? a : e);

        a.add(edge);
    }
}
