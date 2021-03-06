

## CS 387: Game AI


---------


# NOTES


## About
 - **microRTS Prolog Expert System**
 - **Student**
     - Peter J. Mangelsdorf
     - pjm349
 - **Professor**
     - Ehsan Khosroshahi
     - eb452
 - **Contents**
     - [About](#about)                      -- What's here
     - [Links](#links)                      -- What else is here
     - [Learning](#learning)                -- How I got started
     - [Language](#language)                -- What rules look like
     - [Parsing](#parsing)                  -- Processing rules
     - [Considerations](#considerations)    -- Sorting Rules
     - [New Format](#new-format)            -- Nevermind everything else


## Links
 - [README](README.md)      -- Anything I did
 - [Notes](NOTES.md)        -- Anything I thought
 - [Status](STATUS.md)      -- Where I am
 - [TryParse](TryPArse.md)  -- How to parse


---------


## Learning

### Very helpful Video
[Creating a bot for microRTS](https://www.youtube.com/watch?v=BikqC5gZWhU)

### My Analysis of the Project
![Project Structure](ProjectAnalysis.png)

### My Initial Agent Design
![Agent Design](AgentDesign.png)

### An Example Agent
[`Light Rush`](src/ai/abstraction/LightRush.java)


---------


## Language

### Simple Example
```yaml
# Rule set:
# If we have any "light":   send it to attack to the nearest enemy unit
# If we have a base:        train worker until we have 1 workers
# If we have a barracks:    train light
# If we have a worker:      do this if needed: build base, build barracks, harvest resources

# Notes:
# - Actions are of the form doXXX(UnitType)
#
# Actions that can be performed:
# - doTrainWorker:      trains a worker
# - doTrainLight:       trains a light unit
# - doBuildBase:        builds a base in a nearby position
# - doBuildBarracks:    builds a barracks in a nearby position
# - doHarvest:          sends a worker to harvest resources from a resource mine
# - doAttack:           attacks a nearby enemy
#
# Predefined predicates that need to be implemented:
# - idle:                           whether the unit we are considering is idle
# - own(UnitType):                  whether we own any unit of the specified type
# - enoughResourcesFor(UnitType):   whether we have enough resources for the specified unit type
# - ~ means negation
#
# The idea here is:
# - For each of the units we own try to execute the rules that match the unit type of the unit
# - If any rule gets triggered, make the unit perform the action corresponding to the rule head

doTrainWorker("Base")       :- idle, own("Base"),   ~own("Worker"),   enoughResourcesFor("Worker").
doBuildBase("Worker")       :- idle, own("Worker"), ~own("Base"),     enoughResourcesFor("Base").
doBuildBarracks("Worker")   :- idle, own("Worker"),  own("Base"),    ~own("Barracks"),              enoughResourcesFor("Barracks").
doHarvest("Worker")         :- idle, own("Base").
doTrainLight("Barracks")    :- idle, enoughResourcesFor("Light").
doAttack("Light")           :- idle.
```

### Complex Example
```yaml
# Rule set:
# If we have any "light":   send it to attack to the nearest enemy unit
# If we have a base:        train worker until we have 1 workers
# If we have a barracks:    train light
# If we have a worker:      do this if needed: build base, build barracks, harvest resources

# Notes:
# - Generally this uses Prolog syntax
# - numbers and strings surrounded by double quotes are constants (1, 3, "Base", etc.)
# - symbols starting with an upper case letter are variables (X, Y, etc.)
# - ~ represents negation
# - the comparison operators (>; <) should only be applicable if the variables are bound, otherwise they should fail.

# Notes:
# - Actions are of the form doXXX(ID): the make the unit with the specified ID perform an action
#
# Actions that can be performed:
# - doTrainWorker:      trains a worker
# - doTrainLight:       trains a light unit
# - doBuildBase:        builds a base in a nearby position
# - doBuildBarracks:    builds a barracks in a nearby position
# - doHarvest:          sends a worker to harvest resources from a resource mine
# - doAttack:           attacks a nearby enemy
#
# Predefined predicates that need to be implemented:
# - idle(ID):                       whether the unit specified by ID is idle (if ID is unbound, ID will be bound to each of the idle units, one at a time, Prolog-style)
# - own(ID):                        whether we own the unit with the specified ID  (if ID is unbound, ID will be bound to each of the units we own, one at a time)
# - enemy(ID):                      same as "own(ID)", but for enemy units.
# - resourcesAvailable(X):          binds X to the number of resources we have
# - resourcesNeededFor(UnitType,Y): binds Y to the number of resources needed to train UnitType
# - type(X,UnitType):               true if the unit type of X is UnitType
# - ~ means negation
#
# The idea here is:
# - At each game frame, check one rule at a time, and for each rule which is satisfied, send the corresponding command to the corresponding units.

# auxiliary predicates:
ownBase(X)          :-  type(X,"Base"),      own(X).
ownWorker(X)        :-  type(X,"Worker"),    own(X).
ownBarrack(X)       :-  type(X,"Barracks"),  own(X).
workerNeeded()      :- ~ownWorker(X).

# baseBehavior
doTrainWorker(Z)    :-  workerNeeded(),  resourcesAvailable(X),  resourcesNeededFor("Worker",Y),    X>Y,    ownBase(Z), idle(Z).

# workersBehavior
idleWorker(X)       :-  type(X,"Worker"),   own(X),                 idle(X).
doBuildBase(Z)      :- ~ownBase(X),         resourcesAvailable(X),  resourcesNeededFor("Base",Y),       X>Y,    idleWorker(Z).
doBuildBarracks(Z)  :- ~ownBarrack(X),      resourcesAvailable(X),  resourcesNeededFor("Barracks",Y),   X>Y,    idleWorker(Z).
doHarvest(X,Y,Z)    :-  own(X),             idleWorker(X),          type(Y,"Resource"),                 ownBase(Z).

# barracksBehavior
doTrainLight(Z)     :-  resourcesAvailable(X),  resourcesNeededFor("Light",Y),  X>Y,    ownBarrack(Z),  idle(Z).

# meleeUnitBehavior
idleLight(X)        :-  type(X,"Light"),    own(X),         idle(X).
doAttack(X,Y)       :-  own(X),             idleLight(X),   enemy(Y),    type(Y,"Light").
doAttack(X,Y)       :-  own(X),             idleLight(X),   enemy(Y),   ~type(Y,"Resource").
```

### Representing Attacks
```yaml
doAttack("Light") :- idle.
name ("target") :- condition .
RuleName Accepts "TargetName" PredicatedOn Condition EndOfLine
```
```yaml
if lightUnits
    if lightUnits.idle
        lightUnits.attack
```
```yaml
idle(light) = attack(light)
```

### Lang Spec 0
```yaml
# Lexicon Imports
idle    = Idle.java
have    = Have.java
no      = No.java
afford  = Afford.java
=       = Check.java
:       = Do.java

# Command Imports
lightAttack     = Attack.java
firstBarracks   = Barracks.java
gatherResources = Gather.java
firstWorker     = TrainWorker.java
lightRush       = LightRush.java

# Unit Imports
units       = Agent.units
lights      = Agent.units.lightUnits
workers     = Agent.units.workers
bases       = Agent.units.bases
barracks    = Agent.units.barracks

# Behaviors (by Unit)
rules:
    units:
        lights:
            lightAttack     = idle                                                              # 6
        workers:
            firstBase       = idle, have no base, have worker, afford base                      # 2
            firstBarracks   = idle, have no barracks, afford barracks, have base, have worker   # 3
            gatherResources = idle, have base                                                   # 4
        bases:
            firstWorker     = idle, have base, have no workers, afford worker                   # 1
        barracks:
            lightRush       = idle, afford light                                                # 5

# Facts (by Unit)
facts:
    units:
        lights:
            have    = true
            afford  = false     # 5
            idle    = none      # 6
        workers:
            have    = false     # 1
            afford  = true      # 1
            idle    = [unit1]   # 2,3,4
        bases:
            have    = true      # 1,2,3,4
            afford  = false     # 2
            idle    = none      # 1
        barracks:
            have    = false     # 3
            afford  = false     # 3
            idle    = none      # 5
```

### Different Fact Sorts
```yaml
# By Subject
facts:
    units:
        lights:
            have    = true
            afford  = false     # 5
            idle    = none      # 6
        workers:
            have    = false     # 1
            afford  = true      # 1
            idle    = [unit1]   # 2,3,4
        bases:
            have    = true      # 1,2,3,4
            afford  = false     # 2
            idle    = none      # 1
        barracks:
            have    = false     # 3
            afford  = false     # 3
            idle    = none      # 5

# By Verb
facts:
    have:
        lights      = true
        workers     = false     # 1
        bases       = true      # 1,2,3,4
        barracks    = false     # 3
    afford:
        lights      = false     # 5
        workers     = true      # 1
        bases       = false     # 2
        barracks    = flase     # 3
    idle:
        lights      = none      # 6
        workers     = [unit1]   # 2,3,4
        bases       = none      # 1
        barracks    = none      # 5

# By Truth
facts:
    true:
        lights:     have    = true
        workers:    afford  = true      # 1
        workers:    idle    = [unit1]   # 2,3,4
        bases:      have    = true      # 1,2,3,4
    false:
        lights:     afford  = false     # 5
        lights:     idle    = none      # 6
        workers:    have    = false     # 1
        bases:      afford  = false     # 2
        bases:      idle    = none      # 1
        barracks:   have    = false     # 3
        barracks:   afford  = false     # 3
        barracks:   idle    = none      # 5
```

### Full Names
```yaml
# Per Action Full Name Listing
rules.units.lights.lightAttack      = idle                                                              # 6

rules.units.workers.firstBase       = idle, have no base, have worker, afford base                      # 2
rules.units.workers.firstBarracks   = idle, have no barracks, afford barracks, have base, have worker   # 3
rules.units.workers.gatherResources = idle, have base                                                   # 4

rules.units.bases.firstWorker       = idle, have base, have no workers, afford worker                   # 1

rules.units.barracks.lightRush      = idle, afford light                                                # 5

# Per Fact Full Name Listing
facts.units.lights.have     = true
facts.units.lights.afford   = false     # 5
facts.units.lights.idle     = none      # 6

facts.units.workers.have    = false     # 1
facts.units.workers.afford  = true      # 1
facts.units.workers.idle    = [unit1]   # 2,3,4

facts.units.bases.have      = true      # 1,2,3,4
facts.units.bases.afford    = false     # 2
facts.units.bases.idle      = none      # 1

facts.units.barracks.have   = false     # 3
facts.units.barracks.afford = false     # 3
facts.units.barracks.idle   = none      # 5
```
```yaml
rule:
    lightAttack = we own lights

fact:
    we own lights = True
```

### Original
```yaml
doTrainWorker("Base")       :- idle, own("Base"),   ~own("Worker"),  enoughResourcesFor("Worker").                          # 1
doBuildBase("Worker")       :- idle, own("Worker"), ~own("Base"),    enoughResourcesFor("Base").                            # 2
doBuildBarracks("Worker")   :- idle, own("Worker"),  own("Base"),   ~own("Barracks"),   enoughResourcesFor("Barracks").     # 3
doHarvest("Worker")         :- idle, own("Base").                                                                           # 4
doTrainLight("Barracks")    :- idle, enoughResourcesFor("Light").                                                           # 5
doAttack("Light")           :- idle.                                                                                        # 6
```

### Lang Spec 1
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
```yaml
fact agent:
    if they own a base:
        attack!

light unit:
    if idle: attack!

attack:
    light units attack!
```

### Train Worker Labels
```yaml
doTrainWorker("Base")       :- idle, own("Base"),   ~own("Worker"),  enoughResourcesFor("Worker").                          # 1
```
```yaml
doTrainWorker:
    subject: Base
    condition:
        Base: idle
        Base: own
        Worker: not own
        Worker: afford
    action: BuildWorker.java
```
```yaml
doTrainWorker:
    subject: Base
    condition:
        idle: Base
        own: Base
        not own: Worker
        afford: Worker
    action: BuildWorker.java
```
```yaml
doTrainWorker:
    subject: Base
    condition: idle: Base,  own: Base,  not own: Worker,  afford: Worker
    action: BuildWorker.java
```

### Build Base Tags
```yaml
doBuildBarracks("Worker")   :- idle, own("Worker"),  own("Base"),   ~own("Barracks"),   enoughResourcesFor("Barracks").     # 3
```
```yaml
doBuildBarracks:
    subject: Worker
    condition: idle: Worker,  have: Worker, Base  have no: Barracks,  afford: Barracks
    action: BuildBarracks.java
```
```yaml
doBuildBarracks:
    @Worker
    +idle
    +Worker
    +Base
    -Barracks
    $Barracks
```
```yaml
doBuildBarracks:  @Worker  +idle  +Worker  +Base  -Barracks  $Barracks
```

### Complex Build Base Tags
```yaml
doBuildBarracks(Z) :- ~ownBarrack(X),resourcesAvailable(X),resourcesNeededFor("Barracks",Y),X>Y,idleWorker(Z).
```
```yaml
doBuildBarracks:
    @ Z
    - X
    $ X
    $ Y
    +Barracks Y
    +> X Y
    +idle Z
```

### Implied Action
```yaml
# Behaviors (by Unit)
rules:
    units:
        lights:
            idle                                                            -> lightAttack      # 6
        workers:
            idle, have no base, have worker, afford base                    -> firstBase        # 2
            idle, have no barracks, afford barracks, have base, have worker -> firstBarracks    # 3
            idle, have base                                                 -> gatherResources  # 4
        bases:
            idle, have base, have no workers, afford worker                 -> firstWorker      # 1
        barracks:
            idle, afford light                                              -> lightRush        # 5
```

### Refined Truth
#### Number One
```yaml
baseFacts:
    have:
        lights
        workers     # 1
        bases       # 1,2,3,4
        barracks    # 3
    afford:
        lights      # 5
        workers     # 1
        bases       # 2
        barracks    # 3
    idle:
        lights      # 6
        workers     # 2,3,4
        bases       # 1
        barracks    # 5

derivedFacts:
    canRush:
        idle barracks, afford light                                                 # 5
    needFirstWorker:
        idle bases, have no workers, afford worker                                  # 1
    canGatherResources:
        idle worker, have base                                                      # 4
    needFirstBarracks:
        idle worker, have no barracks, afford barracks, have base, have worker      # 3
    needFirstBase:
        idle worker, have no base, have worker, afford base                         # 2
    canAttack:
        idle lights                                                                 # 6

rules:
    canAttack?          lightAttack!        # 6
    needFirstBase?      firstBase!          # 2
    needFirstBarracks?  firstBarracks!      # 3
    canGatherResources? gatherResources!    # 4
    needFirstWorker?    firstWorker!        # 1
    canRush?            lightRush!          # 5
```
#### Number Two
```yaml
objects:
    enemy
    enemies

    resource
    resources
    
    light
    lights
    
    worker
    workers
    
    base
    bases
    
    barracks
    barracks

modifiers:
    no

states:
    have
    afford
    idle

baseFacts:
    have:
        lights
        workers     # 1
        bases       # 1,2,3,4
        barracks    # 3
    afford:
        lights      # 5
        workers     # 1
        bases       # 2
        barracks    # 3
    idle:
        lights      # 6
        workers     # 2,3,4
        bases       # 1
        barracks    # 5

derivedFacts:
    canRush:
        idle barracks, afford light                             # 5
    canFirstWorker:
        idle bases, afford worker                               # 1
    needFirstWorker:
        have no workers                                         # 1
    canGatherResources:
        idle worker, have base                                  # 4
    needGatherResources:
        have resources                                          # 4
    canFirstBarracks:
        idle worker, afford barracks, have base, have worker    # 3
    needFirstBarracks:
        have no barracks                                        # 3
    canFirstBase:
        idle worker, have worker, afford base                   # 2
    needFirstBase:
        have no base                                            # 2
    canAttack:
        idle lights                                             # 6
    needAttack:
        have enemy                                              # 5,6

rules:
    needAttack?             canAttack?              doAttack!           # 6
    needFirstBase?          canFirstBase?           doFirstBase!        # 2
    needFirstBarracks?      canFirstBarracks?       doFirstBarracks!    # 3
    needGatherResources?    canGatherResources?     doGatherResources!  # 4
    needFirstWorker?        canFirstWorker?         doFirstWorker!      # 1
    needAttack?             canRush?                doLightRush!        # 5
```


---------


## Parsing

### Rules
```yaml
word = ""
type = regular
count = 0
loop:
    read char
    if char != 'eof':               # end of clauses
        if char != '\n':            # end of clause
            if char != '#':         # start of comment
                if char != " ":     # separator
                    word += char
                        if char != ":":
                            if char != "?":
                                if char != "!":
                                    continue
                                else:
                                    type = action
                                    add word
                            else:
                                type = query
                                add word
                        else:
                            type = container
                            add word
                else: # ' '
                    if word != "":
                        add word
                        type = regular
                        count + 1
                    else:
                        count + 1
                    if count == 4:

            else: # '#'
                type = comment
        else: # '\n'
            add clause
            type = regular
    else: # 'eof'
        add word
        type = regular
        break

add:
    addType
    addWord

addType:
    if type == comment:
        return
    if type == container:
        return
    if type == query:
        return
    if type == action:
        return
    if type == condition:
        return
    if type == modifier:
        return

addWord:
    if word in list:
        return
    else:
        return
```

### Cleaner Parsing
```yaml
baseFacts:
 - have:   lights
 - have:   workers
 - have:   bases
 - have:   barracks
 - afford: lights
 - afford: workers
 - afford: bases
 - afford: barracks
 - idle:   lights
 - idle:   workers
 - idle:   bases
 - idle:   barracks

derivedFacts:
    canRush:                idle barracks, afford light
    canFirstWorker:         idle bases, afford worker
    needFirstWorker:        have no workers
    canGatherResources:     idle worker, have base
    needGatherResources:    have resources
    canFirstBarracks:       idle worker, afford barracks, have base, have worker
    needFirstBarracks:      have no barracks
    canFirstBase:           idle worker, have worker, afford base
    needFirstBase:          have no base
    canAttack:              idle lights
    needAttack:             have enemy

rules:
 - needAttack?             canAttack?              doAttack!
 - needFirstBase?          canFirstBase?           doFirstBase!
 - needFirstBarracks?      canFirstBarracks?       doFirstBarracks!
 - needGatherResources?    canGatherResources?     doGatherResources!
 - needFirstWorker?        canFirstWorker?         doFirstWorker!
 - needAttack?             canRush?                doLightRush!
```


---------


## Considerations

 - How to mediate interactions between rules?
     - ie, two rules affect the same unit at the same time, which one do we choose?
 - Meditation Techniques:
     - Most Specific First (GOOD)
     - First Found (OK)
     - Last Found (OK)
     - Least Specific (BAD)
 - How to measure specificity?
     - How to compare different areas of specificity?
     - Which specificity do we prefer over other specifics?
         - ie, is it more important to _PickUpLeaves if RakeEquipped_ over _CleanPoolofFish if ToolEquipped_?
             - The _Rake_ is more specific than _Tool_, but _Cleaning the Pool of Fish_ is more specific than _Pick up Leaves_.
 - How do we measure and track importance of entrypoints and endpoints?
     - ie, _cleaning the yard_ as an overall goal,
         - with specific subgoals _remove fish_ and _remove leaves_
         - and specific means _rake_ and _tool_
 - Are the _means_ part of the _goal_?
     - ie, cannot _rake leaves_ without _rake_
         - hence, part of the goal is _finding a rake_.
         - _Any tool_ is easier than _any rake_.
 - So are the goals of equivalent importance?
     - Depends on the unions of the sets
         - How many tasks can be completed with a generic tool?
         - How many tasks require a rake?
             - What mechanisms do we have to rank and sort these things and assign weights?
 - Also, how to read and parse this stuff?
     - https://yaml-online-parser.appspot.com/
     - ick... might not be very clean...
     - https://stackoverflow.com/questions/22004160/extracting-name-value-pairs-from-json-with-java



---------


## New Format

### Draft
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

### Questions
 - How do we indicate negation?
   - If we only include true facts, do we need to explicitly define negates?
   - How do we indicate either is ok?
   - How do we indicate all must be false?

### Motivation
 - Why not YAML?
   - No time, and its still the exact same problem.... uuugggghhhhh so many classes
 - Why the name changes?
   - To be more readable. `afford` is so much easier to read than `hasEnoughResourcesFor...` -- just, don't do this...
 - Why is idle explicit?
   - So that rules are no longer bound to units.
   - This also makes it easier, as `idle` does not need to  be a special type anymore.
 - Why the spacing?
   - Again, readability. If I could, I would make this a `CSV` for even easier readability.

### TODO
 - Make Rules Dynamic (Add Conditions)
 - Revise whether or not a KB even needs to exist or if Rules should:
   - Be their own independent KB's
 - FIX RULE
 - FIX RULEPARSER
 - MAKE FILES
 - Fix how rules/knowledge is transferred - all we need to do on update is revalidate - then pass the KB around

### Data Layout
 - Facts contain Rules
   - These rules will be updated whenever a fact is inspected
   - This will become computationally infeasible with large enough conditions
   - Another reason why I hate objects
 - GAME
     - AGENT
         - INFERENCE ENGINE
             - RULES BASE
                 - RULE
                     - ARRAYLIST
                         - FACT
                         - FACT
                         - FACT
                 - RULE
                     - ARRAYLIST
                         - FACT
                         - FACT
                         - FACT
                 - RULE
                     - ARRAYLIST
                         - FACT
                         - FACT
                         - FACT
                 - RULE
                     - ARRAYLIST
                         - FACT
                         - FACT
                         - FACT

### Parser Behavior
 - Have `Loop` be a once run that is called by other functions
 - Have loop control defined by parse rules
 - Parse rules can call loop
 - Parse rules can call each other
 - parse rules hand off control to one another
 - Trick is not to run every rule ar once -- but to instead have rules for switching between rules


---------

