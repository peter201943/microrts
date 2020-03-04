
package peter;

public class Afford extends ParseRule
{
    private String match;
    private RuleParser ruleParser;

    public Afford(RuleParser ruleParser)
    {
        this.match = "afford(*)";
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