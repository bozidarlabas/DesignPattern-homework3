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
    private final String programName;
    
    public Arguments(String[] args){
        this.programName = args[0];
        this.rowNumber = parseString(args[1]);
        this.columnNumber = parseString(args[2]);
        this.frameSeparation = args[3];
        this.rootDirectory = args[4];
        this.seconds = parseString(args[5]);
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

    public String getProgramName() {
        return programName;
    }
}
