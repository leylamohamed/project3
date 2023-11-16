//Written by Cyrus Vang, vang3339 and Leyla Mohamed, moha1594

public class ArrayList<T extends Comparable<T>> implements List<T> {
    private int size;
    private T[] array;
    private boolean isSorted = true;

    public ArrayList() { //initializes array length to 2
        array = (T[]) new Comparable[2];
    }

    public boolean add(T element) { // adds elements at the end of the list
        if (element == null) {
            return false;
        } else {
            if (isEmpty()) {
                array[0] = element;
                size++;
            } else if (size() == array.length) {
                growArray(); // calls growArray to resize array length
                array[size()] = element;
                size++;
            } else {
                array[size()] = element;
                size++;
            }
            //if size is greater than 1 compares the added element and the element before it to check if it's sorted
            if(size() == 1) {
                isSorted = true;
            } else if(array[size()-1].compareTo(array[size()-2])<0) {
                isSorted = false;
            }
            return true;
        }
    }

    private void growArray() { // helper method to resize array list if needed
        T[] newSize = (T[]) new Comparable[size() * 2];
        for (int i = 0; i < size(); i++) {
            newSize[i] = array[i];
        }
        array = newSize;
    }

    public boolean add(int index, T element) { // adds element at specific index
        if (element == null || index < 0 || index >= array.length) { // sees if index is out of bounds
            return false;
        }
        if (size() == array.length) { //resizes array if necessary
            growArray();
        }
        for (int i = size(); i > index; i--) {
            array[i] = array[i - 1];
        }
        array[index] = element;
        size++;

        if (isSorted && index > 0 && array[index].compareTo(array[index - 1]) < 0) { //updates isSorted variable to false if the element added breaks the current sorted order
            isSorted = false;
        }
        return true;
    }

    public void clear() { // clears all elements from the list
        for (int i = 0; i < size(); i++) {
            array[i] = null;
        }
        size = 0;
        isSorted = true;
    }

    public T get(int index) { //returns element at specified index
        if (index >= 0 && index < array.length) {
            return array[index];
        } else {return null;}
    }

    public int indexOf(T element) {  // returns the first index of the specified element, otherwise null
        if (element == null) {
            return -1;
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] == element) {
                return i;
            }
        }
        return -1;
    }

    public boolean isEmpty() { //returns true if size of list is 0
        if (size() == 0) {
            return true;
        } else return false;
    }

    public int size() {
        return size;
    }

    public void sort() { //sorts list using insertion sort
        for (int i = 1; i < size; i++) {
            T currValue = array[i];
            int j = i - 1;
            while (j >= 0 && array[j].compareTo(currValue) > 0) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = currValue;
        }
        isSorted = true;
    }

    public T remove(int index) { // removes element at specified index and returns it
        if (size == 0 || index < 0 || index >= size()) { // index out-of-bounds
            return null;
        } else { // shifts elements over to cover removed element
            T val = array[index];
            array[index] = null;
            while (index < size - 1) {
                array[index] = array[index + 1];
                index++;
            }
            size--;
            if (size() == 0) {
                isSorted = true;
            } else {
                int i = 0;
                isSorted = true;
                while (i < index-2) {
                    if (array[i].compareTo(array[i+1]) > 0) {
                        isSorted = false;
                        break;
                    }
                    i++;
                }
            }
            return val;
        }
    }

    public void equalTo(T element) { //removes elements not equal to specified element
        if (element != null) {
            int i = 0;
            while (i < size()) {
                if (array[i] != element) {
                    remove(i); // calls remove() method to remove elements
                    i--;
                }
                i++;
            }
            isSorted = true;
        }
    }

    public void reverse() { //reverses the list by swapping
        int left = 0;
        int right = size() - 1;
        while (left < right) {
            T temp = array[left];
            array[left] = array[right];
            array[right] = temp;
            left++;
            right--;
        }
    }

    public void intersect(List<T> otherList) { // sorts the given list and current list and merges them.
//        if (otherList != null) {
//            this.sort();
//            ArrayList<T> other = (ArrayList<T>) otherList;  // Copy the other list to avoid modifying it
//            other.sort();
//
//            ArrayList<T> result = new ArrayList<>();
//
//            int i = 0;
//            int j = 0;
//
//            while (i < this.size() && j < other.size()) {
//                int comparison = this.get(i).compareTo(other.get(j));
//
//                if (comparison == 0) {
//                    result.add(this.get(i));
//                    i++;
//                    j++;
//                } else if (comparison < 0) {
//                    i++;
//                } else {
//                    j++;
//                }
//            }
//        isSorted = true;
//        }


        if (otherList != null) {
            this.sort();
            this.isSorted = true;
            ArrayList<T> other = (ArrayList<T>) otherList;
            other.sort();
            T[] mergedArray = (T[]) new Comparable[this.size + other.size];
            int index = 0;
            int i = 0;
            int j = 0;
            mergedArray[index] = this.get(i);
            while (i < this.size && j < other.size) {
                if (this.get(i).compareTo(other.get(j)) < 0) {
                    mergedArray[index] = this.get(i);
                    index++;
                    i++;
                } else {
                    mergedArray[index] = other.get(j);
                    index++;
                    j++;
                }
            }
            while (i < this.size) {
                mergedArray[index] = this.get(i);
                index++;
                i++;
            }
            while (j < other.size) {
                mergedArray[index] = other.get(j);
                index++;
                j++;
            }
            array = mergedArray;
            size = mergedArray.length;
            isSorted = true;
        }

//        if (otherList == null) {
//            return;
//        }
//        sort();
//        otherList.sort();
//
//        ArrayList<T> other = (ArrayList<T>) otherList;
//        T[] mergedArray = (T[]) new Comparable[size() + other.size()];
//
//        int i = 0;
//        int j = 0;
//        int k = 0;
//
//        while (i < size() && j < other.size()) {
//            if (array[i].compareTo(other.get(j)) <= 0) {
//                mergedArray[k++] = array[i++];
//            } else {
//                mergedArray[k++] = other.get(j++);
//            }
//        }
//
//        while (i < size()) {
//            mergedArray[k++] = array[i++];
//        }
//
//        while (j < other.size()) {
//            mergedArray[k++] = other.get(j++);
//        }
//
//        array = mergedArray;
//        isSorted = true;
    }

    public T getMin() { //returns the minimum value of the list, which is the first element
        if (isEmpty()) {
            return null;
        }
        if (!isSorted) {
            sort();
        }
        return array[0];
    }

    public T getMax() { // returns the maximum value of the list, which is the last element
        if (isEmpty()) {
            return null;
        }
        if (!isSorted) {
            sort();
        }
        return array[size - 1];
    }

    public String toString() { // lists out the elements in order
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size(); i++) {
            result.append(String.valueOf(array[i])).append("\n");
        }
        return result.toString();
    }

    public boolean isSorted() {
        return isSorted;
    }
}
