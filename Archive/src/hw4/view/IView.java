package hw4.view;

import java.io.IOException;

/**
 * View for the image processor which renders the messages passed to it.
 */
public interface IView {

  /**
   * Renders the given String message to the implementation's (class's) given output.
   * @param message the message to be rendered
   * @throws IOException if transmission of the message to the output fails
   * @throws IllegalArgumentException if the given message is null
   */
  void renderMessage(String message) throws IOException, IllegalArgumentException;
}
