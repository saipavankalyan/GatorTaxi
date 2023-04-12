public class RedBlackTreeNode implements Comparable<RedBlackTreeNode> {
    int rideNumber;
    int rideCost;
    int tripDuration;
    boolean isRed;
    boolean isDblack;
    RedBlackTreeNode parent;
    RedBlackTreeNode left;
    RedBlackTreeNode right;
    HeapNode heapNodePtr;

    public RedBlackTreeNode() {

    }

    public RedBlackTreeNode(int rideNumber, int rideCost, int tripDuration) {
        this.rideNumber = rideNumber;
        this.rideCost = rideCost;
        this.tripDuration = tripDuration;
        isRed = true;
        isDblack = false;
        parent = null;
        left = null;
        right = null;
        heapNodePtr = null;
    }

    @Override
    public String toString() {
        return String.format("Ride - [No: %d Cost: %d Duration: %d]", rideNumber, rideCost, tripDuration);
    }

    boolean isLeftChild() {
        return this.parent != null && this == this.parent.left;
    }

    @Override
    public int compareTo(RedBlackTreeNode rbt) {
        return this.rideNumber - rbt.rideNumber;
    }

}