import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GatorTaxi{
    public static void main(String args[]){
        if(args.length > 0){
            String inputFile = args[0];
            ArrayList<String> outputGatorTaxi = new ArrayList<>();
            try{
                File file = new File(inputFile);
                Scanner in = new Scanner(file);

                while (in.hasNextLine()) {
                    String line = in.nextLine();

                    // System.out.println(line);

                    // trim ) in each line
                    line = line.substring(0, line.length() -1);
                    
                    String[] command = new String[2];
                    command = line.split("\\(", 2);
                    String operation = command[0];
        
                    List<String> argumentsString = Arrays.asList(command[1].split(","));
                    List<Integer> arguments = new ArrayList<>();
                    for(String a: argumentsString){
                        if(!a.equals(""))
                        arguments.add(Integer.parseInt(a));
                    }

                    // System.out.print(operation + " ");
                    // for(int a: arguments)
                    //     System.out.print(a+ " ");
                    // System.out.println();

                }

                in.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        else{
            System.out.println("Usage: java GatorTaxi input_file");
            System.exit(1);
        }
    }
}