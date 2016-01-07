/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.facade;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import uzdiz.zadaca.facade.iterator.Iterator;
import uzdiz.zadaca.memento.ElementCareTaker;
import uzdiz.zadaca.mvc.model.Arguments;
import uzdiz.zadaca.mvc.model.Element;
import uzdiz.zadaca.mvc.model.Promjena;
import uzdiz.zadaca.mvc.view.impl.BaseView;
import uzdiz.zadaca.utils.Constants;

/**
 *
 * @author bozidar
 */
public class Comparator {

    private Element currentStoredState; //Current stored state (n)
    private Element realState; //real state from filesystem
    private String rootDirectoryPath;
    private Arguments arguments;
    
    long directorySize;
    boolean bezPromjene = true;
    Promjena promjena = new Promjena();
    int i;
    int j;
    private BaseView view;
    private ArrayList<String> opis;
    private boolean saveStructure;

    public Comparator(BaseView view, Arguments argument) {
        ElementCareTaker careTaker = ElementCareTaker.getInstance();
        this.rootDirectoryPath = argument.getRootDirectory();
        this.view = view;
        this. opis = new ArrayList<>();
    }
    
    public void setNewCurrentState(){
        ElementCareTaker careTaker = ElementCareTaker.getInstance();
        this.currentStoredState = careTaker.getLastFromMementoList().getState();
    }

    public void fetchCurrentFilesystemState() {
        FileManager manager = new FileManager();
        this.realState = manager.listDirectory(rootDirectoryPath, null); //real state from Dilesystem
    }

    public void checkForChanges() {
        int i = 0;
            
        checkCurrentState(currentStoredState);
    }

    private void checkCurrentState(Element element) {
        this.opis.clear();
        for (Iterator iterCurrent = element.getIterator(); iterCurrent.hasNext();) {
            Element elem = (Element) iterCurrent.next();
            checkRealState(realState, elem);
            if (elem.getType().equals(Constants.DIRECTORY)) {
                checkCurrentState(elem);
            }
            //System.out.println("Naziv: " + elem.getDateCreated());
        }
        
        if(saveStructure){
            new FileManager().saveStructure(realState);
            setNewCurrentState();
            saveStructure = false;
        }
    }

    private void checkRealState(Element element, Element compareWith) {
        j = 0;
        for (Iterator iterCurrent = element.getIterator(); iterCurrent.hasNext();) {
            Element elem = (Element) iterCurrent.next();
            Element el = new Element();

            
            if (elem.getDateCreated().equals(compareWith.getDateCreated())) {

                if (!elem.getName().equals(compareWith.getName())) {
                    j++;
                    el.setName(compareWith.getName());
                    bezPromjene = false;
                    opis.add("Element" + elem.getName() + " ime je promijenjeno iz: " + compareWith.getName() + " u " + elem.getName());
                }

                if (elem.getSize() != compareWith.getSize()) {
                    j++;
                    el.setSize(compareWith.getSize());
                    bezPromjene = false;
                    opis.add("Elementu" + elem.getName() + " veličina je promijenjena iz: " + compareWith.getSize() + " u " + elem.getSize());
                }
                if (!elem.getType().equals(compareWith.getType())) {
                    //  System.out.println("Tip je promijenjen iz: " + compareWith.getType() + " u " + elem.getType());
                    j++;
                    el.setType(compareWith.getType());
                    bezPromjene = false;
                    opis.add("Elementu " + elem.getName() + " tip je promijenjen iz: " + compareWith.getType() + " u " + elem.getType());
                }
                
                /*
                el.setDateCreated(elem.getDateCreated());
                el.setDateChanged(compareWith.getDateChanged());
                ElementOriginator originator = new ElementOriginator();
                ElementCareTaker careTaker = new ElementCareTaker();
                originator.setState(el);
                careTaker.add(originator.saveStateToMemento());
                */
                
                if (bezPromjene == false) {
                    saveStructure = true;
                    bezPromjene = true;
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    promjena.setVrijeme(dateFormat.format(date));
                    this.realState.setStoreDate(dateFormat.format(date));
                    promjena.setNazivElementa(compareWith.getName());
                    promjena.setOpis(opis);
                    
                    view.showPromjene(promjena, j);
                }
                /*if(bezPromjene==true){
                 System.out.println("Nema promijena " );
                 }*/

            }
            if (elem.getType().equals(Constants.DIRECTORY)) {
                checkRealState(elem, compareWith);
            }

        }

    }
    
    
    
}
