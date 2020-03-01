

## CS 387: Game AI


---------


# README


## About
 - **Student**
     - Peter J. Mangelsdorf
     - pjm349
 - **Professor**
     - Ehsan Khosroshahi
     - eb452
 - **Contents**
     - [About](#about)      -- What's here
     - [Links](#links)      -- What else is here
     - [Files](#files)      -- What I added
     - [Agent](#agent)      -- What it does


## Links
 - [README](README.md)      -- Anything I did
 - [Notes](Notes.md)        -- Anything I thought


---------


## Files
```
Standard Access@SHODAN /cygdrive/c/Users/Standard Access/Peter/School/College/3 - Junior/CS 387/Projects/HW 4
$ tree
./
└── peter/
    ├── Agents/
    │   ├── InferenceEngine.java    // Class                    -- Has KB and RB, queries and updates both to generate Player Actions
    │   └── RulesBasedAgent.java    // AbstractionLayerAI       -- Has Inference Engine and connect to Game
    ├── Facts/
    │   ├── KnowledgeBase.java      // Class                    -- Has Facts, queries and updates Facts
    │   ├── Fact.java               // Abstract                 -- Derived from Game State and other Facts
    │   ├── Afford.java             // Fact                     -- Can we afford this unit?
    │   ├── Have.java               // Fact                     -- Do we have this unit?
    │   └── Idle.java               // Fact                     -- Is this unit active?
    ├── Rules/
    │   ├── RulesBase.java          // Class                    -- Has Rules, queries and updates Rules
    │   └── Rule.java               // Class                    -- Adds a Player Action if Conditions are met
    ├── Parsers/
    │   ├── RuleParser.java         // Class                    -- Reads Plaintext Rules and Spawns Rule Objects
    │   ├── ParseRule.java          // Abstract                 -- Examines a String and Acts on it
    │   ├── Chars/
    │   │   ├── Period.java         // ParseRule                -- Statement Separator
    │   │   ├── Quote.java          // ParseRule                -- Begin or End of Target Specification
    │   │   ├── Space.java          // ParseRule                -- Word Separator -- ignore if in sequence
    │   │   ├── Comma.java          // ParseRule                -- Clause Separator
    │   │   ├── Hash.java           // ParseRule                -- Comment -- ignores until newline
    │   │   ├── Squiggle.java       // ParseRule                -- Negation -- flips next clause
        │   ├── Equals.java         // ParseRule                -- Import
    │   │   ├── RightParen.java     // ParseRule                -- End of Closure -- Target Indication
    │   │   └── LeftParen.java      // ParseRule                -- Start of Closure -- Target Indication
    │   ├── Words/
    │   │   ├── SetEqual.java       // ParseRule                -- Name - Clause Linker
    │   │   ├── Symbol.java         // ParseRule                -- Rule Name
    │   │   ├── Base.java           // ParseRule                -- Unit Type `Base`
    │   │   ├── Worker.java         // ParseRule                -- Unit Type `Worker`
    │   │   ├── Barracks.java       // ParseRule                -- Unit Type `Barracks`
    │   │   ├── Light.java          // ParseRule                -- Unit Type `Light`
    │   │   ├── Afford.java         // ParseRule                -- Condition `Afford`
    │   │   ├── Have.java           // ParseRule                -- Condition `Have`
    │   │   └── Idle.java           // ParseRule                -- Condition `Idle`
    │   └── Lines/
    │       ├── Assign.java         // ParseRule                -- Find Rule with Symbol and Add Conditions
    │       └── Import.java         // ParseRule                -- Create Rule with Symbol and Action
    └── Actions/
        ├── Action.java             // Abstract                 -- A PlayerAction for a generic unit
        ├── TrainWorker.java        // Action                   -- copies `LightRush.workerBehavior`
        ├── BuildBase.java          // Action                   -- copies `LightRush.baseBehavior`
        ├── Harvest.java            // Action                   -- copies `LightRush.harvestBehavior`
        ├── TrainLight.java         // Action                   -- copies `LightRush.barracksBehavior`
        └── Attack.java             // Action                   -- copies `LightRush.meeleeBehavior`
```



## Agent

### Design
```
doTrainWorker   = "TrainWorker".
doBuildBase     = "BuildBase".
doHarvest       = "Harvest".
doTrainLight    = "TrainLight".
doAttack        = "Attack".

doTrainWorker   :- idle("Base"),        have("Base"),      ~have("Worker"), afford("Worker").
doBuildBase     :- idle("Worker"),      have("Worker"),    ~have("Base"),   afford("Base").
doBuildBarracks :- idle("Worker"),      have("Worker"),     have("Base"),  ~have("Barracks"),   afford("Barracks").
doHarvest       :- idle("Worker"),      have("Base").
doTrainLight    :- idle("Barracks"),    afford("Light").
doAttack        :- idle("Light").
```

### Addendum
 - See [Notes](Notes.md) for design choices and why this format was selected.


