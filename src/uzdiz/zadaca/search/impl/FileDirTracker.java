/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.search.impl;

import java.io.File;
import uzdiz.zadaca.facade.iterator.Iterator;
import uzdiz.zadaca.mvc.model.Element;
import uzdiz.zadaca.mvc.view.impl.BaseView;
import uzdiz.zadaca.search.ElementVisitor;
import uzdiz.zadaca.search.Layer01;
import uzdiz.zadaca.utils.Constants;

/**
 *
 * @author Basic
 */
public class FileDirTracker implements Layer01{
    private Element rootElement;
    private String searchedPath;
    private String searched;
    
    public FileDirTracker(Element rootElement) {
        this.rootElement = rootElement;
    }
    
    private void iterate(Element element, String path) {
        for (Iterator iterCurrent = element.getIterator(); iterCurrent.hasNext();) {
            Element elem = (Element) iterCurrent.next();
            if(elem.getName().equals(searched)){
                searchedPath = path + File.separator + elem.getName();
                return;
            }
            if(elem.getType().equals(Constants.DIRECTORY)){
                iterate(elem, path + File.separator + elem.getName());
            }
        }
    }
    
    @Override
    public String service(String searched) {
        this.searched = searched;
        iterate(rootElement, rootElement.getName());
        return searchedPath;
        
    }
    
}
