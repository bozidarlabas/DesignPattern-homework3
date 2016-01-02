package uzdiz.zadaca;

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
        manager.listDirectory("C:\\Users\\Labas\\Documents\\uzdiz", null);

        //Register all services and return registry
        Registry registry = RegistryBuilder.getRegistry(args, manager.getElementModel());

        //Resolve controller dependency and show window
        WindowController controller = (WindowControllerImpl) registry.resolve("windowController");
        controller.showWindow();
        controller.showData();

      
    }
}

    
/*
    public static void iterate(Element element) {
        setTab(element.getLevel());

        for (Iterator iter = element.getIterator(); iter.hasNext();) {
            Element elementModel = (Element) iter.next();

            if (elementModel.getType().equals(Constants.DIRECTORY)) {

                System.out.println("Name : " + elementModel.getName() + " (velicina: " + elementModel.getSize() + " B)");

                iterate(elementModel);
            } else {
                setTab(element.getLevel());
                System.out.println("Name : " + elementModel.getName() + " (velicina: " + elementModel.getSize() + " B)");
            }

        }
    }

    private static void setTab(int size) {
        for (int i = 0; i < size; i++) {
            System.out.print("\t");
        }
    }
}
*/