package pl.solarfit.simplex;

import java.util.ArrayList;

public class ImplementsSimplex extends SimplexMethod
{
    private final double Q = 1.602E-19;
    private final double kB = 1.381E-23;
    private final double MAX_ERROR = 1.0E-12;
    ArrayList<MPoint> datas = null;
    private double Temp;

    public ImplementsSimplex(double T, int parNumber, double[] startPoint, double[] lambda, ArrayList<MPoint> datas1, double T1)
    {
        super(T, parNumber, startPoint, lambda);
        this.Temp = T1;
        this.datas = datas1;
    }

    public void setData(ArrayList<MPoint> newData)
    {
        this.datas.clear();
        this.datas.addAll(newData);
        System.out.println("[S] Ustawiono nowe dane (" + this.datas.size() + ")");
    }

    public double goalFunction()
    {
        return 10.0;
    }

    public double goalFunction(int parNumber, double[] par)
    {
        double chi2 = 0.0;

        for (int i = 0; i < this.datas.size(); ++i)
        {
            chi2 += (this.datas.get(i).getY() - this.current(this.datas.get(i), par, this.Temp)) * (this.datas.get(i).getY() - this.current(this.datas.get(i), par, this.Temp)) / Math.pow(this.datas.get(i).getY(), 2.0);
        }

        return Math.sqrt(chi2) / (double) this.datas.size();
    }

    public ArrayList<MPoint> fitedCurve(double[] par)
    {
        MPoint mP = null;
        ArrayList<MPoint> fitedC = new ArrayList();

        for (int i = 0; i < this.datas.size(); ++i)
        {
            mP = new MPoint();
            mP.setX(this.datas.get(i).getX());
            mP.setY(this.current(this.datas.get(i), par, this.Temp));
            fitedC.add(mP);
            mP = null;
        }

        return fitedC;
    }

    private double funF(MPoint point, double[] par)
    {
        return par[0] * point.getX() * point.getX() + par[1] * point.getX() + par[2];
    }

    private double current(MPoint point, double[] par, double T)
    {
        double U = point.getX();
        double gamma = par[2] / (par[2] + par[3]);
        double alpha = 1.602E-19 / (par[1] * 1.381E-23 * T);
        double IeaU = par[0] * Math.exp(alpha * U);
        double a = 1.0 / (alpha * par[3] * gamma * IeaU);
        double b = (U / par[2] - par[0]) / IeaU;
        double x1 = Math.min(0.0, -(1.0 + b) / a);
        double x2 = -b / a;
        double x = 0.0;
        double n = Math.ceil(this.log2((x2 - x1) / 1.0E-12));

        for (int i = 0; i < (int) n; ++i)
        {
            x = 0.5 * (x1 + x2);
            if (Math.exp(x) + a * x + b < 0.0)
            {
                x1 = x;
            } else
            {
                x2 = x;
            }
        }

        return -x / (alpha * par[3]);
    }

    public double[] getStartParameters(double[] par, int iter)
    {
        double[] parP = new double[4];
        double[] parM = new double[4];
        double[] dPar = new double[4];
        double Chi = this.goalFunction(4, par);
        double chiP = 0.0;
        double chiM = 0.0;
        double[] lam = this.getLambda();

        for (int i = 0; i < iter; ++i)
        {
            dPar[0] = lam[0] * (Math.random() - 0.5) * 4.0;
            dPar[1] = lam[1] * (Math.random() - 0.5) * 4.0;
            dPar[2] = lam[2] * (Math.random() - 0.5) * 2.0;
            dPar[3] = lam[3] * (Math.random() - 0.5) * 2.0;

            int j;
            for (j = 0; j < 4; ++j)
            {
                parP[j] = par[j] + dPar[j];
                parM[j] = par[j] - dPar[j];
            }

            chiP = this.goalFunction(4, parP);
            chiM = this.goalFunction(4, parM);
            if (chiM < Chi)
            {
                for (j = 0; j < 4; ++j)
                {
                    par[j] = parM[j];
                    lam[j] = parM[j] / 5.0;
                }

                Chi = chiM;
            }

            if (chiP < Chi)
            {
                for (j = 0; j < 4; ++j)
                {
                    par[j] = parP[j];
                    lam[j] = parP[j] / 5.0;
                }

                Chi = chiP;
            }
        }

        this.setLambda(lam);
        return par;
    }

    public double getTemp()
    {
        return this.Temp;
    }

    public void setTemp(double temp)
    {
        this.Temp = temp;
    }

    private double log2(double x)
    {
        return Math.log(x) / Math.log(2.0);
    }
}
