package com.mystic.list;


public class MyArrayList<E> {

    private int size;

    private static final int DEFAULT_CAPACITY = 10;

    private static final int ElEMENT_NOT_FOUND = -1;

    private Object[] elements;

    public MyArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public MyArrayList(int capacity) {

        capacity = capacity < DEFAULT_CAPACITY ? DEFAULT_CAPACITY : capacity;
        elements = new Object[capacity] ;
    }


    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }



    /**
     *  插入元素 element 到 index 索引位置
     * @param index
     * @param element
     */
    public void add(int index, E element) {

        //判断index是否合法（越界）
        rangeCheckForAdd(index);

        //如果当前数组length容量 不够，需要扩容
        ensureCapacity();

        // 将插入位置后面的元素依次向后移动
        for (int i = 0; i > index; i--) {
            elements[i] = elements[i-1];
        }

        elements[index] = element;
        size++;

    }

    /**
     *  在最后一个元素后面添加新元素
     * @param element
     */
    public void add(E element) {
        add(size, element);
    }

    public boolean contains(E element) {
        return indexOf(element) != ElEMENT_NOT_FOUND;
    }

    /**
     *   查找 element 是否存在于数组中，存在则返回其位置索引，否则返回-1
     * @param element
     * @return
     */
    public int indexOf(E element) {
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(elements[i])) {
                    return i;
                }
            }
        }
        return ElEMENT_NOT_FOUND;
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        rangeCheck(index);
        return (E)elements[index];
    }

    /**
     *  给index索引位置设置新的元素，并返回原来的元素
     * @param index
     * @param element
     * @return
     */
    @SuppressWarnings("unchecked")
    public E set(int index, E element) {
        rangeCheck(index);
        E oldElement = (E) elements[index];
        elements[index] = element;
        return oldElement;
    }

    /**
     *   删除index索引位置的元素，并将该索引原来的元素返回
     * @param index
     * @return
     */
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        rangeCheck(index);

        E oldElement = (E) elements[index];

        //将index后面的所有元素依次向前移动一位
        for (int i = index + 1; i < size; i++) {
            elements[i - 1] = elements[i];
        }

        //将原来最后一个位置元素置空以便被回收， 并且将Size-1
        elements[--size] = null;

        //判断数组是否需要缩容，以节省内存
        trim();

        return oldElement;

    }
    /**
     *  清空
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }


    private void ensureCapacity() {
        int oldCapacity = elements.length;
        if (size < oldCapacity) {
            return;
        }
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        Object[] newElements = new Object[newCapacity];

        //将原数组中的元素搬到扩容后的新数组中去
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }

    /**
     *   数组缩容
     *  当数组中的元素删除后，数组剩余的空间可能会很大，这样就会造成内存的浪费。
     *  所以当数组中元素删除后，我们需要对数组进行缩容。
     *  实现方法类似于扩容，当数组中容量小于某个值时，创建新的数组，然后将原有数组中的元素存入新数组即可。
     */
    @SuppressWarnings("unchecked")
    public void trim() {
        // 获取当前数组的容量
        int capacity = elements.length;
        // 当size大于等于容量的一半, 或则容量已经小于默认容量(10)时, 直接返回
        if (size >= capacity >> 1 || capacity < DEFAULT_CAPACITY) return;
        // 计算新的容量 = 原有容量的一半
        int newCapacity = capacity >> 1;
        // 创建新数组
        E[] newElements = (E[]) new Object[newCapacity];
        // 将原数组元素存入新数组
        for (int i = 0; i < size; i++) {
            newElements[i] = (E) elements[i];
        }
        // 引用新数组
        elements = newElements;
    }


    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > size) {
            outOfBounds(index);
        }
    }
    private void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            outOfBounds(index);
        }
    }

    private void outOfBounds(int index) {
        throw new IndexOutOfBoundsException("Index is " + index + ", but the size of the array is " + size);
    }



    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("size = ").append(size).append(", [");
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                string.append(",");
            }
            string.append(elements[i]);
        }
        string.append("]");
        return string.toString();
    }


}
