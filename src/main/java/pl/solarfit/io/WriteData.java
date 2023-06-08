package pl.solarfit.io;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class WriteData
{
    public String savaDataIntoFile;
    private String nameOfFile;
    private String path;
    private List<String> toWrite;


    public WriteData(String nameOfFile, String path, List<String> toWrite)
    {
        this.nameOfFile = nameOfFile;
        this.path = path;
        this.toWrite = toWrite;
    }

    public void saveDataIntoFile()
    {
        Path output = Paths.get(this.path);
        try
        {
            Files.write(output, this.toWrite);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
