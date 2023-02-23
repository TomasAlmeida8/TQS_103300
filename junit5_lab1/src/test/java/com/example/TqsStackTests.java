package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TqsStackTests {
    private TqsStack stack;

    @BeforeEach
    public void setUp() {
        stack = new TqsStack(5);
    }

    @Test
    @DisplayName("A stack is empty on creation")
    public void testIsEmpty() {
        assertTrue(stack.isEmpty());
    }

    @Test
    @DisplayName("A stack has size 0 on creation")
    public void sizeOnCreation() {
        assertEquals(0, stack.size());
    }

    @Test
    @DisplayName("After n pushes to an empty stack, n>0, the stack is not empty and its size is n")
    public void testPush() {
        stack.push(1);
        assertFalse(stack.isEmpty());
        assertEquals(1, stack.size());
        stack.push(2);
        assertFalse(stack.isEmpty());
        assertEquals(2, stack.size());
    }

    @Test
    @DisplayName("If one pushes x then pops, the value popped is x")
    public void testPushPop() {
        stack.push(1);
        assertEquals(1, stack.pop());
    }

    @Test
    @DisplayName("If one pushes x then peeks, the value returned is x, but the size stays the same")
    public void testPushPeek() {
        stack.push(1);
        assertEquals(1, stack.peek());
        assertEquals(1, stack.size());
    }

    @Test
    @DisplayName("If the size is n, then after n pops, the stack is empty and has a size 0")
    public void testPop() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        assertEquals(5, stack.size());
        stack.pop();
        stack.pop();
        stack.pop();
        stack.pop();
        stack.pop();
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    @DisplayName("Popping from an empty stack doe throw a NoSuchElementException")
    public void testPopEmpty() {
        assertThrows(NoSuchElementException.class, () -> {
            stack.pop();
        });
    }

    @Test
    @DisplayName("Peeking into an empty stack does throw a NoSuchElementException")
    public void testPeekEmpty() {
        assertThrows(NoSuchElementException.class, () -> {
            stack.peek();
        });
    }

    @Test
    @DisplayName("For bounded stacks only: pushing onto a full stack does throw an IllegalStateException")
    public void testPushFull() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        assertThrows(IllegalStateException.class, () -> {
            stack.push(6);
        });
    }
}
