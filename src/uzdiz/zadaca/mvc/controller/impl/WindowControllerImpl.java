/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.mvc.controller.impl;

import uzdiz.zadaca.mvc.controller.WindowController;
import uzdiz.zadaca.mvc.model.Arguments;
import uzdiz.zadaca.mvc.model.Element;
import uzdiz.zadaca.mvc.view.WindowView;
import uzdiz.zadaca.registry.Registry;

/**
 *
 * @author Labas
 */
public class WindowControllerImpl implements WindowController{

    private final Arguments arguments;
    private final Element model;
    private final WindowView view;
    
    public WindowControllerImpl(Registry registry, Element model){
        this.arguments = (Arguments) registry.resolve("arguments");
        this.view = (WindowView) registry.resolve("windowView");
        this.model = model;
    }
            
    @Override
    public void showWindow() {
        view.drawScreen(arguments.getRowNumber(), arguments.getColumnNumber(), arguments.getFrameSeparation());
    }

    @Override
    public void showFirstWindowData() {
        view.showFirstScreenData(model);
    }

    @Override
    public void showSecondWindowData() {
        
    }
    
}
