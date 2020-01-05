import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static int expNodes = 0; // Number of expanded nodes
    static int genNodes = 0; // Number of generated nodes
    static int Depth = 0; // The depth of the solution
    static int maxNodes = 0; // Number of maximum nodes in memory

    public static void main(String[] args) {
        /**Scanner sc = new Scanner(System.in);
        String s = sc.nextLine().replaceAll(" ", "");
        String s1 = swap(s,2, 3);
        s = sc.nextLine().replaceAll(" ", "");
        String s2 = swap(s,2, 3);
        s = sc.nextLine().replaceAll(" ", "");
        String s3 = swap(s,2, 3);
        s = sc.nextLine().replaceAll(" ", "");
        String s4 = swap(s,2, 3);
        s = sc.nextLine().replaceAll(" ", "");
        String s5 = swap(s,2, 3);
        s = sc.nextLine().replaceAll(" ", "");
        String s6 = swap(s,2, 3);
        char[] positions = new char[24];
        for (int i = 0; i < 4 ; i++) {
            positions[i] = s1.charAt(i);
        }
        for (int i = 4; i < 8 ; i++) {
            positions[i] = s2.charAt(i - 4);
        }
        for (int i = 8; i < 12 ; i++) {
            positions[i] = s3.charAt(i - 8);
        }
        for (int i = 12; i < 16 ; i++) {
            positions[i] = s4.charAt(i - 12);
        }
        for (int i = 16; i < 20 ; i++) {
            positions[i] = s5.charAt(i - 16);
        }
        for (int i = 20; i < 24 ; i++) {
            positions[i] = s6.charAt(i - 20);
        }
        for (int i = 0; i < 24; i++) {
            if (positions[i] == '1')
                positions[i] = 'o';
            else if(positions[i] == '2')
                positions[i] = 'g';
            else if(positions[i] == '3')
                positions[i] = 'w';
            else if(positions[i] == '4')
                positions[i] = 'b';
            else if(positions[i] == '5')
                positions[i] = 'r';
            else
                positions[i] = 'y';
        }**/
        var st = new RubikState();
        st.executeMoveSeq("F1 F2 F5");
        System.out.print("Problem: ");
        print(st.positions);
//        var solution = UCS_solve.UCS(st);
 //       var solution = IDS_solve.IDS(st, 0);
        var solution = Bidirectional_solve.Bidirectional(st);
        System.out.println(solution);
        Main.Depth = solution.split(" ").length;
        solDetail();
    }

    private static void solDetail() {
        System.out.println("Solution Depth = " + Main.Depth);
        System.out.println("Generated = " + Main.genNodes);
        System.out.println("Expanded = " + Main.expNodes);
        System.out.println("Max in Memory = " + Main.maxNodes);
    }

    static void print(char[] positions) {
        for (int i = 0; i < 24; i += 4) {
            System.out.print(swap(String.valueOf(Arrays.copyOfRange(positions, i, i + 4)), 2, 3) + " ");
        }
        System.out.println();
    }

    static String swap(String str, int i, int j) {
        StringBuilder strB = new StringBuilder(str);
        char l = strB.charAt(i), r = strB.charAt(j);
        strB.setCharAt(i, r);
        strB.setCharAt(j, l);
        return strB.toString();
    }
}
