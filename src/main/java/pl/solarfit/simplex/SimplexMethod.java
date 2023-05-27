package pl.solarfit.simplex;

import java.util.Iterator;
import java.util.Vector;

public abstract class SimplexMethod
{
    double temperature;
    int parNumber;
    double[] boundsMax;
    double[] boundsMin;
    double[] p0;
    double[] lambda;
    double[][] unitVector;
    Vector<double[]> simplex = new Vector();
    int initRN;
    double[] centroid;
    double[][] gFValue;

    public SimplexMethod()
    {
        this.temperature = 0.0;
        this.parNumber = 4;
        this.boundsMax = new double[this.parNumber];
        this.boundsMin = new double[this.parNumber];
        this.p0 = new double[this.parNumber];
        this.lambda = new double[this.parNumber];
        this.unitVector = new double[this.parNumber][this.parNumber];
        this.centroid = new double[this.parNumber];
        this.gFValue = new double[2][this.parNumber + 1];
        this.initRN = 150;

        for (int i = 0; i < this.parNumber; ++i)
        {
            this.p0[i] = 0.0;

            for (int j = 0; j < this.parNumber; ++j)
            {
                if (i == j)
                {
                    this.unitVector[i][j] = 1.0;
                } else
                {
                    this.unitVector[i][j] = 0.0;
                }
            }
        }

    }

    public SimplexMethod(double T, int pN, double[] pI0, double[] lam)
    {
        this.temperature = T;
        this.parNumber = pN;
        this.boundsMax = new double[this.parNumber];
        this.boundsMin = new double[this.parNumber];
        this.p0 = new double[this.parNumber];
        this.p0 = pI0;
        this.lambda = new double[this.parNumber];
        this.lambda = lam;
        this.unitVector = new double[this.parNumber][this.parNumber];
        this.centroid = new double[this.parNumber];
        this.gFValue = new double[2][this.parNumber + 1];
        this.initRN = 25000;

        for (int i = 0; i < this.parNumber; ++i)
        {
            for (int j = 0; j < this.parNumber; ++j)
            {
                if (i == j)
                {
                    this.unitVector[i][j] = 1.0;
                } else
                {
                    this.unitVector[i][j] = 0.0;
                }
            }
        }

    }

    protected abstract double goalFunction();

    protected abstract double goalFunction(int var1, double[] var2);

    public void createSimplex()
    {
        this.simplex.clear();

        for (int i = 0; i < this.parNumber; ++i)
        {
            double[] tempPoint = new double[this.parNumber];

            for (int j = 0; j < this.parNumber; ++j)
            {
                tempPoint[j] = this.p0[j] + this.lambda[j] * this.unitVector[i][j];
            }

            this.setToBounds(tempPoint);
            this.simplex.add(tempPoint);
            Object var4 = null;
        }

        this.simplex.add(this.p0);
    }

    private void reCreateSimplex(double[] point)
    {
        this.p0 = null;
        this.p0 = point;
        this.createSimplex();
    }

    public void minimalize()
    {
        this.createSimplex();
        double cGF = 0.0;
        int j = 0;

        while (true)
        {
            if (j % 100 == 0 && j != 0)
            {
                System.out.println(Math.abs(this.gFValue[1][0] - this.gFValue[1][this.parNumber]));
            }

            double[] yC = new double[this.parNumber];
            double[] yR = new double[this.parNumber];
            double[] yE = new double[this.parNumber];
            boolean isCon = false;

            int i;
            for (i = 0; i < this.parNumber + 1; ++i)
            {
                this.gFValue[0][i] = i;
                this.gFValue[1][i] = this.goalFunction(this.parNumber, this.simplex.get(i));
            }

            this.sort();
            if (Math.abs(this.gFValue[1][0] - this.gFValue[1][this.parNumber]) < 1.0E-13)
            {
                return;
            }

            this.calculeCentroid(this.gFValue);
            yR = this.reflection(this.simplex.get((int) this.gFValue[0][this.parNumber]));
            this.setToBounds(yR);
            double rGF = this.goalFunction(this.parNumber, yR);
            if (rGF < this.gFValue[1][0])
            {
                yE = this.expansionAndReflection(this.simplex.get((int) this.gFValue[0][this.parNumber]));
                this.setToBounds(yE);
                double eGF = this.goalFunction(this.parNumber, yE);
                if (eGF < rGF)
                {
                    this.simplex.set((int) this.gFValue[0][this.parNumber], yE);
                } else
                {
                    this.simplex.set((int) this.gFValue[0][this.parNumber], yR);
                }
            }

            if (rGF >= this.gFValue[1][0] && rGF < this.gFValue[1][this.parNumber - 1])
            {
                this.simplex.set((int) this.gFValue[0][this.parNumber], yR);
            }

            if (rGF >= this.gFValue[1][this.parNumber - 1] && rGF < this.gFValue[1][this.parNumber])
            {
                yC = this.contraction(yR);
                this.setToBounds(yC);
                cGF = this.goalFunction(this.parNumber, yC);
                isCon = true;
            }

            if (rGF >= this.gFValue[1][this.parNumber])
            {
                yC = this.contraction(this.simplex.get((int) this.gFValue[0][this.parNumber]));
                this.setToBounds(yC);
                cGF = this.goalFunction(this.parNumber, yC);
                isCon = true;
            }

            if (isCon)
            {
                if (cGF < rGF && cGF < this.gFValue[1][this.parNumber])
                {
                    this.simplex.set((int) this.gFValue[0][this.parNumber], yC);
                } else
                {
                    for (i = 1; i < this.parNumber + 1; ++i)
                    {
                        this.simplex.set((int) this.gFValue[0][i], this.contraction(this.simplex.get((int) this.gFValue[0][i]), this.simplex.get((int) this.gFValue[0][0])));
                    }
                }
            }

            yR = null;
            yC = null;
            Object var15 = null;
            ++j;
        }
    }

