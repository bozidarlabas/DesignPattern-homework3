/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca;

import uzdiz.zadaca.facade.Comparator;
import uzdiz.zadaca.facade.FileManager;
import uzdiz.zadaca.registry.Registry;

/**
 *
 * @author bozidar
 */
public class ComparatorThread extends Thread {
    
    private boolean threadRunning = true;
    private final int seconds;
    private final FileManager manager;
    private Comparator comparator;
    
    public ComparatorThread(FileManager manager, Comparator comparator, int seconds) {
        this.seconds = seconds;
        this.manager = manager;
        this.comparator = comparator;
    }
    
    @Override
    public void run() {
        doWork();
    }
    
    @Override
    public void start() {
        super.start();
        System.out.println("Start thread");
    }
    
    private void doWork() {
        while (threadRunning) {
            //Facade design pattern
            comparator.fetchCurrentFilesystemState();
            comparator.checkForChanges();
            
            waitInterval(seconds);
        }
    }
    
    public void stopThread() {
        this.threadRunning = false;
        this.interrupt();
        
    }
    
    public void startThread() {
        this.threadRunning = true;
        this.start();
    }
    
    private void waitInterval(long sleepTime) {
        try {
            sleep(sleepTime * 1000);
        } catch (InterruptedException ex) {
        }
    }
    
    
}
