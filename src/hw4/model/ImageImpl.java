package hw4.model;

import java.util.ArrayList;
import java.util.List;

import hw4.imageoperations.IImageOperation;

/**
 * Represents the internals of an image through the IImage interface. Represents not just the visual
 * image, but the structured contents of an image file including: width, height, max channel depth,
 * and the pixels. Does not include details such as file name, as those are at a conceptual level
 * about the image itself. In short, this class represents an image, not a file.
 */
public class ImageImpl implements IImage {

  final private int width;
  final private int height;
  final private int maxChannelDepth;

  // INVARIANT: pixels is rectangular at every single moment/instance
  final private List<List<IPixel>> pixels;

  /**
   * The full constructor, requiring a max channel depth and a 2D list of pixels.
   *
   * @param maxChannelDepth the int max channel depth of a color channel for the image. For 8 bits
   *                        (256 values), this would be 255.
   * @param pixels          the 2D list of pixels representing the actual visual component of the
   *                        image.
   * @throws IllegalArgumentException if pixels is null or if a pixel in pixels is invalid. A pixel
   *                                  is invalid if it has a negative component value or if it has a
   *                                  component value that exceeds the given maxChannelDepth.
   */
  public ImageImpl(int maxChannelDepth, List<List<IPixel>> pixels)
      throws IllegalArgumentException {
    this.maxChannelDepth = maxChannelDepth;

    if (pixels == null) {
      throw new IllegalArgumentException("The image must have a non-null 2D list of IPixel.");
    }

    // any row works because all images are rectangular, so we'll just use 0:
    int givenPixelsHeight = pixels.size();
    if (givenPixelsHeight <= 0) {
      throw new IllegalArgumentException("The image must have a positive height.");
    }

    int givenPixelsWidth = pixels.get(0).size();

    if (givenPixelsWidth <= 0) {
      throw new IllegalArgumentException("The image must have a positive width.");
    }

    for (int row = 0; row < givenPixelsHeight; row++) {
      for (int col = 0; col < givenPixelsWidth; col++) {
        IPixel currPixel = pixels.get(row).get(col);

        if (currPixel.getRed() > this.maxChannelDepth
            || currPixel.getGreen() > this.maxChannelDepth
            || currPixel.getBlue() > this.maxChannelDepth) {
          throw new IllegalArgumentException("The pixel at (" + row + "," + col + ") has at least" +
              " one component with an invalid value, greater than the " +
              "maximum channel depth for this image: " + currPixel.toString());
        }
      }
    }

    // Now pixels is assuredly non-null from here on:
    this.pixels = new ArrayList<>(pixels);

    // If we haven't thrown an exception, and we have reached this line, the given pixels
    // have positive row and col num values (positive with and height)
    this.width = givenPixelsWidth;
    this.height = givenPixelsHeight;

    if (maxChannelDepth <= -1) {
      throw new IllegalArgumentException("The image must have a non-negative max channel depth.");
    }
  }

  /**
   * The convenience constructor without max channel depth, requiring just a 2D list of pixels. The
   * max channel depth is set to 255.
   *
   * @param pixels the 2D list of pixels representing the actual visual component of the image.
   * @throws IllegalArgumentException if pixels is null or if a pixel in pixels is invalid. A pixel
   *                                  is invalid if it has a negative component value or if it has a
   *                                  component value that exceeds the given maxChannelDepth.
   */
  public ImageImpl(List<List<IPixel>> pixels) throws IllegalArgumentException {
    this(255, pixels);
  }

  @Override
  public IImage accept(IImageOperation operation) throws IllegalArgumentException {
    List<List<IPixel>> resultPixels = operation.apply(this);
    IImage result = new ImageImpl(this.maxChannelDepth, resultPixels);
    return result;
  }

  @Override
  public IPixel getPixelAt(int row, int col) throws IllegalArgumentException {
    if (row < 0 || col < 0) {
      throw new IllegalArgumentException("Cannot get a pixel at a negative row or column.");
    }

    if (row > this.getHeight() - 1 || col > this.getWidth() - 1) {
      throw new IllegalArgumentException("Cannot get a pixel at a row or column value past the " +
          "dimensions of the image.");
    }

    return this.pixels.get(row).get(col);
  }

  @Override
  public boolean samePixels(IImageState otherImage) {
    boolean sameDimensions = this.width == otherImage.getWidth()
        && this.height == otherImage.getHeight();

    if (!sameDimensions) {
      return false;
    }

    for (int row = 0; row < this.height; row++) {

      for (int col = 0; col < this.width; col++) {
        if (!this.getPixelAt(row, col).equals(otherImage.getPixelAt(row, col))) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getMaxChannelDepth() {
    return this.maxChannelDepth;
  }
}
