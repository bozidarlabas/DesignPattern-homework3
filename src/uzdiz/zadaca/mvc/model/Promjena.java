/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.mvc.model;

import java.util.ArrayList;
import uzdiz.zadaca.facade.iterator.Iterator;

/**
 *
 * @author Tajana
 */
public class Promjena {
    
    private int rdBroj=0;
    private String vrijeme;
    private ArrayList<String> opis;
    private String nazivElementa;
    private String putanja;
    private ArrayList<Promjena> leaf;
    
    public Promjena(){
        leaf = new ArrayList<>();
    }

    public ArrayList<Promjena> getLeaf() {
        return leaf;
    }

    public void setLeaf(ArrayList<Promjena> leaf) {
        this.leaf = leaf;
    }
    

    public String getNazivElementa() {
        return nazivElementa;
    }

    public void setNazivElementa(String nazivElementa) {
        this.nazivElementa = nazivElementa;
    }

    public String getPutanja() {
        return putanja;
    }

    public void setPutanja(String putanja) {
        this.putanja = putanja;
    }

    public int getRdBroj() {
        return rdBroj;
    }

    public void setRdBroj(int rdBroj) {
        this.rdBroj = rdBroj;
    }

    public String getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(String vrijeme) {
        this.vrijeme = vrijeme;
    }

    public ArrayList<String> getOpis() {
        return opis;
    }

    public void setOpis(ArrayList<String> opis) {
        this.opis = opis;
    }
    
    public Iterator getIterator() {
         return new  ElementIterator();
    }
    
    public class ElementIterator implements Iterator {
        int index;

        @Override
        public boolean hasNext() {
            return index < leaf.size();
        }

        @Override
        public Promjena next() {
            if(this.hasNext()){
                return leaf.get(index++);
            }
            return null;
        }
    }
}
