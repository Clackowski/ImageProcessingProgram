package hw4.imageoperations;

/**
 * An image operation representing the Gaussian Blur filter.
 */
public class GaussianBlurOperation extends AImageConvolutionOperation {

  /**
   * Instantiates the operation by passing in the kernel for a Gaussian filter. The kernel values
   * seen below were defined in the assignment instructions.
   */
  public GaussianBlurOperation() {
    super(new double[][] {
            {0.0625, 0.125, 0.0625},
            {0.125, 0.25, 0.125},
            {0.0625, 0.125, 0.0625}
    });
  }
}
