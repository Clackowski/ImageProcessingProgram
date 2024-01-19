package hw4.model;

import java.util.HashMap;
import java.util.Map;

/**
 * The Model represents the collection of the images loaded into the program.
 */
public class ModelImpl implements IModel {
  private final Map<String, IImage> imageMap;

  public ModelImpl() {
    this.imageMap = new HashMap<>();
  }

  @Override
  public void loadImage(IImage image, String name) {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null!");
    }
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null!");
    }
    this.imageMap.put(name, image);
  }

  @Override
  public IImage getImage(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null!");
    }
    if (this.imageMap.containsKey(name)) {
      return this.imageMap.get(name);
    } else {
      throw new IllegalArgumentException("Image " + name + " not found!");
    }
  }

  // add new images (put, meaning overwrites if same name) with the given name and image

  // get the image with the given name
}
