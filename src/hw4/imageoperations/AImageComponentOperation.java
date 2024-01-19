package hw4.imageoperations;

import java.util.function.Function;

import hw4.model.IImage;
import hw4.model.IPixel;
import hw4.model.RGBPixel;

/**
 * Represents the abstraction for all "component" operations. These are the operations which
 * create a grayscale from a certain type of component value or component-based calculation
 * (like Red, Luma, etc.).
 */
public abstract class AImageComponentOperation extends AImageOperation {
  private final Function<IPixel, Integer> getDesiredComponentValue;

  protected AImageComponentOperation(Function<IPixel, Integer> getDesiredComponentValue) {
    this.getDesiredComponentValue = getDesiredComponentValue;
  }

  @Override
  protected IPixel operateOnPixel(IImage image, int row, int col) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("The given image cannot be null.");
    }

    int desiredComponentValue = this.getDesiredComponentValue.apply(image.getPixelAt(row, col));

    IPixel pixel = new RGBPixel(desiredComponentValue, desiredComponentValue,
            desiredComponentValue);

    return pixel;
  }
}
