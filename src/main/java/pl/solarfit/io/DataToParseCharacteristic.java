package pl.solarfit.io;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class DataToParseCharacteristic
{
    private int numberOfPoints;
    private List<Point2D> listOfChi2andPercent;

    public DataToParseCharacteristic(int numberOfPoints)
    {
        this.numberOfPoints = numberOfPoints;
        this.listOfChi2andPercent = new ArrayList<Point2D>(numberOfPoints);
    }

    public List<Point2D> getListOfChi2andPercent()
    {
        return listOfChi2andPercent;
    }

    public void setElementOfList(int numberOfElement, double precent, double chi2)
    {
        this.listOfChi2andPercent.add(numberOfElement, new Point2D.Double(precent, chi2));
    }

}
