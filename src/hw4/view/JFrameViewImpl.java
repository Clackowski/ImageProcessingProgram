package hw4.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import hw4.controller.IFeatures;
import hw4.histogram.ArrayImageHistogram;
import hw4.histogram.IImageHistogram;

import hw4.model.IImage;
import hw4.model.IPixel;

/**
 * GUI for image processor which uses Swing. This GUI gives tabbed menu options for saving/loading,
 * image operations, shows the current image to operate on, and shows histograms displaying
 * statistics about the image.
 */
public class JFrameViewImpl extends JFrame implements IGUIView {

  private final JMenuItem saveMenuItem;
  private final JMenuItem loadMenuItem;
  private final JTabbedPane imageTabs;
  private final JPanel histogramsPanel;
  private final Map<JMenuItem, String> editMenuItemStringMap;
  private final ArrayList<ArrayList<IImageHistogram>> imageHistograms;


  /**
   * Start up the GUI with its components.
   */
  public JFrameViewImpl() {
    super();

    this.editMenuItemStringMap = new HashMap<>();
    this.imageHistograms = new ArrayList<>();

    // Create the GUI within this frame:
    // Making the top menubar and its components, and adding those to this.menuItemStringMap
    JMenuBar menuBar = new JMenuBar();
    this.setJMenuBar(menuBar);

    JMenu fileMenu = new JMenu("File");
    menuBar.add(fileMenu);

    this.saveMenuItem = new JMenuItem("Save");
    fileMenu.add(this.saveMenuItem);

    this.loadMenuItem = new JMenuItem("Load");
    fileMenu.add(this.loadMenuItem);

    JMenu editMenu = new JMenu("Edit");
    menuBar.add(editMenu);

    JMenu componentMenu = new JMenu("Component");
    editMenu.add(componentMenu);

    JMenu flipMenu = new JMenu("Flip");
    editMenu.add(flipMenu);

    JMenu filterMenu = new JMenu("Filter");
    editMenu.add(filterMenu);

    JMenu colorTransformMenu = new JMenu("Color Transform");
    editMenu.add(colorTransformMenu);

    this.createMenuItem(componentMenu, "Red", "Red-Component");
    this.createMenuItem(componentMenu, "Green", "Green-Component");
    this.createMenuItem(componentMenu, "Blue", "Blue-Component");
    this.createMenuItem(componentMenu, "Value", "Value-Component");
    this.createMenuItem(componentMenu, "Intensity", "Intensity-Component");
    this.createMenuItem(componentMenu, "Luma", "Luma-Component");
    this.createMenuItem(flipMenu, "Vertical", "Vertical-Flip");
    this.createMenuItem(flipMenu, "Horizontal", "Horizontal-Flip");
    this.createMenuItem(filterMenu, "Gaussian Blur", "Blur");
    this.createMenuItem(filterMenu, "Sharpen");
    this.createMenuItem(colorTransformMenu, "Grayscale");
    this.createMenuItem(colorTransformMenu, "Sepia");
    this.createMenuItem(editMenu, "Brighten/Darken", "Brighten");

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    this.add(mainPanel);

    JPanel imagesPanel = new JPanel();
    // imagesPanel.setBorder(BorderFactory.createTitledBorder("Image:"));
    // imagesPanel.setLayout(new GridLayout(1,0,10,10));

    this.imageTabs = new JTabbedPane();
    imagesPanel.add(this.imageTabs);
    this.imageTabs.addChangeListener(l -> {
      if (!this.imageHistograms.isEmpty()) {
        this.updateHistograms();
      }
    });

    mainPanel.add(imagesPanel);

    this.histogramsPanel = new JPanel();
    mainPanel.add(histogramsPanel);


  }


  @Override
  public void renderMessage(String message) throws IllegalArgumentException {
    if (message == null) {
      throw new IllegalArgumentException("The given message cannot be null!");
    }

    JOptionPane optionPane = new JOptionPane();
    JOptionPane.showMessageDialog(optionPane, message);
  }

