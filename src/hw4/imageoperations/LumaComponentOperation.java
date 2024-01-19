package hw4.imageoperations;

import hw4.model.IPixel;

/**
 * Represents a function object for getting a grayscale of the luma value of the pixels in an
 * image. Brightness luma is defined as the weighted sum of the pixel's color values as according
 * to: 0.2126r + 0.7152g + 0.0722b. For example, for an image with a pixel of (210, 42, 250),
 * a new pixel post-operation would be 210*0.2126 + 42*0.7152 + 250*0.0722 == 92: (92, 92, 92).
 * Notice that the answer would technically be 92.7344, but we are truncating since we are storing
 * integers.
 */
public class LumaComponentOperation extends AImageComponentOperation {
  public LumaComponentOperation() {
    super(IPixel::getBrightnessLuma);
  }
}
