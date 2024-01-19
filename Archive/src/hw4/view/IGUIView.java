package hw4.view;

import java.awt.Image;

import hw4.controller.IFeatures;
import hw4.model.IImage;

/**
 * Represents a general GUI-based IView of our ImageProcessing program.
 */
public interface IGUIView extends IView {

  /**
   * Adds/implements the given IFeatures to this IGUIView, which represent the high level
   * features of the GUI-related elements of the program.
   *
   * @param features the IFeatures implementation to use for this IGUIView, when needing to
   *                 handle events with core features as a response and such
   * @throws IllegalArgumentException if the features is null
   */
  void addFeatures(IFeatures features) throws IllegalArgumentException;

  /**
   * Adds the given image to the GUI, using the given imageName.
   *
   * @param image the image to add
   * @param imageName the name of the image to add
   * @throws IllegalArgumentException if image or imageName are null
   */
  void addImage(Image image, String imageName) throws IllegalArgumentException;

  /**
   * Add the histograms from a new image into the view.
   * @param image the image to make the histograms from
   * @throws IllegalArgumentException if image is null
   */
  void addHistograms(IImage image) throws IllegalArgumentException;



  /**
   * Prompt the user with a GUI element containing the given message and return the user's input.
   *
   * @param prompt the prompt to show the user that instructs them on what to input
   * @return the input from the user into the GUI element input field
   * @throws IllegalArgumentException if prompt is null
   */
  String promptUser(String prompt) throws IllegalArgumentException;
}
