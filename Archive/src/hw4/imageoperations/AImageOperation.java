package hw4.imageoperations;

import java.util.ArrayList;
import java.util.List;

import hw4.model.IImage;
import hw4.model.IPixel;

/**
 * Represents an abstract operation on an IImage. Since the apply() method will always
 * be looping through its given image's pixels, this abstract class serves to abstract that
 * functionality and have it use a protected method that's left abstracted to the individual
 * Function Object classes. This protected method is what actually does the specific operation
 * for a single pixel.
 */
public abstract class AImageOperation implements IImageOperation {

  @Override
  public List<List<IPixel>> apply(IImage image) {
    if (image == null) {
      throw new IllegalArgumentException("The given image cannot be null.");
    }

    int width = image.getWidth();
    int height = image.getHeight();
    List<List<IPixel>> resultPixels = new ArrayList<>(0);

    for (int row = 0; row < height; row++) {
      resultPixels.add(new ArrayList<>()); // adds new empty row to ensure we can use get
      for (int col = 0; col < width; col++) {
        IPixel pixel = this.operateOnPixel(image, row, col);

        resultPixels.get(row).add(pixel);
      }
    }
    return resultPixels;
  }

  /**
   * Performs the relevant operation (based on what class it is implemented in) on the pixel
   * in the given image's pixels at the given row and column value. Returns the result of the
   * operation (without modifying the original/target pixel).
   *
   * @param image   is the IImage provided (for sourcing original pixel information from)
   * @param row is the row position of the target pixel
   * @param col is the col position of the target pixel
   * @return a new IPixel based on the target, but with the operation applied
   * @throws IllegalArgumentException if the given image is null or the row and col value is out
   *                                  of bounds for the image.
   */
  protected abstract IPixel operateOnPixel(IImage image, int row, int col)
          throws IllegalArgumentException;

  /**
   * Clamps an integer to 0 if it is negative or to the given clamp (upper limit) if it is
   * greater than the clamp. Otherwise, the integer is left unchanged.
   * Returns the resulting integer.
   *
   * @param value the value to clamp, if out of bounds
   * @param clamp the upper limit to clamp greater values to
   * @return the clamped (if necessary) integer
   */
  protected int clamp(int value, int clamp) {
    if (value < 0) {
      return 0;
    } else {
      return Math.min(value, clamp);
    }
  }
}
