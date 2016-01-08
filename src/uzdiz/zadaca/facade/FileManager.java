/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.facade;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import uzdiz.zadaca.facade.iterator.Iterator;
import uzdiz.zadaca.listener.OnDataLoaded;
import uzdiz.zadaca.memento.ElementCareTaker;
import uzdiz.zadaca.memento.ElementOriginator;
import uzdiz.zadaca.mvc.model.Arguments;
import uzdiz.zadaca.mvc.model.Element;
import uzdiz.zadaca.registry.Registry;
import uzdiz.zadaca.utils.Constants;

/**
 *
 * @author Labas
 */
public class FileManager {

    private Element rootElement;
    private boolean isRoot = false;
    private int level = 0;
    private OnDataLoaded listener;
    private int positionYLeftWindow = 1;
    private int positionYRightWindow = 1;
    public int createdDirectoriesNum = -1, createdFilesNum;
    private long size;
    private Arguments arguments;
    private boolean isEndOfFile = false;
    private String display;
    private Registry registry;

    public FileManager(OnDataLoaded listener, Registry registry) {
        rootElement = new Element();
        this.listener = listener;
        this.arguments = (Arguments) registry.resolve("arguments");
        dir = 0;
        file = 0;
    }

    public FileManager() {
        rootElement = new Element();
        dir = 0;
        file = 0;
    }

    long directorySize;

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }

    int dir;
    int file;

    /**
     * List files and store this in model Element as Composite
     *
     * @param path
     * @param parrent
     */

    public Element listDirectory(String path, Element parrent) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        directorySize = 0;

        if (parrent == null) {
            rootElement.setType(Constants.DIRECTORY);
            rootElement.setLevel(level);
            rootElement.setDateCreated(getCreationTime(folder));
            rootElement.setDateChanged(getModifiedTime(folder));
        }

        //Iterate through directory
        for (File element : listOfFiles) {
            level++;
            Element model = new Element();
            //System.out.println("Name: " + element.getName());
            model.setName(element.getName());
            model.setDateCreated(getCreationTime(element));
            model.setDateChanged(getModifiedTime(element));

            if (element.isDirectory()) {

                //System.out.println("DIREKTORIJ");
                listDirectory(element.getAbsolutePath(), model);
                model.setType(Constants.DIRECTORY);
                dir++;
                model.setLevel(level);
                model.setSize(directorySize);
            } else if (element.isFile()) {
                model.setType(Constants.FILE);
                model.setSize(element.length());
                directorySize += model.getSize();
                file++;
            }

            if (parrent == null) {
                rootElement.getLeaf().add(model);
            } else {
                parrent.getLeaf().add(model);
            }
        }

        if (parrent == null) {
            rootElement.setSize(directorySize);
        }
        return rootElement;
    }

    private String getCreationTime(File element) {
        Path path = Paths.get(element.getAbsolutePath());
        String dateCreatedString = "";
        try {
            BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
            FileTime dateCreated = attr.creationTime();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateCreatedString = df.format(dateCreated.toMillis());
        } catch (IOException ex) {
        }
        return dateCreatedString;
    }

    private String getModifiedTime(File element) {
        Path path = Paths.get(element.getAbsolutePath());
        String dateCreatedString = "";
        try {
            BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
            FileTime dateCreated = attr.lastModifiedTime();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateCreatedString = df.format(dateCreated.toMillis());
        } catch (IOException ex) {
        }
        return dateCreatedString;
    }

    public Element getElementModel() {
        return this.rootElement;
    }

    public void printDirectoryTree(Element element, String display) {
       
        this.size = element.getSize();
        this.display = display;

        positionYLeftWindow = 1;
        positionYRightWindow = 1;
        if (!element.getType().equals(Constants.DIRECTORY)) {
            throw new IllegalArgumentException("folder is not a Directory");
        }
        int indent = 0;

        printDirectoryTree(element, indent);
        listener.finished();
    }

    private void printDirectoryTree(Element element, int indent) {
        if (!element.getType().equals(Constants.DIRECTORY)) {
            throw new IllegalArgumentException("folder is not a Directory");
        }

        sleep();

        if (display.equals(Constants.DATA)) {
            positionYLeftWindow++;
        } else if (display.equals(Constants.STRUCTURE)) {
            positionYLeftWindow += 3;
        }

        if ((isEndOfFile = checkEndOfLine())) {
            positionYLeftWindow = 1;
        }

        listener.showDataOnLeftWindow(element, indent, positionYLeftWindow, isEndOfFile);
        createdDirectoriesNum++;

        listener.showDataOnRightWindow(createdDirectoriesNum, createdFilesNum, this.size, 2);
        for (Iterator iter = element.getIterator(); iter.hasNext();) {
            Element elementModel = (Element) iter.next();
            if (elementModel.getType().equals(Constants.DIRECTORY)) {
                printDirectoryTree(elementModel, indent + 1);
            } else {
                printFile(elementModel, indent + 1);
            }
        }
    }

    //Check end of line for Vertical and Horizontal View
    private boolean checkEndOfLine() {
        if (arguments.getFrameSeparation().equals(Constants.VERTICAL)) {
            if (display.equals(Constants.STRUCTURE)) {
                if ((positionYLeftWindow + 10) >= this.arguments.getRowNumber()) {
                    return true;
                }
            }
            if (positionYLeftWindow >= this.arguments.getRowNumber()) {
                return true;
            }
        } else {
            if (display.equals(Constants.STRUCTURE)) {
                if ((positionYLeftWindow + 5) >= (this.arguments.getRowNumber() / 2)) {
                    return true;
                }
            }
            if (positionYLeftWindow >= (this.arguments.getRowNumber() / 2)) {
                return true;
            }
        }

        return false;
    }

    private void printFile(Element element, int indent) {

        sleep();
        if (display.equals(Constants.DATA)) {
            positionYLeftWindow++;
        } else if (display.equals(Constants.STRUCTURE)) {
            positionYLeftWindow += 3;
        }

        if ((isEndOfFile = checkEndOfLine())) {
            positionYLeftWindow = 1;
        }
        createdFilesNum++;

        listener.showDataOnLeftWindow(element, indent, positionYLeftWindow, isEndOfFile);
        listener.showDataOnRightWindow(createdDirectoriesNum, createdFilesNum, this.size, indent);
    }

    private void sleep() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException ex) {
        }
    }

    public void saveStructure(Element element) {
        ElementOriginator originator = new ElementOriginator();
        originator.setState(element);
        ElementCareTaker careTaker = ElementCareTaker.getInstance();
        careTaker.add(originator.saveStateToMemento());
    }

    public int getDir() {
        return dir;
    }

    public int getFile() {
        return file;
    }
    
    public void resetValues(){
        this. createdDirectoriesNum = -1;
        this.createdFilesNum = 0;
    }

}
