package pl.solarfit.simplex;

public class MPoint
{
    private double x;
    private double y;
    private double z;

    public MPoint(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public MPoint()
    {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }

    public MPoint(double tx, double ty)
    {
        this.x = tx;
        this.y = ty;
        this.z = 0.0;
    }

    public double getZ()
    {
        return z;
    }

    public void setZ(double z)
    {
        this.z = z;
    }

    public void clear()
    {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }

    public void setXY(double tx, double ty)
    {
        this.x = tx;
        this.y = ty;
    }

    public void setXY(MPoint tp)
    {
        this.x = tp.getX();
        this.y = tp.getY();
    }

    public double getX()
    {
        return this.x;
    }

    public void setX(double tx)
    {
        this.x = tx;
    }

    public double getY()
    {
        return this.y;
    }

    public void setY(double ty)
    {
        this.y = ty;
    }
}
