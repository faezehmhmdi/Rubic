import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class Bidirectional_solve {
    static String Bidirectional(RubikState state) {
        HashMap<RubikState, String> forwardParents = new HashMap<RubikState, String>();
        HashMap<RubikState, String> backwardParents = new HashMap<RubikState, String>();
        ArrayDeque<RubikState> fqueue = new ArrayDeque<RubikState>();
        ArrayDeque<RubikState> bqueue = new ArrayDeque<RubikState>();
        RubikState src = state, end = new RubikState();
        forwardParents.put(end, null);
        backwardParents.put(src, null);
        fqueue.add(end);
        bqueue.add(src);
        Main.genNodes = 1;
        Main.expNodes = 0;
        Main.maxNodes = 0;
        // bfs visit from both ends of graph
        while (true) {
            Main.maxNodes = Math.max(fqueue.size() + bqueue.size(), Main.maxNodes);
            end = fqueue.remove();
            Main.expNodes++;
            for (Map.Entry<String, RubikState> move : end.getReachableStates().entrySet()) {
                Main.genNodes++;
                if (!forwardParents.containsKey(move.getValue())) {
                    forwardParents.put(move.getValue(), move.getKey());
                    fqueue.add(move.getValue());
                }
            }
            src = bqueue.remove();
            Main.expNodes++;
            for (Map.Entry<String, RubikState> move : src.getReachableStates().entrySet()) {
                Main.genNodes++;
                if (!backwardParents.containsKey(move.getValue())) {
                    backwardParents.put(move.getValue(), move.getKey());
                    bqueue.add(move.getValue());
                }
                // same state discovered in both ends of search
                if (forwardParents.containsKey(move.getValue())) {
                    String endpath = Solver.path(move.getValue(), forwardParents);
                    String srcpath = Solver.path(move.getValue(), backwardParents);
                    srcpath = Solver.reverse(srcpath);
                    return srcpath + " " + endpath;
                }
            }
        }
    }
}
