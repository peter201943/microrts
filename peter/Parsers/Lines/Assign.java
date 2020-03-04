
package peter;

public class Assign extends ParseRule
{
    private String match;
    private RuleParser ruleParser;

    public Assign(RuleParser ruleParser)
    {
        this.match = "symbol setEquals clause";
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