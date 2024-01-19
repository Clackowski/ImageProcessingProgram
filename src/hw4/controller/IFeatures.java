package hw4.controller;

/**
 * The high-level features for handling the types of things a user would want to do in, say,
 * a GUI-based version of our Image Processing application.
 */
public interface IFeatures {

  /**
   * Loads the image with the given filepath into the GUI.
   *
   * @param filepath the String representing the filepath of the desired image
   * @throws IllegalArgumentException if the filepath is null
   */
  void load(String filepath) throws IllegalArgumentException;

  /**
   * Saves the image to the given filepath with the given imageName.
   *
   * @param filepath the String representing the filepath of the desired destination
   * @param imageName the String representing the desired name for the resulting/saved image,
   *                  including the desired extension
   * @throws IllegalArgumentException if the filepath or imageName are null
   */
  void save(String filepath, String imageName) throws IllegalArgumentException;

  /**
   * Performs the operation with the given name on the loaded image with the given name.
   * Since the operation is imperative, a new image is created from it and this is loaded into
   * the relevant model and view. The histograms are also added to reflect this change.
   *
   * @param operationName the name of the desired image operation
   * @param imageName the name of the desired image to be operated on
   * @throws IllegalArgumentException if the operationName or imageName are null
   */
  void imageOperation(String operationName, String imageName) throws IllegalArgumentException;


}
