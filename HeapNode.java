public class HeapNode implements Comparable<HeapNode> {
    int rideNumber;
    int rideCost;
    int tripDuration;
    int index;
    RedBlackTreeNode rBTPtr;

    public HeapNode(){

    }
    
    public HeapNode(int rideNumber, int rideCost, int tripDuration){
        this.rideNumber = rideNumber;
        this.rideCost = rideCost;
        this.tripDuration = tripDuration;
        index = -1;
        rBTPtr = null;
    }

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
