import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class gatorTaxi {
    public static void main(String args[]) {
        // The input file should be passed as command line argument
        if (args.length > 0) {
            String inputFile = args[0];
            GatorTaxiServer gts = new GatorTaxiServer();

            try {
                File file = new File(inputFile);
                Scanner in = new Scanner(file);

                try (FileWriter out = new FileWriter("output_file.txt", false)) {
                    while (in.hasNextLine()) {
                        String line = in.nextLine();

                        // trim ) in each command a the end
                        line = line.substring(0, line.length() - 1);

                        String[] command = new String[2];
                        // Divide the command based on (
                        command = line.split("\\(", 2);
                        // first part is operation
                        String operation = command[0];

                        // Second part is arguments
                        List<String> argumentsString = Arrays.asList(command[1].split(","));
                        List<Integer> arguments = new ArrayList<>();
                        for (String a : argumentsString) {
                            if (!a.equals(""))
                                arguments.add(Integer.parseInt(a));
                        }

                        // Insert ride
                        if (operation.equals("Insert")) {
                            int rideNumber = arguments.get(0);
                            int rideCost = arguments.get(1);
                            int tripDuration = arguments.get(2);
                            boolean inserted = gts.insert(rideNumber, rideCost, tripDuration);

                            // unable to insert means node already exists, abort.
                            if (!inserted) {
                                out.write("Duplicate RideNumber");
                                return;
                            }
                        }

                        else if (operation.equals("Print")) {
                            // 1 argument, print the node with given rideNumber
                            if (arguments.size() == 1) {
                                int rideNumber = arguments.get(0);
                                RedBlackTreeNode node = gts.print(rideNumber);
                                String output = "(" + node.rideNumber + "," + node.rideCost + "," + node.tripDuration
                                        + ")";
                                out.write(output + System.lineSeparator());
                            } else {
                                // 2 arguments, print all nodes whose rideNumber is in the given range(low,
                                // high)
                                int low = arguments.get(0);
                                int high = arguments.get(1);
                                List<RedBlackTreeNode> nodes = gts.print(low, high);

                                // Output in given format
                                String output = "";
                                for (RedBlackTreeNode node : nodes) {
                                    output = output + "(" + node.rideNumber + "," + node.rideCost + ","
                                            + node.tripDuration + "),";
                                }
                                if (nodes.size() > 0) {
                                    output = output.substring(0, output.length() - 1);
                                    out.write(output + System.lineSeparator());
                                } else {
                                    out.write("(0,0,0)" + System.lineSeparator());
                                }
                            }
                        }
                        // print the ride whose rideCost is minimum and delete it
                        else if (operation.equals("GetNextRide")) {
                            HeapNode node = gts.getNextRide();
                            if (node == null) {
                                out.write("No active ride requests" + System.lineSeparator());
                            } else {
                                String output = "(" + node.rideNumber + "," + node.rideCost + "," + node.tripDuration
                                        + ")";
                                out.write(output + System.lineSeparator());
                            }

                        }
                        // Update the ride with new trip duration
                        else if (operation.equals("UpdateTrip")) {
                            int rideNumber = arguments.get(0);
                            int newTripDuration = arguments.get(1);
                            gts.updateTrip(rideNumber, newTripDuration);
                        }
                        // Cancel ride
                        else if (operation.equals("CancelRide")) {
                            int rideNumber = arguments.get(0);
                            gts.cancelRide(rideNumber);
                        }

                    }
                }

                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // The usage of the program is not correct.
            System.out.println("Usage: java GatorTaxi input_file");
            System.exit(1);
        }
    }
}