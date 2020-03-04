
package peter;

public class Equals extends ParseRule
{
    private String match;
    private RuleParser ruleParser;

    public Equals(RuleParser ruleParser)
    {
        this.match = "=";
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