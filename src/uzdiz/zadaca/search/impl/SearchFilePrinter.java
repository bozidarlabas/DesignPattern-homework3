/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.search.impl;

import uzdiz.zadaca.mvc.model.Element;
import uzdiz.zadaca.mvc.view.impl.BaseView;
import uzdiz.zadaca.search.SearchPrinterLayer02;
import uzdiz.zadaca.utils.Constants;

/**
 *
 * @author Basic
 */
public class SearchFilePrinter implements SearchPrinterLayer02 {

    @Override
    public void print(Element element, BaseView view) {
        view.show(3, 2, 32, "Datoteka pronađena!");
        
        view.show(4, 4, 32, element.getName() + " (datoteka)");
        view.show(5, 4, 32, "Kreirano: " + element.getDateCreated());
        view.show(6, 4, 32, "Ažurirano: " + element.getDateChanged());
    }
    
}
