package hw4.controller;

import hw4.imageoperations.MosaicOperation;
import hw4.model.IModel;
import hw4.view.IView;

/**
 * MosaicScriptControllerImpl class.
 */
public class MosaicScriptControllerImpl extends ControllerImpl2 {

  /**
   * Instantiates the controller with the given model, input source, and view.
   *
   * @param model    the model to communicate to
   * @param readable the input source
   * @param view     the view to send messages to
   */
  public MosaicScriptControllerImpl(IModel model, Readable readable, IView view) {
    super(model, readable, view);
  }

  /**
   * Initmap function.
   */
  protected void initMap() {
    super.initMap();
    this.operationMap.put("mosaic", scanner -> {
          if (scanner.hasNextInt()) {
            return new MosaicOperation(scanner.nextInt());
          } else {
            scanner.next();
            scanner.next();
            scanner.next();
            throw new IllegalArgumentException("Need to pass an integer with mosaic!");
          }
        }
    );
  }
}
