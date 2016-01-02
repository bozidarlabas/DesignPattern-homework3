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
import uzdiz.zadaca.facade.iterator.Iterator;
import uzdiz.zadaca.listener.OnDataLoaded;
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
    private int level = 1;
    private OnDataLoaded listener;
    private int positionYLeftWindow = 1;
    private int positionYRightWindow = 1;
    private int createdDirectoriesNum, createdFilesNum;
    private long size;
    private Arguments arguments;
    private boolean isEndOfFile = false;

    public FileManager(OnDataLoaded listener, Registry registry) {
        rootElement = new Element();
        this.listener = listener;
        this.arguments = (Arguments) registry.resolve("arguments");
    }
    
    public FileManager(){
         rootElement = new Element();
    }

    long directorySize;

    /**
     * List files and store this in model Element as Composite
     *
     * @param path
     * @param parrent
     */
    public void listDirectory(String path, Element parrent) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        directorySize = 0;

        if (parrent == null) {
            rootElement.setName("root");
            rootElement.setType(Constants.DIRECTORY);
            rootElement.setLevel(level);
        }

        //Iterate through directory
        for (File element : listOfFiles) {
            Element model = new Element();
            //System.out.println("Name: " + element.getName());
            model.setName(element.getName());
            model.setDateCreated(getCreationTime(element));
            model.setDateChanged(getModifiedTime(element));

            if (element.isDirectory()) {
                //System.out.println("DIREKTORIJ");
                listDirectory(element.getAbsolutePath(), model);
                model.setType(Constants.DIRECTORY);
                level++;
                model.setLevel(level);
                model.setSize(directorySize);
            } else if (element.isFile()) {
                model.setType(Constants.FILE);
                model.setSize(element.length());
                directorySize += model.getSize();
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
    }

    private String getCreationTime(File element) {
        Path path = Paths.get(element.getAbsolutePath());
        String dateCreated = "";
        try {
            BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
            dateCreated = attr.creationTime().toString();
        } catch (IOException ex) {
        }
        return dateCreated;
    }

    private String getModifiedTime(File element) {
        Path path = Paths.get(element.getAbsolutePath());
        String dateModified = "";
        try {
            BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
            dateModified = attr.lastModifiedTime().toString();
        } catch (IOException ex) {
        }
        return dateModified;
    }

    public Element getElementModel() {
        return this.rootElement;
    }

    
    public void printDirectoryTree(Element element) {
        this.size = element.getSize();

        
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
        StringBuilder sb = new StringBuilder();

        sb.append(getIndentString(indent));
        sb.append("+--");
        sb.append(element.getName());
        sb.append(" (");
        sb.append(element.getSize());
        sb.append(" B)");
        sb.append("/");
        sb.append("\n");
        sleep();
        positionYLeftWindow++;
        
        if(( isEndOfFile = checkEndOfLine())){
            sb.append(Constants.ANSI_ESC + "2J");
            positionYLeftWindow = 1;
        }
        listener.showDataOnLeftWindow(sb, positionYLeftWindow, isEndOfFile);
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

    private boolean checkEndOfLine(){
        return this.arguments.getRowNumber() == positionYLeftWindow;
    }
    
    private void printFile(Element element, int indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(getIndentString(indent));
        sb.append("+--");
        sb.append(element.getName());
        sb.append(" (");
        sb.append(element.getSize());
        sb.append(" B)");
        sb.append("\n");
        sleep();
        positionYLeftWindow++;
        createdFilesNum++;
        
        if(( isEndOfFile = checkEndOfLine())){
            sb.append(Constants.ANSI_ESC + "2J");
            positionYLeftWindow = 1;
        }
        
        listener.showDataOnLeftWindow(sb, positionYLeftWindow, isEndOfFile);
        listener.showDataOnRightWindow(createdDirectoriesNum, createdFilesNum, this.size, indent);
    }

    private void sleep() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException ex) {
        }
    }

    private String getIndentString(int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append("|  ");
        }
        return sb.toString();
    }

}
