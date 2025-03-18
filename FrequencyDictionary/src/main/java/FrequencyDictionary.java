import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class FrequencyDictionary {

    public FrequencyDictionary(String InputFilePath) throws IOException {

        char[] InputChar = InputFilePath.toCharArray();
        char[] newInputChar = new char[InputFilePath.length()-2];
        if (!InputFilePath.isEmpty() & InputChar[0] == '\"') {
            InputFilePath.getChars(1, InputFilePath.length()-1, newInputChar, 0);
        }
        InputFilePath = new String(newInputChar);

        String OutputFilePath = getOutputFile(InputFilePath);

        Map<String, Long> frequencyMap = new HashMap<>();

        File InputFile = new File(InputFilePath);
        Scanner scanner = new Scanner(InputFile);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] list = line.split("[,.?!():\\-\\—\\s]+");
            for(int i = 0; i < list.length; i++) {
                String word = list[i].toLowerCase();
                if (!word.isEmpty()) {
                    if (frequencyMap.containsKey(word)) {
                        Long oldValue = frequencyMap.get(word);
                        Long newValue = oldValue + 1;
                        frequencyMap.put(word, newValue);

                    } else {
                        frequencyMap.put(word, Long.valueOf("1"));
                    }
                }
            }
        }
        scanner.close();

        FileWriter newFile = new FileWriter(OutputFilePath);
        for (Map.Entry<String, Long> entry : frequencyMap.entrySet()) {
            newFile.write(entry.getKey() + ": " + entry.getValue() + "\n");
        }
        newFile.close();
    }


    public String getOutputFile(String inputFilePath) {

        int i = inputFilePath.length()-1;
        char[] InputCharSet = inputFilePath.toCharArray();
        while (!Objects.equals(String.valueOf(InputCharSet[i]),"\\")) {
            i--;
        };

        String InputFileName = "";
        String OutputFilePath = "";

        for (int j = 0; j <= i; j++) {
            OutputFilePath += String.valueOf(InputCharSet[j]);
        }

        for (int j = i+1; j < inputFilePath.length(); j++) {
            InputFileName += String.valueOf(InputCharSet[j]);
        }

        OutputFilePath += "Frequency dictionary for file " + InputFileName;

        return OutputFilePath;
    }

}
