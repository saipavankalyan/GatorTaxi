public class GatorHeap {
    HeapNode[] heapArray;
    int elementCount;

    public GatorHeap() {
        heapArray = new HeapNode[2000];
        elementCount = 0;
    }

    void swap(int a_index, int b_index, HeapNode[] heapArr) {
        HeapNode temp = heapArr[b_index];
        heapArr[b_index] = heapArr[a_index];
        heapArr[a_index] = temp;
    }

    void heapifyUp(int parent) {
        // parent is the index of the parent node

        int left = 2 * parent + 1;
        int right = 2 * parent + 2;

        if (left >= elementCount && right >= elementCount)
            return;

        if (left < elementCount && right >= elementCount) {
            if (heapArray[parent].compareTo(heapArray[left]) > 0) {

                swap(parent, left, heapArray);

                heapArray[parent].index = parent;
                heapArray[left].index = left;

                if (parent != 0)
                    heapifyUp((parent - 1) / 2);
            }
        } else if ((heapArray[parent].compareTo(heapArray[left]) > 0)
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

            if (parent != 0)
                heapifyUp((parent - 1) / 2);
        }
    }

    void insert(HeapNode ele) {
        heapArray[elementCount] = ele;
        heapArray[elementCount].index = elementCount;

        int parent = (int) Math.floor((elementCount - 1) / 2);

        elementCount += 1;

        if (elementCount > 1)
            heapifyUp(parent);
    }

    void heapifyDown(int parent) {

        int left = 2 * parent + 1;
        int right = 2 * parent + 2;

        // no child
        if (left >= elementCount && right >= elementCount)
            return;

        // only left child
        if (left < elementCount && right >= elementCount) {
            if (heapArray[parent].compareTo(heapArray[left]) > 0) {
                swap(parent, left, heapArray);

                heapArray[parent].index = parent;
                heapArray[left].index = left;

                return;
            }
        }

        // both left and right child

        if ((heapArray[parent].compareTo(heapArray[left]) <= 0) && (heapArray[parent].compareTo(heapArray[right]) <= 0))
            return;

        else if (heapArray[left].compareTo(heapArray[right]) < 0) {
            swap(left, parent, heapArray);
            ;

            heapArray[left].index = left;
            heapArray[parent].index = parent;

            heapifyDown(left);
        }

        else {
            swap(right, parent, heapArray);

            heapArray[right].index = right;
            heapArray[parent].index = parent;

            heapifyDown(right);
        }
    }

    HeapNode deleteMinimum() {
        // no elements
        if (elementCount == 0)
            return null;

        HeapNode min = heapArray[0];

        if (elementCount > 1) {
            // replace root with last leaf node
            heapArray[0] = heapArray[elementCount - 1];

            heapArray[0].index = 0;
            elementCount--;

            heapifyDown(0);
        }

        // only one element
        else
            elementCount--;

        return min;
    }

    void updateHeapNode(int index, int newDuration) throws UnsupportedDurationRaiseEx {
        HeapNode node = heapArray[index];

        if (newDuration < node.tripDuration) {
            node.tripDuration = newDuration;

            if (index > 0) {
                int parent = (index - 1) / 2;

                heapifyUp(parent);
            }
        }

        else if ((node.tripDuration < newDuration) && (newDuration <= 2 * node.tripDuration)) {
            node.rideCost += 10;
            node.tripDuration = newDuration;

            heapifyDown(index);
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
            heapifyDown(node.index);
        }

        return node;
    }
}