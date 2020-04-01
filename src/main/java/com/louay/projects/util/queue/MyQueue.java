package com.louay.projects.util.queue;

import java.util.LinkedList;

public class MyQueue<T> implements MyList<T> {
    private int maxSize;
    private int size;
    private LinkedList<T> list;


    public MyQueue() {
        this.list = new LinkedList<>();
        this.maxSize = 16;
    }

    public MyQueue(int maxSize) {
        this.maxSize = maxSize;
        this.list = new LinkedList<>();
    }

    @Override
    public void enqueue(T item) {
        if (isFull()) {
            throw new RuntimeException("Queue is fully, you cannot enqueue to fully queue");
        }
        this.list.addLast(item);
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty, You cannot dequeue from empty queue.");
        }
        return this.list.removeFirst();
    }

    @Override
    public boolean isFull() {
        return this.list.size() >= this.maxSize;
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.size() == 0;
    }

}
