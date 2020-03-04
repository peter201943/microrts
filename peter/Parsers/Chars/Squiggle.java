
package peter;

public class Squiggle extends ParseRule
{
    private String match;
    private RuleParser ruleParser;

    public Squiggle(RuleParser ruleParser)
    {
        this.match = "~";
        this.ruleParser = ruleParser;
    }

    public void Check(String string, InferenceEngine inferenceEngine)
    {
        if (this.match.equals(string))
        {
            // inferenceEngine.ParseChar();
        }
    }
}