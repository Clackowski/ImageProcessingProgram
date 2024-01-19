package hw4.imageoperations;

import java.util.List;
import java.util.function.Function;

import hw4.model.IImage;
import hw4.model.IPixel;

/**
 * Represents an operation that can be applied to an IImage's pixels as a function object.
 * All IImageOperations are imperative, and function by creating a new 2D list of pixels that
 * represents the apply() method's given IImage's pixels after having gone through the relevant
 * operation.
 *
 * <p>More simply put: all IImageOperations have an apply() method which takes in an IImage,
 * goes through every IPixel in the IImage's pixels, makes a new IPixel from it and the relevant
 * operation, and puts that new IPixel in the return 2D list of pixels. The method's given IImage
 * DOES NOT GET CHANGED.
 */
public interface IImageOperation extends Function<IImage, List<List<IPixel>>> {
  /**
   * Applies the relevant operation to the given IImage's pixels. Returns the resulting 2D list
   * of pixels.
   *
   * @param image is the given IImage representing an image.
   * @return a 2D list of pixels that represents the result from performing the relevant operation
   *         on the pixels in the given IImage's 2D list of pixels
   */
  public List<List<IPixel>> apply(IImage image);
}
