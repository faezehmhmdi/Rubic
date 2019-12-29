import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class RubikState {

    /**
     * 24 positions for each color
     * w - white
     * y - yellow
     * g - green
     * b - blue
     * o - orange
     * r - red
     * each consecutive 4 indices are a face labeled starting from
     * top left going around clockwise
     * i:0-3 top face
     * i:4-7 left face
     * i:8-11 front face
     * i:12-15 right face
     * i:16-19 bottom face
     * i:20-23 back face
     */
    char[] positions;

    public RubikState() {
        // create a solved cube
        positions = new char[24];
        for (int i = 0; i < 4; i++)
            positions[i] = 'o';
        for (int i = 4; i < 8; i++)
            positions[i] = 'g';
        for (int i = 8; i < 12; i++)
            positions[i] = 'w';
        for (int i = 12; i < 16; i++)
            positions[i] = 'b';
        for (int i = 16; i < 20; i++)
            positions[i] = 'r';
        for (int i = 20; i < 24; i++)
            positions[i] = 'y';
    }

    public RubikState(char[] positions) {
        this.positions = positions;
    }

    /**
     * Permutations (Moves) show how the positions array indices move
     * F - front rotated clockwise
     * F' - front counter clockwise
     * R - right clockwise
     * R' - right counter clockwise
     * U - up clockwise
     * U' - up counter clockwise
     */
    private static int[] F1 = {2, 0, 3, 1, 8, 9, 6, 7, 12, 13, 10, 11, 23, 22, 14, 15, 16, 17, 18, 19, 20, 21, 5, 4};
    private static int[] F1C = permInverse(F1);
    private static int[] F2 = {20, 1, 22, 3, 6, 4, 7, 5, 0, 9, 2, 11, 12, 13, 14, 15, 8, 17, 10, 19, 16, 21, 18, 23};
    private static int[] F2C = permInverse(F2);
    private static int[] F3 = {0, 1, 7, 5, 4, 16, 6, 17, 10, 8, 11, 9, 2, 13, 3, 15, 14, 12, 18, 19, 20, 21, 22, 23};
    private static int[] F3C = permInverse(F3);
    private static int[] F4 = {0, 9, 2, 11, 4, 5, 6, 7, 8, 17, 10, 19, 14, 12, 15, 13, 16, 21, 18, 23, 20, 1, 22, 3};
    private static int[] F4C = permInverse(F4);
    private static int[] F5 = {0, 1, 2, 3, 4, 5, 21, 20, 8, 9, 6, 7, 12, 13, 10, 11, 18, 16, 19, 17, 15, 14, 22, 23};
    private static int[] F5C = permInverse(F5);
    private static int[] F6 = {13, 15, 2, 3, 1, 5, 0, 7, 8, 9, 10, 11, 12, 19, 14, 18, 16, 17, 4, 6, 22, 20, 23, 21};
    private static int[] F6C = permInverse(F6);

    public static int[] permInverse(int[] p) {
        int n = p.length;
        int[] g = new int[n];
        for (int i = 1; i < n; i++) {
            g[p[i]] = i;
        }
        return g;
    }

    public char[] permApply(int[] perm) {
        char[] newPositions = new char[24];
        for (int i = 0; i < 24; i++) {
            newPositions[i] = positions[perm[i]];
        }
        return newPositions;
    }

    public boolean isGoal() {
        return heuristic() == 0;
    }

    public int heuristic() {
        int result = 0;
        for (int i = 0; i < positions.length; i += 4) {
            HashSet<Character> resultSet = new HashSet<>();
            char[] face = Arrays.copyOfRange(positions, i, i + 4);
            for (char c : face) {
                resultSet.add(c);
            }
            int number_of_different = resultSet.size();
            if (number_of_different == 4)
                result += 4;
            else if (number_of_different == 3)
                result += 2;
            else if (number_of_different == 2)
                result += 1;
        }
        return result;
    }

    public HashMap<String, RubikState> getReachableStates() {
        // map from **Reverse** of action applied to current state to get to another state
        HashMap<String, RubikState> moves = new HashMap<>();
        addBasicMove("F1C", F1, moves);
        addBasicMove("F1", F1C, moves);
        addBasicMove("F2C", F2, moves);
        addBasicMove("F2", F2C, moves);
        addBasicMove("F3C", F3, moves);
        addBasicMove("F3", F3C, moves);
        addBasicMove("F4C", F4, moves);
        addBasicMove("F4", F4C, moves);
        addBasicMove("F5C", F5, moves);
        addBasicMove("F5", F5C, moves);
        addBasicMove("F6C", F6, moves);
        addBasicMove("F6", F6C, moves);
        return moves;
    }

    private void addBasicMove(String name, int[] perm, HashMap<String, RubikState> moves) {
        RubikState state = new RubikState(permApply(perm));
        moves.put(name, state);
    }

    public void executeMoveSeq(String seq) {
        if (seq == null) return;
        String[] moves = seq.toUpperCase().split(" ");
        for (String move : moves) {
            switch (move) {
                case "F1":
                    positions = permApply(F1);
                    break;
                case "F1C":
                    positions = permApply(F1C);
                    break;
                case "F2":
                    positions = permApply(F2);
                    break;
                case "F2C":
                    positions = permApply(F2C);
                    break;
                case "F3":
                    positions = permApply(F3);
                    break;
                case "F3C":
                    positions = permApply(F3C);
                    break;
                case "F4":
                    positions = permApply(F4);
                    break;
                case "F4C":
                    positions = permApply(F4C);
                    break;
                case "F5":
                    positions = permApply(F5);
                    break;
                case "F5C":
                    positions = permApply(F5C);
                    break;
                case "F6":
                    positions = permApply(F6);
                    break;
                case "F6C":
                    positions = permApply(F6C);
                    break;
            }
        }
    }

    @Override
    public int hashCode() {
        return Arrays.toString(this.positions).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().getSimpleName().equals("RubikState")) return false;
        RubikState state = (RubikState) obj;
        if (state.positions.length != this.positions.length) return false;
        for (int i = 0; i < this.positions.length; i++) {
            if (this.positions[i] != state.positions[i])
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return Arrays.toString(positions);
    }
}
