package hw4.histogram;

import java.awt.Color;

/**
 * A data type which stores the distribution of pixel values in an image, for a certain type of
 * value. For example, one could create an image histogram for all red values in an image, or
 * the luma values of the pixels in an image. These values must range from 0 to 255.
 */
public interface IImageHistogram {

  /**
   * Get the number of pixels in the image which have the specified value.
   * @param value the pixel value to check at
   * @return the number of pixels with the specified value
   * @throws IllegalArgumentException if the value is out of bounds
   */
  int getValueAt(int value) throws IllegalArgumentException;

  /**
   * Get the quantity of the highest value in the histogram.
   * @return the highest value
   */
  int getMaxValue();

  /**
   * Get the label of the histogram.
   */
  String getLabel();

  /**
   * Get the color of the histogram.
   */
  Color getColor();
}
