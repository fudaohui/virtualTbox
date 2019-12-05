/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fdh.simulator.utils;

/**
 *
 * @author wangcz
 */
public class ByteList {

    private byte[] array;
    private int increaseSize;

    protected int _size;

    public int getSize() {
        return _size;
    }

    public int getCapacity() {
        return array.length;
    }

    public ByteList() {
        this(10, 10);
    }

    public ByteList(int initialSize, int increaseSize) {
        array = new byte[initialSize];
        this.increaseSize = increaseSize;
        _size = 0;
    }

    public void add(byte data) {
        if (_size >= array.length) {
            byte[] arrayN = new byte[array.length + increaseSize];
            System.arraycopy(array, 0, arrayN, 0, array.length);
            array = arrayN;
        }

        array[_size++] = data;
    }

    public final byte get(int index) {
        return array[index];
    }

    public final void clear() {
        _size = 0;
    }

    public byte[] getArray() {
        final byte[] a = new byte[_size];
        System.arraycopy(array, 0, a, 0, _size);
        return a;
    }

    public void setCapacity(int newCapacity) {
        increaseSize = Math.max(increaseSize, newCapacity);
        final byte[] newArray = new byte[increaseSize];
        _size = Math.min(increaseSize, _size);
        System.arraycopy(array, 0, newArray, 0, _size);
        array = newArray;
    }

    public void addArray(byte[] a) {
        if (_size + a.length > array.length) {
            setCapacity(_size + a.length);
        }
        System.arraycopy(a, 0, array, _size, a.length);
        _size += a.length;
    }

    public void setArray(byte[] a) {
        if (array.length < a.length) {
            setCapacity(a.length);
        }
        System.arraycopy(a, 0, array, 0, a.length);
        _size = a.length;
    }

    @Override
    public ByteList clone() {
        ByteList a = new ByteList();
        a.setArray(this.getArray());
        return a;
    }
}
