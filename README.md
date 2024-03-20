# This Implementation
In this repository is a splay tree which I have written for CS3345.004, Data Structures and Algorithm Analysis. I have implemented it in java, and have chosen to have a pure SplayTree class (no output to stdio) as well as a driver class with the main function in it.

I have opted to use a series of helper functions to simplify large blocks of code. These functions have all been kept private, as is appropriate.

A **splay tree** is a special form of a [binary search tree](https://www.geeksforgeeks.org/binary-search-tree-data-structure/) which self-adjusts after each operation. When a node is accessed, inserted, or deleted, it is *splayed* to the root of the tree. This means a series of rotations are performed on the target node to bring it up to the root. 

Of these rotations, there are three types: **zig**, **zig-zig**, and **zig-zag**. 

### Zig Rotations
**Zig rotations** are simple left or right rotations upon a parent. in the left rotation, the right child of the parent is brought up to be the new parent, and the parent becomes the left child of its own right child. Think of this as a counterclockwise spin on the tree. A right rotation, on the other hand, is a clockwise spin.

### Zig-Zig Rotations
**Zig-Zig rotations** consist of a series of two rotations, either both left or both right, performed on the parent and then the grandparent of the target node (in succession). These rotations resolve the case where the node to be splayed is in the left-left or right-right positions.

### Zig-Zag Rotations
**Zig-Zag rotations** consist of a series of two rotations, where one is left and the other is right. These rotations are performed on the grandparent and then the parent, in that order, to resolve left-right and right-left positional cases.

# Public-Facing Methods (API)
The public facing methods of the SplayTree class are as follows:
- public **boolean** search(int k)
    - Input:
        - k, representing the target integer
    - Output:
        - boolean, representing whether the target integer was found and splayed
    - Function:
        - The method, in this context, serves only to splay a target node whose element is k.

- public **boolean** delete(int k)
    - Input: 
        - k, representing the target integer
    - Output:
        - boolean, representing whether the target integer was present in the tree and deleted successfully
    - Function:
        - To splay and then delete a target node whose element is k.

- public **boolean** insert(int k)
    - Input:
        - k, representing the target integer
    - Output:
        - boolean, representing whether the target integer was successfully inserted and splayed in the tree.
    - Function:
        - To insert and then splay a new node whose element is k.

- public **void** preOrderPrint()
    - Input:
        - null
    - Output:
        - Writes to stdio
    - Function
        - Prints a preorder traversal of the tree, where each value is followed by its child status: 'RT' for root, 'L' for left child of parent, and 'R' for right child of parent.