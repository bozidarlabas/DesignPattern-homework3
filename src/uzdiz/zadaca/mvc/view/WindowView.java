/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.mvc.view;

import uzdiz.zadaca.mvc.model.Arguments;
import uzdiz.zadaca.mvc.model.Element;

/**
 *
 * @author Labas
 */
public interface WindowView {
    public void drawScreen(int rowNumber, int columnNumber, String frameSeparation);
    
    public void showFirstScreenData(Element element);
    
    public void onEnterPressed();
}
