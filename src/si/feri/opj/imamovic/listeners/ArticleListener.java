package si.feri.opj.imamovic.listeners;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ArticleListener implements ActionListener {

    private CardLayout cl;
    private JPanel panelContainer;

    public ArticleListener(CardLayout cl, JPanel panelContainer) {
        this.cl = cl;
        this.panelContainer = panelContainer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cl.show(panelContainer, "article");
    }

}
