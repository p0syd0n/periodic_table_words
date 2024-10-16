import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class Main {
  // Making constants for variables which need to br accesible from all functions.
  // Element file content.
  public static String content;
  // List of element symbols.
  public static String[] elements;


// Putting them into a static block, ig. idk why.
  static {
    // If we dont use try/catch, it will yell at us for "potentially unhandles exception", if the file doesnt exist.
      try {
        // Read the file
          content = Files.readString(Paths.get("elements.txt"));
          // Split the content and convert each element to lowercase
          elements = Arrays.stream(content.split(", "))
                           .map(String::toLowerCase) // Convert each element to lowercase
                           .toArray(String[]::new); // Collect the results back to an array
      } catch (IOException e) {
          // Handle the exception
          System.out.println("File not found. Setting elements to an empty array.");
          elements = new String[] {}; // Set to empty array to avoid null pointer issues
      }
  }
  // Scanner input = new Scanner(System.in);
  // String word = input.nextLine();
  // input.close();
  //String[] characters_in_input = word.split("");
  // if (Arrays.asList(characters_in_input).contains("j")) {
  //   exit("Contains J", 0);
  // }
  
  // Just an exit function, ig
  public static void exit(String message, int status) {
    System.out.println(message);
    System.exit(status);
  }

  // The good stuff. Takes the string word (the remaining word), and a list of symbols which were used for the characters that have been used.
  public static void check(String word, List<String> doneAlready) {
    // Debugging
    System.out.println("Checking: ");
    System.out.println(word);
    for (String i : doneAlready) {
      System.out.println(i);
    }

    // If the function is called but the word is empty, we're done!
    if (word.isEmpty()) {
      // Print elements
      for (String symbol : doneAlready) {
        System.out.print(symbol+" ");
      }
      exit("DOne!", 1);
    }

    // Declare two variables for what we're checking
    String char1, chars;
    // First character
    char1 = word.substring(0, 1);
    // Try/Catch block to handle potential StringIndexOutOfBounds
    try {
      chars = word.substring(0, 2);
    } catch (java.lang.StringIndexOutOfBoundsException e) {
      System.out.println("No next 2 characters.");
      // If it doesnt work, just use the first character.

      /*
       * This doesnt change anything - if the first character works, the first if check and recursive call will realize that and print it.
       * If it doesnt work, it'll just try it again. And then return, and return to the next check in whatever function that called it.
       */
      chars = word.substring(0, 1);
    }
    // Debugging
    System.out.println(char1+" "+chars);

    // Checking the first character
    if (Arrays.asList(elements).contains(char1)) {
      // Getting new word (old word, but without first character)
      String new_word = word.substring(1);
      // Copying the doneAlready symbols to a new list
      List<String> newDoneAlready = new ArrayList<>(doneAlready);
      // Adding the new symbol to the next list
      newDoneAlready.add(char1);
      // Call recursively
      check(new_word, newDoneAlready); 
    }

    // Checking the two characters
    if (Arrays.asList(elements).contains(chars)) {
      // getting the new word from the old word (-2 characters from the top)
      String new_word = word.substring(2);
      // Againt, copying the used elements over to a new ArrayList and adding the symbol
      List<String> newDoneAlready = new ArrayList<>(doneAlready);
      newDoneAlready.add(chars);
      // Calling the check function recursively
      check(new_word, newDoneAlready); 
    }

    // If nothing works, we return. This will return us to the instance of the functiont that called this one, and it can proceed to the next branch check.
    return;

  }
  public static void main(String[] args) {
    // Uh yeah, test data
    String word = "accept";
    List<String> doneAlready = new ArrayList<>();
    check(word, doneAlready);
    exit("Nope! none.", -1);

  }

}