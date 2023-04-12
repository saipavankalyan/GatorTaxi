public class GatorHeap {
    // Storing the heap as a 1D array
    HeapNode[] heapArray;
    // number of nodes in the heap
    int elementCount;

    // Constructor
    public GatorHeap() {
        heapArray = new HeapNode[2000];
        elementCount = 0;
    }

    // Swap 2 nodes in the heap
    void swap(int a_index, int b_index, HeapNode[] heapArr) {
        HeapNode temp = heapArr[b_index];
        heapArr[b_index] = heapArr[a_index];
        heapArr[a_index] = temp;
    }

    // Check if the parent is smaller than child. Otherwise swap
    void heapifyUp(int parent) {
        // parent is the index of the parent node

        int left = 2 * parent + 1;
        int right = 2 * parent + 2;

        // no children, no change
        if (left >= elementCount && right >= elementCount)
            return;

        // only left child exist
        if (left < elementCount && right >= elementCount) {
            // Check if it voilates the min heap
            if (heapArray[parent].compareTo(heapArray[left]) > 0) {

                swap(parent, left, heapArray);

                heapArray[parent].index = parent;
                heapArray[left].index = left;

                // If parent is not root, propogate up
                if (parent != 0)
                    heapifyUp((parent - 1) / 2);
            }
        }
        // If parent is larger than any of the children, swap it with the smallest child
        else if ((heapArray[parent].compareTo(heapArray[left]) > 0)
                || (heapArray[parent].compareTo(heapArray[right]) > 0)) {

            if (heapArray[left].compareTo(heapArray[right]) < 0) {
                swap(left, parent, heapArray);

                heapArray[parent].index = parent;
                heapArray[left].index = left;
            }

            else {
                swap(right, parent, heapArray);

                heapArray[parent].index = parent;
                heapArray[right].index = right;
            }

            // If parent is not root, propogate up
            if (parent != 0)
                heapifyUp((parent - 1) / 2);
        }
    }

    void insert(HeapNode node) {
        // Add new node to the last of heap
        heapArray[elementCount] = node;
        heapArray[elementCount].index = elementCount;

        int parent = (elementCount - 1) / 2;

        // Increase the number of nodes in heap as new node is inserted
        elementCount += 1;

        // Check the new node satisfy min heap
        if (elementCount > 1)
            heapifyUp(parent);
    }

    void heapifyDown(int parent) {

        int left = 2 * parent + 1;
        int right = 2 * parent + 2;

        // no child, no change to heap
        if (left >= elementCount && right >= elementCount)
            return;

        // only left child
        if (left < elementCount && right >= elementCount) {
            // check if the left child voilates and swap if true
            if (heapArray[parent].compareTo(heapArray[left]) > 0) {
                swap(parent, left, heapArray);

                heapArray[parent].index = parent;
                heapArray[left].index = left;

                return;
            }
        }

        // both left and right child
        // if both the children satsify the minheap, no change to heap
        if ((heapArray[parent].compareTo(heapArray[left]) <= 0) && (heapArray[parent].compareTo(heapArray[right]) <= 0))
            return;

        // left child is less than right, so swap parent with left child
        else if (heapArray[left].compareTo(heapArray[right]) < 0) {
            swap(left, parent, heapArray);

            heapArray[left].index = left;
            heapArray[parent].index = parent;

            // propogate down
            heapifyDown(left);
        }
        // right child is less than left, so swap parent with right child
        else {
            swap(right, parent, heapArray);

            heapArray[right].index = right;
            heapArray[parent].index = parent;

            // propogate down
            heapifyDown(right);
        }
    }

    HeapNode deleteMinimum() {
        // no elements, nothing to do
        if (elementCount == 0)
            return null;

        // Minimum will be the root node.
        HeapNode min = heapArray[0];

        // If children exist to the root
        if (elementCount > 1) {
            // replace root with last leaf node
            heapArray[0] = heapArray[elementCount - 1];

            heapArray[0].index = 0;
            // reduce the number of nodes in the heap
            elementCount--;

            // check min heap conditions downwards
            heapifyDown(0);
        }

        // only root
        else
            elementCount--;

        return min;
    }

    void updateHeapNode(int index, int newDuration) throws UnsupportedDurationRaiseEx {
        HeapNode node = heapArray[index];

        // update trip duration
        if (newDuration < node.tripDuration) {
            node.tripDuration = newDuration;

            if (index > 0) {
                int parent = (index - 1) / 2;

                heapifyUp(parent);
            }
        }

        // Add penality of 10 in cost if the new duration is more than old duration but
        // less than twice of old duration
        else if ((node.tripDuration < newDuration) && (newDuration <= 2 * node.tripDuration)) {
            node.rideCost += 10;
            node.tripDuration = newDuration;

            // check min heap conditions downward
            heapifyDown(index);
        }

        // New duration is more than twice of old, cancel the ride
        else {
            throw new UnsupportedDurationRaiseEx();
        }
    }

    HeapNode delete(HeapNode node) {
        // If the node is last leaf node
        if (node.index == elementCount - 1) {
            elementCount--;
        }

        else {
            heapArray[node.index] = heapArray[elementCount - 1];
            heapArray[node.index].index = node.index;
            // reduce the number of nodes in heap
            elementCount--;
            // check min heap conditions downwards
            heapifyDown(node.index);
        }

        return node;
    }
}