package hw4.imageoperations;

import java.math.BigDecimal;
import java.util.function.Function;

import hw4.model.IImage;
import hw4.model.IPixel;
import hw4.model.RGBPixel;

/**
 * Abstract class representing a convolution operation on images used for filtering.
 */
public abstract class AImageConvolutionOperation extends AImageOperation {

  private final double[][] kernel;

  /**
   * Instantiate the convolution operation with the given kernel.
   *
   * @param matrix the matrix to accept as the kernel
   * @throws IllegalArgumentException if the matrix is not square or does not have positive
   *                                  odd dimensions, or if it is simply null
   */
  protected AImageConvolutionOperation(double[][] matrix) throws IllegalArgumentException {
    if (matrix == null) {
      throw new IllegalArgumentException("Matrix must not be null!");
    }

    if (matrix.length == 0 || matrix.length % 2 == 0) {
      throw new IllegalArgumentException("Matrix must be of at least size 1 with odd dimensions!");
    }
    for (int i = 0; i < matrix.length; i += 1) {
      if (matrix[i].length != matrix.length) {
        throw new IllegalArgumentException("Matrix must be square!");
      }
    }

    // safe to use matrix.length twice because the matrix is ensured to be a square
    this.kernel = new double[matrix.length][matrix.length];

    for (int row = 0; row < matrix.length; row++) {
      for (int col = 0; col < matrix.length; col++) {
        this.kernel[row][col] = matrix[row][col];
      }
    }
  }

  /**
   * Filtering involves convolving over each pixel in the image with the kernel. This
   * pixel operation breaks this down into a single convolution: overlaying the kernel on the
   * image at the given position, and setting the new pixel to the sum of the element-wise products.
   * @param image   is the IImage provided (for sourcing original pixel information from)
   * @param row is the row position of the target pixel
   * @param col is the col position of the target pixel
   * @return the pixel to be placed into the new image
   */
  protected IPixel operateOnPixel(IImage image, int row, int col) {
    int red = this.convolve(image, row, col, pixel -> pixel.getRed());
    int green = this.convolve(image, row, col, pixel -> pixel.getGreen());
    int blue = this.convolve(image, row, col, pixel -> pixel.getBlue());
    return new RGBPixel(red, green, blue);
  }

  /**
   * Apply the kernel to a single channel of a single pixel, decided by some Function.
   * @param image the image to convolve over
   * @param row the row of the pixel to convolve at
   * @param col the col of the pixel to convolve at
   * @param component the function object for getting the proper channel of the pixel to apply
   *                  the kernel to
   * @return the value of the pixel at the channel dictated by the component function
   */
  private int convolve(IImage image, int row, int col, Function<IPixel, Integer> component) {
    int mid = this.kernel.length / 2;
    BigDecimal sum = BigDecimal.valueOf(0);

    for (int cRow = 0; cRow < this.kernel.length; cRow += 1) {
      for (int cCol = 0; cCol < this.kernel.length; cCol += 1) {
        int pRow = row + (cRow - mid);
        int pCol = col + (cCol - mid);
        if (pRow >= 0 && pRow < image.getHeight()
                && pCol >= 0 && pCol < image.getWidth()) {
          // Wrapping values in, and calculating with, BigDecimals so that our precision is exact,
          // and we can thus safely truncate without dealing with floating point issues.
          BigDecimal product = BigDecimal.valueOf(component.apply(image.getPixelAt(pRow, pCol)))
                  .multiply(BigDecimal.valueOf(this.kernel[cRow][cCol]));

          sum = sum.add(product);
        }
      }
    }
    int result = this.clamp(sum.intValue(), image.getMaxChannelDepth());

    return result;
  }
}
