/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.mvc.view.impl;

import java.util.Scanner;
import uzdiz.zadaca.facade.FileManager;
import uzdiz.zadaca.mvc.controller.impl.WindowControllerImpl;
import uzdiz.zadaca.mvc.model.Arguments;
import uzdiz.zadaca.mvc.model.Element;
import uzdiz.zadaca.utils.Constants;
import uzdiz.zadaca.mvc.view.WindowView;
import uzdiz.zadaca.registry.Registry;

/**
 *
 * @author Labas
 */
public class WindowViewImpl implements WindowView {

    private WindowControllerImpl controller;
    private Scanner scanner = new Scanner(System.in);

    public WindowViewImpl(Registry registry) {
        controller = (WindowControllerImpl) registry.resolve("windowController");
    }

    public void onEnterPressed() {
      // String command = scanner.nextLine();
    }

    @Override
    public void drawScreen(int rowNumber, int columnNumber, String frameSeparation) {
        clearScreen();

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

    private void drawWindow(int rowNumber, int columnNumber, String frameSeparation) {
        int j = 1;
        if (frameSeparation.equals("V")) {
            for (int i = 2; i < (rowNumber); i++) {
                show(i, columnNumber / 2, 32, "|");
            }
        } else if (frameSeparation.equals("O")) {
            for (int i = 2; i < columnNumber; i++) {
                show(rowNumber / 2, i, 32, "-");
            }
        }
    }

    private void setCusrosrPosition(int x, int y) {
        System.out.print(Constants.ANSI_ESC + y + ";" + x + "f");
    }

    private void set(int x, int y) {
        System.out.print(Constants.ANSI_ESC + x + ";" + y + "f");
    }

    private void show(int x, int y, int boja, String tekst) {
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

    @Override
    public void showFirstScreenData(Element rootElement) {
        FileManager manager = new FileManager();

        String filee = manager.printDirectoryTree(rootElement);
        
       System.out.println(filee);
    }

}
