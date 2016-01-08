/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.search.impl;

import uzdiz.zadaca.mvc.model.Element;
import uzdiz.zadaca.mvc.view.impl.BaseView;
import uzdiz.zadaca.search.Layer01;
import uzdiz.zadaca.search.PrinterLayer02;
import uzdiz.zadaca.utils.Constants;

/**
 *
 * @author Basic
 */
public class Printer implements PrinterLayer02 {
    private Layer01 level01;
    
    @Override
    public void print(Element element, BaseView view) {
        int lineCounter = 3;
        if (element.getType().equals(Constants.DIRECTORY)) {
            view.show(lineCounter++, 2, 32, "Direktorij pronađen!");
        } else {
            view.show(lineCounter++, 2, 32, "Datoteka pronađena!");
        }
    
        String result = level01.service(element.getName());
        int winLimit = view.columnNumber;
        if (view.getFrameSeparation().equals("V")) {
            winLimit = view.columnNumber / 2;
        }
        
        StringBuilder sb = new StringBuilder(result);
        if (sb.length() >= (winLimit - 4)) {
            view.show(lineCounter++, 4, 32, sb.substring(0, winLimit - 4));
            sb.delete(0, winLimit - 4);
            view.show(lineCounter++, 4, 32, sb.toString());
        } else {
            view.show(lineCounter++, 4, 32, result);
        }
        
        view.show(lineCounter++, 4, 32, "Kreirano: " + element.getDateCreated());
        view.show(lineCounter++, 4, 32, "Ažurirano: " + element.getDateChanged());
    }

    @Override
    public void printEndSearch() {
        System.out.print(Constants.ANSI_ESC + "1B");
        // always stops at the beginning of line
        System.out.print(Constants.ANSI_ESC + "1000D");
        System.out.print(Constants.ANSI_ESC + "1C");
        
        System.out.print("Pretraga završena!");
    }   

    @Override
    public void setLowerLayer(Layer01 level01) {
        this.level01 = level01;
    }
}
