/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.memento;

import uzdiz.zadaca.mvc.model.Element;

/**
 *
 * @author bozidar
 */
public class ElementMemento {
    private Element state;
    
    public ElementMemento(Element state){
        this.state = state;
    }

    public Element getState() {
        return state;
    }
    
    
}
