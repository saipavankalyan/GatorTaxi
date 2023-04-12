import java.util.List;

public class GatorTaxiServer {
    GatorHeap minHeap;
    GatorRBTree rbTree;

    public GatorTaxiServer() {
        minHeap = new GatorHeap();
        rbTree = new GatorRBTree();
    }

    boolean insert(int rideNumber, int rideCost, int tripDuration) {

        RedBlackTreeNode node = rbTree.search(rideNumber);

        if (node != null) {
            return false;
        }

        HeapNode hNode = new HeapNode(rideNumber, rideCost, tripDuration);
        RedBlackTreeNode rNode = new RedBlackTreeNode(rideNumber, rideCost, tripDuration);

        minHeap.insert(hNode);
        rbTree.insert(rNode);

        hNode.rBTPtr = rNode;
        rNode.heapNodePtr = hNode;

        return true;
    }

    HeapNode getNextRide() {
        HeapNode hNode = minHeap.deleteMinimum();

        if (hNode == null) {
            return null;
        } else {
            RedBlackTreeNode rNode = hNode.rBTPtr;

            rbTree.delete(rNode);

            return hNode;
        }
    }

    RedBlackTreeNode print(int rideNumber) {
        RedBlackTreeNode rNode = rbTree.search(rideNumber);

        if (rNode != null)
            return rNode;
        else
            return new RedBlackTreeNode(0, 0, 0);
    }

    List<RedBlackTreeNode> print(int low, int high) {
        return rbTree.getValuesInRange(low, high);
    }

    void updateTrip(int rideNumber, int updatedTripDuration) {
        RedBlackTreeNode rNode = rbTree.search(rideNumber);

        if (rNode == null)
            return;

        HeapNode hNode = rNode.heapNodePtr;
        int hIndex = hNode.index;

        try {
            minHeap.updateHeapNode(hIndex, updatedTripDuration);
            rNode.rideCost = hNode.rideCost;
            rNode.tripDuration = hNode.tripDuration;
        } catch (UnsupportedDurationRaiseEx e) {
            cancelRide(rideNumber);
        }
    }

    void cancelRide(int rideNumber) {
        RedBlackTreeNode rNode = rbTree.search(rideNumber);

        if (rNode != null) {
            HeapNode hNode = rNode.heapNodePtr;
            minHeap.delete(hNode);
            rbTree.delete(rNode);
        }
    }
}