    public void minimalizeAnnealed()
    {
        this.createSimplex();
        double finalChi2 = 0.0;
        double cGF = 0.0;
        double[][] gfH = new double[2][this.parNumber + 1];
        double[][] gfG = new double[2][this.parNumber + 1];
        int j = 0;

        while (true)
        {
            if (j % 5 == 0 && j != 0 && this.temperature > 0.0)
            {
                --this.temperature;
            }

            double[] yC = new double[this.parNumber];
            double[] yR = new double[this.parNumber];
            double[] yE = new double[this.parNumber];
            boolean isCon = false;

            int i;
            for (i = 0; i < this.parNumber + 1; ++i)
            {
                this.gFValue[0][i] = i;
                gfH[0][i] = this.gFValue[0][i];
                gfG[0][i] = this.gFValue[0][i];
                this.gFValue[1][i] = this.goalFunction(this.parNumber, this.simplex.get(i));
                gfH[1][i] = this.gFValue[1][i] + this.temperature * Math.log(Math.random());
                gfG[1][i] = this.gFValue[1][i] - this.temperature * Math.log(Math.random());
            }

            this.sort();
            this.sort(gfH);
            this.sort(gfG);
            if (Math.abs(this.gFValue[1][0] - this.gFValue[1][this.parNumber]) < 1.0E-15)
            {
                return;
            }

            this.calculeCentroid(gfG);
            yR = this.reflection(this.simplex.get((int) gfG[0][this.parNumber]));
            this.setToBounds(yR);
            double rGF = this.goalFunction(this.parNumber, yR) + this.temperature * Math.log(Math.random());
            if (rGF < this.gFValue[1][0])
            {
                yE = this.expansionAndReflection(this.simplex.get((int) this.gFValue[0][this.parNumber]));
                this.setToBounds(yE);
                double eGF = this.goalFunction(this.parNumber, yE) + this.temperature * Math.log(Math.random());
                if (eGF < rGF)
                {
                    this.simplex.set((int) this.gFValue[0][this.parNumber], yE);
                } else
                {
                    this.simplex.set((int) this.gFValue[0][this.parNumber], yR);
                }
            }

            if (rGF >= this.gFValue[1][0] && rGF < this.gFValue[1][this.parNumber - 1])
            {
                this.simplex.set((int) this.gFValue[0][this.parNumber], yR);
            }

            if (rGF >= this.gFValue[1][this.parNumber - 1] && rGF < this.gFValue[1][this.parNumber])
            {
                yC = this.contraction(yR);
                this.setToBounds(yC);
                cGF = this.goalFunction(this.parNumber, yC) + this.temperature * Math.log(Math.random());
                isCon = true;
            }

            if (rGF >= this.gFValue[1][this.parNumber])
            {
                yC = this.contraction(this.simplex.get((int) this.gFValue[0][this.parNumber]));
                this.setToBounds(yC);
                cGF = this.goalFunction(this.parNumber, yC) + this.temperature * Math.log(Math.random());
                isCon = true;
            }

            if (isCon)
            {
                if (cGF < rGF && cGF < this.gFValue[1][this.parNumber])
                {
                    this.simplex.set((int) this.gFValue[0][this.parNumber], yC);
                } else
                {
                    for (i = 1; i < this.parNumber + 1; ++i)
                    {
                        this.simplex.set((int) this.gFValue[0][i], this.contraction(this.simplex.get((int) this.gFValue[0][i]), this.simplex.get((int) this.gFValue[0][0])));
                    }
                }
            }

            yR = null;
            yC = null;
            Object var19 = null;
            ++j;
        }
    }

    private void sort()
    {
        double[][] tempArray = new double[2][1];

        for (int i = 0; i < this.parNumber + 1; ++i)
        {
            for (int j = this.parNumber; j > i; --j)
            {
                if (this.gFValue[1][j - 1] > this.gFValue[1][j])
                {
                    tempArray[0][0] = this.gFValue[0][j - 1];
                    tempArray[1][0] = this.gFValue[1][j - 1];
                    this.gFValue[0][j - 1] = this.gFValue[0][j];
                    this.gFValue[1][j - 1] = this.gFValue[1][j];
                    this.gFValue[0][j] = tempArray[0][0];
                    this.gFValue[1][j] = tempArray[1][0];
                }
            }
        }

    }

