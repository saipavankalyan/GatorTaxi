import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GatorTaxi {
    public static void main(String args[]) {
        if (args.length > 0) {
            String inputFile = args[0];
            ArrayList<String> outputGatorTaxi = new ArrayList<>();
            GatorTaxiServer gts = new GatorTaxiServer();

            try {
                File file = new File(inputFile);
                Scanner in = new Scanner(file);

                while (in.hasNextLine()) {
                    String line = in.nextLine();

                    // System.out.println(line);

                    // trim ) in each line
                    line = line.substring(0, line.length() - 1);

                    String[] command = new String[2];
                    command = line.split("\\(", 2);
                    String operation = command[0];

                    List<String> argumentsString = Arrays.asList(command[1].split(","));
                    List<Integer> arguments = new ArrayList<>();
                    for (String a : argumentsString) {
                        if (!a.equals(""))
                            arguments.add(Integer.parseInt(a));
                    }

                    // System.out.print(operation + " ");
                    // for(int a: arguments)
                    // System.out.print(a+ " ");
                    // System.out.println();

                    if(operation.equals("Insert")){
                        int rideNumber = arguments.get(0);
                        int rideCost = arguments.get(1);
                        int tripDuration = arguments.get(2);
                        gts.insert(rideNumber, rideCost, tripDuration);
                    }

                    else if(operation.equals("Print")){
                        if(arguments.size() == 1){
                            int rideNumber = arguments.get(0);
                            RedBlackTreeNode node = gts.print(rideNumber);
                            String output = "("+node.rideNumber+","+node.rideCost+","+node.tripDuration+")";
                            outputGatorTaxi.add(output);
                        }
                        else{
                            int low = arguments.get(0);
                            int high = arguments.get(1);
                            List<RedBlackTreeNode> nodes = gts.print(low, high);
                            String output = "(";
                            for(RedBlackTreeNode node: nodes){
                                output = output +node.rideNumber+","+node.rideCost+","+node.tripDuration+"),";
                            }
                            output = output.substring(0,output.length()-1);
                            outputGatorTaxi.add(output);
                        }
                    }
                    else if(operation.equals("GetNextRide")){
                        HeapNode node = gts.getNextRide();
                        String output = "("+node.rideNumber+","+node.rideCost+","+node.tripDuration+")";
                        outputGatorTaxi.add(output);
                    }
                    else if(operation.equals("UpdateTrip")){
                        int rideNumber = arguments.get(0);
                        int newTripDuration = arguments.get(1);
                        gts.updateTrip(rideNumber, newTripDuration);
                    }
                    else if(operation.equals("CancelRide")){
                        int rideNumber = arguments.get(0);
                        gts.cancelRide(rideNumber);
                    }   
                }

                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            for(String line: outputGatorTaxi)
                System.out.println(line);

            try(FileWriter writer = new FileWriter("output_file.txt")) {
                for(String str: outputGatorTaxi) {
                    writer.write(str + System.lineSeparator());
                }
            } catch(IOException e) {
                e.printStackTrace();
            }    
        } 
        else {
            System.out.println("Usage: java GatorTaxi input_file");
            System.exit(1);
        }
    }
}