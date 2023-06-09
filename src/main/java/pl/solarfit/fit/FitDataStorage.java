package pl.solarfit.fit;

import java.util.ArrayList;
import java.util.List;

public class FitDataStorage
{
    private List<Parameters> historyOfSimplex;
    private int lengthOfSimplex;

    public FitDataStorage(int lengthOfSimplex)
    {
        this.historyOfSimplex = new ArrayList<Parameters>(lengthOfSimplex);
        this.lengthOfSimplex = lengthOfSimplex;
    }

    public List<Parameters> getHistoryOfSimplex()
    {
        return historyOfSimplex;
    }

    public void setHistoryOfSimplex(List<Parameters> historyOfSimplex)
    {
        this.historyOfSimplex = historyOfSimplex;
    }

    public int getLengthOfSimplex()
    {
        return lengthOfSimplex;
    }

    public void setLengthOfSimplex(int lengthOfSimplex)
    {
        this.lengthOfSimplex = lengthOfSimplex;
    }

    public ArrayList<String> toList()
    {
        ArrayList<String> toList = new ArrayList<String>(lengthOfSimplex);
        for (Parameters parameters:this.historyOfSimplex)
            toList.add(parameters.toString());

        return toList;
    }

}
