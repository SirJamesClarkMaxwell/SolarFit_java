package pl.solarfit.io;

import pl.solarfit.simplex.MPoint;

import java.util.List;
import java.util.stream.Collectors;

public class CharacteristicsParse
{
    private final List<String> listOfCharacteristic;

    public CharacteristicsParse(List<String> listOfCharacteristic)
    {
        this.listOfCharacteristic = listOfCharacteristic;
    }

    public List<MPoint> parseCharacteristics()
    {
        return this.listOfCharacteristic.stream().map(x -> x.split("\t")).map(this::convertData).collect(Collectors.toList());
    }

    private MPoint convertData(String[] data)
    {
        double x = Double.parseDouble(data[0]);
        double y = Double.parseDouble(data[1]);
        double z = Double.parseDouble(data[2]);
        return new MPoint(x, y, z);
    }
}
