package pl.solarfit.io;

import pl.solarfit.exeptions.DindntFindBestParse;
import pl.solarfit.simplex.MPoint;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProposalCharacteristicParse extends CharacteristicsParse
{
    private double maxParsePercent;
    private double percentStep;
    private  double bestPercentParse;

    public ProposalCharacteristicParse(List<String> characteristic, double maxParsePercent, double percentStep)
    {
        super(characteristic,false);
        this.maxParsePercent = maxParsePercent;
        this.percentStep = percentStep;
    }

    public ProposalCharacteristicParse(List<String> characteristic)
    {
        super(characteristic,false);
    }

    private double findBestParse()
    {

        DataToParseCharacteristic listOfParseData = new DataToParseCharacteristic(this.parseCharacteristics().toArray().length);
        int i = 0;
        for (double percent = 0.00; percent <= this.maxParsePercent;percent += this.percentStep)
        {

            List<MPoint> actualCharacteristic = autoRange(this.parseCharacteristics(), percent);
            List<MPoint> preFitCharacteristic = new PreFitDataToAutoRange(String.join("\t", this.characteristic)).cauculateCurrent(actualCharacteristic);

            listOfParseData.setElementOfList(i, percent, calculateChi2(preFitCharacteristic, actualCharacteristic));
            i++;
        }

        double bestPercentPercentParse = listOfParseData.getListOfChi2andPercent().stream().min(this::minComparator).map(Point2D::getY).orElseThrow(DindntFindBestParse::new);

        this.bestPercentParse = bestPercentPercentParse;

        return this.bestPercentParse;
    }


    private int minComparator(Point2D oldPoint, Point2D newPoint)
    {
        return Double.compare(oldPoint.getY(), newPoint.getY());
    }

    private double calculateChi2(List<MPoint> proposalCharacteristic, List<MPoint> checkedCharacteristic)
    {
        double chi2 = 0;
        for (int i = 0; i < proposalCharacteristic.toArray().length; i++)
            chi2 += Math.pow(proposalCharacteristic.get(i).getY() - checkedCharacteristic.get(i).getY(), 2);
        return chi2;
    }

    public List<MPoint> getBestParseCharacteristic()
    {
        if(this.bestPercentParse <= 0)
            throw new DindntFindBestParse();
        return autoRange(this.parseCharacteristics(), this.bestPercentParse);
    }
}