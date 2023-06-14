package pl.solarfit.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import pl.solarfit.exeptions.MaxValuNotFoundExeption;
import pl.solarfit.io.CharacteristicsParse;
import pl.solarfit.io.FileLoader;
import pl.solarfit.simplex.MPoint;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Plots
{
    private JSlider bottomslider;
    private JSlider topslider;
    private JButton plotCharacteristicsButton;
    private JButton plotCovarianceRsRchButton;
    private JButton plotCovarianceAI0Button;
    private JButton poltMonteCarloResultsButton;
    private JList list1;
    private JPanel plotPanel;
    private JButton addCharacteristicButton;
    private JFrame f;

    private List<List<String>> stringDatafiles = new ArrayList<>();

    public Plots()
    {

        plotCovarianceRsRchButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

            }
        });
        plotCovarianceAI0Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

            }
        });
        poltMonteCarloResultsButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

            }
        });
        plotCharacteristicsButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JDialog d = new JDialog(f, "Plots");

                ChartPanel chart = null;
                try
                {
                    chart = createUIComponents();
                } catch (CloneNotSupportedException ex)
                {
                    throw new RuntimeException(ex);
                }
                // create a label
                JLabel l = new JLabel("this is a dialog box");


                d.add(l);
                d.add(chart);
                // setsize of dialog
                d.setSize(500, 500);

                // set visibility of dialog
                d.setVisible(true);
            }
        });
        list1.addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {

            }
        });
        bottomslider.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {

            }
        });
        topslider.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {

            }
        });
        addCharacteristicButton.addActionListener(actionEvent -> enterData());

    }

    private void enterData()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);

        int returnValue = fileChooser.showDialog(null, "Open");

        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
            File[] selectedFile = fileChooser.getSelectedFiles();
            for (File file : selectedFile)
                this.stringDatafiles.add(FileLoader.readFile(file.getPath()));
        }
    }

    public List<List<String>> getStringDatafiles()
    {
        return stringDatafiles;
    }

//    public List<MPoint> autoRange(List<MPoint> dataToAutoRange, double percent)
//    {
//        double maxCurrent = findMaxValue(dataToAutoRange);
//        return dataToAutoRange.stream().filter(x -> x.getY() < maxCurrent * (100 - percent)).collect(Collectors.toList());
//
//    }

    public void setStringDatafiles(List<List<String>> stringDatafiles)
    {
        this.stringDatafiles = stringDatafiles;
    }

    private XYDataset createDataset() throws CloneNotSupportedException
    {
        List<XYSeries> seriesOfData = new ArrayList<>();
        XYSeriesCollection dataSets = new XYSeriesCollection();
        int j = this.stringDatafiles.size();
        for (int i = 0; i < this.stringDatafiles.size(); i++)
        {
            XYSeries currentSeries = new XYSeries("");
            List<MPoint> currentCharacteristic = new CharacteristicsParse(this.stringDatafiles.get(i), false).parseCharacteristics();
            int upperLimitOfcharacteristic = autoRange(currentCharacteristic, 10);
            List<MPoint> logRnagedCharacteristic = recreateCharacteristic(currentCharacteristic, upperLimitOfcharacteristic);
            // tutaj do podmiany jest na ProposalCharacterisitcParse

            for (MPoint currentPoint : logRnagedCharacteristic)
                currentSeries.add(currentPoint.getX(), currentPoint.getY());
            int lengthOfCurrentSeries = currentSeries.getItemCount();

            seriesOfData.add((XYSeries) currentSeries.clone());
            currentSeries.clear();
        }
//        for (List<String> stringCharacteristic : this.stringDatafiles)
//        {
//            XYSeries currentSeries = new XYSeries("");
//            List<MPoint> currentCharacteristic = new CharacteristicsParse(stringCharacteristic).parseCharacteristics();
//            List<MPoint> rangedCharacteristic = autoRange(currentCharacteristic, 10);
//            // tutaj do podmiany jest na ProposalCharacterisitcParse
//
//            for (MPoint currentPoint : rangedCharacteristic)
//                currentSeries.add(currentPoint.getX(), currentPoint.getY());
//
//            seriesOfData.add(currentSeries);
//            currentSeries.clear();
//        }


        for (XYSeries seriesToAdd : seriesOfData)
            dataSets.addSeries(seriesToAdd);


        return dataSets;
    }

    private List<MPoint> recreateCharacteristic(List<MPoint> characteristicToRecreate, int maxInt)
    {
        List<MPoint> recreatedCharacteristic = new ArrayList<>();
        for (int i = 0; i < maxInt; i++)
            recreatedCharacteristic.add(new MPoint(characteristicToRecreate.get(i).getX(), Math.log(Math.abs((double) characteristicToRecreate.get(i).getY()))));

        return recreatedCharacteristic;
    }

    public int autoRange(List<MPoint> dataToAutoRange, double percent)
    {
        double maxCurrent = findMaxValue(dataToAutoRange);
        double threshold = maxCurrent * (1 - (percent / 100));

        List<MPoint> filteredData = new ArrayList<>();
        for (MPoint point : dataToAutoRange)
        {
            if (point.getY() < threshold)
            {
                filteredData.add(point);
            }
        }

        return filteredData.size();
    }

    private int maxComparator(MPoint oldPoint, MPoint newPoint)
    {
        return Double.compare(oldPoint.getY(), newPoint.getY());
    }

    //    private double findMaxValue(List<MPoint> pointToCheck)
