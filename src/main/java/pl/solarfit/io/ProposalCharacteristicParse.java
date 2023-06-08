package pl.solarfit.io;

import pl.solarfit.simplex.MPoint;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class ProposalCharacteristicParse extends CharacteristicsParse
{
    private double maxParsePercent;
    private double percentStep;

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
        for (double percent = 0.00; percent =< this.maxParsePercent ; percent + this.percentStep)
        {

            ArrayList <MPoint>  actualCharacteristic = autoRange(this.parseCharacteristics()), percent);
            ArrayList <MPoint> preFitCharacteristic = new PreFitDataToAutoRange().cauculateCurrent(actualCharacteristic);

            
        }
    }

    private double calculateChi2 (List<MPoint> proposalCharacteristic, List<MPoint> checkedCharacteristic )
    {
        return Double.parseDouble(proposalCharacteristic.stream().forEach((ch1,ch2) -> Math.pow((ch1 - ch2),2)).reduce(Double::sum)
    }

}
