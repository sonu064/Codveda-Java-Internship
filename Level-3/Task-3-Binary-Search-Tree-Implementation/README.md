# Binary Search Tree Implementation

A production-quality, fully **recursive Binary Search Tree** implemented in Core Java with a
menu-driven console interface. Supports insertion, deletion (all cases including root),
searching, all four traversals, and tree metrics — with complete edge-case handling.

Developed as **Level 3 – Task 3** of the **Java Development Internship at Codveda Technologies**.

---

## Project Overview

| | |
|---|---|
| **Project Name** | Binary Search Tree Implementation |
| **Type** | Console Application |
| **Language** | Java 21 (Java 17 compatible) |
| **Libraries** | Core Java only — no external dependencies |

The tree stores unique integers. All core operations (insert, search, delete, depth-first
traversals, height, count, min/max) are implemented **recursively**; level-order traversal
uses a **queue** (`ArrayDeque`) as required for breadth-first search.

---

## Features

- **Insert** — recursive; duplicates are detected and rejected with a clear message
- **Delete** — recursive; correctly handles all four cases:
  - Leaf node deletion
  - Node with one child
  - Node with two children (replaced by its **inorder successor**)
  - Root deletion (no special-casing needed — recursion returns the new root)
- **Search** — recursive; prints `Node Found` / `Node Not Found`
- **Traversals** — Inorder (sorted), Preorder, Postorder (recursive), and Level Order (queue-based)
- **Metrics** — tree height (in edges), total node count, minimum, and maximum
- **Edge cases** — empty tree, delete/search/min/max on empty tree, duplicates, negative
  values, large values (full `int` range), and invalid menu input (`InputMismatchException`
  handled; the app never crashes)

---

## Folder Structure

```
Task-3-Binary-Search-Tree-Implementation
│
├── src
│   ├── bst
│   │      BinarySearchTree.java   # All recursive tree logic + queue-based BFS
│   │      TreeNode.java           # Pure data holder: value, left, right
│   │
│   ├── service
│   │      TreeService.java        # Edge-case guards + user-friendly messages
│   │
│   ├── util
│   │      ConsoleHelper.java      # Banner, menu, colored output
│   │      InputValidator.java     # Crash-proof integer & menu input
│   │
│   └── Main.java                  # Entry point: wiring + menu loop
│
├── screenshots
├── README.md
└── .gitignore
```

**Architecture:** `Main` (UI) → `TreeService` (application logic + messaging) →
`BinarySearchTree` (data structure) → `TreeNode` (data). Dependencies are provided via
**constructor injection**, and no class holds static mutable state.

---

## How to Compile

From the `Task-3-Binary-Search-Tree-Implementation` folder:

```bash
javac -d out src/bst/*.java src/service/*.java src/util/*.java src/Main.java
```

## How to Run

```bash
java -cp out Main
```

---

## Sample Output

```
=============================================
     BINARY SEARCH TREE IMPLEMENTATION
=============================================
 1.  Insert Node
 2.  Delete Node
 3.  Search Node
 4.  Inorder Traversal
 5.  Preorder Traversal
 6.  Postorder Traversal
 7.  Level Order Traversal
 8.  Tree Height
 9.  Count Nodes
10.  Find Minimum
11.  Find Maximum
12.  Exit
=============================================
Choose Option (1-12) : 1

Enter value to insert : 50
Node 50 inserted successfully.

Choose Option (1-12) : 1

Enter value to insert : 50
Duplicate value: 50 already exists in the tree.

Choose Option (1-12) : 4

Inorder Traversal (Left -> Node -> Right):
20 -> 30 -> 40 -> 50 -> 60 -> 70 -> 80

Choose Option (1-12) : 7

Level Order Traversal (top to bottom, left to right):
50 -> 30 -> 70 -> 20 -> 40 -> 60 -> 80

Choose Option (1-12) : 2

Enter value to delete : 50
Node 50 deleted successfully.

Choose Option (1-12) : 3

Enter value to search : 50
Node Not Found

Choose Option (1-12) : 8

Tree Height : 2 (edges on the longest root-to-leaf path)

Choose Option (1-12) : 12

Thank you for using the BST application. Goodbye!
```

---

## Technologies Used

- **Java 21** (works on Java 17+)
- **Recursion** — insert, delete, search, DFS traversals, height, count, min/max
- **Java Collections** — `ArrayDeque` as the BFS queue, `ArrayList` for traversal results
- **Exception Handling** — `InputMismatchException`, `NoSuchElementException`, top-level safety net

---

## Time Complexity

| Operation | Average | Worst Case (skewed tree) |
|-----------|---------|--------------------------|
| Insert | O(log n) | O(n) |
| Search | O(log n) | O(n) |
| Delete | O(log n) | O(n) |
| Inorder / Preorder / Postorder | O(n) | O(n) |
| Level Order | O(n) | O(n) |
| Height | O(n) | O(n) |
| Count Nodes | O(n) | O(n) |
| Find Min / Max | O(log n) | O(n) |

*n = number of nodes. Space complexity is O(h) for recursion depth (O(n) worst case)
and O(n) for level-order's queue.*

---

## Screenshots

Capture these into the `screenshots/` folder:

| Screenshot | Description |
|------------|-------------|
| `menu.png` | The main menu on startup |
| `insert-duplicate.png` | Successful insert + duplicate rejection |
| `traversals.png` | All four traversals of the same tree |
| `delete-cases.png` | Deleting a leaf, a one-child node, and the root |
| `empty-tree.png` | Min/Max/Delete attempted on an empty tree |
| `invalid-input.png` | Letters entered at a numeric prompt |

---

## Future Improvements

- Self-balancing variant (AVL or Red-Black rotations)
- Generic implementation (`BinarySearchTree<T extends Comparable<T>>`)
- ASCII-art visualization of the tree structure in the console
- Persistence (save/load the tree to a file)
- Unit tests with JUnit 5

---

## Author

**Sonu Singh** — Java Development Intern @ Codveda Technologies
