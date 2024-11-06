package pl.colorizecycles.ui.views;

import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static pl.colorizecycles.ui.DefaultValues.defaultInsets;

public class MenuPanel extends JPanel {

    public MenuPanel(ArrayList<Pair<String, ActionListener>> buttonOptions) {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        for (Pair<String, ActionListener> entry : buttonOptions) {
            JButton button = new JButton(entry.getLeft());
            button.setMargin(defaultInsets);
            button.addActionListener(entry.getRight());
            JPanel buttonPanel = new JPanel(new BorderLayout());
            buttonPanel.add(button, BorderLayout.CENTER);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
            menuPanel.add(buttonPanel);
        }
        add(menuPanel);
    }
}
