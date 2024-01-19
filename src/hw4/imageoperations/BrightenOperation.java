package hw4.imageoperations;

import hw4.model.IImage;
import hw4.model.IPixel;
import hw4.model.RGBPixel;

/**
 * Represents a function object for brightening pixels by the given
 * increment (or darkening them if the increment is negative). Works using the concept of brightness
 * intensity: the increment is added to each of the relevant channels all the same
 * (i.e. not weighted like Luma).
 *
 * <p>Also note that entering an increment that would bring a pixel channel
 * below the minimum or above the maximum simply results in the channel value getting clamped to
 * the minimum or clamped to the maximum, respectively. For example: an increment of -200 for
 * a pixel with RGB values (200, 220, 40) and max channel depth of 255 results in a pixel
 * with RGB values (0, 20, 0) for, say, a pixel in an ImageImpl's pixels. Similarly, an
 * increment of 200 would result in (255, 255, 240) for that same original pixel.
 */
public class BrightenOperation extends AImageOperation {
  private final int increment;

  public BrightenOperation(int increment) {
    this.increment = increment;
  }

  @Override
  protected IPixel operateOnPixel(IImage image, int row, int col) {
    if (image == null) {
      throw new IllegalArgumentException("The given image cannot be null.");
    }

    int depth = image.getMaxChannelDepth();
    int originalRed = image.getPixelAt(row, col).getRed();
    int originalGreen = image.getPixelAt(row, col).getGreen();
    int originalBlue = image.getPixelAt(row, col).getBlue();

    int newRed = this.clamp(originalRed + this.increment,
            depth);

    int newGreen = this.clamp(originalGreen + this.increment,
            depth);

    int newBlue = this.clamp(originalBlue + this.increment,
            depth);

    IPixel pixel = new RGBPixel(newRed, newGreen, newBlue);

    return pixel;
  }
}
