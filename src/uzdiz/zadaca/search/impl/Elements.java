/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.search.impl;

import uzdiz.zadaca.facade.iterator.Iterator;
import uzdiz.zadaca.mvc.model.Element;
import uzdiz.zadaca.utils.Constants;
import uzdiz.zadaca.search.ElementVisited;
import uzdiz.zadaca.search.ElementVisitor;

/**
 *
 * @author Basic
 */
public class Elements implements ElementVisited {
    
    Element rootElement;
    
    public Elements (Element rootElement) {
        this.rootElement = rootElement;
        
    }
    
    private void iterate(Element element, ElementVisitor visitor) {
        for (Iterator iterCurrent = element.getIterator(); iterCurrent.hasNext();) {
            Element elem = (Element) iterCurrent.next();
            elem.accept(visitor);
            if(elem.getType().equals(Constants.DIRECTORY)){
                iterate(elem, visitor);
            }
        }
    }
    
    @Override
    public void accept(ElementVisitor visitor) {
        iterate(rootElement, visitor);
    }    
}
