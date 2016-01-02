/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.mvc.view.impl;

import uzdiz.zadaca.facade.FileManager;
import uzdiz.zadaca.listener.OnDataLoaded;
import uzdiz.zadaca.mvc.controller.WindowController;
import uzdiz.zadaca.mvc.controller.impl.WindowControllerImpl;
import uzdiz.zadaca.mvc.model.Element;
import uzdiz.zadaca.registry.Registry;
import uzdiz.zadaca.utils.Constants;

/**
 *
 * @author Labas
 */
public class VerticalViewImpl extends BaseView {

    private WindowController controller;

    @Override
    public void drawWindow(int rowNumber, int columnNumber, String frameSeparation) {
        int j = 1;
        for (int i = 2; i < (rowNumber); i++) {
            show(i, columnNumber / 2, 32, "|");
        }
    }

    @Override
    public void showData(Element rootElement, int rowNumber) {
        init();
    }

    private void init() {
        setCusrosrPosition((columnNumber / 2) + 1, 2);
        System.out.println(Constants.CREATED_DIR);
        setCusrosrPosition((columnNumber / 2) + 1, 3);
        System.out.println(Constants.CREATED_FILES);
        setCusrosrPosition((columnNumber / 2) + 1, 4);
        System.out.println(Constants.SIZE);
    }

    public void showDataOnLeftWindow(Element element, int indent, int positionY, boolean endOfScreen) {
        setCusrosrPosition(2, positionY);

        StringBuilder sb = new StringBuilder();

        sb.append(getIndentString(indent));
        sb.append("+--");
        sb.append(element.getName());
        // sb.append(" (");
        // sb.append(element.getSize());
        // sb.append(" B)");
        sb.append("/");
        sb.append("\n");
        if (endOfScreen) {
            sb.append(Constants.ANSI_ESC + "2J");
        }

        System.out.print(sb);

        if (endOfScreen) {
            rewriteScreen();
        }
    }

    private String getIndentString(int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append("|  ");
        }
        return sb.toString();
    }

    public void showDataOnRightWindow(int createdDirectoriesNum, int cretedFilesNum, long size, int positionY) {
        setCusrosrPosition((columnNumber / 2) + 1, 2);
        System.out.println(Constants.CREATED_DIR + createdDirectoriesNum);
        setCusrosrPosition((columnNumber / 2) + 1, 3);
        System.out.println(Constants.CREATED_FILES + cretedFilesNum);
        setCusrosrPosition((columnNumber / 2) + 1, 4);
        System.out.println(Constants.SIZE + size + " B");
    }

    public void finished() {
        setCusrosrPosition(3, (rowNumber + 2));
        onEnterPressed();
    }

    public void onEnterPressed() {
        setCusrosrPosition(3, (rowNumber + 1));
        System.out.print("Unos podataka: ");
        int command = Integer.parseInt(scanner.nextLine());
        switch (command) {
            case 1:
                controller.selectCommandOne();
                break;
            case 2:
                controller.selectCommandTwo();
                break;
        }

    }

    @Override
    public void showElementsNumber(int directoryNumber, int filesNumber) {
        clearScreen();
        rewriteScreen();
        setCusrosrPosition(2, 2);
        System.out.println(Constants.CREATED_DIR + directoryNumber);
        setCusrosrPosition(2, 3);
        System.out.println(Constants.CREATED_FILES + filesNumber);
        finished();
    }

    @Override
    public void setController(WindowController controller) {
        this.controller = controller;
    }

    @Override
    public void showAllData(Element element, int indent, int positionY, boolean endOfScreen) {
        

        StringBuilder sb = new StringBuilder();

        //sb.append(getIndentString(indent));
        sb.append("|+");
        sb.append(element.getName());
        sb.append("\n");
        sb.append("|  Datum kreiranja: " + element.getDateCreated());
        sb.append("\n");
        sb.append("|  Datum modificiranja: " + element.getDateChanged());
        sb.append("\n");
        sb.append("|  Velicina: " + element.getSize());
        // sb.append(" (");
        // sb.append(element.getSize());
        // sb.append(" B)");
        sb.append("/");
        sb.append("\n");
        if (endOfScreen) {
            sb.append(Constants.ANSI_ESC + "2J");
        }

        System.out.print(sb);

        if (endOfScreen) {
            rewriteScreen();
            setCusrosrPosition(2, positionY + 1);
        }
    }

}
