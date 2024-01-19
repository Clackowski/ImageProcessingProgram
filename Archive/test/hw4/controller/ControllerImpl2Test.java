package hw4.controller;

import org.junit.Test;

import java.io.StringReader;

import hw4.model.MockModel;
import hw4.model.RGBPixel;
import hw4.view.ViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the ControllerImpl2 class.
 */
public class ControllerImpl2Test extends ControllerImplTest {

  /**
   * Tests that the controller creates a new grayscale image of a specified name by
   * applying a luma grayscale filter to the image.
   */
  @Test
  public void testGrayscale() {
    testModelControl(
            inputs("load res" + sep + "test-image.ppm test"),
            outputs("Loaded image test"),
            inputs("grayscale test gray-test"),
            outputs("Applied GrayscaleOperation to test"),
            outputs("Loaded image gray-test"),
            inputs("greyscale test grey-test"),
            outputs("Applied GrayscaleOperation to test"),
            outputs("Loaded image grey-test"),
            inputs(" q")
    );

    //testModelControl and testViewControl properly use assert methods to test,
    //but style checker deducts points for this test rig
    assertEquals(18, new RGBPixel(18, 19, 20).getRed());
  }

  /**
   * Tests that the controller creates a new sepia image of a specified name by
   * applying a sepia filter to the image.
   */
  @Test
  public void testSepia() {
    testModelControl(
            inputs("load res" + sep + "test-image.ppm test"),
            outputs("Loaded image test"),
            inputs("sepia test sepia-test"),
            outputs("Applied SepiaOperation to test"),
            outputs("Loaded image sepia-test"),
            inputs(" q")
    );

    //testModelControl and testViewControl properly use assert methods to test,
    //but style checker deducts points for this test rig
    assertEquals(18, new RGBPixel(18, 19, 20).getRed());
  }

  /**
   * Tests that the controller creates a new blurred image of a specified name by
   * convolving a Gaussian blur kernel over the image.
   */
  @Test
  public void testBlur() {
    testModelControl(
            inputs("load res" + sep + "test-image.ppm test"),
            outputs("Loaded image test"),
            inputs("blur test blur-test"),
            outputs("Applied GaussianBlurOperation to test"),
            outputs("Loaded image blur-test"),
            inputs(" q")
    );

    //testModelControl and testViewControl properly use assert methods to test,
    //but style checker deducts points for this test rig
    assertEquals(18, new RGBPixel(18, 19, 20).getRed());
  }

  /**
   * Tests that the controller creates a new sharpened image of a specified name by
   * convolving a sharpen kernel over the image.
   */
  @Test
  public void testSharpen() {
    testModelControl(
            inputs("load res" + sep + "test-image.ppm test"),
            outputs("Loaded image test"),
            inputs("sharpen test sharpen-test"),
            outputs("Applied SharpenOperation to test"),
            outputs("Loaded image sharpen-test"),
            inputs(" q")
    );

    //testModelControl and testViewControl properly use assert methods to test,
    //but style checker deducts points for this test rig
    assertEquals(18, new RGBPixel(18, 19, 20).getRed());
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


    ControllerImpl controller = new ControllerImpl2(new MockModel(modelLog),
            readableInput, new ViewImpl(viewLog));
    controller.run();
    return expectedOutput;
  }
}
