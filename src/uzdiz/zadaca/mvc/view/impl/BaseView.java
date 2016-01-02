/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.mvc.view.impl;

import java.util.Scanner;
import uzdiz.zadaca.facade.FileManager;
import uzdiz.zadaca.mvc.controller.impl.WindowControllerImpl;
import uzdiz.zadaca.mvc.model.Element;
import uzdiz.zadaca.utils.Constants;
import uzdiz.zadaca.registry.Registry;

/**
 *
 * @author Labas
 */
public abstract class BaseView {

    private WindowControllerImpl controller;
    private Scanner scanner = new Scanner(System.in);
    public int rowNumber;
    public int columnNumber;

    public BaseView(Registry registry) {
        controller = (WindowControllerImpl) registry.resolve("windowController");
    }

    public abstract void drawWindow(int rowNumber, int columnNumber, String frameSeparation);

    public abstract void showData(Element rootElement, int rowNumber);

    public void onEnterPressed() {
        // String command = scanner.nextLine();
    }

    public void drawScreen(int rowNumber, int columnNumber, String frameSeparation) {
        clearScreen();
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;

        drawRows(rowNumber, columnNumber);
        drawColumns(rowNumber, columnNumber);
        drawWindow(rowNumber, columnNumber, frameSeparation);

        setCusrosrPosition(3, (rowNumber + 1));
        System.out.print("Unos podataka: ");
        onEnterPressed();
    }

    private void clearScreen() {
        System.out.print(Constants.ANSI_ESC + "2J");
        System.out.print("\n");
    }

    private void drawRows(int rowNumber, int columnNumber) {
        int j = 1;
        //ISCRTAVANJE REDAKA
        for (int i = 2; i < (rowNumber + 3); i++) {
            show(i, j, 32, "|");
            show(i, columnNumber, 32, "|");
        }
    }

    private void drawColumns(int rowNumber, int columnNumber) {
        //ISCRTAVANJE STUPACA
        for (int i = 2; i < columnNumber; i++) {
            show(0, i, 32, "_");
            show(rowNumber + 2, i, 32, "-");
            show(rowNumber, i, 32, "-");
        }
    }

    public void setCusrosrPosition(int x, int y) {
        System.out.print(Constants.ANSI_ESC + y + ";" + x + "f");
    }

    public void set(int x, int y) {
        System.out.print(Constants.ANSI_ESC + x + ";" + y + "f");
    }

    public void show(int x, int y, int boja, String tekst) {
        set(x, y);
        System.out.print(Constants.ANSI_ESC + boja + "m");
        System.out.print(tekst);
        /*
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {

        }
         */
    }

    public void showFirstScreenData(Element rootElement) {
       
    }

}
