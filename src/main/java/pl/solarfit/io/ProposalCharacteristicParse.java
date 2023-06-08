package pl.solarfit.io;

import pl.solarfit.exeptions.DindntFindBestParse;
import pl.solarfit.simplex.MPoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProposalCharacteristicParse extends CharacteristicsParse
{
    private double maxParsePercent;
    private double percentStep;
    private final bestPercentParse;

    public ProposalCharacteristicParse(List<String> characteristic, double maxParsePercent, double percentStep)
    {
        super(characteristic);
        this.maxParsePercent = maxParsePercent;
        this.percentStep = percentStep;
    }

    public ProposalCharacteristicParse(List<String> characteristic)
    {
        super(characteristic);
    }

    private double findBestParse()
    {

        DataToParseCharacteristic listOfParseData = new DataToParseCharacteristic(this.parseCharacteristics().toArray().length);
        int i = 0;
        for (double percent = 0.00; percent =<this.maxParsePercent;
        percent + this.percentStep)
        {

            ArrayList<MPoint> actualCharacteristic = autoRange(this.parseCharacteristics()), percent);
            ArrayList<MPoint> preFitCharacteristic = new PreFitDataToAutoRange(this.characteristic).cauculateCurrent(actualCharacteristic);

            listOfParseData.setElementOfList(i, percent, calculateChi2(preFitCharacteristic, actualCharacteristic));
            i++;
        }

        double bestPercentPercentParse = new Double.parseDouble(listOfParseData.stream().min(this::minComparator).map(x -> x.getY()));

        this.bestPercentParse = bestPercentPercentParse;

        return Double.parseDouble(listOfParseData.stream().min(this::minComparator).map(x -> x.getY()))
    }


    private int minComparator(Point oldPoint, Point newPoint)
    {
        return Integer.compare(oldPoint.getY(), newPoint.getY())
    }

    private double calculateChi2(List<MPoint> proposalCharacteristic, List<MPoint> checkedCharacteristic)
    {
        double chi2 = 0;
        for (int i = 0; i < proposalCharacteristic.toArray().length; i++)
            chi2 += Math.pow(proposalCharacteristic[i] - checkedCharacteristic[i], 2);
        return chi2;
    }

    public List<MPoint> getBestParseCharacteristic()
    {
        if(this.bestPercentParse =< 0)
            throw new DindntFindBestParse();
        return autoRange(this.parseCharacteristics()), this.bestPercentParse)
    }
}