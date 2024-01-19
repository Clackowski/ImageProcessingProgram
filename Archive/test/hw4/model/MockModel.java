package hw4.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Mock for the picture manipulation model.
 */
public class MockModel implements IModel {
  private final Appendable log;
  private final Map<String, IImage> imageMap;

  /**
   * Instantiate the model with a log to check method calls.
   * @param log the log to store activity
   */
  public MockModel(Appendable log) {
    this.log = log;
    this.imageMap = new HashMap<>();
  }

  @Override
  public void loadImage(IImage image, String name) {
    this.imageMap.put(name, new MockImage(this.log, name));
    write("Loaded image " + name);
  }

  @Override
  public IImage getImage(String name) throws IllegalArgumentException {
    if (this.imageMap.containsKey(name)) {
      //write("Found image " + name);
      return this.imageMap.get(name);
    } else {
      //write("Could not find " + name);
      throw new IllegalArgumentException("No image " + name + " found!");
    }

  }

  /**
   * Helper method for appending to the log.
   * @param message the message to append
   * @throws IllegalStateException if there are errors writing to the log
   */
  private void write(String message) throws IllegalStateException {
    try {
      this.log.append(message + System.lineSeparator());
    } catch (IOException e) {
      throw new IllegalStateException("The mock could not append to the log.");
    }
  }
}
