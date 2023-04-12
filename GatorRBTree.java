import java.util.ArrayList;
import java.util.List;

public class GatorRBTree {
    RedBlackTreeNode root;

    public GatorRBTree() {
        root = null;
    }

    RedBlackTreeNode insertPreBalancing(RedBlackTreeNode root, RedBlackTreeNode newNode) {
        if (root == null)
            return newNode;

        if (newNode.compareTo(root) < 0) {
            root.left = insertPreBalancing(root.left, newNode);
            root.left.parent = root;
        }

        else {
            root.right = insertPreBalancing(root.right, newNode);
            root.right.parent = root;
        }

        return root;
    }

    void handleColorChange(RedBlackTreeNode grandParent, RedBlackTreeNode uncle, RedBlackTreeNode parent) {
        grandParent.isRed = !grandParent.isRed;
        uncle.isRed = !uncle.isRed;
        parent.isRed = !parent.isRed;

        handleInsertWithBalance(grandParent);
    }

    void rotateLeft(RedBlackTreeNode node) {
        RedBlackTreeNode right = node.right;
        node.right = right.left;

        if (node.right != null) {
            node.right.parent = node;
        }

        right.left = node;
        RedBlackTreeNode parent = node.parent;
        right.parent = parent;

        if (node == root)
            root = right;
        else if (node.isLeftChild())
            parent.left = right;
        else
            parent.right = right;

        node.parent = right;
    }

    void rotateRight(RedBlackTreeNode node) {
        RedBlackTreeNode left = node.left;
        node.left = left.right;

        if (node.left != null)
            node.left.parent = node;

        left.right = node;
        RedBlackTreeNode parent = node.parent;
        left.parent = parent;

        if (parent == null)
            root = left;
        else if (node.isLeftChild())
            parent.left = left;
        else
            parent.right = left;

        node.parent = left;
    }

    void balanceLeftX(RedBlackTreeNode node, RedBlackTreeNode parent, RedBlackTreeNode grandParent) {
        if (!node.isLeftChild())
            rotateLeft(parent);

        parent.isRed = !parent.isRed;
        grandParent.isRed = !grandParent.isRed;

        rotateRight(grandParent);

        if (node.isLeftChild())
            handleInsertWithBalance(parent);
        else
            handleInsertWithBalance(grandParent);
    }

    void balanceRightX(RedBlackTreeNode node, RedBlackTreeNode parent, RedBlackTreeNode grandParent) {
        if (node.isLeftChild())
            rotateRight(parent);

        parent.isRed = !parent.isRed;
        grandParent.isRed = !grandParent.isRed;

        rotateLeft(grandParent);

        if (!node.isLeftChild())
            handleInsertWithBalance(parent);
        else
            handleInsertWithBalance(grandParent);
    }

    void handleInsertWithBalance(RedBlackTreeNode node) {
        if (node == root)
            root.isRed = false;

        else {
            RedBlackTreeNode parent = node.parent;

            if (!parent.isRed) {
                root.isRed = false;
            }

            else {
                RedBlackTreeNode grandParent = parent.parent;

                RedBlackTreeNode uncle;
                if (parent.isLeftChild())
                    uncle = grandParent.right;
                else
                    uncle = grandParent.left;

                if (uncle != null && uncle.isRed)
                    handleColorChange(grandParent, uncle, parent);

                else if (parent.isLeftChild())
                    balanceLeftX(node, parent, grandParent);

                else if (!parent.isLeftChild())
                    balanceRightX(node, parent, grandParent);

                root.isRed = false;
            }
        }
    }

    RedBlackTreeNode insert(RedBlackTreeNode node) {
        root = insertPreBalancing(root, node);

        handleInsertWithBalance(node);

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

    void getValuesInRangeRecursive(RedBlackTreeNode node, int low, int high, List<RedBlackTreeNode> acc) {
        if (node == null)
            return;

        if (low <= node.rideNumber)
            getValuesInRangeRecursive(node.left, low, high, acc);

        if (node.rideNumber >= low && node.rideNumber <= high)
            acc.add(node);

        else if (node.rideNumber > high)
            return;

        getValuesInRangeRecursive(node.right, low, high, acc);
    }

    List<RedBlackTreeNode> getValuesInRange(int low, int high) {
        List<RedBlackTreeNode> acc = new ArrayList<>();

        getValuesInRangeRecursive(root, low, high, acc);

        return acc;
    }

    RedBlackTreeNode findMinimum(RedBlackTreeNode node) {
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

    void handleDbAfterDeletion(RedBlackTreeNode node) {
        if (node.parent == null) {
            node.isDblack = false;
            return;
        }

        RedBlackTreeNode parent = node.parent;
        RedBlackTreeNode sibling = getSibling(node);

        boolean temp;

        boolean isSibLeftChildRed;
        boolean isSibRightChildRed;

        if (sibling.left != null)
            isSibLeftChildRed = sibling.left.isRed;
        else
            isSibLeftChildRed = false;

        if (sibling.right != null)
            isSibRightChildRed = sibling.right.isRed;
        else
            isSibRightChildRed = false;

        boolean nearSibChildRed;
        boolean farSibChildRed;

        nearSibChildRed = node.isLeftChild() ? isSibLeftChildRed : isSibRightChildRed;
        farSibChildRed = node.isLeftChild() ? isSibRightChildRed : isSibLeftChildRed;

        if ((!sibling.isRed) && (!isSibLeftChildRed) && (!isSibRightChildRed)) {
            node.isDblack = false;

            if (parent.isRed)
                parent.isRed = false;
            else
                parent.isDblack = true;

            sibling.isRed = true;

            if (parent.isDblack)
                handleDbAfterDeletion(parent);
        }

        else if (sibling.isRed) {
            temp = parent.isRed;
            parent.isRed = sibling.isRed;
            sibling.isRed = temp;

            if (node.isLeftChild())
                rotateLeft(parent);
            else
                rotateRight(parent);

            handleDbAfterDeletion(node);
        }

        else if (nearSibChildRed && !sibling.isRed) {
            RedBlackTreeNode nearChild = node.isLeftChild() ? sibling.left : sibling.right;

            temp = sibling.isRed;
            sibling.isRed = nearChild.isRed;
            nearChild.isRed = temp;

            if (node.isLeftChild())
                rotateRight(sibling);
            else
                rotateLeft(sibling);

            handleDbAfterDeletion(node);
        }

        else if (farSibChildRed && !sibling.isRed) {
            RedBlackTreeNode farthestChild = node.isLeftChild() ? sibling.right : sibling.left;

            temp = parent.isRed;
            parent.isRed = sibling.isRed;
            sibling.isRed = temp;

            if (node.isLeftChild())
                rotateLeft(parent);
            else
                rotateRight(parent);

            node.isDblack = false;

            farthestChild.isRed = false;
        }
    }

    void deleteNode(RedBlackTreeNode node) {
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
            RedBlackTreeNode successor = findMinimum(node.right);

            GatorUtils.assignRBTNode(node, successor);

            delete(successor);
        }

        else if (node.left != null || node.right != null) {
            RedBlackTreeNode successor = node.left != null ? node.left : node.right;

            GatorUtils.assignRBTNode(node, successor);

            delete(successor);
        }

        else {
            if (node.isRed) {
                deleteNode(node);
            } else {
                node.isDblack = true;
                handleDbAfterDeletion(node);
                deleteNode(node);
            }
        }
    }
}