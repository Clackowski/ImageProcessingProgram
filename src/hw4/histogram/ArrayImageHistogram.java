package hw4.histogram;

import java.util.function.Function;
import java.awt.Color;

import hw4.model.IImageState;
import hw4.model.IPixel;

/**
 * An IImageHistogram implementation leveraging integer arrays.
 */
public class ArrayImageHistogram implements IImageHistogram {
  private final int[] colorValues;
  private final Color color;
  private final String label;

  /**
   * Fill in the histogram with the pixels in the passed image, using the specified
   * type of pixel value.
   *
   * @param img          the image from which to construct the histogram
   * @param getComponent the function used to extract some value from a pixel
   * @throws IllegalArgumentException if any pixels in the image produce an invalid value
   */
  public ArrayImageHistogram(IImageState img, Function<IPixel, Integer> getComponent,
                             String label, Color color) throws IllegalArgumentException {
    this.colorValues = new int[256];
    this.label = label;
    this.color = color;

    for (int row = 0; row < img.getHeight(); row += 1) {
      for (int col = 0; col < img.getWidth(); col += 1) {
        IPixel pixel = img.getPixelAt(row, col);
        int value = getComponent.apply(pixel);
        this.assertValidValue(value);
        this.colorValues[value] += 1;
      }
    }
  }

  @Override
  public int getValueAt(int value) throws IllegalArgumentException {
    this.assertValidValue(value);
    return this.colorValues[value];
  }

  @Override
  public int getMaxValue() {
    int maxValue = 0;
    for (int i = 0; i < 256; i += 1) {
      if (this.colorValues[i] > maxValue) {
        maxValue = this.colorValues[i];
      }
    }
    return maxValue;
  }

  @Override
  public String getLabel() {
    return this.label;
  }

  @Override
  public Color getColor() {
    return this.color;
  }

  /**
   * Enforce the range of a pixel value being between 0 and 255.
   *
   * @param value the value to check
   * @throws IllegalArgumentException if the value is out of range
   */
  private void assertValidValue(int value) throws IllegalArgumentException {
    if (value < 0 || value > 255) {
      throw new IllegalArgumentException("Pixel value out of range.");
    }
  }
}
