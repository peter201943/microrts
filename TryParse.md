

### Items
 - [TryParse](TryParse.md)      -- Notes
 - [TryParse 1](TryParse1.py)   -- 1st iteration
 - [TryParse 2](TryParse2.py)   -- 2nd iteration
 - [TryParse 3](TryParse3.py)   -- 3rd iteration
 - [TryParse 4](TryParse4.py)   -- 4th iteration
 - [TryParse 5](TryParse5.py)   -- 5th iteration
 - [TryParse 6](TryParse6.py)   -- 6th iteration


### Dataflow
```
String -> [Char] -> [[Char]]
```
```
"abc" -> ["a" "b" "c"] -> [["a"] ["b" "c"]]
```


### References
 - https://stackoverflow.com/questions/9448265/example-parsers-to-learn-how-to-write-them#9448867
 - https://blog.eduonix.com/java-programming-2/write-parser-java/
 - https://stackoverflow.com/questions/6216449/where-can-i-learn-the-basics-of-writing-a-lexer
 - http://lisperator.net/pltut/parser/
 - https://www.jayconrod.com/posts/65/how-to-build-a-parser-by-hand


### Huh
```
bind(aName aClass)
lock(aName aCondition)
```


### New Ideas
 - 'Read Until' function -- scans until this character
 - Records intermediate text as string
 - Returns this string
 - Reduced Grammar -- eliminate commas and periods
   - Use newline instead of period
   - Allow multiple assignments to join -- se `doBuildBarracks` below
 - Better yet -- have search for a string - so can recognize `:-`


### New Code 1
```yaml
# Light Rush Rules Based Agent

# Creates new rules with these actions
doTrainWorker   = "TrainWorker"
doBuildBase     = "BuildBase"
doHarvest       = "Harvest"
doTrainLight    = "TrainLight"
doAttack        = "Attack"
doBuildBarracks = "BuildBarracks"

# Assigns the above rules these conditions
doTrainWorker   :   idle("Base")        have("Base")      ~ have("Worker")  afford("Worker")
doBuildBase     :   idle("Worker")      have("Worker")    ~ have("Base")    afford("Base")
doBuildBarracks :   impossible("")
doBuildBarracks :   
doBuildBarracks :   have("Worker")      have("Base")
doBuildBarracks : ~ have("Barracks")    afford("Barracks")
doHarvest       :   idle("Worker")      have("Base")
doTrainLight    :   idle("Barracks")    afford("Light")
doAttack        :   idle("Light")
```


### New Grammar 1
 - `#`                  -- comment, all characters ignored until end of line
 - `doTrainWorker`      -- arbitrary symbols
 - ` `                  -- separator, can be as many as you want -- CANNOT appear within a symbol
 - `=`                  -- class assignment, looked up in dictionary
 - `"TrainWorker"`      -- The string name of a Java class
 - `\n`                 -- end of clause
 - `:`                  -- condition assignment, single symbol
 - `~`                  -- negation
 - `idle()`             -- condition -- note all must accept a parameter
 - `doBuildBarracks`    -- symbols can have extra conditions added to them by repeating them
 - `impossible()`       -- inactivates a symbol's conditionality
 - `: ...`              -- resets a symbol's conditions
 - `(`                  -- start of condition target
 - `"`                  -- begin/end of target name


### Extra Conditions
```yaml
need("")    # We need this unit
friend("")  # we have this unit
foe("")     # enemy has this unit
X,Y,Z       # Relative/Temporary Symbols
 =          # Relative Assignment
```
```yaml
# New Style?
 idleLight :  X = "light"   own X   idle X
(idleLight : (X = "light") (own X) (idle X))
```


### New Grammar Rules 1
 - `#`      -- Read Until `\n`, ignore result
 - `???`    -- Read Until ` `, save result to 'symbols'
 - `=`      -- Read Until `"`, then Read Until `"`, lookup result in 'classes', assign class to 'last seen symbol', instantiate class
 - ` `      -- Skip
 - `\n`     -- Stop/Reset/Clear 'Reader Variables'
 - `:`      -- Read Until `\n`, parse result by Reading Until `(`, checking result is not ` `, looking up result in 'conditions', instantiate that condition...
            -- ... Read Until `"`, then Read Until `"`, lookup result in 'units', assign result to condition, assign condition to 'last seen symbol'


