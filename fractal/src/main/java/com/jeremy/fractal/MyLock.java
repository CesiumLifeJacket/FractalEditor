package com.jeremy.fractal;

/**
 * Lock implementation, used for thread safety.
 */
public class MyLock {
    private boolean isLocked = false;

    public synchronized void lock()
            throws InterruptedException{
        while(isLocked){
            wait();
        }
        isLocked = true;
    }

    public synchronized void unlock(){
        isLocked = false;
        notify();
    }
}
