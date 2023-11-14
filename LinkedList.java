public class LinkedList<T extends Comparable<T>> implements List<T> {
    private boolean isSorted;
    int size;
    private Node<T> head;
    private Node<T> tail;

    public LinkedList() {
        //initialize list to be empty
        head = null;
        tail = null;
        size = 0;
        isSorted = true;
    }
    public boolean add(T element) { 
        boolean isAdded = false;
        if (element == null) {
            isAdded = false;
        }
        //list is empty
        else if (size == 0) {
            Node<T> n = new Node(element, null);
            head = n;
            tail = n;
            size++;
            isAdded = true;
        }
        else {
            Node<T> n = new Node(element, null);
            if (element.compareTo(tail.getData()) < 0) {
                isSorted = false;
            }
            tail.setNext(n);
            tail = n;
            size++;
            isAdded = true;

        }

        return isAdded;
    }
    public boolean add(int index, T element) { 
        boolean isAdded = false;
        //adding to an empty list
        if (head == null) {
            Node<T> newNode = new Node(element, null);
            head = newNode;
            tail = newNode;
            size++;
            isAdded = true;
        }
        //adding to the front of the list
        else if (index == 0) {
            Node<T> newNode = new Node(element, head);
            if (element.compareTo(head.getData()) > 0) {
                isSorted = false;
            }
            head = newNode;
            size++;
            isAdded = true;
        }
        //adding to the end of the list
        else if (index == size - 1) {
            Node <T> newNode = new Node(element, null);
            if(element.compareTo(tail.getData()) < 0) {
                isSorted = false;
            }
            tail.setNext(newNode);
            tail = newNode;
            size++;
            isAdded = true;
        }
        //adding to the middle of the list
        else {
            int i = 1;
            Node<T> prevNode = head;
            Node<T> currNode = head.getNext();
            while (currNode != null && currNode.getNext() != null && i != index) {
                currNode = currNode.getNext();
                prevNode = prevNode.getNext();
                i++;
            }
            if (currNode == null || i != index) { //failure
                isAdded = false;
            }
            else {
                Node<T> newNext = prevNode.getNext();
                Node<T> newNode = new Node(element, newNext);
                prevNode.setNext(newNode);
                if(element.compareTo(prevNode.getData()) < 0 || element.compareTo(newNext.getData()) > 0) {
                    isSorted = false;
                }
                size++;
                isAdded = true;
            }
        }
        return isAdded;
    }
    public void clear() {
        head = null;
        tail = null;
        size = 0;
        isSorted = true;
    }
    public T get(int index) {
        Node<T> currNode = head;
        int currIndex = 0;
        if(index < 0 || index >= size || currNode == null) {
            return null;
        }
        else {
            while(currNode.getNext() != null && currIndex != index) {
                currNode = currNode.getNext();
                currIndex++;
            }
        }
        return currNode.getData();
    }
    public int indexOf(T element) { //Handle Sorted Case Efficiency
        Node<T> currNode = head;
        int i = 0;
        if (currNode == null) {
            return -1;
        }
        while(currNode.getNext() != null &&  currNode.getData() != element && i < size) {
            currNode = currNode.getNext();
            i++;
        }
        if(currNode.getData() != element) {
            return -1;
        }
        else {
            return i;
        }
    }
    public boolean isEmpty() {
        if(head == null && tail == null && size == 0) {
            return true;
        }
        else {
            return false;
        }
    }
    public int size() {
        return size;
    }
    public void sort() { // Uses Insertion Sort
        if (!isSorted) {
            if (size > 1) { //Sorts only is size > 1 and list not sorted

                // Loops through list
                Node<T> currNode = head;
                while (currNode.getNext() != null) {
                    Node<T> nextNode = currNode.getNext();
                    if (nextNode.getData().compareTo(currNode.getData()) < 0) { // If next is smaller than the current node
                        Node<T> tempPrev = null;
                        Node<T> tempCurr = head;
                        boolean isSwapped = false;
                        while (tempCurr != nextNode && isSwapped == false) {
                            if (tempCurr.getData().compareTo(nextNode.getData()) > 0) { // if the previous node is greater than next node, then swap
                                // Switch pointers
                                if (nextNode.getNext() != null) {
                                    currNode.setNext(nextNode.getNext());
                                } else {
                                    currNode.setNext(null);
                                }
                                nextNode.setNext(tempCurr);
                                tempPrev = tempCurr;

                                isSwapped = true;
                            }
                            //Move to next nodes
                            tempPrev = tempCurr;
                            tempCurr = tempCurr.getNext();
                        }


                    } else {
                        currNode = currNode.getNext();
                    }
                }
            }
            isSorted = true;
        }
    }

    public T remove(int index) {
        Node<T> prevNode = head;
        Node<T> currNode = head.getNext();
        int i = 1;
        if(prevNode == null || index < 0 || index >= size) { //empty list or out-of-bounds
            return null;
        }
        else if (index == 0 && prevNode != null) { //removing first node
            size--;
            T dataRemoved = head.getData();
            head = head.getNext();
            return dataRemoved;
        }
        else { //removing from middle of list
            while (currNode != null && currNode.getNext() != null && i != index) {
                currNode = currNode.getNext();
                prevNode = prevNode.getNext();
                i++;
            }
        }
       if (currNode == null || i != index) { //failure
           return null;
       }
       else { // success
           Node<T> newNext = currNode.getNext();
           prevNode.setNext(newNext);
           if (prevNode.getData().compareTo(newNext.getData()) > 0) {
               isSorted = false;
           }
           size--;
           return currNode.getData();
       }
    }
    public void equalTo(T element) {
        if (head != null) { //non-empty list
            Node <T> prevNode = null;
            Node<T> currNode = head;

            if (isSorted) {
                while(currNode != null && currNode.getData().compareTo(element) <= 0) { //If sorted, it loops until it reaches elements greater than target element
                    if (!currNode.getData().equals(element)) {
                        if (prevNode == null) { //at the front of the list
                            head = currNode.getNext();
                        } else {
                            prevNode.setNext(currNode.getNext());
                        }
                        currNode = currNode.getNext();
                        size--;
                    } else {
                        prevNode = currNode;
                        currNode = currNode.getNext();
                    }
                }
            }
            else { //if unsorted, it loops through the whole list
                //Traverse through list
                while (currNode != null) {
                    if (!currNode.getData().equals(element)) {
                        if (prevNode == null) { //at the front of the list
                            head = currNode.getNext();
                        } else {
                            prevNode.setNext(currNode.getNext());
                        }
                        currNode = currNode.getNext();
                        size--;
                    } else {
                        prevNode = currNode;
                        currNode = currNode.getNext();
                    }
                }
            }
        }
    }
    public void reverse() {
        Node<T> prevNode = null;
        Node<T> currNode = head;
        Node<T> nextNode = null;
        while (currNode != null) {
            nextNode = currNode.getNext();
            currNode.setNext(prevNode);
            prevNode = currNode;
            currNode = nextNode;
        }
        head = prevNode;
        //Check if list is sorted after reversing
        boolean reverseSort = true;
        if (size > 1) {
            Node<T> currNodeSort = head;
            while (currNodeSort != null) {
                if (currNode.getData().compareTo(currNodeSort.getNext().getData()) > 0) {
                    reverseSort = false;
                }
                currNodeSort = currNodeSort.getNext();
            }
        }
        isSorted = reverseSort;
    }

    public void intersect(List<T> otherList) {
        if (otherList != null) {
            LinkedList<T> other = (LinkedList<T>) otherList;
            Node<T> thisPrev = null;
            Node<T> thisCurr = head;
            Node<T> otherCurr = other.head;
            //Loop through both lists
            while(thisCurr != null) {
                otherCurr = other.head;
                while (otherCurr != null) {
                    if (thisCurr.getData().equals(otherCurr.getData())) {
                        //move pointers
                        thisPrev = thisCurr;
                        thisCurr = thisCurr.getNext();
                        otherCurr = otherCurr.getNext();
                    }
                    else {
                        if (otherCurr.getNext() == null) { // if we reached the end of the other list, remove this node
                            if (thisPrev == null) {
                                head = thisCurr.getNext();
                                thisCurr = head;
                            }
                            else {
                                thisPrev.setNext(thisCurr.getNext());
                                thisCurr = thisCurr.getNext();
                            }
                        }
                        else { // we haven't reached the end of the other list
                            otherCurr = otherCurr.getNext();
                        }
                    }
                }
            }
          if (!isSorted) {
              sort();
          }
        }
    }
    public T getMin() {
        T currMin;
        if (head == null) {
            return null;
        }
        else {
            Node<T> currNode = head;
            currMin = head.getData();
            if (isSorted) {
                return head.getData();
            } else {
                while (currNode != null) {
                    if (currNode.getData().compareTo(currMin) < 0) { //check if currNode's data is less than currMin
                        currMin = currNode.getData();
                    }
                    currNode = currNode.getNext();
                }
            }
        }
        return currMin;
    }
    public T getMax() {
        T currMax = null;
        if (head != null) {

            if (isSorted) { //optimizes this in case list is sorted
                return tail.getData();
            }

            Node <T> currNode = head;
            currMax = head.getData();
            while (currNode != null) {
                if (currNode.getData().compareTo(currMax) > 0) { //checks if currNode's data is greater than currMax
                    currMax = currNode.getData();
                }
                currNode = currNode.getNext();
            }
        }
        else {
            return null;
        }
        return currMax;
    }
    public String toString() {
        String output = "";
        Node<T> currNode = head;
        int i = 0;
        while (currNode.getNext() != null) {
            output += currNode.getData() + "\n";
            currNode = currNode.getNext();
        }
        output += currNode.getData() + "\n";
        return output;
    }
    public boolean isSorted() {
        return isSorted;
    }

}
