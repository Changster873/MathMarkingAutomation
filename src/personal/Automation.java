package personal;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Automation process for marking math exercises.
 *
 * @author Chansocheat Chheang
 */
public class Automation {

    /**
     * Starts the marking automation.
     * @param filename
     */
    public Automation (String filename){
        markFile(filename);
    }

    /**
     * Takes the file name as a parameter.
     * Marks the file automatically.
     *
     * @param filename
     */
    public void markFile (String filename){

        try {
            //read the file, if file not found throw an error
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            //eliminate line entries with wrong formatting
            Map <Integer, String> map = checkFormat(filename);

            //final scores
            int correct = 0;
            int incorrect = 0;

            //scanner to extract numbers
            Scanner sc;

            //operation
            String operation;

            System.out.println();
            System.out.println("Marking..."); //$NON-NLS-1$

            for (Map.Entry<Integer,String> entry: map.entrySet()){

                String line = entry.getValue();
                sc = new Scanner(line);

                // 1 + 2 = 3 would turn to 1+2=3
                line.replaceAll("[\\s]","");

                int first = sc.nextInt();

                if (sc.hasNext("[+]")){
                    operation = "plus";
                    sc.next();
                }
                else if (sc.hasNext("[-]")){
                    operation = "minus";
                    sc.next();
                }
                else if (sc.hasNext("[*]")){
                    operation = "times";
                    sc.next();
                }
                else {
                    operation = "divide";
                    sc.next();
                }

                int second = sc.nextInt();
                sc.next(); //equal
                int answer = sc.nextInt();


                boolean score = compute(entry.getKey(),first,second,answer,operation);

                if (score == true){
                    correct++;
                }
                else incorrect++;
            }

            System.out.println();
            System.out.println("Total: "); //$NON-NLS-1$
            System.out.println("Correct: " + correct); //$NON-NLS-1$
            System.out.println("Incorrect: " + incorrect); //$NON-NLS-1$
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     *
     * Checks the format of the file, any wrong spacing or invalid input will not be marked.
     *
     * @param filename name of the file
     * @return entries of lines that are acceptable.
     */
    public Map <Integer, String> checkFormat (String filename){

        //map to be returned
        Map <Integer, String> map = new HashMap<>();

        try {
            //read the file, if not found throw error
            Scanner scan = new Scanner(new File(filename));

            System.out.println();
            System.out.println("Checking format..."); //$NON-NLS-1$
            System.out.println();

            //retrieve first line of file
            String line = null;
            line = scan.nextLine();

            //scanner to check each line
            Scanner sc;

            //count total number of lines
            int count = 0;

            //start checking
            while (line != null){

                count++;

                sc = new Scanner(line);

                if (sc.hasNext("STOP")){
                    count--;
                    break; //$NON-NLS-1$
                }

                if (!line.matches("[\\s]*[\\d]+[\\s][+/*-][\\s][\\d]+[\\s][=][\\s][\\d]+")){
                    if (scan.hasNext()) line = scan.nextLine();
                    continue;
                }

                map.put(count,line);

                System.out.println("Line " + count + " is fine."); //$NON-NLS-1$ //$NON-NLS-2$

                if (scan.hasNext()) line = scan.nextLine();

                sc.close();
            }

            System.out.println();
            System.out.println("Wrong format: " + (count-map.size())); //$NON-NLS-1$

            scan.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * Compute the correct answer if given wrong answer. Otherwise notify user if correct.
     *
     * @param index index of the question
     * @param first first given number
     * @param second second given number
     * @param answer user inputted answer
     * @param operation operation of this question
     */
    public boolean compute (int index, int first, int second, int answer, String operation){

        System.out.print(index + ": "); //$NON-NLS-1$

        if (operation.equals("plus")){ //$NON-NLS-1$
            if (first + second == answer){
                System.out.println("Correct."); //$NON-NLS-1$
                return true;
            }
            else {
                System.out.println("Wrong. Answer is " + (first+second)); //$NON-NLS-1$
                return false;
            }
        }

        if (operation.equals("minus")){ //$NON-NLS-1$
            if (first - second == answer){
                System.out.println("Correct."); //$NON-NLS-1$
                return true;
            }
            else {
                System.out.println("Wrong. Answer is " + (first-second)); //$NON-NLS-1$
                return false;
            }
        }

        if (operation.equals("times")){ //$NON-NLS-1$
            if (first * second == answer){
                System.out.println("Correct."); //$NON-NLS-1$
                return true;
            }
            else {
                System.out.println("Wrong. Answer is " + (first*second)); //$NON-NLS-1$
                return false;
            }
        }

        if (operation.equals("divide")){ //$NON-NLS-1$
            if (first / second == answer){
                System.out.println("Correct."); //$NON-NLS-1$
                return true;
            }
            else {
                System.out.println("Wrong. Answer is " + (first/second)); //$NON-NLS-1$
                return false;
            }
        }
        return false;
    }

}
