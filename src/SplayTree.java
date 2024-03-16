public class SplayTree {
    /*
     Actual splay tree implementation for CS 3345.004 project 1.

     I really like writing code that speaks for itself.
     As such, you'll be seeing quite little green aside from documentation above methods in this code.
     Only really in lines that were confusing to me when I needed to write/bugfix them, so I write down my thinking as reference.
     I hope that's alright.
     */
    private class Node {
        // To properly implement any Tree, we'll need a Node class.
        // Since a splay tree is a special binary tree, the Node class includes left and right pointers.
        // This class literally speaks for itself so I'm not commenting any of the code.
        // You've read java before :)
        private int element;
        private Node left;
        private Node right;

        protected Node(int element, Node left, Node right){
            this.element = element;
            this.left = left;
            this.right = right;
        }

        public int getElement(){ return this.element; }
    }

    private Node root;

    public SplayTree(int n){
        this.root = new Node(1, null, null); // Initialize root as just a Node with 1 in it
        // The following (goofy, admittedly) for loop just initializes the tree as specified in the project doc.
        Node currentNode = root;
        for(int i = 2; i < n+1; i++){
            currentNode.right = new Node(i, null, null);
            currentNode = currentNode.right;
        }
    }

    // Standard BST search, since I want to use this for convenience in other functions
    // Token helper function
    private Node findNode(int k){
        Node currentNode = root;
        while(true){
            if(k < currentNode.getElement()){
                currentNode = currentNode.left;
            } else if(k > currentNode.getElement()){
                currentNode = currentNode.right;
            } else if(k == currentNode.getElement()){
                return currentNode;
            } else {
                 // I'm choosing the arbitrary convention of not alerting when the node isn't found. Entirely my own choice, I just wanted to keep the function pure.
                 // I don't want to print an error message.
                return null;
            }
        }
    }

    // Standard binary tree search with a splay at the end
    // Returns a bool indicating whether the requested value was present within the tree (implying splay performed if true returned)
    // Effective wrapper over findNode(int k) with null case handling.
    public boolean search(int k){
        Node toSplay = findNode(k);
        if(toSplay != null){
            splay(root, toSplay);
            return true;
        }
        return false;
    }

    // Simple approach to deletion:
    // Find node to delete
    // Splay it to root
    // Delete it and handle child cases with proper state (select correct new root)
    // Returns a bool indicating whether the requested value was present within the tree
    public boolean delete(int k){
        Node toDelete = findNode(k);
        if(toDelete == null){
            return false;
        }
        splay(root, toDelete);
        Node leftChild = toDelete.left;
        Node rightChild = toDelete.right;
        if(leftChild == null){
            root = rightChild;
            return true;
        }
        Node maxLeftSubtree = leftChild;
        while(maxLeftSubtree.right != null){
            maxLeftSubtree = maxLeftSubtree.right;
        }
        splay(leftChild, maxLeftSubtree);
        // make right the rightchild of left
        // make left the root
        return true;
    }

    // Simple approach to insertion:
    // Find node to insert upon and insert upon it
    // Splay newly inserted node to root
    // Returns a bool indicating whether a splay occured
    public boolean insert(int k){
        Node parent = root;
        Node child;
        while(true){
            if(k < parent.getElement()){
                if(parent.left == null){
                    parent.left = new Node(k, null, null);
                    child = parent.left;
                    break;
                }
            } else if(k > parent.getElement()){
                if(parent.left == null){
                    parent.right = new Node(k, null, null);
                    child = parent.right;
                    break;
                }
            } else {
                // parent must be equal to k, no duplicates allowed
                return false;
            }
        }
        splay(root, child);
        return true;
    }

    // The meat and potatoes of this project: actually splaying nodes.
    // Chose to allow selecting the effective root so I can splay subtrees (as in deletion algo)
    public void splay(Node root, Node nodeToSplay){
        return;
    }

    // Helper function to print the tree whenever needed. Used in the driver code to keep functions here pure.
    public void preOrderPrint(){
        return;
    }
}
