package hw4.model;

/**
 * The ImageState interface for our image. Represents the observable functionality of an
 * image.
 */
public interface IImageState {

  /**
   * Returns the pixel at the given (row, col) position. All images have a 2D list of pixels that
   * supports accessing pixels in a (row, col) format.
   *
   * @return the pixel at the given (row, col) position.
   * @throws IllegalArgumentException if the position is out of bounds of the pixels.
   */
  IPixel getPixelAt(int row, int col) throws IllegalArgumentException;

  /**
   * Returns the width of the image as defined by the number of columns in its 2D list of pixels.
   *
   * @return the width of the image.
   */
  int getWidth();

  /**
   * Returns the height of the image as defined by the number of rows in its 2D list of pixels.
   *
   * @return the height of the image.
   */
  int getHeight();

  /**
   * Returns the max channel depth of the image: the maximum int value each channel could reach.
   *
   * @return the max channel depth of the image.
   */
  int getMaxChannelDepth();

  /**
   * Returns true if this IImageState has the same pixels as the other IImageState, false otherwise.
   * Also returns false if the pixel width or pixel height of each image is not the same.
   *
   * @param otherImage the other IImageState to compare this one's pixels to.
   * @return true if this IImageState and the given IImageState
   *         have the same width, height, and pixels.
   */
  boolean samePixels(IImageState otherImage);
}
