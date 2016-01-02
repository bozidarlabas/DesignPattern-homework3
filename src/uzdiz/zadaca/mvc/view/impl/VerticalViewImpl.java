/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.mvc.view.impl;

import uzdiz.zadaca.facade.FileManager;
import uzdiz.zadaca.mvc.model.Element;
import uzdiz.zadaca.registry.Registry;

/**
 *
 * @author Labas
 */
public class VerticalViewImpl extends BaseView{
    
    public VerticalViewImpl(Registry registry) {
        super(registry);
    }

    @Override
        public void drawWindow(int rowNumber, int columnNumber, String frameSeparation) {
        int j = 1;
            for (int i = 2; i < (rowNumber); i++) {
                show(i, columnNumber / 2, 32, "|");
            }
    }

    @Override
    public void showData(Element rootElement, int rowNumber, int columnNumber) {
        FileManager manager = new FileManager();

        setCursorOnLeftWindow();

        manager.printDirectoryTree(rootElement);
         
        setCusrosrPosition(3, (rowNumber + 2));
    }
    
    
    private void setCursorOnLeftWindow(){
        setCusrosrPosition(2, 2);
    }
    
}
