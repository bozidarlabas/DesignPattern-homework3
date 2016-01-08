/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.mvc.view.impl;

import java.util.ArrayList;
import java.util.List;
import uzdiz.zadaca.memento.ElementCareTaker;
import uzdiz.zadaca.memento.ElementMemento;
import uzdiz.zadaca.mvc.controller.WindowController;
import uzdiz.zadaca.mvc.model.Element;
import uzdiz.zadaca.mvc.model.Promjena;
import uzdiz.zadaca.utils.Constants;

/**
 *
 * @author Labas
 */
public class HorizontalViewImpl extends BaseView {

    private WindowController controller;

    @Override
    public void drawWindow(int rowNumber, int columnNumber, String frameSeparation) {
        int j = 1;
        for (int i = 2; i < columnNumber; i++) {
            show(rowNumber / 2, i, 32, "-");
        }
    }

    @Override
    public void showData(Element rootElement, int rowNumber) {
        init();
    }

    private void init() {
        setCusrosrPosition(2, (rowNumber / 2) + 1);
        System.out.println(Constants.CREATED_DIR);
        setCusrosrPosition(2, (rowNumber / 2) + 2);
        System.out.println(Constants.CREATED_FILES);
        setCusrosrPosition(2, (rowNumber / 2) + 3);
        System.out.println(Constants.SIZE);
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
    public void showDataOnRightWindow(int createdDirectoriesNum, int cretedFilesNum, long size, int positionY) {

        setCusrosrPosition(2, (rowNumber / 2) + 1);
        System.out.println(Constants.CREATED_DIR + createdDirectoriesNum);
        setCusrosrPosition(2, (rowNumber / 2) + 2);
        System.out.println(Constants.CREATED_FILES + cretedFilesNum);
        setCusrosrPosition(2, (rowNumber / 2) + 3);
        System.out.println(Constants.SIZE + size + " B");
    }

    @Override
    public void showPromjene(ArrayList<Promjena> promjene, int j) {
        clearScreen();
        rewriteScreen();

        setCusrosrPosition(2, (j + 2));

        System.out.println("Vrijeme promjene: " + promjene.get(0).getVrijeme());

        setCusrosrPosition(2, (j + 3));
        System.out.println("Opis promjene");
        setCusrosrPosition(2, (j + 4));
        System.out.println("_____________");
        //System.out.println("Naziv elementa   " + promjene.getNazivElementa());
        int i = 0;
        for (Promjena promjena : promjene) {
            i++;
            setCusrosrPosition(2, (5 + j + i));
            System.out.println(promjena.getOpis());
        }
        setCusrosrPosition(3, (rowNumber + 1));
        System.out.print("Unos podataka: ");
    }

    @Override
    public void finished() {
        setCusrosrPosition(3, (rowNumber + 2));
        onEnterPressed();
    }

    @Override
    public void setController(WindowController controller) {
        this.controller = controller;
    }

    int newLine = 0;

    @Override
    public void showDataOnLeftWindow(Element element, int indent, int positionY, boolean endOfScreen) {
        setCusrosrPosition(2, positionY + newLine);
        boolean indentText = false;

        StringBuilder sb = new StringBuilder();

        sb.append(getIndentString(indent, "|"));
        sb.append("+--");
        sb.append(element.getName());

        if (sb.length() >= (columnNumber - 2)) {
            while (sb.length() > 0) {

                if (sb.length() >= (columnNumber - 2)) {
                    indentText = true;
                    newLine++;
                    System.out.print(sb.substring(0, columnNumber - 2));
                    setCusrosrPosition(2, positionY + newLine);
                    sb.delete(0, columnNumber - 2);
                } else {
                    if (indentText) {
                        indentText = false;
                        System.out.print(getIndentString(indent, " "));
                    }
                    System.out.print(sb);
                    sb.setLength(0);
                }

            }
        } else {
            if (endOfScreen) {
                sb.append(Constants.ANSI_ESC + "2J");
            }

            System.out.print(sb);

            if (endOfScreen) {
                rewriteScreen();
            }
        }
    }

    private String getIndentString(int indent, String text) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append(text).append("  ");
        }
        return sb.toString();
    }

    @Override
    public void showAllData(Element element, int indent, int positionY, boolean endOfScreen) {

        StringBuilder sb = new StringBuilder();

        //sb.append(getIndentString(indent));
        sb.append("|+");
        sb.append(element.getName());
        sb.append("\n");
        sb.append("|  Datum kreiranja: ").append(element.getDateCreated());
        sb.append("\n");
        sb.append("|  Datum modificiranja: ").append(element.getDateChanged());
        sb.append("\n");
        sb.append("|  Velicina: ").append(element.getSize());
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

    @Override
    public void showAllStoredStates() {
        clearScreen();
        rewriteScreen();
        setCusrosrPosition(2, 2);

        ElementCareTaker careTaker = ElementCareTaker.getInstance();
        List<ElementMemento> mementoList = careTaker.getMementoList();
        int i = 0;
        for (ElementMemento memento : mementoList) {
            i++;
            Element element = memento.getState();
            System.out.println("|Redni broj: " + i);
            if (element.getStoreDate() != null) {
                System.out.println("|Vrijeme spremanja: " + element.getStoreDate());
            }
        }

        finished();

    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    public void onEnterPressed() {
        setCusrosrPosition(3, (rowNumber + 1));
        System.out.print("Unos podataka: ");
        String command = scanner.nextLine();
        String check = command.split(" ")[0];
        int selectedElement;
        switch (check) {
            case "1":
                controller.selectCommandOne();
                break;
            case "2":
                controller.selectCommandTwo();
                break;
            case "3":
                controller.selectCommandThree();
                break;
            case "4":
                controller.selectCommandFour();
                break;
            case "5":
                controller.selectCommandFive();
                break;
            case "6":
                selectedElement = Integer.parseInt(command.split(" ")[1]);
                controller.selectCommandSix(selectedElement);
                break;
            case "7":
                selectedElement = Integer.parseInt(command.split(" ")[1]);
                controller.selectCommandSeven(selectedElement);
                break;
            case "8":
                controller.selectCommandEight();
                break;
            case "9":
                if (command.split(" ")[1] != null) {
                    String elementName = command.split(" ")[1];
                    controller.selectCommandNine(elementName);
                } else {
                    showErrorMessage("komanda zahtijeva dva argumenta");
                }
                break;

        }

    }

    @Override
    public void showErrorMessage(String message) {
        clearScreen();
        rewriteScreen();
        show(2, 2, 31, "Greška: " + message + "!");
        show(2, 3, 37, "");
        finished();
    }

}
