package pl.solarfit.view;

import javax.swing.*;

public class MainFrame extends JFrame
{

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(()->
        {
            MainFrame mainFrame = new MainFrame();
            mainFrame.createGUI();
        });

    }

    private void createGUI()
    {
        this.setSize(600,600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(new MainWindow());
        this.pack();
        this.setVisible(true);
    }
}
