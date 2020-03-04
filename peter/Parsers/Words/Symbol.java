
package peter;

public class Symbol extends ParseRule
{
    private String match;
    private RuleParser ruleParser;

    public Symbol(RuleParser ruleParser)
    {
        this.match = "*";
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