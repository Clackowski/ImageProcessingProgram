package hw4.imageoperations;

import hw4.model.IPixel;

/**
 * Represents a function object for getting a grayscale of the blue component of the pixels in
 * an image. For example, if a pixel in the original image was (250, 24, 22), the corresponding
 * pixel in the resulting pixels would be (22, 22, 22).
 */
public class BlueComponentOperation extends AImageComponentOperation {
  public BlueComponentOperation() {
    super(IPixel::getBlue);
  }
}
