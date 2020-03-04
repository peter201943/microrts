
package peter;

public class Base extends ParseRule
{
    private String match;
    private RuleParser ruleParser;

    public Base(RuleParser ruleParser)
    {
        this.match = "base";
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