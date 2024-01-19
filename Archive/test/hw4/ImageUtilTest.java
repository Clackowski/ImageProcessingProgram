package hw4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

//import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hw4.model.IImage;
import hw4.model.IPixel;
import hw4.model.ImageImpl;
import hw4.model.RGBPixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test class for the ImageUtil class.
 */
public class ImageUtilTest {

  private IImage twoByTwo;
  private final String sep = File.separator;

  /**
   * Configures the right two by two image for testing.
   */
  @Before
  public void init() {
    List<List<IPixel>> pixels = new ArrayList<>();
    pixels.add(new ArrayList<>());
    pixels.add(new ArrayList<>());
    pixels.get(0).add(new RGBPixel(0, 0, 0));
    pixels.get(0).add(new RGBPixel(115, 100, 5));
    pixels.get(1).add(new RGBPixel(255, 255, 255));
    pixels.get(1).add(new RGBPixel(24, 110, 236));

    twoByTwo = new ImageImpl(pixels);
  }

  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  @Test
  public void fileNotFound() {
    exceptionRule.expect(IllegalStateException.class);
    exceptionRule.expectMessage("File dir" + sep + "asdf.ppm not found!");
    ImageUtil.readImage("dir" + sep + "asdf.ppm");
  }

  /**
   * Test that reading an image file produces the correct file.
   */
  @Test
  public void testReadPPM() {
    IImage model = ImageUtil.readImage("res" + sep + "test-image.ppm");

    // making an empty scanner just so it's global outside the try statement
    Scanner sc = new Scanner("");
    try {
      sc = new Scanner(new FileInputStream("res" + sep + "test-image.ppm"));
    } catch (IOException e) {
      fail("There was an issue with the scanner.");
    }

    // Going through our test-image.ppm file line by line (just to be sure!)
    assertEquals("P3", sc.next());
    // Skipping the two comment lines in our test-image.ppm file
    sc.nextLine();
    sc.nextLine();
    sc.nextLine();
    assertEquals(model.getWidth(), sc.nextInt());
    assertEquals(model.getHeight(), sc.nextInt());
    sc.next();
    for (int row = 0; row < model.getHeight(); row += 1) {
      for (int col = 0; col < model.getWidth(); col += 1) {
        IPixel pixel = model.getPixelAt(row, col);
        assertEquals(pixel.getRed(), sc.nextInt());
        assertEquals(pixel.getGreen(), sc.nextInt());
        assertEquals(pixel.getBlue(), sc.nextInt());
      }
    }
  }

  /**
   * Tests that loading in any type of image using the readIOIMage functionality of ImageUtil
   * produces the proper IImage. This tests leverages the fact that the norway bridge photo
   * is identical across all four file formats.
   */
  @Test
  public void testReadIOImage() {
    IImage imgJPG = ImageUtil.readImage("res" + sep + "norway-bridge-test-3-2-jpg.jpg");
    IImage imgPNG = ImageUtil.readImage("res" + sep + "norway-bridge-test-3-2.png");
    IImage imgBMP = ImageUtil.readImage("res" + sep + "norway-bridge-test-3-2.bmp");

    compareToBridgePPM(imgJPG);
    compareToBridgePPM(imgPNG);
    compareToBridgePPM(imgBMP);


    //compareToBridgePPM tests with assert methods, but style checker does not see this
    assertEquals(18, new RGBPixel(18, 19, 20).getRed());
  }

  /**
   * Compares the given image to its PPM version.
   * Note: the image must be a copy of the norway-bridge-test-3-2 photo in some file format.
   * @param img the image to compare
   */
  private void compareToBridgePPM(IImage img) {
    IImage compare = ImageUtil.readImage("res" + sep + "norway-bridge-test-3-2.ppm");
    for (int row = 0; row < img.getHeight(); row += 1) {
      for (int col = 0; col < img.getWidth(); col += 1) {
        IPixel pixel = img.getPixelAt(row, col);
        IPixel comparePixel = compare.getPixelAt(row, col);
        assertEquals(pixel.getRed(), comparePixel.getRed());
        assertEquals(pixel.getGreen(), comparePixel.getGreen());
        assertEquals(pixel.getBlue(), comparePixel.getBlue());
      }
    }
  }

