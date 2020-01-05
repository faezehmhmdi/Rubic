import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class UCS_solve {
    public static String UCS(RubikState state) {
        HashMap<RubikState, String> parents = new HashMap<>();
        parents.put(state, null);
        HashMap<RubikState, Integer> costs = new HashMap<>();
        costs.put(state, 0);
        PriorityQueue<RubikState> frontier = new PriorityQueue<>(Comparator.comparingInt(o -> costs.get(o)));
        frontier.add(state);
        Main.genNodes = 0;
        Main.expNodes = 0;
        Main.maxNodes = 0;
        while (!frontier.isEmpty()) {
            Main.maxNodes = Math.max(frontier.size(), Main.maxNodes);
            RubikState minimumF = frontier.poll();
            if (minimumF.isGoal()) // check goal state when expanding
                return Solver.reverse(Solver.path(minimumF, parents));
            else {
                Main.expNodes++;
                for (Map.Entry<String, RubikState> child : minimumF.getReachableStates().entrySet()) {
                    Main.genNodes++;
                    if (!costs.containsKey(child.getValue())) {
                        costs.put(child.getValue(), costs.get(minimumF) + 1);
                        parents.put(child.getValue(), child.getKey());
                        frontier.add(child.getValue());
                    }
                }
            }
        }
        return "No Answer";
    }

}
