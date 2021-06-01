import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
import java.io.File;
/**
 * Write a description of CSVMin here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CSVMin {
    public CSVRecord coldestHourInFile (CSVParser parser){
        CSVRecord smallestSoFar = null;
        for (CSVRecord currentRow: parser){
            if (smallestSoFar == null){
                smallestSoFar = currentRow;
            } else {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            double smallestTemp = Double.parseDouble(smallestSoFar.get("TemperatureF"));
               if (currentTemp != -9999 && currentTemp < smallestTemp){
                smallestSoFar = currentRow;
               }
            }
        }
        return smallestSoFar;
    }
    
    public void testColdestHourInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord largest = coldestHourInFile(fr.getCSVParser());
        System.out.println("coldest temperature was " + largest.get("TemperatureF") + 
                            " at " + largest.get("TimeEST"));
    }
    
    public String fileWithColdestTemperature (){
        CSVRecord smallestSoFar = null;
        String smallF = null;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
            if (smallestSoFar == null){
                smallestSoFar = currentRow;
                smallF = f.getName();
            } else{
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            double smallestTemp = Double.parseDouble(smallestSoFar.get("TemperatureF"));
              if (currentTemp != -9999 && (currentTemp < smallestTemp)){
              smallestSoFar = currentRow;
              smallF = f.getName();
              }
              
            }
        }
        return smallF;
    }
    
    public void testFileWithColdestTemperature(){
        String cold = fileWithColdestTemperature();
        System.out.println("Coldest day was in file " + cold);
        FileResource fr = new FileResource();
        CSVRecord coldest = coldestHourInFile(fr.getCSVParser());
        System.out.println("The coldest temperature on that day was " +coldest.get("TemperatureF"));
        System.out.println("All the temperatures on the coldest day were");
        for (CSVRecord record:fr.getCSVParser()) {
            System.out.print(record.get("DateUTC"));
            System.out.print(" ");
            System.out.println(record.get("TemperatureF"));
        
         }
    }
    
    public CSVRecord lowestHumidityInFile(CSVParser parser){
        CSVRecord lowestSoFar = null;
        for (CSVRecord currentRow: parser){
            String check = currentRow.get("Humidity");
            if (check.equals("N/A")){
                continue;
            }
            
            if (lowestSoFar == null){
                   lowestSoFar = currentRow;
            } else{
                double currentHum = Double.parseDouble(currentRow.get("Humidity"));
                double lowestHum =  Double.parseDouble(lowestSoFar.get("Humidity"));
                if(lowestHum > currentHum){
                lowestSoFar = currentRow;
                            }
            } 
        }  
        return lowestSoFar;
    }
    
    public void testLowestHumidityInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord csv = lowestHumidityInFile(fr.getCSVParser());
        System.out.println("lowest humidity was " + csv.get("Humidity") + 
                            " at " + csv.get("DateUTC"));
    }
    
    public CSVRecord lowestHumidityInManyFiles(){
        CSVRecord lowestSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()){
            
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = lowestHumidityInFile(fr.getCSVParser());
            if (currentRow.get("Humidity") == ("N/A")){
                continue;
            }
            
            if(lowestSoFar == null){
                lowestSoFar = currentRow;
            } else {
               double currentHum = Double.parseDouble(currentRow.get("Humidity"));
               double lowestHum =  Double.parseDouble(lowestSoFar.get("Humidity"));
               if (currentHum < lowestHum){
                   lowestSoFar = currentRow;
               }
            }           
        }
        return lowestSoFar;
    }
    
    public void testLowestHumidityInManyFiles(){
        CSVRecord csv = lowestHumidityInManyFiles();
        System.out.println("lowest humidity was " + csv.get("Humidity") + 
                            " at " + csv.get("DateUTC"));
    }
    
    public double averageTemperatureInFile(CSVParser parser){
        int count = 0;
        double acc = 0.0;
        double tempSum = 0.0;
        double average = 0.0;
        
        for(CSVRecord currentRow: parser){
            count++;
            tempSum = Double.parseDouble(currentRow.get("TemperatureF"));
            acc += tempSum;
        } 
        
        average = acc/count;
        return average;
    }
    
    public void testAverageTemperatureInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double average = averageTemperatureInFile(parser);
        System.out.println("Average temperature in file is " +average);
    }
    
    
    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value){
        int count = 0;
        double acc = 0.0;
        double tempSum = 0.0;
        double average = 0.0;
        
        for (CSVRecord currentRow : parser){
            double currentHum = Double.parseDouble(currentRow.get("Humidity"));
           if (currentHum >= value){
            count++;
            if (count == 0){
            System.out.println("No temperatures with that humidity");
            } else {
            tempSum = Double.parseDouble(currentRow.get("TemperatureF"));
            acc += tempSum;
            average = acc/count;
            }
           }
           
        }
        System.out.println("When Humidity is " +value + " average temperature is " +average);
        return average;
    }
    
    public void testAverageTemperatureWithHighHumidityInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        int value = 80;
        double average = averageTemperatureWithHighHumidityInFile(parser, value);
        
    }
}


