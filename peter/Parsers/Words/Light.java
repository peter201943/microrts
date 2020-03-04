
package peter;

public class Light extends ParseRule
{
    private String match;
    private RuleParser ruleParser;

    public Light(RuleParser ruleParser)
    {
        this.match = "light";
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