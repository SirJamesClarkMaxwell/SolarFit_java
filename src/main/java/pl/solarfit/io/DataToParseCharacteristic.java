package pl.solarfit.io;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DataToParseCharacteristic
{
    private int numberOfPoints;
    private List<Point> listOfChi2andPercent;

    public DataToParseCharacteristic(int numberOfPoints)
    {
        this.numberOfPoints = numberOfPoints;
        this.listOfChi2andPercent = new ArrayList<Point>(numberOfPoints);
    }

    public setElementOfList(int numberOfElement, double precent, double chi2)
    {
        this.listOfChi2andPercent[numberOfElement] = new Point(percent, chi2);
    }

}
