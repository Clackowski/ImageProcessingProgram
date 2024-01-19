package hw4.model;

/**
 * Represents a pixel to be used in an image containing three channels: red, green, and blue.
 */
public interface IPixel {
  /**
   * Returns the int value of the red channel.
   *
   * @return the int value of the red channel.
   */
  int getRed();

  /**
   * Returns the int value of the green channel.
   *
   * @return the int value of the green channel.
   */
  int getGreen();

  /**
   * Returns the int value of the blue channel.
   *
   * @return the int value of the blue channel.
   */
  int getBlue();

  /**
   * Returns the int value of the brightness value. Brightness value is defined as the
   * maximum value of the red, green, and blue channel. For example, for an image with a pixel of
   * (210, 42, 250), we would return 250.
   *
   * @return the int value of the brightness value.
   */
  int getBrightnessValue();

  /**
   * Returns the int value of the brightness intensity. Brightness intensity is defined as the
   * mean (avg) value of the red, green, and blue channel. For example, for an image with a pixel of
   * (210, 42, 250), we would return (210 + 42 + 251) / 3 == 167. Notice that the answer would
   * technically be 167.66667, but we are using integer math since we are storing integers.
   *
   * @return the int value of the brightness intensity.
   */
  int getBrightnessIntensity();

  /**
   * Returns the int value of the brightness luma. Brightness luma is defined as the
   * weighted sum of the red, green, and blue channel. The weights are 0.2126 for red,
   * 0.7152 for green, and 0.0722 for blue. For example, for an image with a pixel of
   * (210, 42, 250), we would return 210*0.2126 + 42*0.7152 + 250*0.0722 == 92. Notice that the
   * answer would technically be 92.7344, but we are truncating since we are storing integers.
   * Note: BigDecimal is used instead of double math, so we never have to worry about floating point
   * issues nor rounding.
   *
   * @return the int value of the brightness luma.
   */
  int getBrightnessLuma();

  /**
   * Returns a string representation of the pixel as appropriate. For example, this could be
   * including the components in parentheses like (r:rValue,g:gValue,b:bValue).
   *
   * @return a string representation of the pixel.
   */
  @Override
  String toString();


  @Override
  int hashCode();

  @Override
  boolean equals(Object other);
}
