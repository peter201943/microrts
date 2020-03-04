
package peter;

public class Idle extends ParseRule
{
    private String match;
    private RuleParser ruleParser;

    public Idle(RuleParser ruleParser)
    {
        this.match = "idle(*)";
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