package hw4.imageoperations;

import hw4.model.IPixel;

/**
 * Represents a function object for getting a grayscale of the brightness value of the pixels in an
 * image. Brightness value is defined as the max value of the red, green, and blue
 * channels. For example, for an image with a pixel of (210, 42, 250), a new pixel post-operation
 * would be max(210, 42, 250) == 250: (250, 250, 250).
 */
public class ValueComponentOperation extends AImageComponentOperation {
  public ValueComponentOperation() {
    super(IPixel::getBrightnessValue);
  }
}
