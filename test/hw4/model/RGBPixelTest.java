package hw4.model;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test class for the RGBPixel class.
 */
public class RGBPixelTest {

  /**
   * Ensures that the RGBPixel constructor throws the proper exception when one of the
   * channels is passed a non-negative value.
   */
  @Test
  public void nonNegativeChannels() {
    try {
      new RGBPixel(-1, 1, 23);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("red channel value must be non-negative.", e.getMessage());
    }
    try {
      new RGBPixel(34, -3, 23);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("green channel value must be non-negative.", e.getMessage());
    }
    try {
      new RGBPixel(6, 4, -23);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("blue channel value must be non-negative.", e.getMessage());
    }
  }

  /**
   * Tests that getRed() returns the proper red channel value.
   */
  @Test
  public void testGetRed() {
    assertEquals(5, new RGBPixel(5, 100, 143).getRed());
  }

  /**
   * Tests that getGreen() returns the proper green channel value.
   */
  @Test
  public void testGetGreen() {
    assertEquals(5, new RGBPixel(10, 5, 143).getGreen());
  }

  /**
   * Tests that getBlue() returns the proper blue channel value.
   */
  @Test
  public void testGetBlue() {
    assertEquals(5, new RGBPixel(30, 100, 5).getBlue());
  }

  /**
   * Tests that getBrightnessValue() returns the proper value brightness.
   */
  @Test
  public void testGetBrightnessValue() {
    assertEquals(240, new RGBPixel(240, 100, 143).getBrightnessValue());
    assertEquals(115, new RGBPixel(13, 115, 24).getBrightnessValue());
    assertEquals(212, new RGBPixel(31, 45, 212).getBrightnessValue());
    assertEquals(255, new RGBPixel(255, 255, 255).getBrightnessValue());

  }

  /**
   * Tests that getBrightnessIntensity() returns the proper intensity brightness.
   */
  @Test
  public void testGetBrightnessIntensity() {
    assertEquals(43, new RGBPixel(5, 100, 24).getBrightnessIntensity());
    assertEquals(47, new RGBPixel(47, 47, 47).getBrightnessIntensity());
    assertEquals(88, new RGBPixel(25, 34, 207).getBrightnessIntensity());
  }

  /**
   * Tests that getBrightnessLuma() returns the proper luma brightness.
   */
  @Test
  public void testGetBrightnessLuma() {
    assertEquals(171, new RGBPixel(67, 212, 78).getBrightnessLuma());
    assertEquals(50, new RGBPixel(57, 34, 196).getBrightnessLuma());
  }

  /**
   * Tests the accuracy of the toString() method for RGBPixels.
   */
  @Test
  public void testToString() {
    assertEquals("(r:5,g:6,b:7)", new RGBPixel(5, 6, 7).toString());
    assertEquals("(r:105,g:21,b:90)", new RGBPixel(105, 21, 90).toString());
  }

  /**
   * Tests the accuracy of the equals() method for RGBPixels.
   */
  @Test
  public void testEquals() {
    IPixel pixel = new RGBPixel(0, 0, 0);
    assertTrue(pixel.equals(pixel));
    assertFalse(pixel.equals("applesauce"));
    assertTrue(new RGBPixel(1, 4, 3).equals(
            new RGBPixel(1, 4, 3)));
    assertFalse(new RGBPixel(6, 32, 5).equals(
            new RGBPixel(5, 45, 64)));
  }

  /**
   * Tests the accuracy of the hashCode() method for RGBPixels.
   */
  @Test
  public void testHashCode() {
    assertEquals(Objects.hash(1, 2, 3), new RGBPixel(1, 2, 3).hashCode());
    assertEquals(new RGBPixel(54, 255, 0).hashCode(),
            new RGBPixel(54, 255, 0).hashCode());
    assertFalse(new RGBPixel(3, 64, 212).hashCode()
            == new RGBPixel(3, 64, 213).hashCode());
  }
}
