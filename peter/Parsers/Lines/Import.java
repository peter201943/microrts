
package peter;

public class Import extends ParseRule
{
    private String match;
    private RuleParser ruleParser;

    public Import(RuleParser ruleParser)
    {
        this.match = "symbol equals quote";
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