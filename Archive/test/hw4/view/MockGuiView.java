package hw4.view;

import java.awt.Image;
import java.io.IOException;

import hw4.controller.IFeatures;
import hw4.model.IImage;

/**
 * The mock for our IGUIView interface.
 */
public class MockGuiView implements IGUIView {
  private final Appendable log;

  /**
   * Instantiate the mock with the given log.
   * @param log the appendable log to store activities
   */
  public MockGuiView(Appendable log) {
    if (log == null) {
      throw new IllegalArgumentException("The given log cannot be null");
    }

    this.log = log;
  }

  @Override
  public void addFeatures(IFeatures features) {
    if (features == null) {
      throw new IllegalArgumentException("The given features cannot be null!");
    }

    this.handleAppending("Added a features: " + features);
  }

  @Override
  public void addImage(Image image, String imageName) {
    if (image == null) {
      throw new IllegalArgumentException("The given image cannot be null!");
    }

    if (imageName == null) {
      throw new IllegalArgumentException("The given imageName cannot be null!");
    }


    this.handleAppending("Added the AWT image named: " + imageName);
  }

  @Override
  public void addHistograms(IImage image) {
    this.handleAppending("Added the IImage representing a histogram: " + image);
  }

  @Override
  public String promptUser(String prompt) {
    if (prompt == null) {
      throw new IllegalArgumentException("The given prompt cannot be null!");
    }

    this.handleAppending("Prompted the user with: " + prompt);
    return prompt;
  }

  @Override
  public void renderMessage(String message) throws IOException, IllegalArgumentException {
    if (message == null) {
      throw new IllegalArgumentException("The given message cannot be null!");
    }

    this.handleAppending("Rendered the following message: " + message);
  }

  /**
   * A helper method to handle the checked exception from appending the given message to this.log.
   *
   * @param message the String message to append
   * @throws IllegalArgumentException if the given message was null or if a problem occurred while
   *                                  trying to append to this.log
   */
  private void handleAppending(String message) throws IllegalArgumentException {
    if (message == null) {
      throw new IllegalArgumentException("The given message cannot be null!");
    }

    try {
      this.log.append(message);
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to append the given message to the log.");
    }
  }
}
