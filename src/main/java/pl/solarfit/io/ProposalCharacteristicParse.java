package pl.solarfit.io;

import pl.solarfit.simplex.MPoint;

import java.util.ArrayList;
import java.util.List;

public class ProposalCharacteristicParse extends CharacteristicsParse
{
    private double maxParsePercent;
    private double percentStep;
    private final   bestPercentParse;

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

        for (double percent = 0.00; percent =<this.maxParsePercent;
        percent + this.percentStep)
        {

            ArrayList<MPoint> actualCharacteristic = autoRange(this.parseCharacteristics()), percent);
            ArrayList<MPoint> preFitCharacteristic = new PreFitDataToAutoRange(this.characteristic).cauculateCurrent(actualCharacteristic);

            listOfParseData.setElementOfList(i,percent,calculateChi2(preFitCharacteristic,actualCharacteristic));
        }

        double bestPercentPercentParse = new  Double.parseDouble(listOfParseData.stream().max(this::maxComparator(    new MPoint().setY(); new MPoint().setY();  )))
        // ! tutaj należy się zastanowić jak przekazać do funkcji setY() wartości chi2 każdego z elementów listy listOfParseData
        this.bestPercentParse = bestPercentPercentParse;
        return this.bestPercentParse


    }


    private double calculateChi2(List<MPoint> proposalCharacteristic, List<MPoint> checkedCharacteristic)
        {
            double chi2 = 0;
            for (int i = 0; i < proposalCharacteristic.toArray().length; i++)
                chi2 += Math.pow(proposalCharacteristic[i] - checkedCharacteristic[i], 2);
            return chi2;
        }
