//Written by Cyrus Vang, vang3339 and Leyla Mohamed, moha1594

public class ArrayList<T extends Comparable<T>> implements List<T> {
    private int size;
    private T[] array;
    private boolean isSorted;

    public ArrayList() {
        array = (T[]) new Comparable[2];
        isSorted = true;
    }

    public boolean add(T element) {
        if (element == null) {
            return false;
        }
        if (size() == array.length) {
            growArray(); //grow the array to resize array length
            array[size()] = element;
            size++;
        } else {
            array[size()] = element;
            size++;
        }
        if (isSorted && size() > 0 && array[size()].compareTo(array[size() - 1]) < 0) { //compares the added element and element before it to see if its sorted
            isSorted = false;
        }
        return true;
    }

    //method to resize array list if needed
    private void growArray() {
        T[] newSize = (T[]) new Comparable[size() * 2];
        for (int i = 0; i < size(); i++) {
            newSize[i] = array[i];
        }
        array = newSize;
    }

    public boolean add(int index, T element) {
        if (index < 0 || index >= array.length) {
            return false;
        }
        if (size() == array.length) {
            growArray();
        }
        for (int i = size(); i > index; i--) {
            array[i] = array[i - 1];
        }
        array[index] = element;
        size++;
        if (isSorted && index > 0 && array[index].compareTo(array[index - 1]) < 0) {
            isSorted = false;
        }
        return true;
    }

    public void clear() {
        for (int i = 0; i < size(); i++) {
            array[i] = null;
            size--;
        }
        isSorted = true;
        T[] originalArray = (T[]) new Comparable[2];
    }

    public T get(int index) {
        if (index >= 0 && index < array.length) {
            return array[index];
        } else {return null;}
    }

    public int indexOf(T element) {
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

    public boolean isEmpty() {
        if (size() == 0) {
            return true;
        } else return false;
    }

    public int size() {
        return size;
    }

    public void sort() {
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

    public T remove(int index) {
        if (index < 0 || index >= size()) {
            return null;
        } else {
            T remove = array[index];
            array[index] = null;
            while (index < size) {
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
                    if (array[i].compareTo(array[i+10]) > 0) {
                        isSorted = false;
                        break;
                    }
                    i++;
                }
            }
            return remove;
        }
    }

    public void equalTo(T element) {
        if (element != null) {
            int i = 0;
            while (i < size()) {
                if (array[i] != element) {
                    remove(i);
                    i--;
                }
                i++;
            }
            isSorted = true;
        }
    }

    public void reverse() {
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

    public void intersect(List<T> otherList) {
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
    }

    public T getMin() {
        if (isEmpty()) {
            return null;
        }
        if (!isSorted) {
            sort();
        }
        return array[0];
    }

    public T getMax() {
        if (isEmpty()) {
            return null;
        }
        if (!isSorted) {
            sort();
        }
        return array[size - 1];
    }

    public String toString() {
//        String result = "";
//        for (int i = 0; i < size(); i++) {
//            result += (String) array[i];
//            result += "\n";
//        }
//        return result;

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
