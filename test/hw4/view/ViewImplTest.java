package hw4.view;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests the ViewImpl, which just means we're testing the one and only renderMessage() method
 * and the construction.
 */
public class ViewImplTest {
  @Test
  public void testConstruction() {
    try {
      IView fails = new ViewImpl(null);
      fail("This should throw an IllegalArgumentException!");
    } catch (IllegalArgumentException e) {
      assertEquals("The given Appendable cannot be null!", e.getMessage());
    }
  }

  @Test
  public void testRenderMessageNullMessageException() {
    IView view = new ViewImpl(new StringBuilder(""));
    try {
      view.renderMessage(null);
      fail("This should throw an IllegalArgumentException!");
    } catch (IllegalArgumentException e) {
      assertEquals("The given message cannot be null!", e.getMessage());
    } catch (IOException e) {
      fail("This should not be thrown, since we are not appending if the given message is null.");
    }
  }

  @Test
  public void testRenderMessageFakeAppendable() {
    Appendable fakeAppendable = new Appendable() {
      @Override
      public Appendable append(CharSequence csq) throws IOException {
        throw new IOException("Fake appendable always fails!");
      }

      @Override
      public Appendable append(CharSequence csq, int start, int end) throws IOException {
        throw new IOException("Fake appendable always fails!");
      }

      @Override
      public Appendable append(char c) throws IOException {
        throw new IOException("Fake appendable always fails!");
      }
    };

    IView view = new ViewImpl(fakeAppendable);

    try {
      view.renderMessage("Mocking is great, but only in programming!");
      fail("This should throw an IllegalArgumentException!");
    } catch (IOException e) {
      assertEquals("Fake appendable always fails!", e.getMessage());
    }
  }

  @Test
  public void testRenderMessageEmpty() {
    Appendable appendable = new StringBuilder();
    IView view = new ViewImpl(appendable);
    try {
      view.renderMessage("AHHHHHHH!");
    } catch (IOException e) {
      fail("The IOException shouldn't be thrown.");
    }

    assertEquals("AHHHHHHH!", appendable.toString());
  }

  @Test
  public void testRenderMessageEmptyString() {
    Appendable appendable = new StringBuilder("");
    IView view = new ViewImpl(appendable);
    try {
      view.renderMessage("AHHHHHHH!");
    } catch (IOException e) {
      fail("The IOException shouldn't be thrown.");
    }

    assertEquals("AHHHHHHH!", appendable.toString());
  }

  @Test
  public void testRenderMessageSomethingAlreadyInside() {
    Appendable appendable = new StringBuilder("R");
    IView view = new ViewImpl(appendable);
    try {
      view.renderMessage("AHHHHHHH!");
    } catch (IOException e) {
      fail("The IOException shouldn't be thrown.");
    }

    assertEquals("RAHHHHHHH!", appendable.toString());
  }
}