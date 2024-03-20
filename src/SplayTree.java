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
        // The splay tree node also includes a parent field for rotations.
        // This class literally speaks for itself so I'm not commenting any of the code.
        // You've read java before :)
        private int element;
        private Node parent;
        private Node left;
        private Node right;

        protected Node(int element, Node left, Node right){
            this.element = element;
            this.parent = null;
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
            currentNode.right.parent = currentNode;
            currentNode = currentNode.right;
        }
    }

    // Alternative constructor, for use in deletion
    public SplayTree(){
        this.root = null;
    }

    // Standard BST search, since I want to use this for convenience in other functions
    // Token helper function
    private Node findNode(Node node, int k){
        if(k == node.getElement()){
            return node;
        } else if(k < node.getElement()){
            return findNode(node.left, k);
        } else if(k > node.getElement()){
            return findNode(node.right, k);
        } else{
            return null;
        }
    }

    // Helper function to help print the tree whenever needed.
    // int directionIndicator is used to determine output based on RT, L, and R as given in the project instructions.
    // 0 indicates root, 1 left, and 2 right.
    private void recPreOrderPrint(Node node, int directionIndicator){
        if(node == null){
            return;
        }

        // Easiest way for me to handle directional printing
        switch(directionIndicator){
            case 0:{
                System.out.print(node.getElement() + "RT ");
                recPreOrderPrint(node.left, 1);
                recPreOrderPrint(node.right, 2);
                break;
            }
            case 1:{
                System.out.print(node.getElement() + "L ");
                recPreOrderPrint(node.left, 1);
                recPreOrderPrint(node.right, 2);
                break;
            }
            case 2:{
                System.out.print(node.getElement() + "R ");
                recPreOrderPrint(node.left, 1);
                recPreOrderPrint(node.right, 2);
                break;
            }
            default:{
                System.out.println("\nSomething has gone horribly wrong in printing.\nSomehow, a direction indicator outside the range *you coded* was given.");
                System.exit(1);
            }
        }
    }

    private void leftRotate(Node a){
        Node b = a.right;
        a.right = b.left;
        if(b.left != null){
            b.left.parent = a;
        }
        b.parent = a.parent;
        if(a.parent == null){
            // passed parent must be root to get here
            this.root = a;
        } else if(a == a.parent.left){
            // passed parent is a left child
            a.parent.left = b;
        } else{
            // passed parent is a right child
            a.parent.right = b;
        }
        b.left = a;
        a.parent = b;
    }

    private void rightRotate(Node a){
        Node b = a.left;
        a.left = b.right;
        if(b.right != null){
            b.right.parent = a;
        }
        b.parent = a.parent;
        if(a.parent == null){
            // passed parent must be root to get here
            this.root = b;
        } else if(a == a.parent.right){
            // passed parent is a right child
            a.parent.right = b;
        } else{
            a.parent.left = b;
        }
        b.right = a;
        a.parent = b;
    }

    // Standard binary tree search with a splay at the end
    // Returns a bool indicating whether the requested value was present within the tree (implying splay performed if true returned)
    // Effective wrapper over findNode(int k) with null case handling.
    public boolean search(int k){
        Node toSplay = findNode(root, k);
        if(toSplay != null){
            splay(toSplay);
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
        Node toDelete = findNode(root, k);
        if(toDelete == null){
            return false;
        }
        splay(toDelete);
        Node leftChild = toDelete.left;
        Node rightChild = toDelete.right;
        if(leftChild == null){
            this.root = rightChild;
            return true;
        }

        SplayTree leftSubtree = new SplayTree();
        leftSubtree.root = root.left;
        if (leftSubtree.root != null) {
            leftSubtree.root.parent = null;
        }

        SplayTree rightSubtree = new SplayTree();
        rightSubtree.root = this.root.right;
        if(rightSubtree.root != null){
            rightSubtree.root.parent = null;
        }

        if(leftSubtree.root != null){
            Node maxLeftSubtree = leftChild;
            while(maxLeftSubtree.right != null){
                maxLeftSubtree = maxLeftSubtree.right;
            }
            // Bring maximum of left subtree to root of left subtree (it will have no right child)
            leftSubtree.splay(maxLeftSubtree);
            // Set right child of new root to be the right subtree's root
            leftSubtree.root.right = rightSubtree.root;
            // Set this newly made root to be root of overall tree
            this.root = leftSubtree.root;
        } else{
            // No reason to worry about left subtree
            this.root = rightSubtree.root;
        }

        return true;
    }

    // Simple approach to insertion:
    // Find node to insert upon and insert upon it
    // Splay newly inserted node to root
    // Returns a bool indicating whether a splay occured
    public boolean insert(int k){
        Node child = new Node(k, null, null);
        Node currentNode = root;
        Node parent = null;

        // Find parent to assign to new node
        while(currentNode != null){
            parent = currentNode;
            if(k < currentNode.getElement()){
                // Left traversal
                currentNode = parent.left;
            } else if(k > currentNode.getElement()) {
                // Right traversal
                currentNode = parent.right;
            } else{
                // Duplicate arrived at
                return false;
            }
        }

        // Assign that parent
        child.parent = parent;

        // Correctly assign child to parent
        if(parent == null){
            // We just added the root of the tree in this case
            root = child;
        } else if(k < parent.getElement()){
            parent.left = child;
        } else if(k > parent.getElement()) {
            parent.right = child;
        } else{
            // Duplicate arrived at
            return false;
        }

        splay(child);
        return true;
    }

    // The meat and potatoes of this project: actually splaying nodes.
    // Chose to allow selecting the effective root so I can splay subtrees (as in deletion algo)
    // Also chose to rely on helper methods to keep this code readable
    public void splay(Node toSplay){
        while(toSplay.parent != null){
            // Handle rotations case-by-case
            if(toSplay.parent == root){
                if(toSplay == root.left){
                    // Left case
                    rightRotate(root);
                } else{
                    // Right case
                    leftRotate(root);
                }
            } else {
                Node parent = toSplay.parent;
                Node grandparent = parent.parent;

                if(parent.left == toSplay && grandparent.left == parent){
                    // Left-left case
                    rightRotate(grandparent);
                    rightRotate(parent);
                } else if(parent.right == toSplay && grandparent.right == parent){
                    // Right-right case
                    leftRotate(grandparent);
                    leftRotate(parent);
                } else if(parent.right == toSplay && grandparent.left == parent){
                    // Left-right case
                    leftRotate(parent);
                    rightRotate(grandparent);
                } else if(parent.left == toSplay && grandparent.right == parent){
                    // Right-left case
                    rightRotate(parent);
                    leftRotate(grandparent);
                }
            }
        }
    }

    // public-facing version of preOrderPrint to keep address of root private.
    public void preOrderPrint(){
        recPreOrderPrint(this.root, 0);
        System.out.println();
        return;
    }
}