  @Override
  public void addFeatures(IFeatures features) throws IllegalArgumentException {
    if (features == null) {
      throw new IllegalArgumentException("The given IFeatures cannot be null!");
    }

    // saving an image:
    this.saveMenuItem.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser(".");
      int returnValue = fileChooser.showSaveDialog(JFrameViewImpl.this);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        String absoluteFilePath = fileChooser.getSelectedFile().getAbsolutePath();
        try {
          features.save(absoluteFilePath,
              this.imageTabs.getTitleAt(this.imageTabs.getSelectedIndex()));
        } catch (IndexOutOfBoundsException ex) {
          JFrameViewImpl.this.renderMessage("No image to save.");
        }
      }
    });

    // loading an image into a tab:
    this.loadMenuItem.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter("PPM, BMP, PNG, JPG",
          "ppm", "bmp", "png", "jpg");
      fileChooser.setFileFilter(filter);
      int returnValue = fileChooser.showOpenDialog(JFrameViewImpl.this);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        String absoluteFilePath = fileChooser.getSelectedFile().getAbsolutePath();
        features.load(absoluteFilePath);
      }
    });

    for (Map.Entry<JMenuItem, String> menuItem : this.editMenuItemStringMap.entrySet()) {
      menuItem.getKey().addActionListener(e -> {
        String commandText = e.paramString()
            .split("cmd=")[1].split(",")[0].toLowerCase();

        try {
          System.out.println(this.imageTabs.getSelectedIndex());
          features.imageOperation(e.getActionCommand().toLowerCase(),
              this.imageTabs.getTitleAt(this.imageTabs.getSelectedIndex()));
        } catch (IndexOutOfBoundsException ex) {
          JFrameViewImpl.this.renderMessage("No image loaded.");
        }
      });
    }
  }

  @Override
  public void addImage(Image image, String imageName) {

    if (image == null) {
      throw new IllegalArgumentException("The given image cannot be null!");
    }

    if (imageName == null) {
      throw new IllegalArgumentException("The given imageName cannot be null!");
    }

    JLabel label = new JLabel();

    JScrollPane scrollPane = new JScrollPane(label);
    label.setIcon(new ImageIcon(image, imageName));

    scrollPane.setPreferredSize(new Dimension(1000, 600));

    this.imageTabs.addTab(imageName, scrollPane);
    this.imageTabs.setSelectedComponent(scrollPane);
    this.updateHistograms();
  }


  @Override
  public void addHistograms(IImage image) {

    if (image == null) {
      throw new IllegalArgumentException("The given image cannot be null!");
    }

    ArrayList<IImageHistogram> histograms = new ArrayList<>();
    histograms.add(new ArrayImageHistogram(image, IPixel::getRed, "red", Color.RED));
    histograms.add(new ArrayImageHistogram(image, IPixel::getGreen, "green", Color.GREEN));
    histograms.add(new ArrayImageHistogram(image, IPixel::getBlue, "blue", Color.BLUE));
    histograms.add(new ArrayImageHistogram(image, IPixel::getBrightnessIntensity,
        "intensity", Color.GRAY));
    this.imageHistograms.add(histograms);
  }

  @Override
  public String promptUser(String prompt) {
    return JOptionPane.showInputDialog(prompt);
  }

  /**
   * Creates a new JMenuItem, sets its menu name to the given itemName, sets its action command
   * field to given desiredActionName, adds it to the given menu, and puts it in
   * this.editMenuItemStringMap.
   *
   * @param menu              the menu to which the new item is added
   * @param itemName          the name that should be displayed in the menu slot for this item
   * @param desiredActionName the name to be assigned to the item's actionCommand field
   * @throws IllegalArgumentException if menu, itemName, or desiredActionName are null
   */
  protected void createMenuItem(JMenu menu, String itemName, String desiredActionName)
      throws IllegalArgumentException {
    if (menu == null) {
      throw new IllegalArgumentException("The given menu cannot be null!");
    }

    if (itemName == null) {
      throw new IllegalArgumentException("The given itemName cannot be null!");
    }

    if (desiredActionName == null) {
      throw new IllegalArgumentException("The given desiredActionName cannot be null!");
    }

    JMenuItem menuItem = new JMenuItem(itemName);
    menuItem.setActionCommand(desiredActionName);
    menu.add(menuItem);
    this.editMenuItemStringMap.put(menuItem, menuItem.getActionCommand().toLowerCase());
  }

  /**
   * Creates a new JMenuItem, sets its menu name to the given itemName, sets its actionCommandName
   * to be the same as the itemName, adds it to the given menu, and puts it in
   * this.editMenuItemStringMap.
   *
   * @param menu     the menu to which the new item is added
   * @param itemName the name that should be displayed in the menu slot for this item
   * @throws IllegalArgumentException if menu or itemName are null
   */
  private void createMenuItem(JMenu menu, String itemName) throws IllegalArgumentException {
    if (menu == null) {
      throw new IllegalArgumentException("The given menu cannot be null!");
    }

    if (itemName == null) {
      throw new IllegalArgumentException("The given itemName cannot be null!");
    }

    this.createMenuItem(menu, itemName, itemName);
  }

  /**
   * Update the currently displayed histograms to the currently displayed image.
   */
  private void updateHistograms() {
    this.histogramsPanel.removeAll();
    int i = this.imageTabs.getSelectedIndex();
    ArrayList<IImageHistogram> histograms = this.imageHistograms.get(i);
    for (IImageHistogram histogram : histograms) {
      JPanel panel = new HistogramPanel(histogram);
      panel.setPreferredSize(new Dimension(300, 200));
      panel.setBackground(Color.WHITE);
      this.histogramsPanel.add(panel);
    }
  }

}
