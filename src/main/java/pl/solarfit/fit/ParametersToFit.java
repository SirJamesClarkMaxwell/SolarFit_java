package pl.solarfit.fit;
import pl.solarfit.simplex.ImplementsSimplex;
import pl.solarfit.simplex.MPoint;

import java.util.ArrayList;
import java.util.List;

public abstract  class ParametersToFit
{

    double[] noweParametry = new double[4];
    double[] parametry = new double[4];
    double[] lambda = new double[4];
    double[] bMin = new double[4];
    double[] bMax = new double[4];
    double temp;
    ImplementsSimplex sim = null;
    List<MPoint> dane = new ArrayList<MPoint>();
    List<MPoint> fitted = new ArrayList<MPoint>();


    public ParametersToFit(double[] noweParametry, double[] parametry, double[] lambda, double[] bMin, double[] bMax,double temp)
    {
        this.noweParametry = noweParametry;
        this.parametry = parametry;
        this.lambda = this.getLambda(parametry);
        this.bMin = bMin;
        this.bMax = bMax;
        this.temp = temp;
    }

    private double[] getLambda(double[] parametry)
    {
        double[] lambda = new double[4];
        lambda[0] = parametry[0] / 5.0D;
        lambda[1] = parametry[1] / 5.0D;
        lambda[2] = parametry[2] / 5.0D;
        lambda[3] = parametry[3] / 5.0D;
        return lambda;
    }

}

/*
/! do wklejenia w miejscie gdzie będą przekazywane dane do konstruktora
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
 */