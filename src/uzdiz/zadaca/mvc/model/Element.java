/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.mvc.model;

import java.util.ArrayList;
import java.util.List;
import uzdiz.zadaca.facade.iterator.Container;
import uzdiz.zadaca.facade.iterator.Iterator;
import uzdiz.zadaca.visitor.ElementVisited;
import uzdiz.zadaca.visitor.ElementVisitor;

/**
 *
 * @author Labas
 */
public class Element implements Container, ElementVisited{
    
    private String name;
    private String type;
    private String dateChanged;
    private String dateCreated;
    private long size;
    private ArrayList<Element> leaf;
    private int level;
    
    public Element(){
        leaf = new ArrayList<>();
    }

    public ArrayList<Element> getLeaf() {
        return leaf;
    }

    public void setLeaf(ArrayList<Element> leaf) {
        this.leaf = leaf;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(String dateChanged) {
        this.dateChanged = dateChanged;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
    

    @Override
    public Iterator getIterator() {
         return new  ElementIterator();
    }

    @Override
    public void accept(ElementVisitor visitor) {
        visitor.visit(this);
    }
    
    public class ElementIterator implements Iterator {
        int index;

        @Override
        public boolean hasNext() {
            return index < leaf.size();
        }

        @Override
        public Element next() {
            if(this.hasNext()){
                return leaf.get(index++);
            }
            return null;
        }
    }
}
