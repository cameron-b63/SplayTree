/*
 * Cameron Bond CNB220001
 * The following code is driver code for my splay tree implementation
 * Completed for CS 3345.004 with Prof. Zhao
 * Began writing code on 3/7/2024
 * 
 * My personal conventions are to put instance variables at the top of classes, followed by constructors and then other methods.
 * I also include the opening curly brace on the same line, sorry if that feels odd
 * Old habits die hard, and if you program a different way I understand.
*/
import java.util.Scanner;

public class SplayTester {
    public static void main(String[] args){
        // Init scanner and tree
        Scanner s = new Scanner(System.in);
        System.out.print("Enter an integer n to initialize the splay tree: ");
        int n = s.nextInt();
        s.nextLine(); // Clear buffer
        SplayTree tree = new SplayTree(n);
        tree.preOrderPrint();

        // Init used constants outside loop
        String menuPromptString = "Enter a letter to select what to do with your splay tree.\n(a) Search for an integer\n(b) Insert a new integer of choice\n(c) Delete an integer of choice\n(*) Enter anything else to print the tree and quit";
        String terminalPromptEmulator = "> ";
        String searchPrompt = "Enter the integer you'd like to search for (touch and splay): ";
        String insertPrompt = "Enter the integer you'd like to insert: ";
        String deletePrompt = "Enter the integer you'd like to delete: ";
        boolean shouldContinueRunningPromptLoop = true;

        // Prompt loop
        while(shouldContinueRunningPromptLoop){
            System.out.println(menuPromptString);
            System.out.print(terminalPromptEmulator);
            String userChoice = s.nextLine();
            char option = userChoice.charAt(0);
            switch(option){
                case 'a':{
                    // Search
                    System.out.print(searchPrompt);
                    int inputInt = s.nextInt();
                    s.nextLine(); // Clear buffer
                    tree.search(inputInt);
                    break;
                }
                case 'b':{
                    // Insert
                    System.out.print(insertPrompt);
                    int inputInt = s.nextInt();
                    s.nextLine(); // Clear buffer
                    tree.insert(inputInt);
                    break;
                }
                case 'c':{
                    // Delete
                    System.out.print(deletePrompt);
                    int inputInt = s.nextInt();
                    s.nextLine(); // Clear buffer
                    tree.delete(inputInt);
                    break;
                }
                default:{
                    // Exit
                    shouldContinueRunningPromptLoop = false;
                    break;
                }
            }
            tree.preOrderPrint();
        }
        s.close();
    }
}
