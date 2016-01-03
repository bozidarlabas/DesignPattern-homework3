package uzdiz.zadaca;

import uzdiz.zadaca.facade.FileManager;
import uzdiz.zadaca.mvc.controller.WindowController;
import uzdiz.zadaca.mvc.controller.impl.WindowControllerImpl;
import uzdiz.zadaca.registry.Registry;
import uzdiz.zadaca.registry.RegistryBuilder;

/**
 *
 * @author bozidar
 */
public class App {

    public static void main(String[] args) {

        //List and store directories and files inside element model
        FileManager manager = new FileManager();
        manager.listDirectory("/Users/macbook/Workspaces/UzDiz/zadace/uzdiz", null);

        //Register all services and return registry
        Registry registry = RegistryBuilder.getRegistry(args, manager.getElementModel());

        //Resolve controller dependency and show window
        WindowController controller = (WindowControllerImpl) registry.resolve("windowController");
        controller.showWindow();
        controller.showData();

      
    }
}