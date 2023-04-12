public class HeapNode implements Comparable<HeapNode> {
    int rideNumber;
    int rideCost;
    int tripDuration;
    int index; // Position of the node in the array
    RedBlackTreeNode rBTPtr; // pointer to the corresponding node in redblack tree

    // Default constructor
    public HeapNode() {

    }

    // Parametrised constructor
    public HeapNode(int rideNumber, int rideCost, int tripDuration) {
        this.rideNumber = rideNumber;
        this.rideCost = rideCost;
        this.tripDuration = tripDuration;
        index = -1;
        rBTPtr = null;
    }

    // to compare objects based on rideCost and secondarily tripDuration
    @Override
    public int compareTo(HeapNode hn) {
        if (this.rideCost == hn.rideCost)
            return (this.tripDuration - hn.tripDuration);
        else
            return (this.rideCost - hn.rideCost);
    }

    // to print the object in the below format
    @Override
    public String toString() {
        return "Ride - [No: " + rideNumber + " Cost: " + rideCost + " Duration: " + tripDuration + "]";
    }
}
