public class AVLTree {
    private AVLNode root;

    AVLTree(){}

    public void insert(AVLNode node) {
        if (root == null) {
            root = node;
            node.balanceFactor = 0;
        } else {
            AVLNode curr = root;
            boolean inserted = false;
            while (!inserted) {
                if (node.key >= curr.key) {
                    if (curr.rightChild == null) {
                        curr.rightChild = node;
                        node.parent = curr;
                        inserted = true;
                    } else {
                        curr = curr.rightChild;
                    }
                } else {
                    if (curr.leftChild == null) {
                        curr.leftChild = node;
                        node.parent = curr;
                        inserted = true;
                    } else {
                        curr = curr.leftChild;
                    }
                }
            }
            curr = node;
            boolean balanced = false;
            while (curr != null && curr.parent != null && !balanced) {
                if (curr == curr.parent.leftChild) {
                    curr.parent.balanceFactor--;
                    if (curr.parent.balanceFactor == 0) {
                        balanced = true;
                    } else if (curr.parent.balanceFactor == -2) {
                        if (curr.balanceFactor == -1) {
                            curr = rotateRight(curr.parent);
                        } else {
                            curr = rotateLeft(curr);
                            curr = rotateRight(curr.parent);
                        }
                        balanced = true;
                    }
                } else {
                    curr.parent.balanceFactor ++;
                    if (curr.parent.balanceFactor == 0) {
                        balanced = true;
                    } else if (curr.parent.balanceFactor == 2) {
                        if (curr.balanceFactor == 1) {
                            curr = rotateLeft(curr.parent);
                        } else {
                            curr = rotateRight(curr);
                            curr = rotateLeft(curr.parent);
                        }
                        balanced = true;
                    }
                }
                curr = curr.parent;
            }
        }
    }

    public void delete(AVLNode node) {
        AVLNode replaceNode;
        if (node.rightChild != null) {
            replaceNode = node.rightChild;
            while (replaceNode.leftChild != null) {
                replaceNode = replaceNode.leftChild;
            }
        } else {
            replaceNode = node;
        }
        AVLNode curr = replaceNode;
        boolean balanced = false;

        while (curr.parent != null && !balanced) {
            if (curr.parent.leftChild == curr) {
                curr.parent.balanceFactor++;
            } else {
                curr.parent.balanceFactor--;
            }

            if (curr.parent.balanceFactor == 1 || curr.parent.balanceFactor == -1) {
                balanced = true;
            } else if (curr.parent.balanceFactor == 2) {
                curr = curr.parent.rightChild;
                if (curr.balanceFactor == 1) {
                    curr = rotateLeft(curr.parent);
                } else {
                    curr = rotateRight(curr);
                    curr = rotateLeft(curr.parent);
                }
                if (curr.balanceFactor == 1 || curr.balanceFactor == -1) {
                    balanced = true;
                }
            } else if (curr.parent.balanceFactor == -2) {
                curr = curr.parent.leftChild;
                if (curr.balanceFactor == -1) {
                    curr = rotateRight(curr.parent);
                } else {
                    curr = rotateLeft(curr);
                    curr = rotateRight(curr.parent);
                }
                if (curr.balanceFactor == 1 || curr.balanceFactor == -1) {
                    balanced = true;
                }
            } else {
                curr = curr.parent;
            }
        }

        if (replaceNode.parent != null) {
            if (replaceNode == replaceNode.parent.leftChild) {
                replaceNode.parent.leftChild = null;
            } else {
                replaceNode.parent.rightChild = null;
            }
        }
        if (replaceNode != node) {
            if (node.parent != null) {
                if (node == node.parent.leftChild) {
                    node.parent.leftChild = replaceNode;
                } else {
                    node.parent.rightChild = replaceNode;
                }
            }
            replaceNode.leftChild = node.leftChild;
            replaceNode.rightChild = node.rightChild;
            if (node.leftChild != null) {
                node.leftChild.parent = replaceNode;
            }
            if (node.rightChild != null) {
                node.rightChild.parent=replaceNode;
            }
            replaceNode.parent = node.parent;
        }

    }

    @Override
    public String toString() {
        return this.root.toString();
    }

    private AVLNode rotateRight(AVLNode node) {
        if (node == root) {
            root = node.leftChild;
        }
        node.rightRotation();
        return node.parent;
    }

    private AVLNode rotateLeft(AVLNode node) {
        if (node == root) {
            root = node.rightChild;
        }
        node.leftRotation();
        return node.parent;
    }

    public AVLNode search(int key) {
        AVLNode curr = root;
        while (curr != null) {
            if (curr.key == key) {
                return curr;
            } else if (curr.key < key){
                curr = curr.rightChild;
            } else {
                curr = curr.leftChild;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        tree.insert(new AVLNode(22));
        tree.insert(new AVLNode(3));
        tree.insert(new AVLNode(4));
        tree.insert(new AVLNode(15));
        tree.insert(new AVLNode(25));
        tree.insert(new AVLNode(0));
        tree.insert(new AVLNode(8));
        tree.insert(new AVLNode(29));
        tree.insert(new AVLNode(19));
        tree.insert(new AVLNode(12));
        tree.insert(new AVLNode(7));
        tree.insert(new AVLNode(11));
        System.out.println(tree);
        AVLNode node = tree.search(19);
        if (node != null) {
            tree.delete(node);
        }
        System.out.println(tree);
    }
}
