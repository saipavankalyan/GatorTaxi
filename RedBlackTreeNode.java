public class RedBlackTreeNode implements Comparable<RedBlackTreeNode> {
    int rideNumber;
    int rideCost;
    int tripDuration;
    boolean isRed; // true if the color of the node is red, false otherwise
    boolean isDblack; // true, if the current and parent nodes are both black, false otherwise
    RedBlackTreeNode parent;
    RedBlackTreeNode left;
    RedBlackTreeNode right;
    HeapNode heapNodePtr; // pointer to the corresponding node in the min heap

    // Default Constructor
    public RedBlackTreeNode() {

    }

    // Parametrised constructor
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

    // to print the object in the below format
    @Override
    public String toString() {
        return String.format("Ride - [No: %d Cost: %d Duration: %d]", rideNumber, rideCost, tripDuration);
    }

    // check if the node is left child
    boolean isLeftChild() {
        return this.parent != null && this == this.parent.left;
    }

    // to compare objects based on rideNumber
    @Override
    public int compareTo(RedBlackTreeNode rbt) {
        return this.rideNumber - rbt.rideNumber;
    }

}