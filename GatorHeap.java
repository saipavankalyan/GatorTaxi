public class GatorHeap {
    HeapNode[] heapArray;
    int elementCount;

    public GatorHeap() {
        heapArray = new HeapNode[2000];
        elementCount = 0;
    }

    void swap(HeapNode a, HeapNode b) {
        HeapNode temp;
        temp = a;
        a = b;
        b = temp;
    }

    void heapifyUpward(int parent) {
        // parent is the index of the parent node

        int left = 2 * parent + 1;
        int right = 2 * parent + 2;

        if (left >= elementCount && right >= elementCount)
            return;

        if (left < elementCount && right >= elementCount) {
            if (heapArray[parent].compareTo(heapArray[left]) > 0) {

                swap(heapArray[parent], heapArray[left]);

                heapArray[parent].index = parent;
                heapArray[left].index = left;

                if (parent != 0)
                    heapifyUpward((int) Math.floor((parent - 1) / 2));

            }

            else if ((heapArray[parent].compareTo(heapArray[left]) > 0)
                    || (heapArray[parent].compareTo(heapArray[right]) > 0)) {

                if (heapArray[left].compareTo(heapArray[right]) < 0) {
                    swap(heapArray[left], heapArray[parent]);

                    heapArray[parent].index = parent;
                    heapArray[left].index = left;
                }

                else {
                    swap(heapArray[right], heapArray[parent]);

                    heapArray[parent].index = parent;
                    heapArray[right].index = right;
                }

                if (parent != 0)
                    heapifyUpward((int) Math.floor((parent - 1) / 2));
            }

            else
                return;
        }
    }

    void insert(HeapNode ele) {
        heapArray[elementCount] = ele;
        heapArray[elementCount].index = elementCount;

        int parent = (int) Math.floor((elementCount - 1) / 2);

        elementCount += 1;

        if (elementCount > 1)
            heapifyUpward(parent);
    }

    void heapifyDownward(int parent) {
        int left = 2 * parent + 1;
        int right = 2 * parent + 2;

        // no child
        if (left >= elementCount && right >= elementCount)
            return;

        // only left child
        if (left < elementCount && right >= elementCount) {
            if (heapArray[parent].compareTo(heapArray[left]) > 0) {
                swap(heapArray[parent], heapArray[left]);

                heapArray[parent].index = parent;
                heapArray[left].index = left;

                return;
            }
        }

        // both left and right child

        if ((heapArray[parent].compareTo(heapArray[left]) <= 0) && (heapArray[parent].compareTo(heapArray[right]) <= 0))
            return;

        else if (heapArray[left].compareTo(heapArray[right]) < 0) {
            swap(heapArray[parent], heapArray[left]);

            heapArray[left].index = left;
            heapArray[parent].index = parent;

            heapifyDownward(left);
        }

        else {
            swap(heapArray[parent], heapArray[right]);

            heapArray[left].index = right;
            heapArray[parent].index = parent;

            heapifyDownward(right);
        }
    }

    HeapNode deleteMin() {
        // no elements
        if (elementCount == 0)
            return null;

        HeapNode min = heapArray[0];

        if (elementCount > 1) {
            // replace root with last leaf node
            heapArray[0] = heapArray[elementCount - 1];

            heapArray[0].index = 0;
            elementCount--;

            heapifyDownward(0);
        }

        // only one element
        else
            elementCount--;

        return min;
    }

    void updateNode(int newIndex, int newDuration) throws UnsupportedDurationRaiseEx {
        HeapNode node = heapArray[newIndex];

        if (newDuration < node.tripDuration) {
            node.tripDuration = newDuration;

            if (newIndex > 0) {
                int parent = (int) Math.floor((newIndex - 1) / 2);

                heapifyUpward(parent);
            }
        }

        else if ((node.tripDuration < newDuration) && (newDuration <= 2 * node.tripDuration)) {
            node.rideCost += 10;
            node.tripDuration = newDuration;

            heapifyDownward(newIndex);
        }

        else {
            throw new UnsupportedDurationRaiseEx();
        }
    }

    HeapNode delete(HeapNode node) {
        if (node.index == elementCount - 1) {
            elementCount--;
        }

        else {
            heapArray[node.index] = heapArray[elementCount - 1];
            heapArray[node.index].index = node.index;
            elementCount--;
            heapifyDownward(node.index);
        }

        return node;
    }
}