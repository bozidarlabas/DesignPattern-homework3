/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.mvc.controller.impl;

import java.util.List;
import uzdiz.zadaca.ComparatorThread;
import uzdiz.zadaca.facade.Comparator;
import uzdiz.zadaca.facade.FileManager;
import uzdiz.zadaca.listener.OnDataLoaded;
import uzdiz.zadaca.memento.ElementCareTaker;
import uzdiz.zadaca.memento.ElementMemento;
import uzdiz.zadaca.mvc.controller.WindowController;
import uzdiz.zadaca.mvc.model.Arguments;
import uzdiz.zadaca.mvc.model.Element;
import uzdiz.zadaca.mvc.view.impl.BaseView;
import uzdiz.zadaca.registry.Registry;
import uzdiz.zadaca.utils.Constants;

/**
 *
 * @author Labas
 */
public class WindowControllerImpl implements WindowController, OnDataLoaded {

    private final Arguments arguments;
    private Element model;
    private final BaseView view;
    private FileManager manager;
    private String display;
    private ComparatorThread thread;

    public WindowControllerImpl(Registry registry, Element model) {
        this.arguments = (Arguments) registry.resolve("arguments");
        this.view = (BaseView) registry.resolve("windowView");
        this.model = model;
        this.manager = new FileManager(this, registry);
    }

    @Override
    public void showWindow() {
        view.drawScreen(arguments.getRowNumber(), arguments.getColumnNumber(), arguments.getFrameSeparation());
    }

    @Override
    public void showData() {
        fetchDataStructure(model);

    }

    @Override
    public void selectCommandOne() {
        view.showElementsNumber(manager.createdDirectoriesNum, manager.createdFilesNum);
    }

    public void fetchDataStructure(Element rootElement) {
        display = Constants.DATA;
        manager.printDirectoryTree(rootElement, display);
        view.showData(model, arguments.getRowNumber());
    }

    @Override
    public void showDataOnLeftWindow(Element element, int indent, int positionY, boolean endOfScreen) {
        if (display.equals(Constants.DATA)) {
            view.showDataOnLeftWindow(element, indent, positionY, endOfScreen);
        } else if (display.equals(Constants.STRUCTURE)) {
            view.showAllData(element, indent, positionY, endOfScreen);
        }

    }

    @Override
    public void showDataOnRightWindow(int createdDirectoriesNum, int cretedFilesNum, long size, int positionY) {
        if (display.equals(Constants.DATA)) {
            view.showDataOnRightWindow(createdDirectoriesNum, cretedFilesNum, size, positionY);
        } else if (display.equals(Constants.STRUCTURE)) {

        }
    }

    @Override
    public void finished() {
        view.finished();
    }

    @Override
    public void selectCommandTwo() {
        view.clearScreen();
        view.rewriteScreen();
        view.setCusrosrPosition(2, 2);
        display = Constants.STRUCTURE;
        manager.printDirectoryTree(model, display);
    }

    @Override
    public void selectCommandThree() {
        view.clearScreen();
        view.rewriteScreen();
        view.setCusrosrPosition(2, 2);
        display = Constants.STRUCTURE;
        Comparator comparator = new Comparator(view, arguments); //TODO change from 0 to n
        this.thread = new ComparatorThread(manager, comparator, arguments.getSeconds());
        thread.startThread();
        view.finished();
        //display = Constants.THREAD_START;
    }

    @Override
    public void selectCommandFour() {
        view.clearScreen();
        view.rewriteScreen();
        thread.stopThread();
        view.setCusrosrPosition(2, 2);
        view.showMessage("Dretva je zaustavljena");
        view.finished();
    }

    @Override
    public void selectCommandFive() {
        view.showAllStoredStates();
        view.finished();
    }

    @Override
    public void selectCommandSix(int n) {
        ElementCareTaker careTaker = ElementCareTaker.getInstance();
        if ((n - 1) <= careTaker.getMementoList().size()) {
            Element selectedElement = careTaker.getMementoList().get(n-1).getState();
            this.model = selectedElement;
            
            view.clearScreen();
            view.rewriteScreen();
            view.setCusrosrPosition(2, 2);
            view.showMessage("Stanje strukture s rednim brojem " + n + " postaje novo trenutno stanje strukture");
        }
        
        finished();

    }

}
