/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.facade;

import uzdiz.zadaca.facade.iterator.Iterator;
import uzdiz.zadaca.memento.ElementCareTaker;
import uzdiz.zadaca.mvc.model.Element;
import uzdiz.zadaca.utils.Constants;

/**
 *
 * @author bozidar
 */
public class Comparator {
    
    private Element currentStoredState; //Current stored state (n)
    private String rootDirectoryPath;
    Element realState;
    
    public Comparator(String rootDirectoryPath, int n){
        ElementCareTaker careTaker = ElementCareTaker.getInstance();
        this.currentStoredState = careTaker.getMementoList().get(n).getState();
        this.rootDirectoryPath = rootDirectoryPath;
    }
    
    public void fetchCurrentFilesystemState(){
        FileManager manager = new FileManager();
        this.realState = manager.listDirectory(rootDirectoryPath, null); //real state from Dilesystem
    }
    
    public void checkForChanges(){
        int i = 0;
        
        checkCurrentState(currentStoredState);
    }
    
    private void checkCurrentState(Element element){
        for (Iterator iterCurrent = element.getIterator(); iterCurrent.hasNext();) {
            Element elem = (Element) iterCurrent.next();
            checkRealState(realState, elem);
            if(elem.getType().equals(Constants.DIRECTORY)){
                checkCurrentState(elem);
            }
            //System.out.println("Naziv: " + elem.getDateCreated());
        }
    }
    
    private void checkRealState(Element element, Element compareWith){
       for (Iterator iterCurrent = element.getIterator(); iterCurrent.hasNext();) {
            Element elem = (Element) iterCurrent.next();
            if(elem.getDateCreated().equals(compareWith.getDateCreated())){
                //System.out.println("ISTI SU: " + elem.getName() + " i " + compareWith.getName());
                if(!elem.getName().equals(compareWith.getName())){
                    System.out.println("Ime je promijenjeno iz: " + compareWith.getName() + " u " + elem.getName());
                }
            }
            if(elem.getType().equals(Constants.DIRECTORY)){
                checkRealState(elem, compareWith);
            }
            //System.out.println("Naziv: " + elem.getName());
        }
    }
    
}
