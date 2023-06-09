public class GatorUtils {
    // Used to copy RedBlack tree node
    public static void assignRBTNode(RedBlackTreeNode dup, RedBlackTreeNode orig) {
        dup.rideCost = orig.rideCost;
        dup.rideNumber = orig.rideNumber;
        dup.tripDuration = orig.tripDuration;
        dup.heapNodePtr = orig.heapNodePtr;

        if (dup.heapNodePtr != null)
            dup.heapNodePtr.rBTPtr = dup;
    }
}