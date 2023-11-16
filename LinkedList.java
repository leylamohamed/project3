//Written by Leyla Mohamed (moha1594) and Cyrus Vang (vang3339)
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
    public boolean add(T element) { //adds an element to the end of the list
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
        else { //add to end of list
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
    public boolean add(int index, T element) { //adds an element to a specific index
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
    public void clear() { //clears all nodes from the list
        head = null;
        tail = null;
        size = 0;
        isSorted = true;
    }
    public T get(int index) { //returns the value of a node at a specific index
        Node<T> currNode = head;
        int currIndex = 0;
        if(index < 0 || index >= size || currNode == null) { //if index out of bounds, or list is empty
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
    public int indexOf(T element) { //returns the first index of a specific element

        Node<T> currNode = head;
        int i = 0;
        if (currNode == null) {
            return -1;
        }
        //Increased efficiency if the list is sorted
         if(isSorted) {
            while(currNode.getNext() != null && currNode.getData().compareTo(element) < 0 && i < size) {
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

        else {
            while (currNode.getNext() != null && currNode.getData() != element && i < size) {
                currNode = currNode.getNext();
                i++;
            }
            if (currNode.getData() != element) {
                return -1;
            } else {
                return i;
            }
        }
    }
    public boolean isEmpty() { //represents whether the list is empty or not
        if(head == null && tail == null && size == 0) {
            return true;
        }
        else {
            return false;
        }
    }
    public int size() { //returns the size of the list
        return size;
    }
    public void sort() { // Uses Insertion Sort to sort the list
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

    public T remove(int index) { //removes the node and returns the data at a specific index
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
            boolean removeSorted = true;
            if (head != null && head.getNext() != null) {
                Node<T> curr = head;
                while(curr.getNext() != null) {
                    if (curr.getData().compareTo(curr.getNext().getData()) > 0) {
                        removeSorted = false;
                    }
                    curr = curr.getNext();
                }

            }
            isSorted = removeSorted;
            return dataRemoved;
        }
        else { //removing from middle of list
            while (currNode != null && currNode.getNext() != null && i != index) {
                currNode = currNode.getNext();
                prevNode = prevNode.getNext();
                i++;
            }
        }
       if (currNode == null || i != index) { //failure -- index not in list
           return null;
       }
       else { // success
           Node<T> newNext = currNode.getNext();
           prevNode.setNext(newNext);

           if (newNext == null) {
               tail = prevNode;
           }

           //Checks if it's sorted
           if (newNext != null && (prevNode.getData().compareTo(newNext.getData()) > 0)) {
               isSorted = false;
           }
           size--;
           return currNode.getData();
       }
    }
    public void equalTo(T element) { // removes all the nodes from the list that are not equal to this element
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
                boolean equalSorted = true;
                if (size > 1) {
                    if (head != null && head.getNext() != null) {
                        Node<T> curr = head;
                        while(curr.getNext() != null) {
                            if (curr.getData().compareTo(curr.getNext().getData()) > 0) {
                                equalSorted = false;
                            }
                            curr = curr.getNext();
                        }
                    }
                }
                isSorted = equalSorted;
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
                boolean equalSorted = true;
                if (size > 1) {
                    if (head != null && head.getNext() != null) {
                        Node<T> curr = head;
                        while(curr.getNext() != null) {
                            if (curr.getData().compareTo(curr.getNext().getData()) > 0) {
                                equalSorted = false;
                            }
                            curr = curr.getNext();
                        }
                    }
                }
                isSorted = equalSorted;
            }
        }
    }
    public void reverse() { // reverses the linked list in place
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
        //Checks if list is sorted after reversing
        boolean reverseSort = true;
        if (size > 1) {
            Node<T> currNodeSort = head;
            while (currNodeSort.getNext() != null) {
                if (currNodeSort.getData().compareTo(currNodeSort.getNext().getData()) > 0) {
                    reverseSort = false;
                }
                currNodeSort = currNodeSort.getNext();
            }
        }
        isSorted = reverseSort;
    }

    public void intersect(List<T> otherList) { // removes the nodes from the calling list that are not in the other list
        if (otherList != null) {
            LinkedList<T> other = (LinkedList<T>) otherList;
            Node<T> prevNode = null;
            Node<T> currNode = head;
            //Loop through both lists
            while(currNode != null) {
                if(!contains(other, currNode.getData())) { //Check if this node's data is in the other list
                    if (prevNode == null) {
                        head = currNode.getNext();
                        currNode = head;
                        size--;
                    }
                    else { //removes this node if it's not in the other list
                        prevNode.setNext(currNode.getNext());
                        currNode = currNode.getNext();
                    }
                }
                else {
                    prevNode = currNode;
                    currNode = currNode.getNext();
                }
            }
            if(!isSorted) { //sorts the result list
                sort();
            }
        }
    }
    public boolean contains(LinkedList<T> currList, T element) { //helper method for intersect() to see if an element is in a linked list
        Node<T> currNode = currList.head;
        while (currNode != null) { //loops through a linked list and returns a boolean if the element is found in a node
            if(currNode.getData().compareTo(element) == 0) {
                return true;
            }
            currNode = currNode.getNext();
        }
        return false;
    }
    public T getMin() { //returns the minimum value in the linked list
        T currMin;
        if (head == null) {
            return null;
        }
        else {
            Node<T> currNode = head;
            currMin = head.getData();
            if (isSorted) { // increases efficiency if list is sorted
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
    public T getMax() { //returns the maximum value in the linked list
        T currMax = null;
        if (head != null) {

            if (isSorted) { //increases efficiency if list is sorted
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
    public boolean isSorted() { //represents whether the list is sorted
        return isSorted;
    }

}
