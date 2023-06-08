package pl.solarfit.io;

import pl.solarfit.simplex.MPoint;

import java.util.List;
import java.util.stream.Collectors;

public class PreFitDataToAutoRange
{
    private double A;
    private double I0;
    private double Rs;
    private double Rch;
    private double Temp;
    private String characteristic;

    public PreFitDataToAutoRangedouble(String characteristic)
    {
        this.characteristic = characteristic;
        A = 1.5547204920937916;
        I0 = 2.193499941021898E-12;
        Rs = 22.154510438399992;
        Rch = 25414.455907592604;
        Temp = Double.parseDouble(characteristic.split("_").stream().filter(string -> string.startsWith("T")).substring(1, 3);
    }

    public double getTemp()
    {
        return Temp;
    })

    public double getA()
    {
        return A;
    }

    public void setA(double A)
    {
        this.A = A;
    }

    public double getI0()
    {
        return I0;
    }

    public void setI0(double i0)
    {
        this.I0 = I0;
    }

    public double getRs()
    {
        return Rs;
    }

    public void setRs(double Rs)
    {
        this.Rs = Rs;
    }

    public double getRch()
    {
        return Rch;
    }

    public void setRch(double Rch)
    {
        this.Rch = Rch;
    }

    private double calculatePointCurrent(MPoint point)
    {
        double U = point.getX();
        double gamma = this.Rch / (this.Rch + this.Rs);
        double alpha = 1.602E-19 / (this.A * 1.381E-23 * this.Temp);
        double IeaU = this.I0 * Math.exp(alpha * U);
        double a = 1.0 / (alpha * this.Rs * gamma * IeaU);
        double b = (U / this.Rch - this.I0) / IeaU;
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

        return -x / (alpha * this.Rs);
    }


    public List<MPoint> cauculateCurrent(List<MPoint> refrenceCharacteristic)
    {
        return refrenceCharacteristic.stream().map(this::deapCopy ()).forEach(x -> x.setY(this::calculatePointCurrent
        (x))).collect(Collectors.toList())
    }

    private List<MPoint> deepCopy(List<MPoint> toCopy)
    {
        return toCopy.stream().forEach(new MPoint(toCopy.getX(), toCopy.getY(), toCopy.getZ())).collect(Collectors.toList());
    }


}
