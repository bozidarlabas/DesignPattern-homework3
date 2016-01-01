/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.mvc.model;

/**
 *
 * @author bozidar
 */
public class Arguments {
    private final int rowNumber;
    private final int columnNumber;
    private final String frameSeparation;
    private final String rootDirectory;
    private final int seconds;
    
    public Arguments(String[] args){
        this.rowNumber = parseString(args[0]);
        this.columnNumber = parseString(args[1]);
        this.frameSeparation = args[2];
        this.rootDirectory = args[3];
        this.seconds = parseString(args[4]);
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public String getFrameSeparation() {
        return frameSeparation;
    }

    public int getSeconds() {
        return seconds;
    }

    private int parseString(String data){
        return Integer.parseInt(data);
    }

    public String getRootDirectory() {
        return rootDirectory;
    }

 
}
