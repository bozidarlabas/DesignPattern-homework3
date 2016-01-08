/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.search;

import uzdiz.zadaca.mvc.model.Element;
import uzdiz.zadaca.mvc.view.impl.BaseView;

/**
 *
 * @author Basic
 */
public interface PrinterLayer02 {
    public void setLowerLayer(Layer01 L01);
    public void print(Element element, BaseView view);
    public void printEndSearch();
}
