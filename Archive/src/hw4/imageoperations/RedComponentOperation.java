package hw4.imageoperations;

import hw4.model.IPixel;

/**
 * Represents a function object for getting a grayscale of the red component of the pixels in
 * an image. For example, if a pixel in the original image was (250, 24, 22), the corresponding
 * pixel in the resulting pixels would be (250, 250, 250).
 */
public class RedComponentOperation extends AImageComponentOperation {
  public RedComponentOperation() {
    super(IPixel::getRed);
  }
}
