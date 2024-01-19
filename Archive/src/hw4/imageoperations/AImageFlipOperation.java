package hw4.imageoperations;

import hw4.model.IImage;
import hw4.model.IPixel;
import hw4.model.RGBPixel;

/**
 * Represents the abstraction for all "flip" operations. These are the operations which
 * create a somehow flipped image, like horizontally or vertically flipped images.
 */
public abstract class AImageFlipOperation extends AImageOperation {

  @Override
  protected IPixel operateOnPixel(IImage image, int row, int col) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("The given image cannot be null.");
    }

    IPixel otherPixel = this.getOtherPixel(image, row, col);

    int otherRed = otherPixel.getRed();
    int otherGreen = otherPixel.getGreen();
    int otherBlue = otherPixel.getBlue();

    IPixel pixel = new RGBPixel(otherRed, otherGreen, otherBlue);

    return pixel;
  }

  /**
   * Returns the IPixel at the corresponding flipped location. The type of flip depends on which
   * class this is being abstracted in. Dynamics!
   *
   * @param image the given IImage to source pixel information from.
   * @param row the given row index to look through image's pixels with.
   * @param col the given col index to look through image's pixels with.
   * @return the IPixel at the corresponding flipped location.
   * @throws IllegalArgumentException if the given IImage is null.
   */
  abstract protected IPixel getOtherPixel(IImage image, int row, int col)
          throws IllegalArgumentException;
}
