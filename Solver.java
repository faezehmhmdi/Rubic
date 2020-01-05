import java.util.HashMap;


class Solver {
    static String reverse(String path) {
        path += " ";
        StringBuilder reverse = new StringBuilder();

        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) == ' ')
                reverse.append("C ");
            else if (path.charAt(i) != 'C')
                reverse.append(path.charAt(i));
            else {
                reverse.append(" ");
                i++;
            }
        }

        String[] ar = reverse.toString().split(" ");
        for (int i = 0; i < ar.length / 2; i++) {
            String temp = ar[i];
            ar[i] = ar[ar.length - 1 - i];
            ar[ar.length - 1 - i] = temp;
        }

        return String.join(" ", ar);
    }

    static String path(RubikState state, HashMap<RubikState, String> parents) {
        Main.Depth = 0;
        String path = parents.get(state);
        if (path == null)
            return "";
        RubikState next = new RubikState(state.positions);
        next.executeMoveSeq(path);
        while (parents.get(next) != null) {
            path += " " + parents.get(next);
            next = new RubikState(state.positions);
            next.executeMoveSeq(path);
        }
        return path;
    }

}
