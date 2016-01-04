package uzdiz.zadaca.registry;

import uzdiz.zadaca.mvc.controller.WindowController;
import uzdiz.zadaca.mvc.controller.impl.WindowControllerImpl;
import uzdiz.zadaca.mvc.model.Arguments;
import uzdiz.zadaca.mvc.model.Element;
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
        
        BaseView view = getCurrentView(arguments.getFrameSeparation());
        registry.register("windowView", view);
        
        WindowController controller = new WindowControllerImpl(registry, elementModel);
        registry.register("windowController", controller);
        
        registry.register("elementModel", elementModel);
        
        view.setRegistry(registry);
    }

    public static Registry getRegistry(String args[], Element elementModel) {
        Registry registry = new Registry();
        buildRegistry(args, elementModel, registry);
        return registry;
    }

    public static BaseView getCurrentView(String viewType) {
        BaseView view = null;
        
        switch(viewType){
            case Constants.HORIZONTAL:
                view = new HorizontalViewImpl();
                break;
            case Constants.VERTICAL:
                view = new VerticalViewImpl();
                break;
        }
        return view;
    }
}
