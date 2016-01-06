/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.visitor;

/**
 *
 * @author Basic
 */
public interface ElementVisited {
    public void accept(ElementVisitor visitor);
}