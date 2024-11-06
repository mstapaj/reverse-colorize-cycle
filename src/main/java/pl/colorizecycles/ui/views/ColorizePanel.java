package pl.colorizecycles.ui.views;

import pl.colorizecycles.colorizeMethods.ColorizeResult;
import pl.colorizecycles.ui.ErrorMessageFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static pl.colorizecycles.colorizeMethods.Colorize.colorize;
import static pl.colorizecycles.ui.DefaultValues.inputInsets;
import static pl.colorizecycles.ui.DefaultValues.timeoutMinutes;
import static pl.colorizecycles.utils.UtilsMethods.convertListListToArray;
import static pl.colorizecycles.utils.UtilsMethods.transposeArrayList;

public class ColorizePanel extends JPanel {

    private final JTextField inputText;
    private final JTable resultTable;
    private final JScrollPane jScrollPane;
    private final JLabel elapsedTimeColorize;

    public ColorizePanel(ActionListener backToMenu, String pageTitle) {
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel(pageTitle, SwingConstants.CENTER);
        top.add(title, BorderLayout.NORTH);

        JLabel inputLabel = new JLabel("Enter numbers separated by a comma");
        top.add(inputLabel, BorderLayout.CENTER);

        inputText = new JTextField();
        inputText.setMargin(inputInsets);
        inputText.setToolTipText("Enter numbers separated by a comma");
        top.add(inputText, BorderLayout.SOUTH);

        add(top);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel buttonPanel = new JPanel();
        JButton buttonCalculate = new JButton("Colorize cycle");
        buttonCalculate.addActionListener(new CalculateColors());
        buttonPanel.add(buttonCalculate);
        JButton buttonBack = new JButton("Back");
        buttonBack.addActionListener(backToMenu);
        buttonPanel.add(buttonBack);
        bottom.add(buttonPanel, BorderLayout.NORTH);

        elapsedTimeColorize = new JLabel();
        bottom.add(elapsedTimeColorize, BorderLayout.CENTER);

        resultTable = new JTable();
        resultTable.setRowHeight(30);
        DefaultTableCellRenderer endRenderer = new DefaultTableCellRenderer();
        endRenderer.setHorizontalAlignment(JLabel.RIGHT);
        resultTable.setDefaultRenderer(Object.class, endRenderer);
        resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jScrollPane = new JScrollPane(resultTable);
        jScrollPane.setPreferredSize(new Dimension(900, 400));
        bottom.add(jScrollPane, BorderLayout.SOUTH);

        add(bottom, BorderLayout.SOUTH);
    }


    private class CalculateColors implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<ColorizeResult> future = executor.submit(() -> {
                List<Integer> numbers = Arrays.stream(inputText.getText().split(","))
                        .map(n -> Integer.parseInt(n.replaceAll("\\s", "")))
                        .toList();
                return colorize(numbers);
            });

            try {
                ColorizeResult resultData = future.get(timeoutMinutes, TimeUnit.MINUTES);

                if (!resultData.isSuccessful()) {
                    new ErrorMessageFrame("Calculation failed");
                } else {
                    String[][] data = convertListListToArray(transposeArrayList(resultData.getPreparedTableData()));
                    String[] columnNames = resultData.getPreparedHeaders().toArray(new String[0]);
                    DefaultTableModel model = new DefaultTableModel(data, columnNames);
                    resultTable.setModel(model);
                    elapsedTimeColorize.setText("Elapsed time: " + resultData.getColorizeTime());
                    elapsedTimeColorize.repaint();
                    resultTable.repaint();
                    jScrollPane.repaint();
                }
            } catch (NumberFormatException err) {
                new ErrorMessageFrame("Wrong format of the numbers entered");
            } catch (TimeoutException err) {
                new ErrorMessageFrame("Timeout error: Calculation took too long");
                future.cancel(true);
            } catch (InterruptedException | ExecutionException err) {
                new ErrorMessageFrame("Error: " + err.getMessage());
            }
            finally {
                executor.shutdown();
            }
        }
    }
}
