package pl.solarfit.fit;

import pl.solarfit.simplex.MPoint;

import java.util.ArrayList;

public class Fit extends ParametersToFit
{
    public double[][] deltaChi2 = new double[][]{{1.0D, 4.0D, 9.0D}, {2.3D, 6.17D, 11.8D}, {3.53D, 8.02D, 14.2D}, {4.72D, 9.7D, 16.3D}};
    private ArrayList<Double> parI = new ArrayList<Double>();
    private ArrayList<Double> parA = new ArrayList<Double>();
    private ArrayList<Double> parRs = new ArrayList<Double>();
    private ArrayList<Double> parRsch = new ArrayList<Double>();
    private ArrayList<Double> chi2 = new ArrayList<Double>();
    private ArrayList<MPoint> mcInit = new ArrayList<MPoint>();

    private boolean append = true;
    private double noise;

    public Fit(double[] noweParametry, double[] parametry, double[] lambda, double[] bMin, double[] bMax, double temp)
    {
        super(noweParametry, parametry, lambda, bMin, bMax, temp);
    }

    public boolean isAppend()
    {
        return append;
    }

    public void setAppend(boolean append)
    {
        this.append = append;
    }

    public double getNoise()
    {
        return noise;
    }

    public void setNoise(double noise)
    {
        this.noise = Double.parseDouble(String.valueOf(noise)) / 100.0D;
    }

    @Override
    protected void fit()
    {

        int chi2seg = 0;

        this.sim.setTemp(temp);
        this.sim.setBounds(bMax, bMin);
        this.sim.minimalizeAnnealed();

        noweParametry = this.sim.getFitedPar();
        this.fitted.clear();
        this.fitted = this.sim.fitedCurve(noweParametry);
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
        }

        if (append)
        {
            this.parI.add(Double.valueOf(noweParametry[0]));
            this.parA.add(Double.valueOf(noweParametry[1]));
            this.parRsch.add(Double.valueOf(noweParametry[2]));
            this.parRs.add(Double.valueOf(noweParametry[3]));
            this.chi2.add(Double.valueOf(newChi2));
        }

    }




    private double getChi2()
    {
        double suma_chi2 = 0.0D;
        double pomiar_chi2 = 0.0D;

        if (this.mcInit.size() > 0)
        {
            for (int i = 0; i < this.fitted.size(); i++)
            {
                double sigma = ((MPoint) this.fitted.get(i)).getY() * noise;
                pomiar_chi2 = Math.pow((((MPoint) this.fitted.get(i)).getY() - ((MPoint) this.mcInit.get(i)).getY()) / sigma, 2.0D);
                suma_chi2 += pomiar_chi2;
            }
            return suma_chi2;
        }
        return 0.0D;
    }


}

