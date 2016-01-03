/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.facade.memento;

import uzdiz.zadaca.mvc.model.Element;

/**
 *
 * @author bozidar
 */
public class ElementOriginator {
    private Element state;

    public Element getState() {
        return state;
    }

    public void setState(Element state) {
        this.state = state;
    }
    
    
}
