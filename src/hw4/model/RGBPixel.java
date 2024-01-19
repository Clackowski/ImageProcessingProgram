package hw4.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Implementation of an IPixel by storing the red, green, and blue channels.
 */
public class RGBPixel implements IPixel {
  private final int red;
  private final int green;
  private final int blue;

  /**
   * Instantiate an RGBPixel by passing in values for the three channels.
   * @param red the red channel
   * @param green the green channel
   * @param blue the blue channel
   */
  public RGBPixel(int red, int green, int blue) {
    if (red < 0) {
      throw new IllegalArgumentException("red channel value must be non-negative.");
    }

    if (green < 0) {
      throw new IllegalArgumentException("green channel value must be non-negative.");
    }

    if (blue < 0) {
      throw new IllegalArgumentException("blue channel value must be non-negative.");
    }

    this.red = red;
    this.blue = blue;
    this.green = green;
  }

  @Override
  public int getRed() {
    return this.red;
  }

  @Override
  public int getGreen() {
    return this.green;
  }

  @Override
  public int getBlue() {
    return this.blue;
  }

  @Override
  public int getBrightnessValue() {
    return Math.max(Math.max(this.red, this.green), this.blue);
  }

  @Override
  public int getBrightnessIntensity() {
    return (this.red + this.green + this.blue) / 3;
  }

  @Override
  public int getBrightnessLuma() {
    BigDecimal redWeight = BigDecimal.valueOf(0.2126);
    BigDecimal greenWeight = BigDecimal.valueOf(0.7152);
    BigDecimal blueWeight = BigDecimal.valueOf(0.0722);

    BigDecimal red = BigDecimal.valueOf(this.red);
    BigDecimal green = BigDecimal.valueOf(this.green);
    BigDecimal blue = BigDecimal.valueOf(this.blue);

    BigDecimal value = redWeight.multiply(red)
            .add(greenWeight.multiply(green))
            .add(blueWeight.multiply(blue));

    return value.intValue();
  }

  @Override
  public String toString() {
    return "(r:" + this.getRed() + ",g:" + this.getGreen() + ",b:" + this.getBlue() + ")";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof IPixel)) {
      return false;
    }

    // now it's safe to cast:
    IPixel other = (IPixel) o;
    return this.getRed() == other.getRed()
            && this.getGreen() == other.getGreen()
            && this.getBlue() == other.getBlue();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getRed(), this.getGreen(), this.getBlue());
  }
}
