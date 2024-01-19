package hw4.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import hw4.ImageUtil;
import hw4.imageoperations.BlueComponentOperation;
import hw4.imageoperations.BrightenOperation;
import hw4.imageoperations.GaussianBlurOperation;
import hw4.imageoperations.GrayscaleOperation;
import hw4.imageoperations.GreenComponentOperation;
import hw4.imageoperations.HorizontalFlipOperation;
import hw4.imageoperations.IImageOperation;
import hw4.imageoperations.IntensityComponentOperation;
import hw4.imageoperations.LumaComponentOperation;
import hw4.imageoperations.RedComponentOperation;
import hw4.imageoperations.SepiaOperation;
import hw4.imageoperations.SharpenOperation;
import hw4.imageoperations.ValueComponentOperation;
import hw4.imageoperations.VerticalFlipOperation;
import hw4.model.IImage;
import hw4.model.IModel;
import hw4.view.IGUIView;

/**
 * Represents both an instance of the IAsynchController and the IFeatures. This means the class
 * handles both linking this controller to an IGUIView, adding this (as an IFeatures, not a
 * Controller) to the IGUIView using addFeatures(IFeatures features), and actually implements the
 * different features in this IFeatures.
 */
public class AsynchControllerImpl implements IAsynchController, IFeatures {

  protected IGUIView view;
  protected final IModel model;
  protected final Map<String, Supplier<IImageOperation>> imageOperationMap;

  /**
   * The constructor, which takes in an IModel and assigns it to this.model if it is not null. It
   * also initializes this.imageOperationMap.
   *
   * @param model the given IModel
   * @throws IllegalArgumentException if model is null
   */
  public AsynchControllerImpl(IModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("The given model cannot be null!");
    }

    this.model = model;

    this.imageOperationMap = new HashMap<>();
    this.initializeOperationMap();
  }

  @Override
  public void load(String filepath) {
    if (filepath == null) {
      throw new IllegalArgumentException("The given filepath cannot be null!");
    }

    try {

      IImage image = ImageUtil.readImage(filepath);
      String[] filepathArray = filepath.split("\\".concat(File.separator));
      String filenameWithExtension = filepathArray[filepathArray.length - 1];
      this.model.loadImage(image, filenameWithExtension);

      this.view.addHistograms(image);
      this.view.addImage(ImageUtil.toJavaImage(image), filenameWithExtension);
    } catch (IllegalArgumentException | IllegalStateException e) {
      displayMessage(e.getMessage());
    }
  }

  @Override
  public void save(String filepath, String imageName) {
    if (filepath == null) {
      throw new IllegalArgumentException("The given filepath cannot be null!");
    }

    if (imageName == null) {
      throw new IllegalArgumentException("The given imageName cannot be null!");
    }

    IImage img = this.model.getImage(imageName);
    try {
      ImageUtil.saveImage(img, filepath);
    } catch (IllegalArgumentException | IllegalStateException e) {
      displayMessage(e.getMessage());
    }
  }

  @Override
  public void imageOperation(String operationName, String imageName) {
    if (operationName == null) {
      throw new IllegalArgumentException("The given operationName cannot be null!");
    }

    if (imageName == null) {
      throw new IllegalArgumentException("The given imageName cannot be null!");
    }

    IImageOperation operation = this.imageOperationMap.get(operationName).get();
    IImage image = this.model.getImage(imageName).accept(operation);

    String[] imageNamePrefixSplit = imageName.split("\\.");
    String newImageName = imageNamePrefixSplit[0] + "-"
        + operationName + "." + imageNamePrefixSplit[1];

    this.model.loadImage(image, newImageName);
    this.view.addHistograms(image);
    this.view.addImage(ImageUtil.toJavaImage(image), newImageName);
  }


  @Override
  public void setView(IGUIView view) {
    if (view == null) {
      throw new IllegalArgumentException("The given view cannot be null!");
    }

    this.view = view;
    view.addFeatures(this);
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
