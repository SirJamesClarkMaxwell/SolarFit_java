package pl.solarfit.fit;

public class Parameters
{
    private double A;
    private double I0;
    private double Rs;
    private double Rch;

    public Parameters(double a, double i0, double rs, double rch)
    {
        A = a;
        I0 = i0;
        Rs = rs;
        Rch = rch;
    }

    public double getA()
    {
        return A;
    }

    public void setA(double a)
    {
        A = a;
    }

    public double getI0()
    {
        return I0;
    }

    public void setI0(double i0)
    {
        I0 = i0;
    }

    public double getRs()
    {
        return Rs;
    }

    public void setRs(double rs)
    {
        Rs = rs;
    }

    public double getRch()
    {
        return Rch;
    }

    public void setRch(double rch)
    {
        Rch = rch;
    }

    @Override
    public String toString()
    {
        StringBuilder toString = new StringBuilder();
        toString.append("A  =" + this.A);
        toString.append("I0  =" + this.I0);
        toString.append("Rs  =" + this.Rs);
        toString.append("Rch  =" + this.Rch);

        return toString.toString();
    }
}
