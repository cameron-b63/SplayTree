/*
 * Cameron Bond CNB220001
 * The following code is driver code for my splay tree implementation
 * Completed for CS 3345.004 with Prof. Zhao
 * Began writing code on 3/7/2024
 * 
 * My personal conventions are to put instance variables at the top of classes, followed by constructors and then other methods.
 * I also include the opening curly brace on the same line, sorry if that feels quite unreadable
 * Old habits die hard, and if you program a different way I understand.
*/
import java.util.Scanner;

public class SplayTester {
    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter an integer n to initialize the splay tree: ");
        int n = s.nextInt();
        SplayTree tree = new SplayTree(n);
        tree.preOrderPrint();

        s.close();
    }
}
