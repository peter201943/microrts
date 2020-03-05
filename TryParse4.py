




## Setup

# `plain`, `extra`

plain = True
extra = False
debug = True





## Class
class Plan:
    def __init__(self):
        self.name = ""
        self.action = None
        self.conditions = []
    def Add(self, condition):
        self.conditions.append(condition)
    def __str__(self):
        text = self.name + "\n\t" + self.action
        for condition in self.conditions:
            text += "\n\t\t" + condition
        return text + "\n"





## Plain String
# https://stackoverflow.com/questions/10660435/pythonic-way-to-create-a-long-multi-line-string

plainString = """
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
"""

print("\n\nplainString")
if plain:
    print(plainString)





## Into Lines

# https://stackoverflow.com/questions/12533955/string-split-on-new-line-tab-and-some-number-of-spaces

plainLines = []
plainLines = plainString.split("\n")

print("\n\nplainLines")
if extra:
    print(plainLines)
if plain:
    for line in plainLines:
        print(line)





## No Comments

codeLines = []
for line in plainLines:
    if len(line) > 0:
        if line[0] != "#":
            codeLines.append(line)

print("\n\ncodeLines")
if extra:
    print(codeLines)
if plain:
    for line in codeLines:
        print(line)





## Assignment Only

# https://stackoverflow.com/questions/5188792/how-to-check-a-string-for-specific-characters

assignments = []
for line in codeLines:
    if '=' in line:
        assignments.append(line)

print("\n\nassignments")
if extra:
    print(assignments)
if plain:
    for line in assignments:
        print(line)





## Conditioning Only

conditions = []
for line in codeLines:
    if ':' in line:
        conditions.append(line)

print("\n\nconditions")
if extra:
    print(conditions)
if plain:
    for line in conditions:
        print(line)






## Assignment Parsing

# https://stackoverflow.com/questions/5188792/how-to-check-a-string-for-specific-characters
# https://www.journaldev.com/23759/python-find-string-in-list
# https://stackoverflow.com/questions/1602934/check-if-a-given-key-already-exists-in-a-dictionary
# https://www.codevscolor.com/python-print-key-value-dictionary/

if debug:
    print("\n\nDebug")

classes = ["TrainWorker", "BuildBase", "Harvest", "TrainLight", "Attack", "BuildBarracks"]
symbols = {}
lastSeen = None
word = ""
for line in assignments:
    if len(word) > 0:
        if debug:
            print("LINE word " + word)
        if word in classes:
            if debug:
                print("Found " + word)
            symbols[lastSeen] = word
        elif word in symbols:
            lastSeen = word
            if debug:
                print("lastSeen " + word)
        elif word not in symbols:
            if debug:
                print("Adding " + word)
            symbols[word] = ""
            lastSeen = word
        else:
            print("ERROR")
    if debug:
        print("line " + line)
    lastSeen = None
    word = ""
    for char in line:
        if debug:
            print("char " + char)
        if char not in " =\n":
            word += char
        elif char == " ":
            if len(word) > 0:
                if debug:
                    print("CHAR word " + word)
                if word in classes:
                    if debug:
                        print("Found " + word)
                    symbols[lastSeen] = word
                elif word in symbols:
                    lastSeen = word
                    if debug:
                        print("lastSeen " + word)
                elif word not in symbols:
                    if debug:
                        print("Adding " + word)
                    symbols[word] = ""
                    lastSeen = word
                else:
                    print("ERROR")
                word = ""

print("\n\nassignmentParsing")
if extra:
    print(symbols)
if plain:
    for entry in symbols:
        print("Key: {}\n\tValue: {}".format(entry,symbols[entry]))





## Condition Parsing

conditionals = ["idle", "have", "afford"]
lastSeen = None
word = ""

for line in conditions:
    if debug:
        print("line " + line)
    if len(word) > 0:
        print("LINE word " + word)
    word = ""
    for char in line:
        if char not in " :\n":
            word += char
        elif char == " ":
            if len(word) > 0:
                if debug:
                    print("CHAR word " + word)
                if word in symbols:
                    lastSeen = word
                elif word in conditionals:
                    pass
                    #name = symbols[word]
#                    symbols[]

print("\n\nconditionsParsing")
if extra:
    print(symbols)
"""
if plain:
    for entry in symbols:
        print("Key: {}\n\tValue: {}".format(entry,symbols[entry]))
        for subentry in entry:
            print("\t\tSub: {}".format(entry[subentry]))
"""


