import java.util.ArrayList;
import java.util.List;

public class GatorRBTree {
    RedBlackTreeNode root;

    public GatorRBTree() {
        root = new RedBlackTreeNode();
    }

    RedBlackTreeNode insertUnbalanced(RedBlackTreeNode root, RedBlackTreeNode newNode) {
        if (root == null)
            return newNode;

        if (newNode.compareTo(root) < 0) {
            root.left = insertUnbalanced(root.left, newNode);
            root.left.parent = root;
        }

        else {
            root.right = insertUnbalanced(root.right, newNode);
            root.right.parent = root;
        }

        return root;
    }

    void handleimbalanceColor(RedBlackTreeNode grandParent, RedBlackTreeNode uncle, RedBlackTreeNode parent) {
        grandParent.isRed = !grandParent.isRed;
        uncle.isRed = !uncle.isRed;
        parent.isRed = !parent.isRed;

        handleInsertImbalance(grandParent);
    }

    void rotateLeft(RedBlackTreeNode node) {
        RedBlackTreeNode right = node.right;
        node.right = right.left;

        if (node.right != null) {
            node.right.parent = node;
        }

        right.left = node;
        RedBlackTreeNode nodeParent = node.parent;
        right.parent = nodeParent;

        if (node == root)
            root = right;

        else if (node.isLeftChild())
            nodeParent.left = right;

        else
            nodeParent.right = right;

        node.parent = right;
    }

    void rotateRight(RedBlackTreeNode node) {
        RedBlackTreeNode left = node.left;
        node.left = left.right;

        if (node.left != null)
            node.left.parent = node;

        left.right = node;
        RedBlackTreeNode nodeParent = node.parent;
        left.parent = nodeParent;

        if (nodeParent != null)
            root = left;
        else if (node.isLeftChild())
            nodeParent.left = left;
        else
            nodeParent.right = left;

        node.parent = left;
    }

    void handleLXb(RedBlackTreeNode node, RedBlackTreeNode parent, RedBlackTreeNode grandParent) {
        if (!node.isLeftChild())
            rotateLeft(parent);

        parent.isRed = !parent.isRed;
        grandParent.isRed = !grandParent.isRed;

        rotateRight(grandParent);

        if (node.isLeftChild())
            handleInsertImbalance(parent);
        else
            handleInsertImbalance(grandParent);
    }

    void handleRXb(RedBlackTreeNode node, RedBlackTreeNode parent, RedBlackTreeNode grandParent) {
        if (node.isLeftChild())
            rotateRight(parent);

        parent.isRed = !parent.isRed;
        grandParent.isRed = !grandParent.isRed;

        rotateLeft(grandParent);

        if (!node.isLeftChild())
            handleInsertImbalance(parent);
        else
            handleInsertImbalance(grandParent);
    }

    void handleInsertImbalance(RedBlackTreeNode node) {
        if (node == root)
            root.isRed = false;

        else {
            RedBlackTreeNode parent = node.parent;

            if (!parent.isRed) {
                root.isRed = false;
                return;
            }

            else {
                RedBlackTreeNode grandParent = parent.parent;

                RedBlackTreeNode uncle;
                if (parent.isLeftChild())
                    uncle = grandParent.right;
                else
                    uncle = grandParent.left;

                if (uncle != null && uncle.isRed)
                    handleimbalanceColor(grandParent, uncle, parent);

                else if (parent.isLeftChild())
                    handleLXb(node, parent, grandParent);

                else if (!parent.isLeftChild())
                    handleRXb(node, parent, grandParent);

                root.isRed = false;
            }
        }
    }

    RedBlackTreeNode insert(RedBlackTreeNode node) {
        root = insertUnbalanced(root, node);

        handleInsertImbalance(node);

        return node;
    }

    RedBlackTreeNode searchRecursive(RedBlackTreeNode node, int rideNumber) {
        if (node == null)
            return null;

        if (node.rideNumber == rideNumber)
            return node;

        if (node.rideNumber < rideNumber)
            return searchRecursive(node.right, rideNumber);
        else
            return searchRecursive(node.left, rideNumber);
    }

    RedBlackTreeNode search(int rideNumber) {
        return searchRecursive(root, rideNumber);
    }

