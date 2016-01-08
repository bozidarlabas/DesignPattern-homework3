/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.facade;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    long directorySize;
    boolean bezPromjene = true;
    Promjena promjena = new Promjena();
    ArrayList<Promjena> promjene = new ArrayList<>();
    int i;
    int j;
    private BaseView view;
    private String opis;
    private boolean saveStructure;
    private int command;
    private Date checkChangedDate;
    private int realStateDirNum, realStateFileNum, currentStateDirNum, currentStateFileNum;

    public Comparator(BaseView view, Arguments argument) {
        ElementCareTaker careTaker = ElementCareTaker.getInstance();
        this.rootDirectoryPath = argument.getRootDirectory();
        this.view = view;
    }

    public void setStates(Element element, Element compareElement) {
        this.currentStoredState = element;
        this.realState = compareElement;
    }

    public void setCurrentSizes(int currentDirNum, int currentFilesNum) {
        this.currentStateDirNum = currentDirNum;
        this.currentStateFileNum = currentFilesNum;
    }

    public void setNewCurrentState() {
        ElementCareTaker careTaker = ElementCareTaker.getInstance();
        this.currentStoredState = careTaker.getLastFromMementoList().getState();

    }

    public void checkAddChange() {
        promjena = new Promjena();
        if (currentStateDirNum < realStateDirNum) {
            saveStructure = true;
            currentStateDirNum += (realStateDirNum - currentStateDirNum);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            promjena.setVrijeme(dateFormat.format(date));
            this.realState.setStoreDate(dateFormat.format(date));
            promjena.setOpis("Dodan je novi direktorij");
            promjene.add(promjena);
            //view.showPromjene(promjena, j);
           
        } else if (currentStateDirNum > realStateDirNum) {
            saveStructure = true;
            currentStateDirNum -= (currentStateDirNum - realStateDirNum);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            promjena.setVrijeme(dateFormat.format(date));
            this.realState.setStoreDate(dateFormat.format(date));
            promjena.setOpis("Obrisan je direktorij");
            promjene.add(promjena);
            //view.showPromjene(promjena, j);
            
        }

        if (currentStateFileNum < realStateFileNum) {
            saveStructure = true;
            currentStateFileNum += (realStateFileNum - currentStateFileNum);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            promjena.setVrijeme(dateFormat.format(date));
            this.realState.setStoreDate(dateFormat.format(date));
            promjena.setOpis("Dodana je nova datoteka");
            promjene.add(promjena);
            //iew.showPromjene(promjena, j);
            new FileManager().saveStructure(realState);
        } else if (currentStateFileNum > realStateFileNum) {
            saveStructure = true;
            currentStateFileNum -= (currentStateFileNum - realStateFileNum);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            promjena.setVrijeme(dateFormat.format(date));
            this.realState.setStoreDate(dateFormat.format(date));
            promjena.setOpis("Obrisana je datoteka");
            promjene.add(promjena);
            //view.showPromjene(promjena, j);
            new FileManager().saveStructure(realState);
        }
    }

    public void fetchCurrentFilesystemState() {
        FileManager manager = new FileManager();
        this.realState = manager.listDirectory(rootDirectoryPath, null); //real state from Dilesystem
        this.realStateDirNum = manager.getDir();
        this.realStateFileNum = manager.getFile();
    }

    public void checkForChanges() {
        int i = 0;
        checkCurrentState(currentStoredState);
        checkAddChange();
        

        if (saveStructure && command == 3) {
            new FileManager().saveStructure(realState);
            setNewCurrentState();
            saveStructure = false;
            view.showPromjene(promjene, j);
            promjene.clear();
        }

    }

    private void checkCurrentState(Element element) {
        this.opis = "";
        for (Iterator iterCurrent = element.getIterator(); iterCurrent.hasNext();) {
            Element elem = (Element) iterCurrent.next();
            checkRealState(realState, elem);
            if (elem.getType().equals(Constants.DIRECTORY)) {
                checkCurrentState(elem);
            }
            //System.out.println("Naziv: " + elem.getDateCreated());
        }

    }

    private void checkRealState(Element element, Element compareWith) {
        j = 0;
        for (Iterator iterCurrent = element.getIterator(); iterCurrent.hasNext();) {
            Element elem = (Element) iterCurrent.next();
            Element el = new Element();
            //System.out.println("stvarno: " + elem.getName());

            if (elem.getDateCreated().equals(compareWith.getDateCreated())) {

                if (!elem.getName().equals(compareWith.getName())) {
                    promjena = new Promjena();
                    j++;
                    el.setName(compareWith.getName());
                    bezPromjene = false;
                    opis = ("Ime je promijenjeno iz: " + compareWith.getName() + " u " + elem.getName());
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    promjena.setVrijeme(dateFormat.format(date));
                    this.realState.setStoreDate(dateFormat.format(date));
                    promjena.setNazivElementa(compareWith.getName());
                    promjena.setOpis(opis);
                    promjene.add(promjena);

                }

                if (elem.getSize() != compareWith.getSize()) {
                    j++;
                    el.setSize(compareWith.getSize());
                    bezPromjene = false;
                    opis = ("Element: " + elem.getName() + " veličina je promijenjena iz: " + compareWith.getSize() + " u " + elem.getSize());
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    promjena.setVrijeme(dateFormat.format(date));
                    this.realState.setStoreDate(dateFormat.format(date));
                    promjena.setNazivElementa(compareWith.getName());
                    promjena.setOpis(opis);
                    promjene.add(promjena);
                }
                if (!elem.getType().equals(compareWith.getType())) {
                    //  System.out.println("Tip je promijenjen iz: " + compareWith.getType() + " u " + elem.getType());
                    j++;
                    el.setType(compareWith.getType());
                    bezPromjene = false;
                    opis = ("Element: " + elem.getName() + " tip je promijenjen iz: " + compareWith.getType() + " u " + elem.getType());
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    promjena.setVrijeme(dateFormat.format(date));
                    this.realState.setStoreDate(dateFormat.format(date));
                    promjena.setNazivElementa(compareWith.getName());
                    promjena.setOpis(opis);
                    promjene.add(promjena);
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

                    // view.showPromjene(promjena, j);
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

    public void setCommand(int command) {
        this.command = command;
    }

    public void setDate(Date checkChangedDate) {
        this.checkChangedDate = checkChangedDate;
    }

}
