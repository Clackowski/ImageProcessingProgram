package hw4.imageoperations;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import hw4.ImageUtil;
import hw4.model.IImage;
import hw4.model.IPixel;
import hw4.model.ImageImpl;

import static org.junit.Assert.assertEquals;

/**
 * The test class for all IImageOperations.
 * Tests apply() method for every non-abstract operation class.
 */
public class ImageOperationsTest {

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

  IImageOperation redGrayscale;
  IImageOperation greenGrayscale;
  IImageOperation blueGrayscale;
  IImageOperation brightnessValueGrayscale;
  IImageOperation brightnessIntensityGrayscale;
  IImageOperation brightnessLumaGrayscale;
  IImageOperation horizontalFlip;
  IImageOperation verticalFlip;
  IImageOperation brighten5;
  IImageOperation brighten2500;
  IImageOperation darken5;
  IImageOperation darken2500;
  IImageOperation sharpen;
  IImageOperation blur;
  IImageOperation grayscale;
  IImageOperation sepia;

  List<List<IPixel>> defaultExpected;
  List<List<IPixel>> expectedRedGrayscale;
  List<List<IPixel>> expectedGreenGrayscale;
  List<List<IPixel>> expectedBlueGrayscale;
  List<List<IPixel>> expectedBrightnessValueGrayscale;
  List<List<IPixel>> expectedBrightnessIntensityGrayscale;
  List<List<IPixel>> expectedBrightnessLumaGrayscale;
  List<List<IPixel>> expectedHorizontalFlip;
  List<List<IPixel>> expectedVerticalFlip;
  List<List<IPixel>> expectedBrighten5;
  List<List<IPixel>> expectedBrighten2500;
  List<List<IPixel>> expectedDarken5;
  List<List<IPixel>> expectedDarken2500;
  List<List<IPixel>> expectedSharpen;
  List<List<IPixel>> expectedBlur;
  List<List<IPixel>> expectedGrayscale;
  List<List<IPixel>> expectedSepia;


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

    this.redGrayscale = new RedComponentOperation();
    this.greenGrayscale = new GreenComponentOperation();
    this.blueGrayscale = new BlueComponentOperation();
    this.brightnessValueGrayscale = new ValueComponentOperation();
    this.brightnessIntensityGrayscale = new IntensityComponentOperation();
    this.brightnessLumaGrayscale = new LumaComponentOperation();
    this.horizontalFlip = new HorizontalFlipOperation();
    this.verticalFlip = new VerticalFlipOperation();
    this.brighten5 = new BrightenOperation(5);
    this.brighten2500 = new BrightenOperation(2500);
    this.darken5 = new BrightenOperation(-5);
    this.darken2500 = new BrightenOperation(-2500);
    this.sharpen = new SharpenOperation();
    this.blur = new GaussianBlurOperation();
    this.grayscale = new GrayscaleOperation();
    this.sepia = new SepiaOperation();

