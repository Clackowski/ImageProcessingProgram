package hw4;

import hw4.controller.ControllerMosaicImpl;
import hw4.controller.MosaicScriptControllerImpl;
import hw4.view.JFrameMosaicViewImpl;
import java.io.File;
import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import hw4.controller.AsynchControllerImpl;
import hw4.controller.ControllerImpl2;
import hw4.controller.IAsynchController;
import hw4.controller.IController;
import hw4.model.IModel;
import hw4.model.ModelImpl;
import hw4.view.IGUIView;
import hw4.view.IView;
import hw4.view.JFrameViewImpl;
import hw4.view.ViewImpl;

/**
 * The class for our main() method to run the program.
 */
public class ImageProcessing {

  private static InputStream in;
  private static Appendable out;
  private static IModel model;

  private enum ProgramType {
    SYNC,
    ASYNC
  }

  private static ProgramType type;

  /**
   * The main() method to run our program.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    model = new ModelImpl();
    parseArgs(args);

    switch (type) {
      case SYNC:
        runSync();
        break;
      case ASYNC:
        runAsync();
        break;
      default:
        break;
    }
  }

  /**
   * Run the image processor with a synchronous controller and view.
   */
  private static void runSync() {
    IView view = new ViewImpl(out);
    //IController controller = new ControllerImpl2(model,
    //new InputStreamReader(in), view);
    IController controller = new MosaicScriptControllerImpl(model, new InputStreamReader(in), view);

    controller.run();
  }

  /**
   * Run the image processor with an asynchronous controller and view.
   */
  private static void runAsync() {
    //IAsynchController controller = new AsynchControllerImpl(model);
    IAsynchController controller = new ControllerMosaicImpl(model);
    //JFrameViewImpl.setDefaultLookAndFeelDecorated(false);
    //JFrameViewImpl frame = new JFrameViewImpl();

    JFrameMosaicViewImpl.setDefaultLookAndFeelDecorated(false);
    JFrameMosaicViewImpl frame = new JFrameMosaicViewImpl();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setPreferredSize(new Dimension(1500, 1000));

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    } catch (InstantiationException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (UnsupportedLookAndFeelException e) {
      throw new RuntimeException(e);
    }

    frame.setVisible(true);
    frame.pack();
    IGUIView view = frame;

    controller.setView(view);
    try {
      view.renderMessage("Please load an image with File > Load to begin editing. Loading " +
          "or editing a current image will add the new image to a new tab, and "
          + "switch to it automatically. The 'old' image will be in the original tab.");
    } catch (IOException e) {
      throw new IllegalStateException("An error occurred in displaying a message.");
    }
  }

  /**
   * Helper method for parsing keyword arguments and making the proper modifications to the running
   * of the program.
   *
   * @param args the keyword arguments
   * @throws IllegalStateException if the syntax for parsing kwargs is not followed or there are
   *                               errors with the filesystem
   */
  private static void parseArgs(String[] args) throws IllegalStateException {
    if (args.length == 0) {
      type = ProgramType.ASYNC;
    }
    for (int i = 0; i < args.length; i += 1) {
      String arg = args[i];
      switch (arg) {
        case "-file":
        case "-f":
          if (i < args.length - 1 && new File(args[i + 1]).canRead()) {
            try {
              in = new FileInputStream(args[i + 1]);
              out = System.out;
              i += 1;
            } catch (IOException e) {
              throw new IllegalStateException("Error reading from the file.");
            }
          } else {
            throw new IllegalStateException("Must pass a file path following " + arg + " flag!");
          }
          type = ProgramType.SYNC;
          return;
        case "-text":
          in = System.in;
          out = System.out;
          type = ProgramType.SYNC;
          return;
        default:
          throw new IllegalStateException("Invalid keyword arguments!");
      }
    }
  }
}
