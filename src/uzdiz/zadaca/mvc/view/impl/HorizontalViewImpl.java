/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.mvc.view.impl;

import uzdiz.zadaca.facade.FileManager;
import uzdiz.zadaca.mvc.controller.WindowController;
import uzdiz.zadaca.mvc.model.Element;
import uzdiz.zadaca.mvc.model.Promjena;
import uzdiz.zadaca.registry.Registry;

/**
 *
 * @author Labas
 */
public class HorizontalViewImpl extends BaseView {

  

    @Override
    public void drawWindow(int rowNumber, int columnNumber, String frameSeparation) {
        int j = 1;
        for (int i = 2; i < columnNumber; i++) {
            show(rowNumber / 2, i, 32, "-");
        }
    }

    @Override
    public void showData(Element rootElement, int rowNumber) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showElementsNumber(int DirectoryNumber, int FilesNumber) {

    }


    @Override
    public void showDataOnRightWindow(int createdDirectoriesNum, int cretedFilesNum, long size, int positionY) {
        
    }
    
     public void showPromjene(Promjena promjene, int j ) {
       
    }

    @Override
    public void finished() {
         
    }

    @Override
    public void setController(WindowController controller) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showDataOnLeftWindow(Element element, int indent, int positionY, boolean endOfScreen) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showAllData(Element element, int indent, int positionY, boolean endOfScreen) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showAllStoredStates() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showMessage(String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