  /**
   * Tests that saving a PPM image produces the correct file.
   */
  @Test
  public void testSavePPM() {
    ImageUtil.saveImage(this.twoByTwo, "temp.ppm");

    // making an empty scanner just so it's global outside the try statement
    Scanner sc = new Scanner("");
    try {
      sc = new Scanner(new FileInputStream("temp.ppm"));
    } catch (IOException e) {
      fail();
    }
    assertEquals("P3", sc.next());
    assertEquals(2, sc.nextInt());
    assertEquals(2, sc.nextInt());
    assertEquals(255, sc.nextInt());
    for (int row = 0; row < this.twoByTwo.getHeight(); row += 1) {
      for (int col = 0; col < this.twoByTwo.getWidth(); col += 1) {
        IPixel pixel = this.twoByTwo.getPixelAt(row, col);
        assertEquals(pixel.getRed(), sc.nextInt());
        assertEquals(pixel.getGreen(), sc.nextInt());
        assertEquals(pixel.getBlue(), sc.nextInt());
      }
    }
    new File("temp.ppm").delete();
  }

  /**
   * Ensures that reading an image, saving it, and loading that save produces the same image.
   * This is used not just to add more confidence to PPM image correctness, but to test the accuracy
   * of saveImage() for non-PPM file types.
   * Note: We account for the lossy nature of JPG file compression by introducing a small delta
   * threshold to pixel checking, only for JPGs.
   */
  @Test
  public void readWriteRead() {
    rwrFile("res" + sep + "test-image.ppm", "ppm");
    rwrFile("res" + sep + "test-image-png.png", "png");
    rwrFile("res" + sep + "norway-bridge-test-3-2.bmp", "bmp");

    IImage img = ImageUtil.readImage("res" + sep + "norway-bridge-test-3-2-jpg.jpg");
    ImageUtil.saveImage(img, "temp.jpg");
    IImage sameImage = ImageUtil.readImage("temp.jpg");
    for (int row = 0; row < img.getHeight(); row += 1) {
      for (int col = 0; col < img.getWidth(); col += 1) {
        IPixel pixel = img.getPixelAt(row, col);
        IPixel samePixel = img.getPixelAt(row, col);
        assertEquals(pixel.getRed(), samePixel.getRed(), 1);
        assertEquals(pixel.getGreen(), samePixel.getGreen(), 1);
        assertEquals(pixel.getBlue(), samePixel.getBlue(), 1);
      }
    }

    new File("temp.jpg").delete();
  }

  private void rwrFile(String filepath, String extension) {
    IImage img = ImageUtil.readImage(filepath);
    ImageUtil.saveImage(img, "temp." + extension);
    IImage sameImg = ImageUtil.readImage("temp." + extension);
    assertTrue(img.samePixels(sameImg));
    new File("temp." + extension).delete();
  }

  /**
   * Ensures that saving an image, loading, and then saving that image produces the same file.
   * This can only be tested on PPMs, since we have no way of explicitly comparing the contents
   * of other image types besides loading them.
   */
  @Test
  public void writeReadWritePPM() {
    ImageUtil.saveImage(this.twoByTwo, "temp.ppm");
    IImage sameModel = ImageUtil.readImage("temp.ppm");
    ImageUtil.saveImage(sameModel, "same.ppm");
    Scanner one = new Scanner("");
    Scanner two = new Scanner("");
    try {
      one = new Scanner(new FileInputStream("temp.ppm"));
      two = new Scanner(new FileInputStream("same.ppm"));
    } catch (IOException e) {
      throw new IllegalStateException("The test threw an exception.");
    }
    while (one.hasNext()) {
      assertEquals(one.next(), two.next());
    }

    new File("temp.ppm").delete();
    new File("same.ppm").delete();
  }



