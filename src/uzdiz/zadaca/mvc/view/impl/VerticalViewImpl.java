/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.mvc.view.impl;

import uzdiz.zadaca.facade.FileManager;
import uzdiz.zadaca.listener.OnDataLoaded;
import uzdiz.zadaca.mvc.model.Element;
import uzdiz.zadaca.registry.Registry;
import uzdiz.zadaca.utils.Constants;

/**
 *
 * @author Labas
 */
public class VerticalViewImpl extends BaseView implements OnDataLoaded {

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
    public void showData(Element rootElement, int rowNumber) {
        init();
        FileManager manager = new FileManager(this, registry);
        manager.printDirectoryTree(rootElement);
    }

    private void init() {
        setCusrosrPosition((columnNumber / 2) + 1, 2);
        System.out.println(Constants.CREATED_DIR);
        setCusrosrPosition((columnNumber / 2) + 1, 3);
        System.out.println(Constants.CREATED_FILES);
        setCusrosrPosition((columnNumber / 2) + 1, 4);
        System.out.println(Constants.SIZE);
    }

    @Override
    public void showDataOnLeftWindow(StringBuilder sb, int positionY, boolean endOfScreen) {
        setCusrosrPosition(2, positionY);

        System.out.print(sb);
        
        if (endOfScreen) {
            rewriteScreen();
        }
        
        
        
    }

    @Override
    public void showDataOnRightWindow(int createdDirectoriesNum, int cretedFilesNum, long size, int positionY) {
        setCusrosrPosition((columnNumber / 2) + 1, 2);
        System.out.println(Constants.CREATED_DIR + createdDirectoriesNum);
        setCusrosrPosition((columnNumber / 2) + 1, 3);
        System.out.println(Constants.CREATED_FILES + cretedFilesNum);
        setCusrosrPosition((columnNumber / 2) + 1, 4);
        System.out.println(Constants.SIZE + size + " B");

    }

    @Override
    public void finished() {
        setCusrosrPosition(3, (rowNumber + 2));
    }

    

}
