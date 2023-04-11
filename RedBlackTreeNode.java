public class RedBlackTreeNode implements Comparable<RedBlackTreeNode>{
    int rideNumber;
    int rideCost;
    int tripDuration;
    boolean isRed;
    boolean isDeletedBlack;
    RedBlackTreeNode parent;
    RedBlackTreeNode left;
    RedBlackTreeNode right;
    HeapNode heapNodePtr;

    @Override
    public String toString(){
        return "Ride - [No: " + rideNumber + " Cost: " + rideCost + " Duration: " + tripDuration + "]";
    }
    
    boolean isLeftChild(){
        return this.parent != null && this == this.parent.left;
    }

    @Override
    public int compareTo(RedBlackTreeNode rbt){
        return this.rideNumber - rbt.rideNumber;
    }
    



}
