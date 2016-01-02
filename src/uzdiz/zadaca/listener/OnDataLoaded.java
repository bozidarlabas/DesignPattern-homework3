/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.listener;

import uzdiz.zadaca.mvc.model.Element;

/**
 *
 * @author Labas
 */
public interface OnDataLoaded {

    public void showDataOnLeftWindow(Element element, int indent, int positionY, boolean endOfScreen);

    public void showDataOnRightWindow(int createdDirectoriesNum, int cretedFilesNum, long size, int positionY);
    
    public void finished();
}
