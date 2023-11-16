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
        if(index < 0 || index >= array.length){
            return false;
        } else {
            if(size == array.length){
                this.growArray();
            }
            for (int i = size()-1; i >= index; i--){
                array[i+1] = array[i];
            }
            array[index] = element;
            size++;

            if(size() == 1){
                isSorted = true;
            }else {
                //checks the case where the element is added at the beginning of the list
                if (index == 0) {
                    if (array[index].compareTo(array[index + 1]) > 0) {
                        isSorted = false;
                    }
                }
                //checks the case if the element is added at the end of the list
                else if (index == size() - 1) {
                    if (array[index].compareTo(array[index - 1]) < 0) {
                        isSorted = false;
                    }
                }
                //checks the case where the element is added between the beginning and the end of the list
                else if (array[index].compareTo(array[index + 1]) > 0 || array[index].compareTo(array[index - 1]) < 0) {
                    isSorted = false;
                }
            }
            return true;
        }
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
            boolean removeSort = true;
            if (size > 1) {
                int i = 0;
                while (i < size - 1 && removeSort) {
                    if (array[i].compareTo(array[i+1]) > 0) {
                        removeSort = false;
                    }

                    i++;

                }
            }
            isSorted = removeSort;
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
        boolean removeSort = true;
        if (size > 1) {
            int i = 0;
            while (i < size - 1 && removeSort) {
                if (array[i].compareTo(array[i+1]) > 0) {
                    removeSort = false;
                }
                i++;
            }
        }
        isSorted = removeSort;
    }

    public void intersect(List<T> otherList) { // sorts the given list and current list and merges them.
        if (otherList == null) {
            return;  // Do not attempt to merge if otherList is null
        }

        ArrayList<T> other = (ArrayList<T>) otherList;
        sort(); // Sort the current list
        other.sort(); // Sort the other list

        ArrayList<T> result = new ArrayList<>();

        int i = 0;
        int j = 0;

        while (i < size() && j < other.size()) {
            int comparison = get(i).compareTo(other.get(j));

            if (comparison == 0) {
                result.add(get(i));
                i++;
                j++;
            } else if (comparison < 0) {
                i++;
            } else {
                j++;
            }
        }

        // Clear the current list and add elements from the result list
        clear();
        for (int k = 0; k < result.size(); k++) {
            add(result.get(k));
        }

        // Update isSorted to true
        isSorted = true;
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
