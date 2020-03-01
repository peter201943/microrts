

## CS 387: Game AI


---------


# README


## About
 - **microRTS Prolog Expert System**
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
 - [Status](Status.md)      -- Where I am


---------


## Files
```
Standard Access@SHODAN /cygdrive/c/Users/Standard Access/Peter/School/College/3 - Junior/CS 387/Projects/HW 4
$ tree
./
└── peter/
    ├── Actions/
    │   ├── Action.java                 // Abstract                 -- Generates a `PlayerAction` once evaluated
    │   ├── TrainWorker.java            // Action                   -- copies `LightRush.workerBehavior`
    │   ├── BuildBase.java              // Action                   -- copies `LightRush.baseBehavior`
    │   ├── Harvest.java                // Action                   -- copies `LightRush.harvestBehavior`
    │   ├── TrainLight.java             // Action                   -- copies `LightRush.barracksBehavior`
    │   └── Attack.java                 // Action                   -- copies `LightRush.meeleeBehavior`
    ├── Agents/
    │   ├── InferenceEngine.java        // Class                    -- Has KB and RB, queries and updates both to generate Player Actions
    │   └── RulesBasedAgent.java        // AbstractionLayerAI       -- Has Inference Engine and connects to Game
    │   └── RulesBasedAgentDemo.java    // RulesBasedAgent          -- Hardcoded Example
    ├── Facts/
    │   ├── Afford.java                 // Fact                     -- Can we afford this unit?
    │   ├── Fact.java                   // Abstract                 -- Derived from Game State and other Facts
    │   ├── Have.java                   // Fact                     -- Do we have this unit?
    │   ├── Idle.java                   // Fact                     -- Is this unit active?
    │   └── KnowledgeBase.java          // Class                    -- Has Facts, queries and updates Facts
    ├── Parsers/
    │   ├── Chars/
    │   │   ├── Comma.java              // ParseRule                -- Clause Separator
    │   │   ├── Equals.java             // ParseRule                -- Import
    │   │   ├── Hash.java               // ParseRule                -- Comment -- ignores until newline
    │   │   ├── LeftParen.java          // ParseRule                -- Start of Closure -- Target Indication
    │   │   ├── Period.java             // ParseRule                -- Statement Separator
    │   │   ├── Quote.java              // ParseRule                -- Begin or End of Target Specification
    │   │   ├── RightParen.java         // ParseRule                -- End of Closure -- Target Indication
    │   │   ├── Space.java              // ParseRule                -- Word Separator -- ignore if in sequence
    │   │   └── Squiggle.java           // ParseRule                -- Negation -- flips next clause
    │   ├── Lines/
    │   │   ├── Assign.java             // ParseRule                -- Find Rule with Symbol and Add Conditions
    │   │   └── Import.java             // ParseRule                -- Create Rule with Symbol and Action
    │   ├── Words/
    │   │   ├── Afford.java             // ParseRule                -- Condition `Afford
    │   │   ├── Barracks.java           // ParseRule                -- Unit Type `Barracks``
    │   │   ├── Base.java               // ParseRule                -- Unit Type `Base`
    │   │   ├── Have.java               // ParseRule                -- Condition `Have`
    │   │   ├── Idle.java               // ParseRule                -- Condition `Idle`
    │   │   ├── Light.java              // ParseRule                -- Unit Type `Light`
    │   │   ├── SetEqual.java           // ParseRule                -- Name - Clause Linker
    │   │   ├── Symbol.java             // ParseRule                -- Rule Name
    │   │   └── Worker.java             // ParseRule                -- Unit Type `Worker`
    │   ├── ParseRule.java              // Abstract                 -- Examines a String and Acts on it
    │   └── RuleParser.java             // Class                    -- Reads Plaintext Rules and Spawns Rule Objects
    └── Rules/
        ├── RulesBase.java              // Class                    -- Has Rules, queries and updates Rules
        └── Rule.java                   // Class                    -- Adds a Player Action if Conditions are met
```


## Agent

### Design
```
# Light Rush Rules Based Agent

# Creates new rules with these actions
doTrainWorker   = "TrainWorker".
doBuildBase     = "BuildBase".
doHarvest       = "Harvest".
doTrainLight    = "TrainLight".
doAttack        = "Attack".

# Assigns the above rules these conditions
doTrainWorker   :- idle("Base"),        have("Base"),      ~have("Worker"), afford("Worker").
doBuildBase     :- idle("Worker"),      have("Worker"),    ~have("Base"),   afford("Base").
doBuildBarracks :- idle("Worker"),      have("Worker"),     have("Base"),  ~have("Barracks"),   afford("Barracks").
doHarvest       :- idle("Worker"),      have("Base").
doTrainLight    :- idle("Barracks"),    afford("Light").
doAttack        :- idle("Light").
```

### Addendum
 - See [Notes](Notes.md) for design choices and why this format was selected.


---------


This Project can be accessed from [Github](https://github.com/peter201943/microrts).