package hw4.controller;

import hw4.imageoperations.GaussianBlurOperation;
import hw4.imageoperations.GrayscaleOperation;
import hw4.imageoperations.SepiaOperation;
import hw4.imageoperations.SharpenOperation;
import hw4.model.IModel;
import hw4.view.IView;

/**
 * Controller extending the functionality of the first image processor controller
 * with supported commands for blur, sharpen, sepia filter, and grayscale filter.
 */
public class ControllerImpl2 extends ControllerImpl {

  /**
   * Instantiates the controller with the given model, input source, and view.
   *
   * @param model    the model to communicate to
   * @param readable the input source
   * @param view     the view to send messages to
   */
  public ControllerImpl2(IModel model, Readable readable, IView view) {
    super(model, readable, view);
  }

  @Override
  protected void initMap() {
    super.initMap();
    this.operationMap.put("grayscale", scanner -> new GrayscaleOperation());
    this.operationMap.put("greyscale", scanner -> new GrayscaleOperation());
    this.operationMap.put("sepia", scanner -> new SepiaOperation());
    this.operationMap.put("sharpen", scanner -> new SharpenOperation());
    this.operationMap.put("blur", scanner -> new GaussianBlurOperation());
  }
}
