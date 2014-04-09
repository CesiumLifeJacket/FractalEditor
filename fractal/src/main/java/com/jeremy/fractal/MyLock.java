package com.jeremy.fractal;

/**
 * Created by JeremyIV on 4/5/14.
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
