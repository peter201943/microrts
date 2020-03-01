
package peter;

import java.util.ArrayList;

/**
 * Reads contents from string and adds to KN and RB for IE based on ParseRules
 */
public class RuleParser
{


    // ***********************************
    // VARIABLES
    // ***********************************

    // Parsing Variables
    private ArrayList<ParseRule> characterRules;        // `#`, `,`, `.`, `(`, `)`, `"`, `~`, ` `
    private ArrayList<ParseRule> wordRules;             // `:-`, `Worker`, `Base`, `Barracks`, `Light`, `idle`, `own`, `afford`, `*`
    private ArrayList<ParseRule> lineRules;             // structure of words, eg, conditions must follow `:-`

    // Scanning variables
    char[] fileCharacters;                              // Where we store the file contents
    int currentPosition;                                // Where we are in the file contents

    // Parsing Variables
                                                        // Character is implicit/momentary
    String word;                                        // Word is built up from characters
    ArrayList<String> line;                             // Line is built up from strings



    // ***********************************
    // CLASS BASICS
    // ***********************************

    public RuleParser()
    {
        this.characterRules = new ArrayList<ParseRule>();
        this.wordRules = new ArrayList<ParseRule>();
        this.lineRules = new ArrayList<ParseRule>();

        this.fileCharacters = null;
        this.word = "";
        this.line = new ArrayList<String>();
    }

    public void AddCharRule(ParseRule charRule)
    {
        this.characterRules.Add(charRule);
    }

    public void AddWordRule(ParseRule wordRule)
    {
        this.wordRules.Add(wordRule);
    }

    public void AddLineRule(ParseRule lineRule)
    {
        this.lineRules.Add(lineRule);
    }



    // ***********************************
    // PARSE LOOPS
    // ***********************************

    public void Parse(String fileContents, InferenceEngine inferenceEngine)
    {
        // Convert String into Array
        fileCharacters = fileContents.toCharArray();

        // Start the Parsing Loop
        ParseChar(inferenceEngine);
    }

    /**
     * Called by individual Parsing Rules.
     * Until EOF is reached
     */
    public void ParseChar(InferenceEngine inferenceEngine)
    {
        // Get a Char until Space
        for (char fileCharacter : fileCharacters)
        {
            // Check Char Rules
            for (ParseRule parseRule : characterRules)
            {
                parseRule.Check(fileCharacter);
            }

            // Check if Space
            if (fileCharacter == " ")
            {
                EndOfWord();
                continue;
            }

            // Check if Hash or Period
            if ((fileCharacter == "#") || (fileCharacter == "."))
            {
                EndOfLine();
                continue;
            }
        }
    }



    // ***********************************
    // CASES
    // ***********************************

    private void EndOfWord()
    {
        // Add to Line
        line.Add(word);

        // Check Word Rules
        for (ParseRule parseRule : wordRules)
        {
            parseRule.Check(word);
        }                

        // Clear word
        word = "";
    }

    private void EndOfLine()
    {
        // Add to Line
        line.Add(word);

        // Check Word Rules
        for (ParseRule parseRule : wordRules)
        {
            parseRule.Check(word);
        }                

        // Check Line Rules
        for (ParseRule parseRule : lineRules)
        {
            parseRule.Check(line);
        }

        // Clear word
        word = "";

        // Clear line
        line = new ArrayList<String>();
    }

    private void EndOfFile()
    {
        // Add to Line
        line.Add(word);

        // Check Word Rules
        for (ParseRule parseRule : wordRules)
        {
            parseRule.Check(word);
        }                

        // Check Line Rules
        for (ParseRule parseRule : lineRules)
        {
            parseRule.Check(line);
        }

        // Clear word
        word = "";

        // Clear line
        line = new ArrayList<String>();

        // Clear Current
        current = 0;
    }
}