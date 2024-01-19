package hw4.controller;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import hw4.model.IModel;
import hw4.model.ModelImpl;
import hw4.view.IGUIView;
import hw4.view.JFrameViewImpl;
import hw4.view.MockGuiView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test class for the AsynchControllerImpl class, which implements both the asynchronous controller
 * interface and the Features interface.
 */
public class AsynchControllerImplTest {

  IAsynchController asynchController;
  IFeatures asynchControllerFeatures;
  AsynchControllerImpl controllerFeaturesImpl;
  IModel model;
  IGUIView view;
  Appendable log;
  IGUIView mockView;

  @Before
  public void init() {
    this.model = new ModelImpl();

    this.view = new JFrameViewImpl();
    this.log = new StringBuilder();
    this.mockView = new MockGuiView(this.log);

    this.asynchController = new AsynchControllerImpl(this.model);
    this.asynchControllerFeatures = new AsynchControllerImpl(this.model);
    this.controllerFeaturesImpl = new AsynchControllerImpl(this.model);
  }

  @Test
  public void testSetView() {
    this.asynchController.setView(this.mockView);

    assertEquals("Added a features: " + this.asynchController, this.log.toString());
  }

  @Test
  public void testLoad() {
    this.controllerFeaturesImpl.setView(this.mockView);
    this.controllerFeaturesImpl.load("res" + File.separator +
            "test-image.ppm");

    assertEquals("Added a features: " + this.controllerFeaturesImpl
            + "Added the IImage representing a histogram: "
            + this.model.getImage("test-image.ppm")
            + "Added the AWT image named: " + "test-image.ppm", this.log.toString());
  }

  @Test
  public void testSave() {

    // delete the file so that the test can be run again (requires the starting state of the file
    // not existing yet)
    new File("res" + File.separator + "test-image-result.png").delete();

    this.controllerFeaturesImpl.setView(this.mockView);

    // should fail because there is no such image in the model yet
    try {
      this.controllerFeaturesImpl.save("res" + File.separator +
              "test-image.ppm", "test-image.ppm");
      fail("This should fail since the image has not been loaded yet!");
    } catch (IllegalArgumentException e) {
      assertEquals("Image test-image.ppm not found!", e.getMessage());
    }

    this.controllerFeaturesImpl.load("res" + File.separator +
            "test-image-result.png");

    // now load it in
    this.controllerFeaturesImpl.load("res" + File.separator +
            "test-image.ppm");

    // now this should be able to save the result out
    this.controllerFeaturesImpl.save("res" + File.separator +
            "test-image-result.png", "test-image.ppm");

    // so, we should finally be able to load that previously non-existent file back in
    this.controllerFeaturesImpl.load("res" + File.separator +
            "test-image-result.png");

    assertEquals("Added a features: " + this.controllerFeaturesImpl
            + "Rendered the following message: There was an error reading from the file."
            + "Added the IImage representing a histogram: "
            + this.model.getImage("test-image.ppm")
            + "Added the AWT image named: " + "test-image.ppm"
            + "Added the IImage representing a histogram: "
            + this.model.getImage("test-image-result.png")
            + "Added the AWT image named: " + "test-image-result.png", this.log.toString());

    // delete the file so that the test can be run again (requires the starting state of the file
    // not existing yet)
    new File("res" + File.separator + "test-image-result.png").delete();
  }

  @Test
  public void testImageOperation() {
    this.controllerFeaturesImpl.setView(this.mockView);
    this.controllerFeaturesImpl.load("res" + File.separator +
            "test-image.ppm");
    this.controllerFeaturesImpl.imageOperation("grayscale",
            "test-image.ppm");

    assertEquals("Added a features: " + this.controllerFeaturesImpl
            + "Added the IImage representing a histogram: "
            + this.model.getImage("test-image.ppm")
            + "Added the AWT image named: " + "test-image.ppm"
            + "Added the IImage representing a histogram: "
            + this.model.getImage("test-image-grayscale.ppm")
            + "Added the AWT image named: " + "test-image-grayscale.ppm", this.log.toString());
  }

  @Test
  public void testSetViewException() {
    try {
      this.asynchController.setView(null);
      fail("This should throw an IllegalArgumentException!");
    } catch (IllegalArgumentException e) {
      assertEquals("The given view cannot be null!", e.getMessage());
    }
  }

  @Test
  public void testLoadException() {
    try {
      this.asynchControllerFeatures.load(null);
      fail("This should throw an IllegalArgumentException!");
    } catch (IllegalArgumentException e) {
      assertEquals("The given filepath cannot be null!", e.getMessage());
    }
  }

  @Test
  public void testSaveException() {
    try {
      this.asynchControllerFeatures.save(null, null);
      fail("This should throw an IllegalArgumentException!");
    } catch (IllegalArgumentException e) {
      assertEquals("The given filepath cannot be null!", e.getMessage());
    }

    try {
      this.asynchControllerFeatures.save(null, "Hello");
      fail("This should throw an IllegalArgumentException!");
    } catch (IllegalArgumentException e) {
      assertEquals("The given filepath cannot be null!", e.getMessage());
    }

    try {
      this.asynchControllerFeatures.save("Testing!", null);
      fail("This should throw an IllegalArgumentException!");
    } catch (IllegalArgumentException e) {
      assertEquals("The given imageName cannot be null!", e.getMessage());
    }
  }

  @Test
  public void testImageOperationException() {
    try {
      this.asynchControllerFeatures.imageOperation(null, null);
      fail("This should throw an IllegalArgumentException!");
    } catch (IllegalArgumentException e) {
      assertEquals("The given operationName cannot be null!", e.getMessage());
    }

    try {
      this.asynchControllerFeatures.imageOperation("hello", null);
      fail("This should throw an IllegalArgumentException!");
    } catch (IllegalArgumentException e) {
      assertEquals("The given imageName cannot be null!", e.getMessage());
    }

    try {
      this.asynchControllerFeatures.imageOperation(null, "hi");
      fail("This should throw an IllegalArgumentException!");
    } catch (IllegalArgumentException e) {
      assertEquals("The given operationName cannot be null!", e.getMessage());
    }
  }

  @Test
  public void testConstructorExceptionBothInterfaces() {
    try {
      IAsynchController fails = new AsynchControllerImpl(null);
      fail("This should throw an IllegalArgumentException!");
    } catch (IllegalArgumentException e) {
      assertEquals("The given model cannot be null!", e.getMessage());
    }

    try {
      IFeatures alsoFails = new AsynchControllerImpl(null);
      fail("This should throw an IllegalArgumentException!");
    } catch (IllegalArgumentException e) {
      assertEquals("The given model cannot be null!", e.getMessage());
    }
  }
}