### Implied Data Structures
 - `symbols`            -- 
 - `classes`            -- 
 - `conditions`         -- 
 - `reader`             -- 
   - `lastseensymbol`   -- which symbol we last saw
 - `unknownsymbols`     -- any errors/unfinished symbols
 - `constructor`        -- agent we are assembling in java


### New Code 2
```yaml
# Light Rush Rules Based Agent

# Creates new rules with these actions
doTrainWorker   = TrainWorker
doBuildBase     = BuildBase
doHarvest       = Harvest
doTrainLight    = TrainLight
doAttack        = Attack
doBuildBarracks = BuildBarracks

# Assigns the above rules these conditions
doTrainWorker   :   idle Base       have Base     ~ have Worker     afford Worker
doBuildBase     :   idle Worker     have Worker   ~ have Base       afford Base
doBuildBarracks :   have Worker     have Base     ~ have Barracks   afford Barracks
doHarvest       :   idle Worker     have Base
doTrainLight    :   idle Barracks   afford Light
doAttack        :   idle Light
```


### New Grammar 2
 - `#`                  -- Comment
 - `\n`                 -- Clause Separator
 - `doTrainWorker`      -- Symbol
 - ` `                  -- Word Separator
 - `=`                  -- Assignment
 - `:`                  -- Conditioning
 - `idle`               -- Condition
 - `Base`               -- Condition Target, Target Name
 - `~`                  -- Negation Decorator

### New Rules 2
 - `sof`                -- Read Until `eof`, recurse on 'Results'
 - `#`                  -- Read Until `\n`, Do Nothing with 'Results'
 - `\n`                 -- Reset 'Line Variables'
 - `doTrainWorker`      -- Store to 'Symbols' if not present, 'Line Symbol' always
 - ` `                  -- Ignore
 - `=`                  -- Read Until `\n`, Read Until `*` in 'Results', lookup 'Results` in 'Classes', instantiate new instance of class, assign class to 'Last Seen Symbol'
 - `:`                  -- Read Until `\n`, then Read Until ` ` in 'Results'
 - `idle`               -- Read Until `*`, Read Until ` ` in 'Results', lookup 'Results' in 'Conditions', instantiate new instance, Read Until `*`, lookup 'Results' in 'Units', assign to condition


### Example Parsing
```yaml
doBuildBarracks :   have Worker     have Base
```
 1.  Reader Entry. Read Until `eof`. Parse 'Results'.
 2.  `\n` found. Reading Until ` `.
 3.  `doBuildBarracks` found. Symbol Unseen. `Line Symbol` set. `Symbols` match found.
 4.  Read Until `*`.
 5.  `:` found. Read Until `\n`.
 6.  `   have Worker     have Base` found. Read Until ` `.
 7.  `have` found. Instantiate new Have.java. Read Until ` `.
 8.  `Worker` found. Add Target to Instance. Add Instance to Symbol. Read Until ` `.
 9.  `have` found. Instantiate new Have.java. Read Until ` `.
 10. `Base` found. Add Target to Instance. Add Instance to Symbol. Read Until ` `.
 11. `eol` found. Reading Until `\n`.
 12. `\n` found. Reading Until ` `.
 13. `eof` found. Reader Exit.


### Lulz
```scheme
; Light Rush Rules Based Agent

(doTrainWorker
( = TrainWorker)
( : (idle Base)
    (have Base)
    (~ (have Worker))
    (afford Worker)))

(doBuildBase
( = BuildBase)
( : (idle Worker)
    (have Worker)
    (~ (have Base))
    (afford Base)))

(doHarvest
( = Harvest)
( : (idle Worker)
    (have Base)))

(doTrainLight
( = TrainLight)
( : (idle Barracks)
    (afford Light)))

(doAttack
( = Attack)
( : (idle Light)))

(doBuildBarracks
( = BuildBarracks)
( : (have Worker)
    (have Base)   
    (~ (have Barracks)) 
    (afford Barracks)))

```