package hw4.model;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import hw4.imageoperations.BlueComponentOperation;
import hw4.imageoperations.BrightenOperation;
import hw4.imageoperations.GaussianBlurOperation;
import hw4.imageoperations.GrayscaleOperation;
import hw4.imageoperations.GreenComponentOperation;
import hw4.imageoperations.HorizontalFlipOperation;
import hw4.imageoperations.IntensityComponentOperation;
import hw4.imageoperations.LumaComponentOperation;
import hw4.imageoperations.RedComponentOperation;
import hw4.imageoperations.SepiaOperation;
import hw4.imageoperations.SharpenOperation;
import hw4.imageoperations.ValueComponentOperation;
import hw4.imageoperations.VerticalFlipOperation;
import hw4.ImageUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * The test class for our ImageImpl class. Tests the image implementation of inherited methods
 * from IImage and IImageState (including accepting IImageOperations),
 * as well as constructor exceptions.
 */
public class ImageImplTest {

  IImage testImage;
  IImage testImageRed;
  IImage testImageGreen;
  IImage testImageBlue;
  IImage testImageValue;
  IImage testImageIntensity;
  IImage testImageLuma;
  IImage testImageHorizontalFlip;
  IImage testImageVerticalFlip;
  IImage testImageBrighten5;
  IImage testImageBrighten2500;
  IImage testImageDarken5;
  IImage testImageDarken2500;

  // note: width = 5 and height = 2, so there are 2 rows and 5 columns ( (row,col) is "flipped"
  // compared to (widthValue - 1, heightValue - 1) )
  IImage testImage5By2Edit;

  // used for testing the four new operations involving either kernels/convolution or color
  // transformations
  IImage testImageRainbow;
  IImage testImageRainbowSharpen;
  IImage testImageRainbowBlur;
  IImage testImageRainbowGrayscale;
  IImage testImageRainbowSepia;


  @Before
  public void init() {
    this.testImage = ImageUtil.readImage("res" + File.separator +
            "test-image.ppm");

    this.testImageRed = ImageUtil.readImage("res" + File.separator +
            "test-image-red.ppm");

    this.testImageGreen = ImageUtil.readImage("res" + File.separator +
            "test-image-green.ppm");

    this.testImageBlue = ImageUtil.readImage("res" + File.separator +
            "test-image-blue.ppm");

    this.testImageValue = ImageUtil.readImage("res" + File.separator +
            "test-image-value.ppm");

    this.testImageIntensity = ImageUtil.readImage("res" + File.separator +
            "test-image-intensity.ppm");

    this.testImageLuma = ImageUtil.readImage("res" + File.separator +
            "test-image-luma.ppm");

    this.testImageHorizontalFlip = ImageUtil.readImage("res" + File.separator +
            "test-image-horizontal-flip.ppm");

    this.testImageVerticalFlip = ImageUtil.readImage("res" + File.separator +
            "test-image-vertical-flip.ppm");

    this.testImageBrighten5 = ImageUtil.readImage("res" + File.separator +
            "test-image-brighten-5.ppm");

    this.testImageBrighten2500 = ImageUtil.readImage("res" + File.separator +
            "test-image-brighten-2500.ppm");

    this.testImageDarken5 = ImageUtil.readImage("res" + File.separator +
            "test-image-darken-5.ppm");

    this.testImageDarken2500 = ImageUtil.readImage("res" + File.separator +
            "test-image-darken-2500.ppm");

    this.testImage5By2Edit = ImageUtil.readImage("res" + File.separator +
            "test-image-5-by-2.ppm");

    this.testImageRainbow = ImageUtil.readImage("res" + File.separator +
            "test-image-6-by-5-rainbow-brightness-gradient.ppm");

    this.testImageRainbowSharpen = ImageUtil.readImage("res" + File.separator +
            "test-image-6-by-5-rainbow-brightness-gradient-sharpened.ppm");

    this.testImageRainbowBlur = ImageUtil.readImage("res" + File.separator +
            "test-image-6-by-5-rainbow-brightness-gradient-blurred.ppm");

    this.testImageRainbowGrayscale = ImageUtil.readImage("res" + File.separator +
            "test-image-6-by-5-rainbow-brightness-gradient-grayscaled.ppm");

    this.testImageRainbowSepia = ImageUtil.readImage("res" + File.separator +
            "test-image-6-by-5-rainbow-brightness-gradient-sepiad.ppm");
  }

