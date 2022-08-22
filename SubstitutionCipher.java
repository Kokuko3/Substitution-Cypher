/**
 * This program takes a file and applies a subsitution cipher to it. 
 * @author Mason McDaniel
 * @version 11/17/2021
 */
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class SubstitutionCipher {

    /**
     * Private constants used to shift characters for the substitution cipher.
     */
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";

    /**
     * Constructs a new String where each letter in the String input is shifted
     * by the amount shift to the right, preserving whether the original
     * character was uppercase or lowercase. For example, the String "ABC" with
     * shift 3 would cause this method to return "DEF". A negative value should
     * shift to the left. For example, the String "ABC" with shift -3 would
     * cause this method to return "XYZ". Punctuation, numbers, whitespace and
     * other non-letter characters should be left unchanged. NOTE: For full
     * credit you are REQUIRED to use a StringBuilder to build the String in
     * this method rather than using String concatenation.
     *
     * @param input
     *            String to be encrypted
     * @param shift
     *            Amount to shift each character of input to the right
     * @return the encrypted String as outlined above
     */
    public static String shift(String input, int shift) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < input.length(); ++i){
            char charToSwap = input.charAt(i);                              
            if(UPPERCASE.indexOf(charToSwap) != -1){                                                
                int startIndex = UPPERCASE.indexOf(charToSwap);     //Checks to see if charToSwap is in the UPPERCASE string
                int shiftIndex = startIndex + shift;                
                if (shiftIndex > 25){                               //If index exceeds UPPERCASE index, then the shiftIndex is subtracted by the number of letters in the alphabet.
                    shiftIndex = shiftIndex - 26;
                }
                else if (shiftIndex < 0){                           //If the shiftIndex is negative, add 26 to it to 'loop'.
                    shiftIndex = 26 + shiftIndex;
                }
                char shiftChar = UPPERCASE.charAt(shiftIndex);
                sb.append(shiftChar);
            }
            else if(LOWERCASE.indexOf(charToSwap) != -1){                                                
                int startIndex = LOWERCASE.indexOf(charToSwap);    //Checks to see if charToSwap is in the LOWERCASE string
                int shiftIndex = startIndex + shift;
                if (shiftIndex > 25){                              //If index exceeds LOWERCASE index, then the shiftIndex is subtracted by the number of letters in the alphabet.
                    shiftIndex = shiftIndex - 26;
                }
                else if (shiftIndex < 0){                          //If the shiftIndex is negative, add 26 to it to 'loop'.
                    shiftIndex = 26 + shiftIndex;
                }
                char shiftChar = LOWERCASE.charAt(shiftIndex);
                sb.append(shiftChar);
            }
            else{
                sb.append(charToSwap);
            }
                
        }
        return sb.toString();
    }

    /**
     * Displays the message "promptMsgIn" to the user and reads the next full line
     * that the user enters. If the user enters an empty string, reports the
     * error message "ERROR! Empty Input Not Allowed!" and then loops,
     * repeatedly prompting them with "promptMsg" to enter a new string until
     * the user enters a non-empty String
     *
     * @param in
     *            Scanner to read user input from
     * @param promptMsgIn
     *            Message to display to user to prompt them for input
     * @return the String entered by the user
     */
    public static String promptForStringIn(Scanner in, String promptMsgIn) {   
        System.out.print(promptMsgIn);
        String inFile = in.next();
        while(inFile.length() == 0){                                            //If there is a blankline print ERROR message.
            System.out.println("ERROR! Empty Input Not Allowed!");
            inFile = in.next();
        }
        return inFile;
    }

    /**
     * Displays the message "promptMsgOut" to the user and reads the next full line
     * that the user enters. If the user enters an empty string, reports the
     * error message "ERROR! Empty Input Not Allowed!" and then loops,
     * repeatedly prompting them with "promptMsgOut" to enter a new string until
     * the user enters a non-empty String
     *
     * @param in
     *            Scanner to read user input from
     * @param promptMsgOut
     *            Message to display to user to prompt them for input
     * @return the String entered by the user
     */
    public static String promptForStringOut(Scanner in, String promptMsgOut) {
        System.out.print(promptMsgOut);
        String outFile = in.next();
        while(outFile.length() == 0){
            System.out.println("ERROR! Empty Input Not Allowed!");
            outFile = in.next();
        }
        return outFile;
    }

    /**
     * Opens the file inFile for reading and the file outFile for writing,
     * reading one line at a time from inFile, shifting it the number of
     * characters given by "shift" and writing that line to outFile. If an
     * exception occurs, must report the error message: "ERROR! File inFile not
     * found or cannot write to outFile" where "inFile" and "outFile" are the
     * filenames given as parameters.
     *
     * @param inFile
     *            the file to be transformed
     * @param outFile
     *            the file to write the transformed output to
     * @param shift
     *            the amount to shift the characters from inFile by
     * @return false if an exception occurs and the error message is written,
     *         otherwise true
     */
    public static boolean transformFile(String inFile, String outFile,
        int shift) {
        File fileIn = null;
        Scanner inS;
        PrintWriter outS;
        boolean fileWorks = true;             
        try {
            fileIn = new File(inFile);                                    //assigns the new file to fileIn      
            inS = new Scanner(fileIn);                                    //scans through the file      
            outS = new PrintWriter(outFile);                              //writes the file to a .txt file 
            while (inS.hasNext()) {
                String input = inS.nextLine();                                              
                String outFileText = shift(input, shift);                 //sends each line to shift function.  
                outS.println(outFileText);
          }
        outS.close();
        inS.close();
        } catch (FileNotFoundException e) {                             
          System.out.println("ERROR - File "+inFile+" not found!");
          fileWorks = false;
        }
        return fileWorks;
    }

    /**
     * Prompts the user to enter a single character choice. The only allowable
     * values are 'E', 'D' or 'Q'. All other values are invalid, including all
     * values longer than one character in length, however the user is allowed
     * to enter values in either lower or upper case. If the user enters an
     * invalid value, the method displays the error message "ERROR! Enter a
     * valid value!" and then prompts the user repeatedly until a valid value is
     * entered. Returns a single uppercase character representing the user's
     * choice.
     *
     * @param in
     *            Scanner to read user choices from
     * @return the user's choice as an uppercase character
     */
    public static char getChoice(Scanner in) {
        char choice = in.next().charAt(0);
        choice = Character.toUpperCase(choice);                     //converts input to an uppercase character and assigns to chocie
        int goodChoice = 0;
        while(goodChoice == 0){
            if(choice == 'E'|choice == 'D'|choice == 'Q'){          //If choice is equals to one of the three options it is allowed to be, breaks the loop.                
                goodChoice = 1;
            }
            else{
                System.out.print("ERROR! Enter a valid value!");
                System.out.println("Enter your choice: ");
                choice = in.next().charAt(0);
                choice = Character.toUpperCase(choice);
            }
        }
        return choice;
    }

    /**
     * Displays the menu of choices to the user.
     */
    public static void displayMenu() {
        System.out.println("[E]ncode a file");
        System.out.println("[D]ecode a file");
        System.out.println("[Q]uit");
        System.out.print("Enter your choice: ");
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        displayMenu();
        char choice = getChoice(in);
        while(choice != 'Q'){
            String promptMsgIn = "Enter an input file: ";                         
            String inFile = promptForStringIn(in, promptMsgIn);
            String promptMsgOut = "Enter an output file: ";
            String outFile = promptForStringOut(in, promptMsgOut);
            System.out.print("Enter a shift amount: ");
            int shift = in.nextInt();
            transformFile(inFile, outFile, shift);
            System.out.println("Finished writing to file.");
            System.out.println();
            displayMenu();
            choice = getChoice(in);
        }
        System.out.println();
        System.out.println("Goodbye!");
        in.close();
    }

}