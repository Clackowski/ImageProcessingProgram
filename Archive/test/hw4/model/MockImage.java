package hw4.model;

import java.io.IOException;

import hw4.imageoperations.IImageOperation;

/**
 * Mock of an image for controller testing.
 */
public class MockImage implements IImage {

  private final Appendable log;
  private final String name;

  /**
   * Instantiates the mock with the given log.
   * @param log the log to store activity of the mock
   * @param name the name of the mock
   */
  public MockImage(Appendable log, String name) {
    this.log = log;
    this.name = name;
  }


  /**
   * Accept which writes the applied operation to the log.
   * @param operation is an operation that returns a 2D list of pixels with modifications according
   *                  to operations such as Brighten, RedComponent, etc.
   * @return itself to satisfy the interface
   */
  public IImage accept(IImageOperation operation) {
    write("Applied " + operation.getClass().getSimpleName() + " to " + this.name);
    return this;
  }

  /**
   * Pixel get which writes the coordinates to the log.
   * @param row the row to search
   * @param col the col to search
   * @return new black pixel to satisfy interface
   */
  public IPixel getPixelAt(int row, int col) {
    write(String.format("Get pixel at %d %d", row, col));
    return new RGBPixel(0, 0, 0);
  }

  /**
   * Helper method for appending to the log.
   * @param message the message to write
   * @throws IllegalStateException if there are any errors appending to the log
   */
  private void write(String message) throws IllegalStateException {
    try {
      this.log.append(message + System.lineSeparator());
    } catch (IOException e) {
      throw new IllegalStateException("Mock failed in appending to log");
    }
  }

  //Appends to log
  @Override
  public int getWidth() {
    write("Get width");
    return 0;
  }

  //Appends to log
  @Override
  public int getHeight() {
    write("Get height");
    return 0;
  }

  //Appends to log
  @Override
  public int getMaxChannelDepth() {
    write("Get max channel depth");
    return 0;
  }

  //stub method
  @Override
  public boolean samePixels(IImageState other) {
    return false;
  }

}
