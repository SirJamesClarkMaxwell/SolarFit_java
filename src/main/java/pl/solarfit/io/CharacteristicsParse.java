package pl.solarfit.io;

import pl.solarfit.exeptions.MaxValuNotFoundExeption;
import pl.solarfit.simplex.MPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CharacteristicsParse
{
    protected final List<String> characteristic;
    private boolean isLog;

    public CharacteristicsParse(List<String> characteristic,boolean isLog)
    {
        this.characteristic = characteristic;
        this.isLog = isLog;

    }

    public List<MPoint> parseCharacteristics()
    {
        return this.characteristic.stream().map(x -> x.split("\t")).map(this::convertData).collect(Collectors.toList());
    }

    private MPoint convertData(String[] data)

    {
        double x = Double.parseDouble(data[0]);
        double y = 0;
        double z = Double.parseDouble(data[2]);

        if (this.isLog)
        {
            y = Math.log(Math.abs((double) Double.parseDouble(data[1])));
        } else
        {
            y = Double.parseDouble(data[1]);
        }

        return new MPoint(x, y, z);
    }

    /**
     * @param dataToAutoRange
     * @param percent
     * @return
     */
    public List<MPoint> autoRange(List<MPoint> dataToAutoRange, double percent)
    {
        double maxCurrent = findMaxValue(dataToAutoRange);
        return dataToAutoRange.stream().filter(x -> x.getY() < maxCurrent * (100 - percent)).collect(Collectors.toList());

    }

    private int maxComparator(MPoint oldPoint, MPoint newPoint)
    {
        return Double.compare(oldPoint.getY(), newPoint.getY());
    }

    private double findMaxValue(List<MPoint> pointToCheck)
    {
        return pointToCheck.stream().max(this::maxComparator).map(MPoint::getY).orElseThrow(MaxValuNotFoundExeption::new);
    }
}

