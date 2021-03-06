

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
 - [Notes](NOTES.md)        -- Anything I thought
 - [Status](STATUS.md)      -- Where I am
 - [TryParse](TryPArse.md)  -- How to parse


---------


## Files (Outdated)
ignore this. This is no longer true.  
Ran out of time to implement this.  
You can however see the pitiful attempts with `TryParse1-6.py`.  
The bulk of the work is now done from within a single file (`src.ai.peter.RulesAgent.java`).  
Most of this was deleted, but can still be seen in `src/peter`.  
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
    ├── Facts/
    │   ├── Afford.java                 // Fact                     -- Can we afford this unit?
    │   ├── Fact.java                   // Abstract                 -- Derived from Game State and other Facts
    │   ├── Have.java                   // Fact                     -- Do we have this unit?
    │   ├── Idle.java                   // Fact                     -- Is this unit active?
    │   └── Negation.java               // Fact                     -- Decorates another Fact, allows rule to explicitly define false values
    ├── Parsers/
    │   ├── Chars/
    │   │   ├── Comma.java              // ParseRule                -- Clause Separator
    │   │   ├── Equals.java             // ParseRule                -- Import
    │   │   ├── Hash.java               // ParseRule                -- Comment -- ignores until newline
    │   │   ├── LeftParen.java          // ParseRule                -- Start of Closure -- Target Indication
    │   │   ├── NewLine.java            // ParseRule                -- End of Line
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
```yaml
# Light Rush Rules Based Agent

# Assigns the above rules these conditions
doTrainWorker   :   is Base         idle Base     ~ have Worker     afford Worker
doBuildBase     :   is Worker       idle Worker   ~ have Base       afford Base
doBuildBarracks :   is Worker       have Base     ~ have Barracks   afford Barracks
doHarvest       :   is Worker       idle Worker     have Base
doTrainLight    :   is Barracks     idle Barracks   afford Light
doAttack        :   is Light        idle Light
```


### Running
### Files
```
.
├── src
│   └── ai
│       └── peter
│           └── RulesAgent
:
└── SimpleAI.txt
```

#### Config
```
.
└── resources
    └── config.properties
```
```
AI2=ai.peter.RulesAgent
```
#### Execution
```
.
└── src
    └── rts
        └── MicroRTS
```
```
javac MicroRTS.java
```

### Addendum
 - See [Notes](Notes.md) for design choices and why this format was selected.


---------


This Project can be accessed from [Github](https://github.com/peter201943/microrts).