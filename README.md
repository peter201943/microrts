

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
    ├── KnowledgeBase.java      // Class                    -- Has Facts, queries and updates Facts
    ├── RulesBase.java          // Class                    -- Has Rules, queries and updates Rules
    ├── Rule.java               // Class                    -- Adds a Player Action if Conditions are met
    ├── Fact.java               // Class                    -- Derived from Game State and other Facts
    ├── InferenceEngine.java    // Class                    -- Has KB and RB, queries and updates both to generate Player Actions
    ├── RuleParser.java         // Class                    -- Reads Plaintext Rules and Spawns Rule Objects
    └── RulesBasedAgent.java    // AbstractionLayerAI       -- Has Inference Engine and connect to Game
```



## Agent

### Background
```yaml
fact syntax:
    rule:
    #comment
    action!
    condition?
    link
    shortname
    long name

fact types:
    subjects
    states
    sugar

fact subjects:
    they
    we

fact states:
    own
    not

fact sugar:
    do
    the
    a
    is
    if
```

### Actual
```yaml
fact agent:
    if they own a base:
        attack!

light unit:
    if idle: attack!

attack:
    light units attack!
```


