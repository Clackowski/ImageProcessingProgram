package hw4.model;

import hw4.imageoperations.IImageOperation;

/**
 * The IImage interface extends the observable interface functionality and supports
 * the actual assignment command functionality for how we can transform images. This is carried
 * out through an accept() method that takes in an IImageOperation and uses it to return a new
 * image with the desired changes from the type of operation.
 */
public interface IImage extends IImageState {

  /**
   * Accepts the given IImageOperation, which operates on an IImage's pixels, and
   * returns a new IImage with the same information except for the new pixels.
   *
   * @param operation is an operation that returns a 2D list of pixels with modifications according
   *                  to operations such as Brighten, RedComponent, etc.
   * @return a new IImage with the new pixel information from the operation.
   * @throws IllegalArgumentException if applying the given IImageOperation throws an
   *                                  IllegalArgumentException.
   */
  IImage accept(IImageOperation operation) throws IllegalArgumentException;
}
