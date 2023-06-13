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
    @Override
    protected void fit()
    {
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
