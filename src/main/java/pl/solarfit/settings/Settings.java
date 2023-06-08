package pl.solarfit.settings;

import pl.solarfit.fit.Parameters;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class Settings extends Parameters
{
    private List<double> defaultSettings;

    public Settings(double A, double I0, double Rs, double Rch)
    {
        super();
        this.defaultSettings = new ArrayList<double>(4)
        {
            A,I0,Rs,Rch
        };
    }

    void readDefaultSettings()
    {
        StringBuilder settings = StringBuilder();
        try (var file = new Scanner(new InputStreamReader(new FileInputStream(settings.txt), StandardCharsets.UTF_8)))
        {
            while (file.hasNext())
            {

            }
            }

        } catch (IOException e)
        {
            e.getMessage();
        }


    }
