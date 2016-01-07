/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.search.impl;

import uzdiz.zadaca.search.SearchPrinterLayer02;
import uzdiz.zadaca.mvc.model.Element;
import uzdiz.zadaca.mvc.view.impl.BaseView;
import uzdiz.zadaca.utils.Constants;
import uzdiz.zadaca.search.ElementVisited;
import uzdiz.zadaca.search.ElementVisitor;

/**
 *
 * @author Basic
 */

public class Search implements ElementVisitor {

    private String searched;
    private BaseView view;
    private SearchPrinterLayer02 printer;
    
    public Search (String searched, BaseView view) {
        this.searched = searched;
        this.view = view;
    }
    
    @Override
    public void visit(ElementVisited element) {
        Element elem = (Element)element;
        if(elem.getName().equals(searched)){
            if (elem.getType().equals(Constants.DIRECTORY)) {
                printer = new SearchDirectoryPrinter();
            } else {
                printer = new SearchFilePrinter();
            }
            printer.print(elem, view);
        }
    }
    
    public String getSearched() {
        return searched;
    }

    public void setSearched(String searched) {
        this.searched = searched;
    }

    public BaseView getView() {
        return view;
    }

    public void setView(BaseView view) {
        this.view = view;
    }
    
}