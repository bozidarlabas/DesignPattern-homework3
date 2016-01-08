/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca;

import static java.lang.Thread.sleep;
import java.util.Date;
import uzdiz.zadaca.facade.Comparator;
import uzdiz.zadaca.facade.FileManager;
import uzdiz.zadaca.mvc.view.impl.BaseView;
import uzdiz.zadaca.registry.Registry;

/**
 *
 * @author bozidar
 */
public class ComparatorThread extends Thread {
    
    private boolean threadRunning = true;
    private final int seconds;
    private int currentDirNum, currentFilesNum;
    private Comparator comparator;
    Date checkChangedDate = new Date();
    
    public ComparatorThread(int currentDirNum, int currentFilesNum, Comparator comparator, int seconds) {
        this.seconds = seconds;
        this.currentDirNum = currentDirNum;
        this.currentFilesNum = currentFilesNum;
        this.comparator = comparator;
    }
    
    @Override
    public void run() {
        doWork();
    }
    
    @Override
    public void start() {
        super.start();
        System.out.println("Dretva je startana");
    }
    
    private void doWork() {
        comparator.setCommand(3);
        comparator.setCurrentSizes(currentDirNum, currentFilesNum);
        while (threadRunning) {
            
            //Facade design pattern
            
            comparator.setNewCurrentState();
            comparator.setDate(checkChangedDate);
            comparator.fetchCurrentFilesystemState();
           // comparator.checkAddChange();
            comparator.checkForChanges();
          
            waitInterval(seconds);
            checkChangedDate = new Date();
            
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
