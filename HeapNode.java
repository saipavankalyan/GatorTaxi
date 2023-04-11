public class HeapNode implements Comparable<HeapNode> {
    int rideNumber;
    int rideCost;
    int tripDuration;
    int index;
    RedBlackTreeNode rBTPtr;

    @Override
    public int compareTo(HeapNode hn) {
        if (this.rideCost == hn.rideCost)
            return this.tripDuration - hn.tripDuration;
        else
            return this.rideCost - hn.rideCost;
    }

    @Override
    public String toString() {
        return "Ride - [No: " + rideNumber + " Cost: " + rideCost + " Duration: " + tripDuration + "]";
    }
}
