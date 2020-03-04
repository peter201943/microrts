
package peter;

public abstract class ParseRule
{
    private String match;
    private RuleParser ruleParser;

    public ParseRule(RuleParser ruleParser)
    {
        this.match = "";
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