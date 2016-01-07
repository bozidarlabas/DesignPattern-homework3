package uzdiz.zadaca;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import uzdiz.zadaca.facade.FileManager;
import uzdiz.zadaca.mvc.controller.WindowController;
import uzdiz.zadaca.mvc.controller.impl.WindowControllerImpl;
import uzdiz.zadaca.mvc.model.Element;
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
        Element rootElement = manager.listDirectory(args[3], null);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        rootElement.setStoreDate(dateFormat.format(date));
        
        
        //Register all services and return registry
        Registry registry = RegistryBuilder.getRegistry(args, rootElement);
        manager.setRegistry(registry);
        manager.saveStructure(rootElement);
        
        //Resolve controller dependency and show window
        WindowController controller = (WindowControllerImpl) registry.resolve("windowController");
        controller.showWindow();
        controller.showData();

      
    }
}