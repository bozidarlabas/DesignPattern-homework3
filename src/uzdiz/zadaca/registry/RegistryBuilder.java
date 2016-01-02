package uzdiz.zadaca.registry;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import uzdiz.zadaca.mvc.controller.WindowController;
import uzdiz.zadaca.mvc.controller.impl.WindowControllerImpl;
import uzdiz.zadaca.mvc.model.Arguments;
import uzdiz.zadaca.mvc.model.Element;
import uzdiz.zadaca.mvc.view.WindowView;
import uzdiz.zadaca.mvc.view.impl.BaseView;
import uzdiz.zadaca.mvc.view.impl.HorizontalViewImpl;
import uzdiz.zadaca.mvc.view.impl.VerticalViewImpl;
import uzdiz.zadaca.utils.Constants;

/**
 * Created by bozidar on 03.12.2015..
 */
public class RegistryBuilder {

    public static void buildRegistry(String args[], Element elementModel, Registry registry) {
        Arguments arguments = new Arguments(args);
        registry.register("arguments", arguments);

        registry.register("windowView", getCurrentView(arguments.getFrameSeparation(), registry));
        
        WindowController controller = new WindowControllerImpl(registry, elementModel);
        registry.register("windowController", controller);

        registry.register("elementModel", elementModel);
        
       
    }

    public static Registry getRegistry(String args[], Element elementModel) {
        Registry registry = new Registry();
        buildRegistry(args, elementModel, registry);
        return registry;
    }

    public static BaseView getCurrentView(String viewType, Registry registry) {
        BaseView view = null;
        
        switch(viewType){
            case Constants.HORIZONTAL:
                view = new HorizontalViewImpl(registry);
                break;
            case Constants.VERTICAL:
                view = new VerticalViewImpl(registry);
                break;
        }
        return view;
    }
}
