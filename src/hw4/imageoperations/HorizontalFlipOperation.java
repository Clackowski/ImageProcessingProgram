package hw4.imageoperations;

import hw4.model.IImage;
import hw4.model.IPixel;

/**
 * Represents a function object for getting a horizontal flip of the pixels in an image.
 * For example, the top left and top right pixels of the image would flip values for their
 * corresponding representation in the resulting pixels.
 */
public class HorizontalFlipOperation extends AImageFlipOperation {

  @Override
  protected IPixel getOtherPixel(IImage image, int row, int col) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("The given image cannot be null.");
    }

    return image.getPixelAt(row, image.getWidth() - 1 - col);
  }
}
