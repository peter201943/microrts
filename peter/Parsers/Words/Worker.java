
package peter;

public class Worker extends ParseRule
{
    private String match;
    private RuleParser ruleParser;

    public Worker(RuleParser ruleParser)
    {
        this.match = "worker";
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