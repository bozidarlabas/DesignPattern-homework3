/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.search.impl;

import uzdiz.zadaca.mvc.model.Element;
import uzdiz.zadaca.mvc.view.impl.BaseView;
import uzdiz.zadaca.search.ElementVisitor;
import uzdiz.zadaca.search.PrinterLayer02;

/**
 *
 * @author Basic
 */

public class Search implements ElementVisitor {

    private String searched;
    private BaseView view;
    private PrinterLayer02 printer;
    private Element rootElement;
    
    public Search (String searched, BaseView view, Element rootElement) {
        this.searched = searched;
        this.view = view;
        this.rootElement = rootElement;
        this.printer = new Printer();
    }
    
    @Override
    public void visit(Element element) {
        Element elem = (Element)element;
        if(elem.getName().equals(searched)){
            FileDirTracker fdt = new FileDirTracker(rootElement);
            printer.setLowerLayer(fdt);
            printer.print(elem, view);
        }
    }
    
    @Override 
    public void visit(Elements elements) {
        printer.printEndSearch();
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