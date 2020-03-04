
package peter;

public class Barracks extends ParseRule
{
    private String match;
    private RuleParser ruleParser;

    public Barracks(RuleParser ruleParser)
    {
        this.match = "barracks";
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