
package peter;

import java.util.ArrayList;

/**
 * Reads contents from string and adds to KN and RB for IE based on ParseRules
 */
public class RuleParser
{
    private ArrayList<ParseRule> characterRules;        // `#`, `,`, `.`, `(`, `)`, `"`, `~`, ` `
    private ArrayList<ParseRule> wordRules;             // `:-`, `Worker`, `Base`, `Barracks`, `Light`, `idle`, `own`, `afford`, `*`
    private ArrayList<ParseRule> lineRules;             // structure of words, eg, conditions must follow `:-`

    public RuleParser()
    {
        this.characterRules = new ArrayList<ParseRule>();
        this.wordRules = new ArrayList<ParseRule>();
        this.lineRules = new ArrayList<ParseRule>();
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

    public void Parse(String fileContents, InferenceEngine inferenceEngine)
    {
        // Setup variables
                                                            // Character is implicit/momentary
        String word;                                        // word is built up from characters
        ArrayList<String> line = new ArrayList<String>();   // Line is built up from strings

        // Convert String into Array
        char[] fileCharacters = fileContents.toCharArray();

        // Get a Char until Space
        for (char fileCharacter : fileCharacters)
        {
            // Check Char Rules
            for (ParseRule parseRule : characterRules)
            {
                parseRule.Check(fileCharacter);
            }

            // Check if space
            if (fileCharacter == " ")
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
            }
        }
    }
}