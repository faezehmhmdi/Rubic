import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class IDS_solve {
    static String IDS(RubikState state, int initialDepthLimit) {
        Main.genNodes = 0;
        Main.expNodes = 0;
        Main.maxNodes = 1;
        for (int limit = initialDepthLimit; limit < 20; limit++) {
            HashMap<RubikState, String> parents = new HashMap<>();
            parents.put(state, null);
            if (state.isGoal()) {
                return Solver.reverse(Solver.path(state, parents));
            }
            HashMap<RubikState, Integer> depths = new HashMap<>();
            depths.put(state, 0);
            PriorityQueue<RubikState> frontier = new PriorityQueue<>(Comparator.comparingInt(o -> -1 * depths.get(o)));
            frontier.add(state);
            while (!frontier.isEmpty()) {
                Main.maxNodes = Math.max(frontier.size(), Main.maxNodes);
                RubikState toExpand = frontier.poll();
                if (depths.get(toExpand) > limit)
                    continue;
                Main.expNodes++;
                for (Map.Entry<String, RubikState> child : toExpand.getReachableStates().entrySet()) {
                    Main.genNodes++;
                    if (!depths.containsKey(child.getValue())) {
                        depths.put(child.getValue(), depths.get(toExpand) + 1);
                        parents.put(child.getValue(), child.getKey());
                        frontier.add(child.getValue());
                        if (child.getValue().isGoal()) //Check goal state when visiting
                            return Solver.reverse(Solver.path(child.getValue(), parents));
                    }
                }
            }
            System.out.println("Cutoff with limit = " + limit);
        }
        return "No Answer";
    }

}
