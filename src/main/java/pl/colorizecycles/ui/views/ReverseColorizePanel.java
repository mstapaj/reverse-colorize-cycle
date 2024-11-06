package pl.colorizecycles.ui.views;

import pl.colorizecycles.colorizeMethods.ColorizeResult;
import pl.colorizecycles.colorizeMethods.ReverseColorizeMethod;
import pl.colorizecycles.ui.ErrorMessageFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.*;

import static pl.colorizecycles.ui.DefaultValues.inputInsets;
import static pl.colorizecycles.ui.DefaultValues.timeoutMinutes;
import static pl.colorizecycles.utils.UtilsMethods.convertListListToArray;
import static pl.colorizecycles.utils.UtilsMethods.transposeArrayList;

public class ReverseColorizePanel extends JPanel {
    private final JTextField verticesNumber;
    private final JTextField iterations;
    private final JCheckBox checkBoxColorReduce;
    private final JTable resultTable;
    private final JScrollPane jScrollPane;
    private final JTextField maxProcessorNumber;
    private final ReverseColorizeMethod reverseColorizeMethod;
    private final JLabel elapsedTimeColorize;
    private final JLabel elapsedTimeReverseColorize;

    public ReverseColorizePanel(ActionListener backToMenu, ReverseColorizeMethod reverseColorizeMethod, String pageTitle) {
        this.reverseColorizeMethod = reverseColorizeMethod;
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel(pageTitle, SwingConstants.CENTER);
        JPanel verticesPanel = new JPanel(new BorderLayout());
        verticesPanel.add(title, BorderLayout.NORTH);
        JLabel verticesNumberLabel = new JLabel("Vertices number:  ");
        verticesPanel.add(verticesNumberLabel, BorderLayout.WEST);
        verticesNumber = new JTextField();
        verticesNumber.setMargin(inputInsets);
        verticesPanel.add(verticesNumber, BorderLayout.CENTER);
        top.add(verticesPanel, BorderLayout.NORTH);

        JPanel iterationsPanel = new JPanel(new BorderLayout());
        JLabel iterationLabel = new JLabel("Expected iterations:  ");
        iterationsPanel.add(iterationLabel, BorderLayout.WEST);
        iterations = new JTextField();
        iterations.setColumns(20);
        iterations.setMargin(inputInsets);
        iterationsPanel.add(iterations, BorderLayout.CENTER);
        top.add(iterationsPanel, BorderLayout.WEST);

        JPanel checkBoxPanel = new JPanel(new BorderLayout());
        JLabel checkBoxColorReduceLabel = new JLabel("5, 4, 3 color reduce:  ");
        checkBoxPanel.add(checkBoxColorReduceLabel, BorderLayout.WEST);
        checkBoxColorReduce = new JCheckBox();
        checkBoxPanel.add(checkBoxColorReduce, BorderLayout.CENTER);
        top.add(checkBoxPanel, BorderLayout.EAST);

        JPanel maxProcessorPanel = new JPanel(new BorderLayout());
        JLabel maxProcessorLabel = new JLabel("Max processor number:  ");
        maxProcessorPanel.add(maxProcessorLabel, BorderLayout.WEST);
        maxProcessorNumber = new JTextField();
        maxProcessorNumber.setMargin(inputInsets);
        maxProcessorPanel.add(maxProcessorNumber, BorderLayout.CENTER);
        top.add(maxProcessorPanel, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel buttonPanel = new JPanel();
        JButton buttonCalculate = new JButton("Reverse colorize cycle");
        buttonCalculate.addActionListener(new CalculateReverseColors());
        buttonPanel.add(buttonCalculate);
        JButton buttonBack = new JButton("Back");
        buttonBack.addActionListener(backToMenu);
        buttonPanel.add(buttonBack);
        bottom.add(buttonPanel, BorderLayout.NORTH);

        JPanel timePanel = new JPanel(new BorderLayout());
        elapsedTimeColorize = new JLabel();
        timePanel.add(elapsedTimeColorize, BorderLayout.WEST);
        elapsedTimeReverseColorize = new JLabel();
        timePanel.add(elapsedTimeReverseColorize, BorderLayout.EAST);
        add(timePanel, BorderLayout.CENTER);

        resultTable = new JTable();
        DefaultTableCellRenderer endRenderer = new DefaultTableCellRenderer();
        endRenderer.setHorizontalAlignment(JLabel.RIGHT);
        resultTable.setDefaultRenderer(Object.class, endRenderer);
        resultTable.setRowHeight(30);
        resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jScrollPane = new JScrollPane(resultTable);
        jScrollPane.setPreferredSize(new Dimension(900, 400));
        bottom.add(jScrollPane, BorderLayout.SOUTH);
        add(bottom, BorderLayout.SOUTH);
    }

    private class CalculateReverseColors implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<ColorizeResult> future = executor.submit(() -> reverseColorizeMethod.reverseColorize(
                    Integer.parseInt(verticesNumber.getText().replaceAll("\\s", "")),
                    Integer.parseInt(iterations.getText().replaceAll("\\s", "")),
                    checkBoxColorReduce.isSelected(),
                    Integer.parseInt(maxProcessorNumber.getText().replaceAll("\\s", "")))
            );

            try {
                ColorizeResult colorizeResult = future.get(timeoutMinutes, TimeUnit.MINUTES);

                if (!colorizeResult.isSuccessful()) {
                    new ErrorMessageFrame("Calculation failed");
                } else {
                    String[][] data = convertListListToArray(transposeArrayList(colorizeResult.getPreparedTableData()));
                    String[] columnNames = colorizeResult.getPreparedHeaders().toArray(new String[0]);
                    DefaultTableModel model = new DefaultTableModel(data, columnNames);
                    elapsedTimeColorize.setText("Elapsed time colorize: " + colorizeResult.getColorizeTime());
                    elapsedTimeReverseColorize.setText("Elapsed time reverse colorize: " + colorizeResult.getReverseColorizeTime());
                    elapsedTimeColorize.repaint();
                    elapsedTimeReverseColorize.repaint();
                    resultTable.setModel(model);
                    resultTable.repaint();
                    jScrollPane.repaint();
                }
            } catch (NumberFormatException err) {
                new ErrorMessageFrame("Wrong format of the numbers entered");
            } catch (OutOfMemoryError err) {
                new ErrorMessageFrame("Out of memory error during calculations");
            } catch (TimeoutException err) {
                new ErrorMessageFrame("Timeout error: Calculation took too long");
                future.cancel(true);
            } catch (InterruptedException | ExecutionException err) {
                new ErrorMessageFrame("Error during calculations: " + err.getMessage());
            } finally {
                executor.shutdown();
            }
        }
    }
}
