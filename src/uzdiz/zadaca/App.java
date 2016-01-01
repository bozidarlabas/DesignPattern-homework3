package uzdiz.zadaca;

import uzdiz.zadaca.mvc.controller.WindowController;
import uzdiz.zadaca.mvc.controller.impl.WindowControllerImpl;
import uzdiz.zadaca.mvc.model.Arguments;
import uzdiz.zadaca.mvc.view.WindowView;
import uzdiz.zadaca.mvc.view.impl.WindowViewImpl;
import uzdiz.zadaca.registry.Registry;
import uzdiz.zadaca.registry.RegistryBuilder;

/**
 *
 * @author bozidar
 */
public class App {
    
    public static void main(String[] args){
        Registry registry = RegistryBuilder.getRegistry();
        registry.register("arguments", new Arguments(args));
        WindowView view = new WindowViewImpl();
        registry.register("windowView", view);
        
        WindowController controller = new WindowControllerImpl(registry);
        controller.showWindow();
    }
}

//EXAMPLE//
//        Arguments arg = (Arguments) registry.resolve("arguments");
//        System.out.println("Broj redova: " + arg.getRowNumber());
//        System.out.println("Broj stupaca: " + arg.getColumnNumber());
