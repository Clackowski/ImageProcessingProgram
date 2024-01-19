package hw4.imageoperations;

/**
 * Represents a sepia color transformation ("photo filter") operation on images, using the
 * abstraction AImageColorTransformationOperation. The values in the kernel are as defined in
 * the assignment.
 */
public class SepiaOperation extends AImageColorTransformationOperation {

  /**
   * Instantiates the filter operation with the proper sepia transformation matrix.
   */
  public SepiaOperation() {
    super(new double[] {0.393, 0.769, 0.189},
            new double[] {0.349, 0.686, 0.168},
            new double[] {0.272, 0.534, 0.131});
  }
}
