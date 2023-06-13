package pl.solarfit.fit;
import pl.solarfit.simplex.ImplementsSimplex;
public class Fit
{
    public void daneFit(boolean append)
    {

        double[] noweParametry = new double[4];
        double[] parametry = new double[4];
        double[] lambda = new double[4];
        double[] bMin = new double[4];
        double[] bMax = new double[4];

        int chi2seg = 0;
        String curTab = null;
        bMin[0] = Double.parseDouble(this.gui.bMin0.getText());
        bMin[1] = Double.parseDouble(this.gui.bMin1.getText());
        bMin[2] = Double.parseDouble(this.gui.bMin2.getText());
        bMin[3] = Double.parseDouble(this.gui.bMin3.getText());

        bMax[0] = Double.parseDouble(this.gui.bMax0.getText());
        bMax[1] = Double.parseDouble(this.gui.bMax1.getText());
        bMax[2] = Double.parseDouble(this.gui.bMax2.getText());
        bMax[3] = Double.parseDouble(this.gui.bMax3.getText());

        parametry[0] = Double.parseDouble(this.gui.par0.getText());
        parametry[1] = Double.parseDouble(this.gui.par1.getText());
        parametry[2] = Double.parseDouble(this.gui.par2.getText());
        parametry[3] = Double.parseDouble(this.gui.par3.getText());

        lambda = getLambda(parametry);

        double temp = Double.parseDouble(this.gui.temp.getText());
        noweParametry[0] = 0.0D;
        noweParametry[1] = 0.0D;
        noweParametry[2] = 0.0D;
        noweParametry[3] = 0.0D;

        this.sim.setTemp(temp);
        this.sim.setBounds(bMax, bMin);
        this.sim.minimalizeAnnealed();

        noweParametry = this.sim.getFitedPar();

        this.fit.clear();
        this.fit = this.sim.fitedCurve(noweParametry);
        double newChi2 = getChi2();
        if (append)
        {
            if (newChi2 < this.deltaChi2[0][0])
            {
                chi2seg = 1;
            } else if (newChi2 < this.deltaChi2[0][1])
            {
                chi2seg = 2;
            } else if (newChi2 < this.deltaChi2[0][2])
            {
                chi2seg = 3;
            }
            log(noweParametry[0] + "\t" + noweParametry[1] + "\t" + noweParametry[2] + "\t" + noweParametry[3] + "\t" + newChi2 + "\t" + chi2seg);
        }
        int ndf = getNDF();
        int mcX = getMCX();
        int mcY = getMCY();

        if (append)
        {
            curTab = this.gui.tabs.getComponentAt(this.gui.tabs.getSelectedIndex()).getName();
            if ("tabPlot".equals(curTab))
            {
                plotData(this.gui.logScaleButton.isSelected());
            } else if ("tabCovRR".equals(curTab))
            {
                plotRR();
            } else if ("tabCovIA".equals(curTab))
            {
                plotIA();
            } else if ("tabMC".equals(curTab))
            {
                plotMC(ndf, mcX, mcY);
            }
            this.parI.add(Double.valueOf(noweParametry[0]));
            this.parA.add(Double.valueOf(noweParametry[1]));
            this.parRsch.add(Double.valueOf(noweParametry[2]));
            this.parRs.add(Double.valueOf(noweParametry[3]));
            this.chi2.add(Double.valueOf(newChi2));

        } else
        {
            plotData(this.gui.logScaleButton.isSelected());
        }

        this.gui.parFit0.setText(String.valueOf(noweParametry[0]));
        this.gui.parFit1.setText(String.valueOf(noweParametry[1]));
        this.gui.parFit2.setText(String.valueOf(noweParametry[2]));
        this.gui.parFit3.setText(String.valueOf(noweParametry[3]));

        public void daneFit ( boolean append){
        double[] noweParametry = new double[4];
        double[] parametry = new double[4];
        double[] lambda = new double[4];
        double[] bMin = new double[4];
        double[] bMax = new double[4];

        int chi2seg = 0;
        String curTab = null;
        bMin[0] = Double.parseDouble(this.gui.bMin0.getText());
        bMin[1] = Double.parseDouble(this.gui.bMin1.getText());
        bMin[2] = Double.parseDouble(this.gui.bMin2.getText());
        bMin[3] = Double.parseDouble(this.gui.bMin3.getText());

        bMax[0] = Double.parseDouble(this.gui.bMax0.getText());
        bMax[1] = Double.parseDouble(this.gui.bMax1.getText());
        bMax[2] = Double.parseDouble(this.gui.bMax2.getText());
        bMax[3] = Double.parseDouble(this.gui.bMax3.getText());

        parametry[0] = Double.parseDouble(this.gui.par0.getText());
        parametry[1] = Double.parseDouble(this.gui.par1.getText());
        parametry[2] = Double.parseDouble(this.gui.par2.getText());
        parametry[3] = Double.parseDouble(this.gui.par3.getText());

        lambda = getLambda(parametry);

        double temp = Double.parseDouble(this.gui.temp.getText());

        noweParametry[0] = 0.0D;
        noweParametry[1] = 0.0D;
        noweParametry[2] = 0.0D;
        noweParametry[3] = 0.0D;

        this.sim.setTemp(temp);
        this.sim.setBounds(bMax, bMin);
        this.sim.minimalizeAnnealed();

        noweParametry = this.sim.getFitedPar();
        this.fit.clear();
        this.fit = this.sim.fitedCurve(noweParametry);
        double newChi2 = getChi2();

        if (append)
        {
            if (newChi2 < this.deltaChi2[0][0])
            {
                chi2seg = 1;
            } else if (newChi2 < this.deltaChi2[0][1])
            {
                chi2seg = 2;
            } else if (newChi2 < this.deltaChi2[0][2])
            {
                chi2seg = 3;
            }
            log(noweParametry[0] + "\t" + noweParametry[1] + "\t" + noweParametry[2] + "\t" + noweParametry[3] + "\t" + newChi2 + "\t" + chi2seg);
        }
        int ndf = getNDF();
        int mcX = getMCX();
        int mcY = getMCY();
        if (append)
        {
            curTab = this.gui.tabs.getComponentAt(this.gui.tabs.getSelectedIndex()).getName();

            if ("tabPlot".equals(curTab))
            {
                plotData(this.gui.logScaleButton.isSelected());

            } else if ("tabCovRR".equals(curTab))
            {
                plotRR();

            } else if ("tabCovIA".equals(curTab))
            {
                plotIA();

            } else if ("tabMC".equals(curTab))
            {
                plotMC(ndf, mcX, mcY);
            }

            this.parI.add(Double.valueOf(noweParametry[0]));

            this.parA.add(Double.valueOf(noweParametry[1]));

            this.parRsch.add(Double.valueOf(noweParametry[2]));

            this.parRs.add(Double.valueOf(noweParametry[3]));

            this.chi2.add(Double.valueOf(newChi2));

        } else
        {

            plotData(this.gui.logScaleButton.isSelected());
        }

        this.gui.parFit0.setText(String.valueOf(noweParametry[0]));
        this.gui.parFit1.setText(String.valueOf(noweParametry[1]));
        this.gui.parFit2.setText(String.valueOf(noweParametry[2]));
        this.gui.parFit3.setText(String.valueOf(noweParametry[3]));
    }
    }
}
