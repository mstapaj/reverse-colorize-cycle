package pl.colorizecycles.ui;

import org.apache.commons.lang3.tuple.Pair;
import pl.colorizecycles.colorizeMethods.ReverseColorizeBruteforceIter;
import pl.colorizecycles.colorizeMethods.ReverseColorizeBruteforceRandom;
import pl.colorizecycles.colorizeMethods.ReverseColorizeBruteforceRec;
import pl.colorizecycles.ui.views.ColorizePanel;
import pl.colorizecycles.ui.views.MenuPanel;
import pl.colorizecycles.ui.views.ReverseColorizePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class MainFrame extends JFrame {

    private JPanel currentPanel;
    private final ActionListener switchToMenu = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            setSize(400, 230);
            switchPanel(new MenuPanel(menuOptions));
        }
    };
    private final ActionListener switchToColorizeCycle = e -> {
        setSize(1000, 620);
        switchPanel(new ColorizePanel(switchToMenu, "Colorize cycle"));
    };

    private final ActionListener switchToReverseColorizeRec = e -> {
        setSize(1000, 700);
        switchPanel(new ReverseColorizePanel(switchToMenu, new ReverseColorizeBruteforceRec(),
                "Reverse colorize cycle recursion"));
    };

    private final ActionListener switchToReverseColorizeIter = e -> {
        setSize(1000, 700);
        switchPanel(new ReverseColorizePanel(switchToMenu, new ReverseColorizeBruteforceIter(),
                "Reverse colorize cycle iterative"));
    };
    private final ActionListener switchToReverseColorizeRandom = e -> {
        setSize(1000, 700);
        switchPanel(new ReverseColorizePanel(switchToMenu, new ReverseColorizeBruteforceRandom(),
                "Reverse colorize cycle random list"));
    };
    private final ArrayList<Pair<String, ActionListener>> menuOptions = new ArrayList<>(Arrays.asList(
            Pair.of("Colorize cycle", switchToColorizeCycle),
            Pair.of("Reverse colorize cycle iter", switchToReverseColorizeIter),
            Pair.of("Reverse colorize cycle recursion", switchToReverseColorizeRec),
            Pair.of("Reverse colorize cycle random list", switchToReverseColorizeRandom)
    ));


    public MainFrame() {
        setTitle("Colorize Cycle App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(400, 230);
        setLocationRelativeTo(null);

        currentPanel = new MenuPanel(menuOptions);
        setContentPane(currentPanel);
    }

    private void switchPanel(JPanel newPanel) {
        getContentPane().removeAll();
        currentPanel = newPanel;
        getContentPane().add(currentPanel);
        revalidate();
        repaint();
    }
}