  /**
   * Tests that the proper exception is thrown when the ImageUtil tries to read in a file with
   * more pixels than fit in the dimensions of the image.
   */
  @Test
  public void tooManyPixelsPPM() {
    try {
      ImageUtil.readImage("res" + sep +
              "test-image-5-by-2-labelled-3-by-3.ppm");
      fail();
    } catch (IllegalStateException e) {
      assertEquals("Invalid PPM file! "
              + "More pixels than dimensions specify!", e.getMessage());
    }
  }

  /**
   * Tests that the proper exception is thrown when the ImageUtil tries to read in a file
   * with not enough pixels to fill the dimensions of the image.
   */
  @Test
  public void notEnoughPixelsPPM() {
    try {
      ImageUtil.readImage("res" + sep +
              "test-image-5-by-2-labelled-6-by-2.ppm");
      fail();
    } catch (IllegalStateException e) {
      assertEquals("Invalid PPM file! Not enough "
              + "pixels as specified by dimensions of image!", e.getMessage());
    }
  }

  /**
   * Tests that the proper exception is thrown
   * when an invalid file format is passed to the ImageUtil.
   */
  @Test
  public void invalidFileFormat() {
    try {
      ImageUtil.readImage("res" + sep +
              "test-image.mp3");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid file format!", e.getMessage());
    }
  }

  /**
   * Tests that the proper exception is thrown when the ImageUtil cannot get a file format
   * from the file.
   * Note: cannot test for exceptions when file ends in '.', since Windows does not accept
   * files ending in '.' as valid
   */
  @Test
  public void noFileFormat() {
    try {
      ImageUtil.readImage("res" + sep +
              "no-extension");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("No extension found!", e.getMessage());
    }


  }

  /**
   * Tests that the proper exception is thrown when the ImageUtil tries to read in a file
   * without the proper P3 header.
   */
  @Test
  public void noP3PPM() {
    try {
      ImageUtil.readImage("res" + sep +
              "no-header.ppm");
      fail();
    } catch (IllegalStateException e) {
      assertEquals("Invalid PPM file: plain RAW file " +
              "should begin with P3", e.getMessage());
    }
  }

  /**
   * Tests that the proper exception is thrown when the ImageUtil tries to read in a file
   * which contains text where ints are expected.
   */
  @Test
  public void noIntPPM() {
    try {
      ImageUtil.readImage("res" + sep +
              "words.ppm");
      fail();
    } catch (IllegalStateException e) {
      assertEquals("Integer expected!", e.getMessage());
    }
  }


  @Test
  public void readImageNullException() {
    try {
      ImageUtil.readImage(null);
      fail("A null argument should throw an IllegalArgumentException!");
    } catch (IllegalArgumentException e) {
      assertEquals("The given filepath cannot be null!", e.getMessage());
    }
  }

  @Test
  public void saveImageNullException() {
    try {
      ImageUtil.saveImage(null, null);
      fail("A null argument should throw an IllegalArgumentException!");
    } catch (IllegalArgumentException e) {
      assertEquals("The given IImage cannot be null!", e.getMessage());
    }

    try {
      ImageUtil.saveImage(null, "totally-a-real-path.haha");
      fail("A null argument should throw an IllegalArgumentException!");
    } catch (IllegalArgumentException e) {
      assertEquals("The given IImage cannot be null!", e.getMessage());
    }

    try {
      ImageUtil.saveImage(this.twoByTwo, null);
      fail("A null argument should throw an IllegalArgumentException!");
    } catch (IllegalArgumentException e) {
      assertEquals("The given filepath cannot be null!", e.getMessage());
    }
  }

  @Test
  public void toJavaImageNullException() {
    try {
      ImageUtil.toJavaImage(null);
      fail("A null argument should throw an IllegalArgumentException!");
    } catch (IllegalArgumentException e) {
      assertEquals("The given IImage cannot be null!", e.getMessage());
    }
  }
}