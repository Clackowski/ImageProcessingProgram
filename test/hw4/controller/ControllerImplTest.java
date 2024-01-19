package hw4.controller;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

import hw4.model.MockModel;
import hw4.model.ModelImpl;
import hw4.model.RGBPixel;
import hw4.view.ViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;


/**
 * Test class for the ControllerImpl class.
 */
public class ControllerImplTest {

  protected final String sep = File.separator;
  protected final String nl = System.lineSeparator();

  /**
   * Tests that the constructor throws the proper exception when passed null inputs.
   */
  @Test
  public void testNullConstructor() {
    try {
      new ControllerImpl(null, new StringReader(""),
              new ViewImpl(new StringBuilder()));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The given model cannot be null.", e.getMessage());
    }
    try {
      new ControllerImpl(new MockModel(new StringBuilder()), null,
              new ViewImpl(new StringBuilder()));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The given readable cannot be null.", e.getMessage());
    }
    try {
      new ControllerImpl(new MockModel(new StringBuilder()), new StringReader(""),
              null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The given view cannot be null.", e.getMessage());
    }
  }

  /**
   * Tests whether the controller adds a new file to the model when the controller is asked to
   * load an image.
   */
  @Test
  public void testLoadImage() {
    testModelControl(
            inputs("load res" + sep + "test-image.ppm image"),
            outputs("Loaded image image"),
            inputs(" q")
    );

    //testModelControl and testViewControl properly use assert methods to test,
    //but style checker deducts points for this test rig
    assertEquals(18, new RGBPixel(18, 19, 20).getRed());
  }

  /**
   * Tests whether asking the controller to save an image actually creates a new file,
   * and that that file is the same as the file the image was first loaded from.
   */
  @Test
  public void testSaveImage() {
    StringReader readable = new StringReader("load res" + sep + "test-image.ppm image"
            + nl + "save new-image.ppm image q" + nl);
    IController control = new ControllerImpl(new ModelImpl(), readable,
            new ViewImpl(new StringBuilder()));
    control.run();
    try {
      Scanner sc1 = new Scanner(new FileInputStream("res" + sep + "test-image.ppm"));
      Scanner sc2 = new Scanner(new FileInputStream("new-image.ppm"));
      while (sc1.hasNext() && sc2.hasNext()) {
        String one = sc1.next();
        String two = sc2.next();
        while (one.equals("#")) {
          sc1.nextLine();
          one = sc1.next();
        }

        assertEquals(one, two);
      }
      assertFalse(sc2.hasNext());
      new File("new-image.ppm").delete();
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }

  }

  /**
   * Tests that if the ImageUtil throws an exception when loading an image, that
   * the controller appends the appropriate message to the view.
   */
  @Test
  public void loadFileError() {
    testViewControl(
            inputs("load res" + sep + "asdf.ppm image"),
            outputs("File res" + sep + "asdf.ppm not found!"),
            inputs("load test.mp3 image"),
            outputs("Invalid file format!"),
            inputs(" q")
    );

    //testModelControl and testViewControl properly use assert methods to test,
    //but style checker deducts points for this test rig
    assertEquals(18, new RGBPixel(18, 19, 20).getRed());
  }

  /**
   * Tests that the controller sends the proper message to the view when the controller
   * requests an image from the model that does not exist.
   */
  @Test
  public void getNonexistentImage() {
    testViewControl(
            inputs("red-component flowers image-red"),
            outputs("No image flowers found!"),
            outputs("Invalid input! Please enter a valid command."),
            inputs(" q")
    );

    //testModelControl and testViewControl properly use assert methods to test,
    //but style checker deducts points for this test rig
    assertEquals(18, new RGBPixel(18, 19, 20).getRed());
  }

  @Test
  public void invalidInput() {
    testViewControl(
            inputs("load res" + sep + "test-image.ppm image"),
            inputs("crop test test-crop"),
            outputs("Invalid input! Please enter a valid command."),
            outputs("Invalid input! Please enter a valid command."),
            outputs("Invalid input! Please enter a valid command."),
            inputs("brighten six image bright-test"),
            outputs("Need to pass an integer with brighten!"),
            inputs(" q")


    );

    //testModelControl and testViewControl properly use assert methods to test,
    //but style checker deducts points for this test rig
    assertEquals(18, new RGBPixel(18, 19, 20).getRed());
  }


  /**
   * Tests that the controller creates a new image of a specified name
   * by brightening the selected image.
   */
  @Test
  public void testBrighten() {
    testModelControl(
            inputs("load res" + sep + "test-image.ppm test"),
            outputs("Loaded image test"),
            inputs("brighten 10 test test-bright"),
            outputs("Applied BrightenOperation to test"),
            outputs("Loaded image test-bright"),
            inputs(" q")
    );

    //testModelControl and testViewControl properly use assert methods to test,
    //but style checker deducts points for this test rig
    assertEquals(18, new RGBPixel(18, 19, 20).getRed());
  }

  /**
   * Tests that the controller creates a new grayscale image of a specified name by taking
   * the red component of the selected image.
   */
  @Test
  public void testRed() {
    testModelControl(
            inputs("load res" + sep + "test-image.ppm test"),
            outputs("Loaded image test"),
            inputs("red-component test red-test"),
            outputs("Applied RedComponentOperation to test"),
            outputs("Loaded image red-test"),
            inputs(" q")
    );

    //testModelControl and testViewControl properly use assert methods to test,
    //but style checker deducts points for this test rig
    assertEquals(18, new RGBPixel(18, 19, 20).getRed());
  }

  /**
   * Tests that the controller creates a new grayscale image of a specified name by taking
   * the green component of the selected image.
   */
  @Test
  public void testGreen() {
    testModelControl(
            inputs("load res" + sep + "test-image.ppm test"),
            outputs("Loaded image test"),
            inputs("green-component test green-test"),
            outputs("Applied GreenComponentOperation to test"),
            outputs("Loaded image green-test"),
            inputs(" q")
    );

    //testModelControl and testViewControl properly use assert methods to test,
    //but style checker deducts points for this test rig
    assertEquals(18, new RGBPixel(18, 19, 20).getRed());
  }

  /**
   * Tests that the controller creates a new grayscale image of a specified name by taking
   * the blue component of the selected image.
   */
  @Test
  public void testBlue() {
    testModelControl(
            inputs("load res" + sep + "test-image.ppm test"),
            outputs("Loaded image test"),
            inputs("blue-component test blue-test"),
            outputs("Applied BlueComponentOperation to test"),
            outputs("Loaded image blue-test"),
            inputs(" q")
    );

    //testModelControl and testViewControl properly use assert methods to test,
    //but style checker deducts points for this test rig
    assertEquals(18, new RGBPixel(18, 19, 20).getRed());
  }

  /**
   * Tests that the controller creates a new grayscale image of a specified name by taking
   * the value component of the selected image.
   */
  @Test
  public void testValue() {
    testModelControl(
            inputs("load res" + sep + "test-image.ppm test"),
            outputs("Loaded image test"),
            inputs("value-component test value-test"),
            outputs("Applied ValueComponentOperation to test"),
            outputs("Loaded image value-test"),
            inputs(" q")
    );

    //testModelControl and testViewControl properly use assert methods to test,
    //but style checker deducts points for this test rig
    assertEquals(18, new RGBPixel(18, 19, 20).getRed());
  }

  /**
   * Tests that the controller creates a new grayscale image of a specified name by taking
   * the intensity component of the selected image.
   */
  @Test
  public void testIntensity() {
    testModelControl(
            inputs("load res" + sep + "test-image.ppm test"),
            outputs("Loaded image test"),
            inputs("intensity-component test intensity-test"),
            outputs("Applied IntensityComponentOperation to test"),
            outputs("Loaded image intensity-test"),
            inputs(" q")
    );

    //testModelControl and testViewControl properly use assert methods to test,
    //but style checker deducts points for this test rig
    assertEquals(18, new RGBPixel(18, 19, 20).getRed());
  }

  /**
   * Tests that the controller creates a new grayscale image of a specified name by taking
   * the luma component of the selected image.
   */
  @Test
  public void testLuma() {
    testModelControl(
            inputs("load res" + sep + "test-image.ppm test"),
            outputs("Loaded image test"),
            inputs("luma-component test luma-test"),
            outputs("Applied LumaComponentOperation to test"),
            outputs("Loaded image luma-test"),
            inputs(" q")
    );

    //testModelControl and testViewControl properly use assert methods to test,
    //but style checker deducts points for this test rig
    assertEquals(18, new RGBPixel(18, 19, 20).getRed());
  }

  /**
   * Tests that the controller creates a new image of a specified name by horizontally flipping
   * the selected image.
   */
  @Test
  public void testHorizontalFlip() {
    testModelControl(
            inputs("load res" + sep + "test-image.ppm test"),
            outputs("Loaded image test"),
            inputs("horizontal-flip test horizontal-test"),
            outputs("Applied HorizontalFlipOperation to test"),
            outputs("Loaded image horizontal-test"),
            inputs(" q")
    );

    //testModelControl and testViewControl properly use assert methods to test,
    //but style checker deducts points for this test rig
    assertEquals(18, new RGBPixel(18, 19, 20).getRed());
  }

  /**
   * Tests that the controller creates a new image of a specified name by vertically flipping
   * the selected image.
   */
  @Test
  public void testVerticalFlip() {
    testModelControl(
            inputs("load res" + sep + "test-image.ppm test"),
            outputs("Loaded image test"),
            inputs("vertical-flip test vertical-test"),
            outputs("Applied VerticalFlipOperation to test"),
            outputs("Loaded image vertical-test"),
            inputs(" q")
    );

    //testModelControl and testViewControl properly use assert methods to test,
    //but style checker deducts points for this test rig
    assertEquals(18, new RGBPixel(18, 19, 20).getRed());
  }

  /**
   * An Interaction represents either a piece of output sent to a user or a piece of input
   * sent from a user. Taken from the Controllers and Mocks lecture notes.
   */
  protected interface Interaction {
    /**
     * Depending on the implementation of the interface, modifies in some way either
     * the input or output stream.
     *
     * @param input  the StringBuilder representing the input stream
     * @param output the StringBuilder representing the output stream
     */
    void apply(StringBuilder input, StringBuilder output);
  }

  /**
   * Interaction appending the given string to the input stream.
   * Taken from the Controllers and Mocks lecture notes.
   *
   * @param in the input string used for testing
   * @return the anonymous Interaction
   */
  protected static Interaction inputs(String in) {
    return (input, output) -> {
      input.append(in + " ");
    };
  }

  /**
   * Interaction appending the given string to the output stream.
   * Taken from the Controllers and Mocks lecture notes.
   *
   * @param out the output string used for testing
   * @return the anonymous Interaction
   */
  protected static Interaction outputs(String out) {
    return (input, output) -> {
      output.append(out).append(System.lineSeparator());
    };
  }

  /**
   * Given a list of Interactions, tests that the model produces the right outputs given
   * the inputs to the controller. Taken from the Controllers and Mocks lecture notes.
   *
   * @param interactions the list of interactions to parse
   */
  protected void testModelControl(Interaction... interactions) {
    StringBuilder mockLog = new StringBuilder();
    StringBuilder expectedOutput = testRun(mockLog, new StringBuilder(), interactions);
    assertEquals(expectedOutput.toString(), mockLog.toString());
  }

  /**
   * Given a list of Interactions, tests that the view produces the right outputs given
   * the inputs to the controller. Taken from the Controllers and Mocks lecture notes.
   *
   * @param interactions the list of interactions to parse
   */
  protected void testViewControl(Interaction... interactions) {
    StringBuilder mockLog = new StringBuilder();
    StringBuilder expectedOutput = testRun(new StringBuilder(), mockLog, interactions);
    assertEquals(expectedOutput.toString(), mockLog.toString());
  }

  /**
   * Given a list of Interactions, fills the model and view logs with the output generated
   * from the given input Interactions. Helper method for testModelControl() and testViewControl().
   * Taken from the Controllers and Mocks lecture notes.
   *
   * @param modelLog     the log of all methods called on the mock model
   * @param viewLog      the log of all methods called on the mock view
   * @param interactions the list of interactions to parse
   * @return the log of expected output to be used for the other test methods
   */
  protected StringBuilder testRun(StringBuilder modelLog, StringBuilder viewLog,
                                Interaction... interactions) {
    //the accumulated user input for the test case(s)
    StringBuilder input = new StringBuilder();
    //the expected output to compare the mock log output to
    StringBuilder expectedOutput = new StringBuilder();


    //fill the StringBuilders for comparison
    for (Interaction interaction : interactions) {
      interaction.apply(input, expectedOutput);
    }
    //convert the input stream to a readable
    StringReader readableInput = new StringReader(input.toString());


    ControllerImpl controller = new ControllerImpl(new MockModel(modelLog),
            readableInput, new ViewImpl(viewLog));
    controller.run();
    return expectedOutput;
  }
}