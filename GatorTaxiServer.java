import java.util.List;

public class GatorTaxiServer {
    GatorHeap minHeap;
    GatorRBTree rbTree;

    // Constructor
    public GatorTaxiServer() {
        minHeap = new GatorHeap();
        rbTree = new GatorRBTree();
    }

    // return true if successfully inserted and false if node already exists
    boolean insert(int rideNumber, int rideCost, int tripDuration) {

        RedBlackTreeNode node = rbTree.search(rideNumber);

        // Node is already available in the RedBlack tree we won't be able to insert and
        // should abort. So, return false
        if (node != null) {
            return false;
        }

        HeapNode hNode = new HeapNode(rideNumber, rideCost, tripDuration);
        RedBlackTreeNode rNode = new RedBlackTreeNode(rideNumber, rideCost, tripDuration);

        minHeap.insert(hNode);
        rbTree.insert(rNode);

        hNode.rBTPtr = rNode;
        rNode.heapNodePtr = hNode;

        // Successfully able to insert node in the Redblack tree
        return true;
    }

    // returns the node which has the least rideCost and deletes it
    HeapNode getNextRide() {
        HeapNode hNode = minHeap.deleteMinimum();

        // node was not available in heap. Nothing to do.
        if (hNode == null) {
            return null;
        } else {
            RedBlackTreeNode rNode = hNode.rBTPtr;

            // delete the corresponding node in RB tree
            rbTree.delete(rNode);

            return hNode;
        }
    }

    RedBlackTreeNode print(int rideNumber) {
        RedBlackTreeNode rNode = rbTree.search(rideNumber);

        // If node exist
        if (rNode != null)
            return rNode;
        else
            // If node doesn't exist, print(0,0,0)
            return new RedBlackTreeNode(0, 0, 0);
    }

    // print all the rides whose rideNumber is within the given range
    List<RedBlackTreeNode> print(int low, int high) {
        return rbTree.getValuesInRange(low, high);
    }

    void updateTrip(int rideNumber, int updatedTripDuration) {
        RedBlackTreeNode rNode = rbTree.search(rideNumber);

        // If nodes is not present, nothing to do.
        if (rNode == null)
            return;

        HeapNode hNode = rNode.heapNodePtr;
        int hIndex = hNode.index;

        try {
            // update corresponding heap node
            minHeap.updateHeapNode(hIndex, updatedTripDuration);
            rNode.rideCost = hNode.rideCost;
            rNode.tripDuration = hNode.tripDuration;
        } catch (UnsupportedDurationRaiseEx e) {
            // If the new trip duration is greater than twice of the old duration, cancel
            // the ride
            cancelRide(rideNumber);
        }
    }

    void cancelRide(int rideNumber) {
        RedBlackTreeNode rNode = rbTree.search(rideNumber);

        if (rNode != null) {
            HeapNode hNode = rNode.heapNodePtr;
            // delete the node in both the data structures
            minHeap.delete(hNode);
            rbTree.delete(rNode);
        }
    }
}