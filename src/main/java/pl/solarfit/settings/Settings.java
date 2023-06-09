package pl.solarfit.settings;

import pl.solarfit.fit.Parameters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class Settings extends Parameters
{
    private List<Double> defaultSettings;

    public Settings(double A, double I0, double Rs, double Rch)
    {
        super(A, I0, Rs, Rch);
        this.defaultSettings = (List.of(A, I0, Rs, Rch));
    }

    void readDefaultSettings()
    {
        StringBuilder settings = new StringBuilder();
        try (var file = new Scanner(new InputStreamReader(new FileInputStream("settings.txt"), StandardCharsets.UTF_8)))
        {
            while (file.hasNext())
            {

            }
        } catch (FileNotFoundException ex)
        {
            throw new RuntimeException(ex);
        }

    }


}