  @Test
  public void testGetPixelAt() {
    assertEquals(this.testImage.getPixelAt(0, 0), (new RGBPixel(0, 0, 0)));
    assertEquals(this.testImage.getPixelAt(0, 1), (new RGBPixel(0, 0, 4)));
    assertEquals(this.testImage.getPixelAt(0, 2), (new RGBPixel(4, 4, 4)));
    assertEquals(this.testImage.getPixelAt(1, 0),
            (new RGBPixel(121, 81, 121)));

    assertEquals(this.testImage.getPixelAt(1, 1),
            (new RGBPixel(220, 220, 220)));

    assertEquals(this.testImage.getPixelAt(2, 2),
            (new RGBPixel(255, 255, 255)));

    assertEquals(this.testImageVerticalFlip.getPixelAt(0, 0),
            (new RGBPixel(251, 251, 251)));

    assertEquals(this.testImage5By2Edit.getPixelAt(0, 0),
            (new RGBPixel(0, 0, 0)));

    assertEquals(this.testImage5By2Edit.getPixelAt(0, 4),
            (new RGBPixel(220, 220, 220)));
  }

  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  @Test
  public void testTooHighPixels() {
    try {
      ImageUtil.readImage("res" + File.separator + "high-red.ppm");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The pixel at (0,0) has at least" +
              " one component with an invalid value, greater than the " +
              "maximum channel depth for this image: (r:300,g:12,b:4)", e.getMessage());
    }
    try {
      ImageUtil.readImage("res" + File.separator + "high-green.ppm");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The pixel at (0,0) has at least" +
              " one component with an invalid value, greater than the " +
              "maximum channel depth for this image: (r:210,g:300,b:52)", e.getMessage());
    }
    try {
      ImageUtil.readImage("res" + File.separator + "high-blue.ppm");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The pixel at (0,0) has at least" +
              " one component with an invalid value, greater than the " +
              "maximum channel depth for this image: (r:45,g:60,b:256)", e.getMessage());
    }
  }

  @Test
  public void testGetPixelAtExceptionBothNeg() {
    exceptionRule.expect(IllegalArgumentException.class);
    exceptionRule.expectMessage("Cannot get a pixel at a negative row or column.");
    this.testImage.getPixelAt(-1, -1);
  }

  @Test
  public void testGetPixelAtExceptionNegRow() {
    exceptionRule.expect(IllegalArgumentException.class);
    exceptionRule.expectMessage("Cannot get a pixel at a negative row or column.");
    this.testImage.getPixelAt(-1, 0);
  }

  @Test
  public void testGetPixelAtExceptionNegCol() {
    exceptionRule.expect(IllegalArgumentException.class);
    exceptionRule.expectMessage("Cannot get a pixel at a negative row or column.");
    this.testImage.getPixelAt(0, -1);
  }

  @Test
  public void testGetPixelAtExceptionColTooBig() {
    exceptionRule.expect(IllegalArgumentException.class);
    exceptionRule.expectMessage("Cannot get a pixel at a row or column value past the " +
            "dimensions of the image.");
    this.testImage.getPixelAt(0, 3);
  }

  @Test
  public void testGetPixelAtExceptionRowTooBig() {
    exceptionRule.expect(IllegalArgumentException.class);
    exceptionRule.expectMessage("Cannot get a pixel at a row or column value past the " +
            "dimensions of the image.");
    this.testImage.getPixelAt(3, 0);
  }

  @Test
  public void testGetPixelAtExceptionBothTooBig() {
    exceptionRule.expect(IllegalArgumentException.class);
    exceptionRule.expectMessage("Cannot get a pixel at a row or column value past the " +
            "dimensions of the image.");
    this.testImage.getPixelAt(3, 2);
  }

  @Test
  public void testGetPixelAtExceptionBothTooBigReversed() {
    exceptionRule.expect(IllegalArgumentException.class);
    exceptionRule.expectMessage("Cannot get a pixel at a row or column value past the " +
            "dimensions of the image.");
    this.testImage.getPixelAt(2, 3);
  }

  @Test
  public void testGetPixelAtExceptionBothTooBigAgain() {
    exceptionRule.expect(IllegalArgumentException.class);
    exceptionRule.expectMessage("Cannot get a pixel at a row or column value past the " +
            "dimensions of the image.");
    this.testImage.getPixelAt(3, 3);
  }

  @Test
  public void testGetPixelAtExceptionBothNegative5by2() {
    exceptionRule.expect(IllegalArgumentException.class);
    exceptionRule.expectMessage("Cannot get a pixel at a negative row or column.");
    this.testImage5By2Edit.getPixelAt(-1, -1);
  }

  @Test
  public void testGetPixelAtExceptionNegativeRow5by2() {
    exceptionRule.expect(IllegalArgumentException.class);
    exceptionRule.expectMessage("Cannot get a pixel at a negative row or column.");
    this.testImage5By2Edit.getPixelAt(-1, 0);
  }

  @Test
  public void testGetPixelAtExceptionNegativeCol5by2() {
    exceptionRule.expect(IllegalArgumentException.class);
    exceptionRule.expectMessage("Cannot get a pixel at a negative row or column.");
    this.testImage5By2Edit.getPixelAt(0, -1);
  }

  @Test
  public void testGetPixelAtExceptionColTooBig5by2() {
    exceptionRule.expect(IllegalArgumentException.class);
    exceptionRule.expectMessage("Cannot get a pixel at a row or column value past the " +
            "dimensions of the image.");
    this.testImage5By2Edit.getPixelAt(3, 0);
  }

  @Test
  public void testGetPixelAtExceptionColTooBigAgain5by2() {
    exceptionRule.expect(IllegalArgumentException.class);
    exceptionRule.expectMessage("Cannot get a pixel at a row or column value past the " +
            "dimensions of the image.");
    this.testImage5By2Edit.getPixelAt(4, 2);
  }

  @Test
  public void testGetPixelAtExceptionRowTooBig5by2() {
    exceptionRule.expect(IllegalArgumentException.class);
    exceptionRule.expectMessage("Cannot get a pixel at a row or column value past the " +
            "dimensions of the image.");
    this.testImage5By2Edit.getPixelAt(5, 0);
  }

  @Test
  public void testGetPixelAtExceptionBothTooBig5by2() {
    exceptionRule.expect(IllegalArgumentException.class);
    exceptionRule.expectMessage("Cannot get a pixel at a row or column value past the " +
            "dimensions of the image.");
    this.testImage5By2Edit.getPixelAt(5, 2);
  }

  @Test
  public void nullPixels() {
    exceptionRule.expect(IllegalArgumentException.class);
    exceptionRule.expectMessage("The image must have a non-null 2D list of IPixel.");
    new ImageImpl(null);
  }

  @Test
  public void nonPosWidth() {
    exceptionRule.expect(IllegalArgumentException.class);
    exceptionRule.expectMessage("The image must have a positive width.");
    List<List<IPixel>> testList = new ArrayList<>();
    testList.add(new ArrayList<>());
    new ImageImpl(testList);
  }

  @Test
  public void nonPosHeight() {
    exceptionRule.expect(IllegalArgumentException.class);
    exceptionRule.expectMessage("The image must have a positive height.");
    new ImageImpl(new ArrayList<>());
  }

  @Test
  public void testBrighten() {
    assertTrue(this.testImage.accept(new BrightenOperation(5))
            .samePixels(this.testImageBrighten5));

    assertTrue(this.testImage.accept(new BrightenOperation(2500)).samePixels(
            this.testImageBrighten2500));

    assertTrue(this.testImage.accept(new BrightenOperation(-5)).samePixels(
            this.testImageDarken5));

    assertTrue(this.testImage.accept(new BrightenOperation(-2500)).samePixels(
            this.testImageDarken2500));

    assertTrue(this.testImage.accept(new BrightenOperation(0)).samePixels(
            this.testImage));

    assertTrue(this.testImage.accept(new BrightenOperation(-0)).samePixels(
            this.testImage));
  }

  @Test
  public void testRedComponent() {
    assertTrue(this.testImage.accept(new RedComponentOperation()).samePixels(this.testImageRed));
  }

  @Test
  public void testGreenComponent() {
    assertTrue(this.testImage.accept(new GreenComponentOperation()).samePixels(
            this.testImageGreen));
  }

  @Test
  public void testBlueComponent() {
    assertTrue(this.testImage.accept(new BlueComponentOperation()).samePixels(
            this.testImageBlue));
  }

  @Test
  public void testValueComponent() {
    assertTrue(this.testImage.accept(new ValueComponentOperation()).samePixels(
            this.testImageValue));
  }

  @Test
  public void testIntensityComponent() {
    assertTrue(this.testImage.accept(new IntensityComponentOperation()).samePixels(
            this.testImageIntensity));
  }

  @Test
  public void testLumaComponent() {
    assertTrue(this.testImage.accept(new LumaComponentOperation()).samePixels(
            this.testImageLuma));
  }

  @Test
  public void testHorizontalFlip() {
    assertTrue(this.testImage.accept(new HorizontalFlipOperation()).samePixels(
            this.testImageHorizontalFlip));

    assertTrue(this.testImage.accept(new HorizontalFlipOperation()).accept(
            new HorizontalFlipOperation()).samePixels(this.testImage));

    assertTrue(this.testImageHorizontalFlip.accept(new HorizontalFlipOperation()).samePixels(
            this.testImage));

    assertTrue(this.testImageHorizontalFlip.accept(new HorizontalFlipOperation()).accept(
            new HorizontalFlipOperation()).samePixels(this.testImageHorizontalFlip));
  }

  @Test
  public void testVerticalFlip() {
    assertTrue(this.testImage.accept(new VerticalFlipOperation()).samePixels(
            this.testImageVerticalFlip));

    assertTrue(this.testImage.accept(new VerticalFlipOperation()).accept(
            new VerticalFlipOperation()).samePixels(this.testImage));

    assertTrue(this.testImageVerticalFlip.accept(new VerticalFlipOperation()).samePixels(
            this.testImage));

    assertTrue(this.testImageVerticalFlip.accept(new VerticalFlipOperation()).accept(
            new VerticalFlipOperation()).samePixels(this.testImageVerticalFlip));
  }

  @Test
  public void testSharpenOperation() {
    assertTrue(this.testImageRainbow.accept(new SharpenOperation()).samePixels(
            this.testImageRainbowSharpen));
  }

  @Test
  public void testBlurOperation() {
    assertTrue(this.testImageRainbow.accept(new GaussianBlurOperation()).samePixels(
            this.testImageRainbowBlur));
  }

  @Test
  public void testGrayscaleOperation() {
    assertTrue(this.testImageRainbow.accept(new GrayscaleOperation()).samePixels(
            this.testImageRainbowGrayscale));
  }

  @Test
  public void testSepiaOperation() {
    assertTrue(this.testImageRainbow.accept(new SepiaOperation()).samePixels(
            this.testImageRainbowSepia));
  }
}