public class GatorUtils {
    public static void copyNode(RedBlackTreeNode dup, RedBlackTreeNode orig) {
        dup.rideCost = orig.rideCost;
        dup.rideNumber = orig.rideNumber;
        dup.tripDuration = orig.tripDuration;
        dup.heapNodePtr = orig.heapNodePtr;

        if (dup.heapNodePtr != null)
            dup.heapNodePtr.rBTPtr = dup;

        assert (dup.heapNodePtr == orig.heapNodePtr);
    }
}