
package peter;

public class Have extends ParseRule
{
    private String match;
    private RuleParser ruleParser;

    public Have(RuleParser ruleParser)
    {
        this.match = "have(*)";
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