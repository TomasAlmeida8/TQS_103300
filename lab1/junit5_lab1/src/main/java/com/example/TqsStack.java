package com.example;

import java.util.NoSuchElementException;

public class TqsStack {
    private int[] arr;
    private int top;
    private int size;
    
    public TqsStack(int size) {
        this.arr = new int[size];
        this.top = -1;
        this.size = size;
    }
    
    public boolean isEmpty() {
        return (top == -1);
    }
    
    public boolean isFull() {
        return (top == size-1);
    }
    
    public void push(int val) {
        if (isFull()) {
            throw new IllegalStateException("The stack is full!");
        }
        arr[++top] = val;
    }
    
    public int pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Nothing to remove!");
        }
        else {
            return arr[top--];
        }
    }
    
    public int peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Nothing to peek!");
        }
        return arr[top];
    }

    public int size() {
        return top + 1;
    }
}