    void getAllInRangeRecursive(RedBlackTreeNode node, int low, int high, List<RedBlackTreeNode> acc) {
        if (node == null)
            return;

        if (low <= node.rideNumber)
            getAllInRangeRecursive(node.left, low, high, acc);

        if (node.rideNumber >= low && node.rideNumber <= high)
            acc.add(node);

        else if (node.rideNumber > high)
            return;

        getAllInRangeRecursive(node.right, low, high, acc);
    }

    List<RedBlackTreeNode> getAllInRange(int low, int high) {
        List<RedBlackTreeNode> acc = new ArrayList<>();

        getAllInRangeRecursive(root, low, high, acc);

        return acc;
    }

    RedBlackTreeNode findMin(RedBlackTreeNode node) {
        RedBlackTreeNode curr = node;

        while (curr.left != null)
            curr = curr.left;

        return curr;
    }

    RedBlackTreeNode getSibling(RedBlackTreeNode node) {
        RedBlackTreeNode parent = node.parent;

        if (node.isLeftChild())
            return parent.right;
        else
            return parent.left;
    }

    void handleDoubleBlack(RedBlackTreeNode node) {
        if (node.parent == null) {
            if (root != node) {
                
            }

            node.isDeletedBlack = false;
            return;
        }

        RedBlackTreeNode parent = node.parent;
        RedBlackTreeNode sibling = getSibling(node);

        boolean temp;

        boolean sibLeftRed;
        boolean sibRightRed;

        if (sibling.left != null)
            sibLeftRed = sibling.left.isRed;
        else
            sibLeftRed = false;

        if (sibling.right != null)
            sibRightRed = sibling.right.isRed;
        else
            sibRightRed = false;

        boolean nearestChildRed;
        boolean farthestChildRed;

        nearestChildRed = node.isLeftChild() ? sibLeftRed : sibRightRed;
        farthestChildRed = node.isLeftChild() ? sibRightRed : sibLeftRed;

        if ((!sibling.isRed) && (!sibLeftRed) && (!sibRightRed)) {
            node.isDeletedBlack = false;

            if (parent.isRed)
                parent.isRed = false;
            else
                parent.isDeletedBlack = true;

            sibling.isRed = true;

            if (parent.isDeletedBlack)
                handleDoubleBlack(parent);
        }

        else if (sibling.isRed) {
            temp = parent.isRed;
            parent.isRed = sibling.isRed;
            sibling.isRed = temp;

            if (node.isLeftChild())
                rotateLeft(parent);
            else
                rotateRight(parent);

            handleDoubleBlack(node);
        }

        else if (nearestChildRed && !sibling.isRed) {
            RedBlackTreeNode nearChild = node.isLeftChild() ? sibling.left : sibling.right;

            temp = sibling.isRed;
            sibling.isRed = nearChild.isRed;
            nearChild.isRed = temp;

            if (node.isLeftChild())
                rotateRight(sibling);
            else
                rotateLeft(sibling);

            handleDoubleBlack(node);
        }

        else if (farthestChildRed && !sibling.isRed) {
            RedBlackTreeNode farthestChild = node.isLeftChild() ? sibling.right : sibling.left;

            temp = parent.isRed;
            parent.isRed = sibling.isRed;
            sibling.isRed = temp;

            if (node.isLeftChild())
                rotateLeft(parent);
            else
                rotateRight(parent);

            node.isDeletedBlack = false;

            farthestChild.isRed = false;
        }
    }

    void hardDelete(RedBlackTreeNode node) {
        if (node.parent == null)
            root = null;

        else {
            if (node.isLeftChild())
                node.parent.left = null;
            else
                node.parent.right = null;
        }
    }

    void delete(RedBlackTreeNode node) {

        // both children
        if (node.left != null && node.right != null) {
            RedBlackTreeNode rightMinNode = findMin(node.right);

            GatorUtils.copyNode(node, rightMinNode);

            delete(rightMinNode);
        }

        else if (node.left != null || node.right != null) {
            RedBlackTreeNode nextNode = node.left != null ? node.left : node.right;

            GatorUtils.copyNode(node, nextNode);

            delete(nextNode);
        }

        else {
            if (node.isRed)
                hardDelete(node);
            else
                node.isDeletedBlack = true;
            handleDoubleBlack(node);
            hardDelete(node);
        }
    }
}