/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.memento;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bozidar
 */
public class ElementCareTaker {

    private static final ElementCareTaker INSTANCE = new ElementCareTaker();

    private List<ElementMemento> mementoList = new ArrayList();

    public ElementCareTaker() {
    }

    public static ElementCareTaker getInstance() {
        return INSTANCE;
    }

    public void add(ElementMemento state) {
        this.mementoList.add(state);
    }

    public List<ElementMemento> getMementoList() {
        return mementoList;
    }

    public ElementMemento getLastFromMementoList() {
        return mementoList.get(mementoList.size() - 1);
    }
    
    public void clearStates(){
        mementoList.clear();
    }

}
