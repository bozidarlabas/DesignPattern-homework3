package uzdiz.zadaca.registry;

import java.util.ArrayList;
import uzdiz.zadaca.mvc.controller.WindowController;
import uzdiz.zadaca.mvc.controller.impl.WindowControllerImpl;
import uzdiz.zadaca.mvc.model.Arguments;
import uzdiz.zadaca.mvc.model.Element;
import uzdiz.zadaca.mvc.view.WindowView;
import uzdiz.zadaca.mvc.view.impl.WindowViewImpl;

/**
 * Created by bozidar on 03.12.2015..
 */
public class RegistryBuilder {
    
     public static void buildRegistry(String args[], Element elementModel, Registry registry){
        registry.register("arguments", new Arguments(args));
        
        WindowView view = new WindowViewImpl(registry);
        registry.register("windowView", view);
        
        WindowController controller = new WindowControllerImpl(registry, elementModel);
        registry.register("windowController", controller);
        
        registry.register("elementModel", elementModel);
    }

 
    public static Registry getRegistry(String args[], Element elementModel){
        Registry registry = new Registry();
         buildRegistry(args, elementModel, registry);
        return registry;
    }
}