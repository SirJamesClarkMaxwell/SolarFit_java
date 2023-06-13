package pl.solarfit.fit;

import pl.solarfit.simplex.ImplementsSimplex;
import pl.solarfit.simplex.MPoint;

public class PreFit extends ParametersToFit
{
    Double limitMin = Double.valueOf(0.5D);
    Double limitMax = Double.valueOf(1.00D);
    public PreFit(double[] noweParametry, double[] parametry, double[] lambda, double[] bMin, double[] bMax, double temp)
    {
        super(noweParametry, parametry, lambda, bMin, bMax, temp);
    }

    public void dataFit()
    {
        double[] parametry = new double[4];
        double[] lambda = new double[4];
        double[] bMin = new double[4];
        double[] bMax = new double[4];



        double temp = Double.parseDouble(this.gui.temp.getText());

        lambda[0] = parametry[0] / 5.0D;
        lambda[1] = parametry[1] / 5.0D;
        lambda[2] = parametry[2] / 5.0D;
        lambda[3] = parametry[3] / 5.0D;

        System.out.println("Parameters read:");
        System.out.println("  I0: " + parametry[0]);
        System.out.println("   A: " + parametry[1]);
        System.out.println("Rsch: " + parametry[2]);
        System.out.println("  Rs: " + parametry[3]);
        System.out.println("   T: " + temp);

        this.fitted.clear();


        System.out.println("Using experimental data");
        for (int i = 0; i < this.dane.size(); i++)

        {

            if (((MPoint) this.dane.get(i)).getX() >= this.limitMin.doubleValue() && ((MPoint) this.dane.get(i)).getX() <= this.limitMax.doubleValue())
            {
                this.fitted.add(new MPoint(((MPoint) this.dane.get(i)).getX(), ((MPoint) this.dane.get(i)).getY()));
            }
        }
        this.sim = null;
        this.sim = new ImplementsSimplex(150.0D, 4, parametry, lambda, this.fitted, temp);
        this.sim.setTemp(temp);
    }
}