    private void sort(double[][] sortArray)
    {
        double[][] tempArray = new double[2][1];

        for (int i = 0; i < this.parNumber + 1; ++i)
        {
            for (int j = this.parNumber; j > i; --j)
            {
                if (sortArray[1][j - 1] > sortArray[1][j])
                {
                    tempArray[0][0] = sortArray[0][j - 1];
                    tempArray[1][0] = sortArray[1][j - 1];
                    sortArray[0][j - 1] = sortArray[0][j];
                    sortArray[1][j - 1] = sortArray[1][j];
                    sortArray[0][j] = tempArray[0][0];
                    sortArray[1][j] = tempArray[1][0];
                }
            }
        }

    }

    private double[] reflection(double[] cPoint)
    {
        double[] tempP = new double[this.parNumber];

        for (int i = 0; i < cPoint.length; ++i)
        {
            tempP[i] = this.centroid[i] + (this.centroid[i] - cPoint[i]);
        }

        return tempP;
    }

    private double[] expansionAndReflection(double[] cPoint)
    {
        double[] tempP = new double[this.parNumber];

        for (int i = 0; i < cPoint.length; ++i)
        {
            tempP[i] = this.centroid[i] + 2.0 * (this.centroid[i] - cPoint[i]);
        }

        return tempP;
    }

    private double[] contraction(double[] cPoint)
    {
        double[] tempP = new double[this.parNumber];

        for (int i = 0; i < cPoint.length; ++i)
        {
            tempP[i] = this.centroid[i] + 0.5 * (cPoint[i] - this.centroid[i]);
        }

        return tempP;
    }

    private double[] contraction(double[] cPoint, double[] mainPoint)
    {
        double[] tempP = new double[this.parNumber];

        for (int i = 0; i < cPoint.length; ++i)
        {
            tempP[i] = mainPoint[i] + 0.5 * (cPoint[i] - mainPoint[i]);
        }

        return tempP;
    }

    private void calculeCentroid()
    {
        this.centroid = null;
        this.centroid = new double[this.parNumber];
        Iterator i$ = this.simplex.iterator();

        while (i$.hasNext())
        {
            double[] top = (double[]) i$.next();

            for (int i = 0; i < top.length; ++i)
            {
                double[] var10000 = this.centroid;
                var10000[i] += 1.0 / ((double) this.parNumber + 1.0) * top[i];
            }
        }

    }

    private void calculeCentroid(double[][] gf)
    {
        this.centroid = null;
        this.centroid = new double[this.parNumber];

        for (int i = 0; i < this.parNumber; ++i)
        {
            for (int j = 0; j < this.parNumber; ++j)
            {
                double[] var10000 = this.centroid;
                var10000[j] += ((double[]) this.simplex.get((int) gf[0][i]))[j] / (double) this.parNumber;
            }
        }

    }

    private boolean isUnderMin(double[] point)
    {
        for (int i = 0; i < this.parNumber; ++i)
        {
            if (point[i] < this.boundsMin[i])
            {
                return true;
            }
        }

        return false;
    }

    private boolean isOverMax(double[] point)
    {
        for (int i = 0; i < this.parNumber; ++i)
        {
            if (point[i] > this.boundsMax[i])
            {
                return true;
            }
        }

        return false;
    }

    private void setToBounds(double[] point)
    {
        for (int i = 0; i < this.parNumber; ++i)
        {
            if (point[i] < this.boundsMin[i])
            {
                point[i] = this.boundsMin[i];
            }

            if (point[i] > this.boundsMax[i])
            {
                point[i] = this.boundsMax[i];
            }
        }

    }

    public void setBounds(double[] bMax, double[] bMin)
    {
        this.boundsMax = bMax;
        this.boundsMin = bMin;
    }

    public void setBoundsMax(double[] boundsMax)
    {
        this.boundsMax = boundsMax;
    }

    public void setBoundsMin(double[] boundsMin)
    {
        this.boundsMin = boundsMin;
    }

    public void setP0(double[] p0)
    {
        this.p0 = p0;
    }

    public double getTemperature()
    {
        return this.temperature;
    }

    public void setTemperature(double temperature)
    {
        this.temperature = temperature;
    }

    public int getParNumber()
    {
        return this.parNumber;
    }

    public void setParNumber(int parNumber)
    {
        this.parNumber = parNumber;
    }

    public void setInitRN(int initRN)
    {
        this.initRN = initRN;
    }

    public double[] getLambda()
    {
        return this.lambda;
    }

    public void setLambda(double[] lambda)
    {
        this.lambda = lambda;
    }

    public double[] getFitedPar()
    {
        return this.simplex.get((int) this.gFValue[0][0]);
    }
}