    this.defaultExpected = this.imageToPixels(this.testImage);
    this.expectedRedGrayscale = this.imageToPixels(this.testImageRed);
    this.expectedGreenGrayscale = this.imageToPixels(this.testImageGreen);
    this.expectedBlueGrayscale = this.imageToPixels(this.testImageBlue);
    this.expectedBrightnessValueGrayscale = this.imageToPixels(this.testImageValue);
    this.expectedBrightnessIntensityGrayscale = this.imageToPixels(this.testImageIntensity);
    this.expectedBrightnessLumaGrayscale = this.imageToPixels(this.testImageLuma);
    this.expectedHorizontalFlip = this.imageToPixels(this.testImageHorizontalFlip);
    this.expectedVerticalFlip = this.imageToPixels(this.testImageVerticalFlip);
    this.expectedBrighten5 = this.imageToPixels(this.testImageBrighten5);
    this.expectedBrighten2500 = this.imageToPixels(this.testImageBrighten2500);
    this.expectedDarken5 = this.imageToPixels(this.testImageDarken5);
    this.expectedDarken2500 = this.imageToPixels(this.testImageDarken2500);
    this.expectedSharpen = this.imageToPixels(this.testImageRainbowSharpen);
    this.expectedBlur = this.imageToPixels(this.testImageRainbowBlur);
    this.expectedGrayscale = this.imageToPixels(this.testImageRainbowGrayscale);
    this.expectedSepia = this.imageToPixels(this.testImageRainbowSepia);
  }

  @Test
  public void testRedComponent() {
    assertEquals(this.expectedRedGrayscale, this.redGrayscale.apply(this.testImage));

    // This is the same because the red component is the one that was preserved and used for
    // all color channels during the first grayscale.
    assertEquals(this.expectedRedGrayscale, this.redGrayscale.apply(this.testImageRed));
  }

  @Test
  public void testGreenComponent() {
    assertEquals(this.expectedGreenGrayscale, this.greenGrayscale.apply(this.testImage));
  }

  @Test
  public void testBlueComponent() {
    assertEquals(this.expectedBlueGrayscale, this.blueGrayscale.apply(this.testImage));
  }

  @Test
  public void testValueComponent() {
    assertEquals(this.expectedBrightnessValueGrayscale,
            this.brightnessValueGrayscale.apply(this.testImage));
  }

  @Test
  public void testIntensityComponent() {
    assertEquals(this.expectedBrightnessIntensityGrayscale,
            this.brightnessIntensityGrayscale.apply(this.testImage));
  }

  @Test
  public void testLumaComponent() {
    assertEquals(this.expectedBrightnessLumaGrayscale,
            this.brightnessLumaGrayscale.apply(this.testImage));
  }

  @Test
  public void testHorizontalFlip() {
    assertEquals(this.expectedHorizontalFlip, this.horizontalFlip.apply(this.testImage));

    assertEquals(this.defaultExpected, this.horizontalFlip.apply(this.testImageHorizontalFlip));
  }

  @Test
  public void testVerticalFlip() {
    assertEquals(this.expectedVerticalFlip, this.verticalFlip.apply(this.testImage));

    assertEquals(this.defaultExpected, this.verticalFlip.apply(this.testImageVerticalFlip));
  }

  @Test
  public void testComposingFlips() {
    IImage verticalHorizontalFlipped = new ImageImpl(this.horizontalFlip
            .apply(this.testImageVerticalFlip));

    assertEquals(this.expectedHorizontalFlip, this.verticalFlip.apply(verticalHorizontalFlipped));

    assertEquals(this.expectedVerticalFlip, this.horizontalFlip.apply(verticalHorizontalFlipped));
  }

  @Test
  public void testBrighten5() {
    assertEquals(this.expectedBrighten5, this.brighten5.apply(this.testImage));
  }

  @Test
  public void testBrighten2500() {
    assertEquals(this.expectedBrighten2500, this.brighten2500.apply(this.testImage));
  }

  @Test
  public void testBrightenNegative5() {
    assertEquals(this.expectedDarken5, this.darken5.apply(this.testImage));
  }

  @Test
  public void testBrightenNegative2500() {
    assertEquals(this.expectedDarken2500, this.darken2500.apply(this.testImage));
  }

  @Test
  public void testSharpen() {
    assertEquals(this.expectedSharpen, this.sharpen.apply(this.testImageRainbow));
  }

  @Test
  public void testBlur() {
    assertEquals(this.expectedBlur, this.blur.apply(this.testImageRainbow));
  }

  @Test
  public void testGrayscale() {
    assertEquals(this.expectedGrayscale, this.grayscale.apply(this.testImageRainbow));
  }

  @Test
  public void testSepia() {
    assertEquals(this.expectedSepia, this.sepia.apply(this.testImageRainbow));
  }

  /**
   * Goes through every (row, col) value in the given IImage and returns a new 2D list of pixels
   * with each corresponding pixel value.
   *
   * @param image the given IImage from which to source pixel information.
   * @return a new 2D list of pixels with corresponding, equal pixel values.
   * @throws IllegalArgumentException if the given image is null.
   */
  private List<List<IPixel>> imageToPixels(IImage image) {
    if (image == null) {
      throw new IllegalArgumentException("Given image cannot be null.");
    }

    List<List<IPixel>> result = new ArrayList<>();
    for (int row = 0; row < image.getHeight(); row++) {
      ArrayList<IPixel> rowList = new ArrayList<>();
      for (int col = 0; col < image.getWidth(); col++) {
        rowList.add(image.getPixelAt(row, col));
      }
      result.add(rowList);
    }
    return result;
  }
}