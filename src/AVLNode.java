public class AVLNode {
    AVLNode parent;
    AVLNode leftChild;
    AVLNode rightChild;
    int balanceFactor;
    int key;

    AVLNode(int key) {
        this.key = key;
        this.balanceFactor = 0;
    }

    void rightRotation() {
        if (this.leftChild != null) {
            int a = this.balanceFactor;
            int b = this.leftChild.balanceFactor;
            if (this.leftChild.balanceFactor >= 0) {
                this.balanceFactor++;
            } else {
                this.balanceFactor = this.balanceFactor - this.leftChild.balanceFactor + 1;
            }
            this.leftChild.balanceFactor = Math.max(a + 2, b + 1);

            AVLNode left = this.leftChild;
            this.leftChild = left.rightChild;
            if (left.rightChild != null) {
                left.rightChild.parent = this;
            }
            if (this.parent != null) {
                if (this.parent.leftChild == this) {
                    this.parent.leftChild = left;
                } else {
                    this.parent.rightChild = left;
                }
            }
            left.rightChild = this;
            left.parent = this.parent;
            this.parent = left;
        }
    }

    void leftRotation() {
        if (this.rightChild != null) {
            int a = this.balanceFactor;
            int b = this.rightChild.balanceFactor;
            if (this.rightChild.balanceFactor <= 0) {
                this.balanceFactor--;
            } else {
                this.balanceFactor = this.balanceFactor - this.rightChild.balanceFactor - 1;
            }
            this.rightChild.balanceFactor = Math.min(a - 2, b - 1);

            AVLNode right = this.rightChild;
            this.rightChild = right.leftChild;
            if (right.leftChild != null) {
                right.leftChild.parent = this;
            }
            right.leftChild = this;
            if (this.parent != null) {
                if (this.parent.leftChild == this) {
                    this.parent.leftChild = right;
                } else {
                    this.parent.rightChild = right;
                }
            }

            right.parent = this.parent;
            this.parent = right;
        }
    }

    @Override
    public String toString() {
        return this.toStringHelper(0);
    }

    private String toStringHelper(int indent) {
        StringBuilder str = new StringBuilder();

        if (this.rightChild != null) {
            str.append(rightChild.toStringHelper(indent + 2));
        }

        for (int i = 0; i < indent; i ++) {
            str.append("  ");
        }
        str.append(key);
        str.append('\n');

        if (this.leftChild != null) {
            str.append(leftChild.toStringHelper(indent + 2));
        }
        return str.toString();
    }
}
