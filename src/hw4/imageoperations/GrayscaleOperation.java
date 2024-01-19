package hw4.imageoperations;

/**
 * Represents a grayscale color transformation ("photo filter") operation on images, using the
 * abstraction AImageColorTransformationOperation. The values in the kernel are as defined in
 * the assignment. Note that this operation has the same end-result as the LumaComponentOperation,
 * but we were asked to implement it again through a kernel/filtering.
 */
public class GrayscaleOperation extends AImageColorTransformationOperation {

  /**
   * Instantiates the filter operation with the right grayscale filtering matrix.
   */
  public GrayscaleOperation() {
    super(new double[] {0.2126, 0.7152, 0.0722},
            new double[] {0.2126, 0.7152, 0.0722},
            new double[] {0.2126, 0.7152, 0.0722});
  }
}
