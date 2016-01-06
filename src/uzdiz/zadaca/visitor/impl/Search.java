/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.visitor.impl;

import uzdiz.zadaca.mvc.model.Element;
import uzdiz.zadaca.mvc.view.impl.BaseView;
import uzdiz.zadaca.utils.Constants;
import uzdiz.zadaca.visitor.ElementVisited;
import uzdiz.zadaca.visitor.ElementVisitor;

/**
 *
 * @author Basic
 */

public class Search implements ElementVisitor {

    private String searched;
    private BaseView view;
    
    public Search (String searched, BaseView view) {
        this.searched = searched;
        this.view = view;
    }
    
    @Override
    public void visit(ElementVisited element) {
        Element elem = (Element)element;
        if(elem.getName().equals(searched)){
            print(elem);
        }
    }
    
    private void print(Element element) {
        if (element.getType().equals(Constants.DIRECTORY)) {
            view.show(3, 2, 32, "Direktorij pronađen!");
        } else {
            view.show(3, 2, 32, "Datoteka pronađena!");
        }
        view.show(4, 4, 32, element.getName() + " (" + element.getType() + ")");
        view.show(5, 4, 32, "Kreirano: " + element.getDateCreated());
        view.show(6, 4, 32, "Ažurirano: " + element.getDateChanged());
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