package hw4.view;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class JFrameMosaicViewImpl extends JFrameViewImpl implements IGUIView{

  public JFrameMosaicViewImpl() {
    super();

    JMenu editMenu = new JMenu("Edit");
    this.createMenuItem(editMenu, "Mosaic", "Mosaic");
    super.getJMenuBar().add(editMenu);


  }


}
