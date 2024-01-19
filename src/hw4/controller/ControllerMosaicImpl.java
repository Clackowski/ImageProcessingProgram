package hw4.controller;

import hw4.imageoperations.BlueComponentOperation;
import hw4.imageoperations.BrightenOperation;
import hw4.imageoperations.GaussianBlurOperation;
import hw4.imageoperations.GrayscaleOperation;
import hw4.imageoperations.GreenComponentOperation;
import hw4.imageoperations.HorizontalFlipOperation;
import hw4.imageoperations.IntensityComponentOperation;
import hw4.imageoperations.LumaComponentOperation;
import hw4.imageoperations.MosaicOperation;
import hw4.imageoperations.RedComponentOperation;
import hw4.imageoperations.SepiaOperation;
import hw4.imageoperations.SharpenOperation;
import hw4.imageoperations.ValueComponentOperation;
import hw4.imageoperations.VerticalFlipOperation;
import hw4.model.IModel;
import hw4.view.IGUIView;
import java.io.IOException;

public class ControllerMosaicImpl extends AsynchControllerImpl implements IAsynchController {

  /**
   * The constructor, which takes in an IModel and assigns it to this.model if it is not null. It
   * also initializes this.imageOperationMap.
   *
   * @param model the given IModel
   * @throws IllegalArgumentException if model is null
   */
  public ControllerMosaicImpl(IModel model) throws IllegalArgumentException {
    super(model);

    this.initializeOperationMap();
  }

  /**
   * Initializes this.imageOperationMap so that all command text matches up to the correct
   * IImageOperation and so that any potential String input prompted from the user can be dealt
   * with/used accordingly if needed (Any input made into a view's dialogue box/input format will
   * always be a String).
   */
  private void initializeOperationMap() {
    this.imageOperationMap.put("brighten", () -> {

      String response = this.view.promptUser("Enter a positive integer to brighten by or a" +
          " negative integer to darken by.");

      if (!response.matches("-[0-9]+|[0-9]+")) {
        displayMessage("Must enter an integer, with or without a negative" +
            " sign at the front.");
      }

      int increment = Integer.parseInt(response);

      return new BrightenOperation(increment);
    });
    this.imageOperationMap.put("mosaic", () -> {

      String response = this.view.promptUser(
          "Enter a positive integer to choose how many seeds to use.");

      if (!response.matches("-[0-9]+|[0-9]+")) {
        displayMessage("Must enter an integer, with or without a negative" +
            " sign at the front.");
      }

      int numSeeds = Integer.parseInt(response);

      return new MosaicOperation(numSeeds);
    });
    this.imageOperationMap.put("red-component", RedComponentOperation::new);
    this.imageOperationMap.put("green-component", GreenComponentOperation::new);
    this.imageOperationMap.put("blue-component", BlueComponentOperation::new);
    this.imageOperationMap.put("value-component", ValueComponentOperation::new);
    this.imageOperationMap.put("intensity-component", IntensityComponentOperation::new);
    this.imageOperationMap.put("luma-component", LumaComponentOperation::new);
    this.imageOperationMap.put("vertical-flip", VerticalFlipOperation::new);
    this.imageOperationMap.put("horizontal-flip", HorizontalFlipOperation::new);
    this.imageOperationMap.put("grayscale", GrayscaleOperation::new);
    this.imageOperationMap.put("sepia", SepiaOperation::new);
    this.imageOperationMap.put("sharpen", SharpenOperation::new);
    this.imageOperationMap.put("blur", GaussianBlurOperation::new);
  }

  private void displayMessage(String message) throws IllegalStateException {
    try {
      this.view.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException("An error occurred in displaying a message.");
    }
  }
}
