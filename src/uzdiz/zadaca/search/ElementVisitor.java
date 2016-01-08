/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.search;

import uzdiz.zadaca.mvc.model.Element;
import uzdiz.zadaca.search.impl.Elements;

/**
 *
 * @author Basic
 */
public interface ElementVisitor {
    public void visit(Element element);
    public void visit(Elements elements);
}
