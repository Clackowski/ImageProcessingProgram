package hw4.imageoperations;

import java.math.BigDecimal;

import hw4.model.IImage;
import hw4.model.IPixel;
import hw4.model.RGBPixel;

/**
 * Abstract class representing a color transformation ("photo filter") operation on images.
 */
public class AImageColorTransformationOperation extends AImageOperation {
  private final double[] redTransformationWeights = new double[3];
  private final double[] greenTransformationWeights = new double[3];
  private final double[] blueTransformationWeights = new double[3];

  /**
   * Instantiate the color transformation operation with the given list of weights for each
   * color component.
   *
   * @param redTransformationWeights the weights per component for calculating the red component
   * @param greenTransformationWeights the weights per component for calculating the green component
   * @param blueTransformationWeights the weights per component for calculating the blue component
   * @throws IllegalArgumentException if any weights don't have 3 values or if they are simply null
   */
  protected AImageColorTransformationOperation(double[] redTransformationWeights,
                                            double[] greenTransformationWeights,
                                            double[] blueTransformationWeights)
          throws IllegalArgumentException {
    if (redTransformationWeights == null) {
      throw new IllegalArgumentException("Red Transformation Weights must not be null!");
    }

    if (greenTransformationWeights == null) {
      throw new IllegalArgumentException("Green Transformation Weights must not be null!");
    }

    if (blueTransformationWeights == null) {
      throw new IllegalArgumentException("Blue Transformation Weights must not be null!");
    }

    if (redTransformationWeights.length != 3) {
      throw new IllegalArgumentException("Red Transformation Weights must have 3 values!");
    }

    if (greenTransformationWeights.length != 3) {
      throw new IllegalArgumentException("Green Transformation Weights must have 3 values!");
    }

    if (blueTransformationWeights.length != 3) {
      throw new IllegalArgumentException("Blue Transformation Weights must have 3 values!");
    }

    for (int i = 0; i < 3; i++) {
      this.redTransformationWeights[i] = redTransformationWeights[i];
      this.greenTransformationWeights[i] = greenTransformationWeights[i];
      this.blueTransformationWeights[i] = blueTransformationWeights[i];
    }
  }

  @Override
  protected IPixel operateOnPixel(IImage image, int row, int col) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("The given image cannot be null.");
    }

    IPixel originalPixel = image.getPixelAt(row, col);
    int depth = image.getMaxChannelDepth();

    int newRed = this.calculateComponentTransformation(this.redTransformationWeights,
            originalPixel, depth);

    int newGreen = this.calculateComponentTransformation(this.greenTransformationWeights,
            originalPixel, depth);

    int newBlue = this.calculateComponentTransformation(this.blueTransformationWeights,
            originalPixel, depth);

    IPixel pixel = new RGBPixel(newRed, newGreen, newBlue);

    return pixel;
  }

  /**
   * Calculates the resulting integer color component value given the weights to use for the
   * transformation, the original pixel, and the maximum value so that the result is clamped between
   * 0 and the given clamp.
   *
   * @param weights enforced to be a non-null array of 3 doubles
   * @param pixel the original value of the pixel to transform
   * @param clamp the maximum value
   * @return the resulting integer color component value from the transformation: the weighted sum
   *         of itself times each value in the given weights
   */
  private int calculateComponentTransformation(double[] weights, IPixel pixel, int clamp) {
    if (weights == null) {
      throw new IllegalArgumentException("Weights must not be null!");
    }

    if (weights.length != 3) {
      throw new IllegalArgumentException("Weights must have 3 values!");
    }

    BigDecimal originalRedValue = BigDecimal.valueOf(pixel.getRed());
    BigDecimal originalGreenValue = BigDecimal.valueOf(pixel.getGreen());
    BigDecimal originalBlueValue = BigDecimal.valueOf(pixel.getBlue());

    // Using precise BigDecimal math and then clamping the result as needed
    int result = this.clamp(originalRedValue.multiply(BigDecimal.valueOf(weights[0]))
            .add(originalGreenValue.multiply(BigDecimal.valueOf(weights[1])))
            .add(originalBlueValue.multiply(BigDecimal.valueOf(weights[2]))).intValue(), clamp);

    return result;
  }
}
