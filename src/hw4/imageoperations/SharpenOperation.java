package hw4.imageoperations;

/**
 * An image operation representing the Sharpen filter.
 */
public class SharpenOperation extends AImageConvolutionOperation {

  /**
   * Instantiates the operation by passing in the kernel for the filter operation. The kernel values
   * seen below were defined in the assignment instructions.
   */
  public SharpenOperation() {
    super(new double[][] {
            {-0.125, -0.125, -0.125, -0.125, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, 0.25, 1, 0.25, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, -0.125, -0.125, -0.125, -0.125}
    });
  }
}
