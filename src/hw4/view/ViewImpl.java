package hw4.view;

import java.io.IOException;

/**
 * View implementation which stores an Appendable for rendering messages.
 */
public class ViewImpl implements IView {

  private final Appendable out;


  /**
   * Instantiate the view with the given output source.
   *
   * @param out the output stream
   */
  public ViewImpl(Appendable out) {
    if (out == null) {
      throw new IllegalArgumentException("The given Appendable cannot be null!");
    }

    this.out = out;
  }

  @Override
  public void renderMessage(String message) throws IOException, IllegalArgumentException {
    if (message != null) {
      this.out.append(message);
    } else {
      throw new IllegalArgumentException("The given message cannot be null!");
    }
  }
}
