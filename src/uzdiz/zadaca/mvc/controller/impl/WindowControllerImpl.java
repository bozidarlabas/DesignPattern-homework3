/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.mvc.controller.impl;

import uzdiz.zadaca.ComparatorThread;
import uzdiz.zadaca.facade.Comparator;
import uzdiz.zadaca.facade.FileManager;
import uzdiz.zadaca.facade.iterator.Iterator;
import uzdiz.zadaca.listener.OnDataLoaded;
import uzdiz.zadaca.mvc.controller.WindowController;
import uzdiz.zadaca.mvc.model.Arguments;
import uzdiz.zadaca.mvc.model.Element;
import uzdiz.zadaca.mvc.view.impl.BaseView;
import uzdiz.zadaca.registry.Registry;
import uzdiz.zadaca.utils.Constants;
import uzdiz.zadaca.visitor.impl.Elements;
import uzdiz.zadaca.visitor.impl.Search;

/**
 *
 * @author Labas
 */
public class WindowControllerImpl implements WindowController, OnDataLoaded {

    private final Arguments arguments;
    private final Element model;
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
        Comparator comparator = new Comparator(arguments.getRootDirectory(), 0); //TODO change from 0 to n
        this.thread = new ComparatorThread(manager, comparator, arguments.getSeconds());
        thread.startThread();
        view.finished();
        display = Constants.THREAD_START;
    }

    @Override
    public void selectCommandFour() {
        thread.stopThread();
        view.finished();
    }

    @Override
    public void selectCommandNine(String searched) {
        view.clearScreen();
        view.rewriteScreen();
        view.show(2, 2, 32, "Tražim \"" + searched + "\"...");
        
        Elements elements = new Elements(model);
        elements.accept(new Search(searched, view));
        
        view.finished();
        
    }
}
