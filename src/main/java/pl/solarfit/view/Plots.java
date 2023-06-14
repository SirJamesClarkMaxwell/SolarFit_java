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

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JPanel chartPanel;
    private JFrame f;

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
                JDialog d = new JDialog(f, "dialog Box");

                ChartPanel chart  = createUIComponents();
                // create a label
                JLabel l = new JLabel("this is a dialog box");


                d.add(l);
                d.add(chart);
                // setsize of dialog
                d.setSize(100, 100);

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
    }

    private XYDataset createDataset()
    {

        var series1 = new XYSeries(""); //nazwa serii
        series1.add(18, 567);          //dane do serii, czyli wczytaj skads x i y i zrób serieA.
        series1.add(20, 612);
        series1.add(25, 800);
        series1.add(30, 980);
        series1.add(40, 1410);
        series1.add(50, 2350);
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);         //dodanie serii(mozna dodac wiecej, zeby bylo kilka wykresow na jednym panelu)

        return dataset;
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

    private ChartPanel createUIComponents()
    {
        XYDataset dataset = createDataset();
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
        plotPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 4, new Insets(0, 0, 0, 0), 0, 0));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        plotPanel.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, 1, 1, null, null, null, 0, false));
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
        plotPanel.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(1, 1), new Dimension(1, 1), new Dimension(1, 1), 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        plotPanel.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
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
        chartPanel = new JPanel();
        chartPanel.setLayout(new GridBagLayout());
        plotPanel.add(chartPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$()
    {
        return plotPanel;
    }


}
