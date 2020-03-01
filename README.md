

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
    │   ├── Afford.java             // Fact                     -- 
    │   ├── Have.java               // Fact                     -- 
    │   └── Idle.java               // Fact                     -- 
    ├── Rules/
    │   ├── RulesBase.java          // Class                    -- Has Rules, queries and updates Rules
    │   └── Rule.java               // Abstract                 -- Adds a Player Action if Conditions are met
    ├── Parsers/
    │   ├── RuleParser.java         // Class                    -- Reads Plaintext Rules and Spawns Rule Objects
    │   ├── ParseRule.java          // Abstract                 -- 
    │   ├── Chars/
    │   │   ├── Period.java         // ParseRule                -- 
    │   │   ├── Quote.java          // ParseRule                -- 
    │   │   ├── Space.java          // ParseRule                -- 
    │   │   ├── Comma.java          // ParseRule                -- 
    │   │   ├── Hash.java           // ParseRule                -- 
    │   │   ├── Squiggle.java       // ParseRule                -- 
    │   │   ├── RightParen.java     // ParseRule                -- 
    │   │   └── LeftParen.java      // ParseRule                -- 
    │   ├── Words/
    │   │   ├── SetEqual.java       // ParseRule                -- 
    │   │   ├── Any.java            // ParseRule                -- 
    │   │   ├── Base.java           // ParseRule                -- 
    │   │   ├── Worker.java         // ParseRule                -- 
    │   │   ├── Barracks.java       // ParseRule                -- 
    │   │   ├── Light.java          // ParseRule                -- 
    │   │   ├── Afford.java         // ParseRule                -- 
    │   │   ├── Have.java           // ParseRule                -- 
    │   │   └── Idle.java           // ParseRule                -- 
    │   └── Lines/
    │       ├── Assign.java         // ParseRule                -- 
    │       └── Import.java         // ParseRule                -- 
    └── Behaviors/
        ├── Behavior.java           // Abstract                 -- 
        ├── TrainWorker.java        // Behavior                 -- 
        ├── BuildBase.java          // Behavior                 -- 
        ├── Harvest.java            // Behavior                 -- 
        ├── TrainLight.java         // Behavior                 -- 
        └── Attack.java             // Behavior                 -- 
```



## Agent

### Design
```yaml
import:
doTrainWorker   = "TrainWorker"
doBuildBase     = "BuildBase"
doHarvest       = "Harvest"
doTrainLight    = "TrainLight"
doAttack        = "Attack"

assign:
doTrainWorker   :- idle("Base"),        have("Base"),      ~have("Worker"), afford("Worker").
doBuildBase     :- idle("Worker"),      have("Worker"),    ~have("Base"),   afford("Base").
doBuildBarracks :- idle("Worker"),      have("Worker"),     have("Base"),  ~have("Barracks"),   afford("Barracks").
doHarvest       :- idle("Worker"),      have("Base").
doTrainLight    :- idle("Barracks"),    afford("Light").
doAttack        :- idle("Light").
```