//    {
//        return pointToCheck.stream().max(this::maxComparator).map(MPoint::getY).orElseThrow(MaxValuNotFoundExeption::new);
//    }
    private double findMaxValue(List<MPoint> pointToCheck)
    {
        if (pointToCheck.isEmpty())
        {
            throw new MaxValuNotFoundExeption();
        }

        double maxValue = pointToCheck.get(0).getY();
        for (MPoint point : pointToCheck)
        {
            if (point.getY() > maxValue)
            {
                maxValue = point.getY();
            }
        }

        return maxValue;
    }

    private JFreeChart createChart(XYDataset dataset)
    {

        JFreeChart chart = ChartFactory.createXYLineChart("", //tytul
                "U [V]",     //x label
                "I [mA]",   //y label
                dataset, PlotOrientation.VERTICAL, true, true, false);

        XYPlot plot = chart.getXYPlot();

        var renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f)); //kolor wykresu pierwszej serii

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("", new Font("Serif", Font.BOLD, 18)));

        return chart;
    }

    private ChartPanel createUIComponents() throws CloneNotSupportedException
    {
        XYDataset dataset = null;
        try
        {
            dataset = createDataset();
        } catch (CloneNotSupportedException e)
        {
            throw new RuntimeException(e);
        }
        JFreeChart chart = createChart(dataset);

        ChartPanel wykres = new ChartPanel(chart);
        wykres.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        wykres.setBackground(Color.white);
        //plotPanel.add(wykres);//stworz panel wykresu i dodaj go do panelu od wykresu
        // TODO: place custom component creation code here
        return wykres;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$()
    {
        plotPanel = new JPanel();
        plotPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 5, new Insets(0, 0, 0, 0), 0, 0));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        plotPanel.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, 1, 1, null, null, null, 0, false));
        bottomslider = new JSlider();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(bottomslider, gbc);
        topslider = new JSlider();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(topslider, gbc);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        plotPanel.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 2, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(1, 1), new Dimension(1, 1), new Dimension(1, 1), 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        plotPanel.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        poltMonteCarloResultsButton = new JButton();
        poltMonteCarloResultsButton.setText("polt Monte Carlo results");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(poltMonteCarloResultsButton, gbc);
        plotCharacteristicsButton = new JButton();
        plotCharacteristicsButton.setText("plot characteristics");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(plotCharacteristicsButton, gbc);
        plotCovarianceRsRchButton = new JButton();
        plotCovarianceRsRchButton.setText("plot Covariance Rs - Rch");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(plotCovarianceRsRchButton, gbc);
        plotCovarianceAI0Button = new JButton();
        plotCovarianceAI0Button.setText("plot Covariance A - I0");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(plotCovarianceAI0Button, gbc);
        list1 = new JList();
        list1.setDropMode(DropMode.ON);
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        defaultListModel1.addElement("1");
        defaultListModel1.addElement("2");
        defaultListModel1.addElement("3");
        defaultListModel1.addElement("4");
        defaultListModel1.addElement("5");
        defaultListModel1.addElement("6");
        list1.setModel(defaultListModel1);
        plotPanel.add(list1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTHWEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        addCharacteristicButton = new JButton();
        addCharacteristicButton.setText("add characteristic");
        plotPanel.add(addCharacteristicButton, new com.intellij.uiDesigner.core.GridConstraints(2, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$()
    {
        return plotPanel;
    }


}
