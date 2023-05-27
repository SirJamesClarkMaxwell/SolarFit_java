package pl.solarfit.io;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileLoader
{
    public static List<String> readFile(String pathToFile)
    {
        try
        {
            return FileUtils.readLines(new File(pathToFile), "UTF-8");
        } catch (IOException e)
        {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


}
