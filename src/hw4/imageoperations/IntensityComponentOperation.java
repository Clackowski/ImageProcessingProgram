package hw4.imageoperations;

import hw4.model.IPixel;

/**
 * Represents a function object for getting a grayscale of the intensity value of the pixels in an
 * image. Brightness intensity is defined as the mean (avg) value of the red, green, and blue
 * channel. For example, for an image with a pixel of (210, 42, 250), a new pixel post-operation
 * would be (210 + 42 + 250) / 3 == 167: (167, 167, 167). Only integer math is used.
 */
public class IntensityComponentOperation extends AImageComponentOperation {
  public IntensityComponentOperation() {
    super(IPixel::getBrightnessIntensity);
  }
}
