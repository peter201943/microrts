
package peter;

import java.util.ArrayList;

/**
 * Reads contents from file and adds to KN and RB for IE
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

    public void Parse(String fileContents, InferenceEngine inferenceEngine)
    {
        // Read the File

        // Add to IE
    }
}