package hw4.model;

/**
 * Representation of the collection of loaded pictures in the image program.
 */
public interface IModel {

  /**
   * Adds the given image to the collection of images loaded into the program with the given
   * filename. If an image with the same name has already been loaded, the new image will
   * overwrite the old one.
   * @param image the image to add to the model
   * @param name the name of the image to add
   */
  public void loadImage(IImage image, String name);

  /**
   * Finds the image loaded into the model.
   * @param name the name of the image to find
   * @return the image to find
   * @throws IllegalArgumentException if no image with the given name has been loaded into
   *                                  the program
   */
  public IImage getImage(String name) throws IllegalArgumentException;
}
