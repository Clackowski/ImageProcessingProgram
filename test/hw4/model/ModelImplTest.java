package hw4.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

/**
 * Test class for the ModelImpl class.
 */
public class ModelImplTest {

  private IModel model;
  private IImage image;

  @Before
  public void init() {
    this.model = new ModelImpl();
    this.image = new MockImage(new StringBuilder(), "test");
  }

  /**
   * Tests that the model throws the proper exception when an image is requested
   * which has not been yet placed in the model.
   */
  @Test
  public void getImageNoImage() {
    try {
      this.model.getImage("test-image");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Image test-image not found!", e.getMessage());
    }
  }

  /**
   * Tests whether loadImage actually stores an image in the model that can be queried;
   * that is, after calling loadImage and setting a certain name, getImage() should not
   * throw an exception when asking for that image.
   */
  @Test
  public void testLoadImage() {
    this.model.loadImage(this.image, "test-image");
    boolean getImageFail;
    try {
      this.model.getImage("test-image");
      getImageFail = false;
    } catch (IllegalArgumentException e) {
      getImageFail = true;
    }
    assertFalse(getImageFail);
  }

  /**
   * Tests that getImage() returns the correct image which was loaded into the model.
   */
  @Test
  public void testGetImage() {
    this.model.loadImage(this.image, "test-image");
    assertEquals(this.image, this.model.getImage("test-image"));
  }

  @Test
  public void nullLoadImage() {
    try {
      this.model.loadImage(null, "test-image");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Image cannot be null!", e.getMessage());
    }
    try {
      this.model.loadImage(this.image, null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Name cannot be null!", e.getMessage());
    }
  }
}
