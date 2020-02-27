

## CS 387: Game AI


---------


# Notes


## About
 - **Student**
     - Peter J. Mangelsdorf
     - pjm349
 - **Professor**
     - Ehsan Khosroshahi
     - eb452
 - **Contents**
     - [About](#about)          -- What's here
     - [Links](#links)          -- What else is here
     - [Learning](#learning)    -- How I got started
     - [Language](#language)    -- What rules look like


## Links
 - [README](README.md)      -- Anything I did
 - [Notes](Notes.md)        -- Anything I thought


---------


## Learning

### Very helpful Video
[Creating a bot for microRTS](https://www.youtube.com/watch?v=BikqC5gZWhU)

### My Analysis of the Project
![Project Structure](ProjectAnalysis.png)

### My Initial Agent Design
![Agent Design](AgentDesign.png)

### `Light Rush`
[`Light Rush`](src/ai/abstraction/LightRush.java)


---------


## Language

### Simple Example
```yaml
# Rule set:
# If we have any "light": send it to attack to the nearest enemy unit
# If we have a base: train worker until we have 1 workers
# If we have a barracks: train light
# If we have a worker: do this if needed: build base, build barracks, harvest resources

# Notes:
# - Actions are of the form doXXX(UnitType)
#
# Actions that can be performed:
# - doTrainWorker: trains a worker
# - doTrainLight: trains a light unit
# - doBuildBase: builds a base in a nearby position
# - doBuildBarracks: builds a barracks in a nearby position
# - doHarvest: sends a worker to harvest resources from a resource mine
# - doAttack: attacks a nearby enemy
#
# Predefined predicates that need to be implemented:
# - idle: whether the unit we are considering is idle
# - own(UnitType): whether we own any unit of the specified type
# - enoughResourcesFor(UnitType): whether we have enough resources for the specified unit type
# - ~ means negation
#
# The idea here is:
# - For each of the units we own try to execute the rules that match the unit type of the unit
# - If any rule gets triggered, make the unit perform the action corresponding to the rule head

doTrainWorker("Base") :- idle, own("Base"),~own("Worker"),enoughResourcesFor("Worker").
doBuildBase("Worker") :- idle, own("Worker"),~own("Base"),enoughResourcesFor("Base").
doBuildBarracks("Worker") :- idle, own("Worker"),own("Base"),~own("Barracks"),enoughResourcesFor("Barracks").
doHarvest("Worker") :- idle, own("Base").
doTrainLight("Barracks") :- idle, enoughResourcesFor("Light").
doAttack("Light") :- idle.
```

### Complex Example
```yaml
# Rule set:
# If we have any "light": send it to attack to the nearest enemy unit
# If we have a base: train worker until we have 1 workers
# If we have a barracks: train light
# If we have a worker: do this if needed: build base, build barracks, harvest resources

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
# - doTrainWorker: trains a worker
# - doTrainLight: trains a light unit
# - doBuildBase: builds a base in a nearby position
# - doBuildBarracks: builds a barracks in a nearby position
# - doHarvest: sends a worker to harvest resources from a resource mine
# - doAttack: attacks a nearby enemy
#
# Predefined predicates that need to be implemented:
# - idle(ID): whether the unit specified by ID is idle (if ID is unbound, ID will be bound to each of the idle units, one at a time, Prolog-style)
# - own(ID): whether we own the unit with the specified ID  (if ID is unbound, ID will be bound to each of the units we own, one at a time)
# - enemy(ID): same as "own(ID)", but for enemy units.
# - resourcesAvailable(X): binds X to the number of resources we have
# - resourcesNeededFor(UnitType,Y): binds Y to the number of resources needed to train UnitType
# - type(X,UnitType): true if the unit type of X is UnitType
# - ~ means negation
#
# The idea here is:
# - At each game frame, check one rule at a time, and for each rule which is satisfied, send the corresponding command to the corresponding units.

# auxiliary predicates:
ownBase(X) :- type(X,"Base"),own(X).
ownWorker(X) :- type(X,"Worker"),own(X).
ownBarrack(X) :- type(X,"Barracks"),own(X).
workerNeeded() :- ~ownWorker(X).

# baseBehavior
doTrainWorker(Z) :- workerNeeded(),resourcesAvailable(X),resourcesNeededFor("Worker",Y),X>Y,ownBase(Z),idle(Z).

# workersBehavior
idleWorker(X) :- type(X,"Worker"),own(X),idle(X).
doBuildBase(Z) :- ~ownBase(X),resourcesAvailable(X),resourcesNeededFor("Base",Y),X>Y,idleWorker(Z).
doBuildBarracks(Z) :- ~ownBarrack(X),resourcesAvailable(X),resourcesNeededFor("Barracks",Y),X>Y,idleWorker(Z).
doHarvest(X,Y,Z) :- own(X),idleWorker(X),type(Y,"Resource"),ownBase(Z).

# barracksBehavior
doTrainLight(Z) :- resourcesAvailable(X),resourcesNeededFor("Light",Y),X>Y,ownBarrack(Z),idle(Z).

# meleeUnitBehavior
idleLight(X) :- type(X,"Light"),own(X),idle(X).
doAttack(X,Y) :- own(X),idleLight(X),enemy(Y),type(Y,"Light").
doAttack(X,Y) :- own(X),idleLight(X),enemy(Y),~type(Y,"Resource").
```

### Representing Attacks
```yaml
doAttack("Light") :- idle.
name ("target") :- condition .
RuleName accepts "TargetName" PredicatedOn Condition EndOfLine 
```
