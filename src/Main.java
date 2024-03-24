import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private static final int POPULATION_SIZE = 100;
    private static final double CROSSOVER_RATE = 0.9;
    private static final double TOURNAMENT = 0.1;
    private static final double MUTATION_RATE = 1;
    private static final Random random = new Random();
    private static final List<String> words = new ArrayList<>(); 
    private static final String INPUTS_DIR_NAME = "inputs"; 
    private static final String OUTPUTS_DIR_NAME = "outputs";
    private static String outputsPath = "";
    private static String outputFileName = "";
    
    public static void main(String[] args){
        start();
    }

    private static void start(){
        outputsPath = getCurrentPath() + OUTPUTS_DIR_NAME;
        
        File outputsDirectory = new File(outputsPath);

        // Create outputs directory if it doesn't exist
        if (!outputsDirectory.exists()) {
            boolean created = outputsDirectory.mkdirs();
            if (!created) {
                System.out.println("Failed to create the 'outputs' directory.");
                return;
            }
        }
    }

    File[] files = getAllFiles();

    // Processing each input file
    if(files != null){
        for(File file : files){
            if(file.isFile()) {
                outputFileName = getOutputFileName(file);
                if(readAllWordsInFile(file)){
                    buildCrossword();
                }
                words.clear();
            }
            else{
                System.out.println("No files found in the input directory.");
            }
        }
    }
    
    
}
