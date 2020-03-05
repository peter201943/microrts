




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
doAttack        :   idle Light"
"""

print("\n\nplainString")
print(plainString)





## Into Lines

# https://stackoverflow.com/questions/12533955/string-split-on-new-line-tab-and-some-number-of-spaces

plainLines = []
plainLines = plainString.split("\n")

print("\n\nplainLines")
print(plainLines)
for line in plainLines:
    print(line)





## No Comments

codeLines = []
for line in plainLines:
    if len(line) > 0:
        if line[0] != "#":
            codeLines.append(line)

print("\n\ncodeLines")
print(codeLines)
for line in codeLines:
    print(line)





## Assignment Only

# https://stackoverflow.com/questions/5188792/how-to-check-a-string-for-specific-characters

assignments = []
for line in codeLines:
    if '=' in line:
        assignments.append(line)

print("\n\nassignments")
print(assignments)
for line in assignments:
    print(line)





## Conditioning Only

conditions = []
for line in codeLines:
    if ':' in line:
        conditions.append(line)

print("\n\nconditions")
print(conditions)
for line in conditions:
    print(line)






## Assignment Parsing

# https://stackoverflow.com/questions/5188792/how-to-check-a-string-for-specific-characters
# https://www.journaldev.com/23759/python-find-string-in-list
# https://stackoverflow.com/questions/1602934/check-if-a-given-key-already-exists-in-a-dictionary
# https://www.codevscolor.com/python-print-key-value-dictionary/

print("\n\n")

classes = ["TrainWorker", "BuildBase", "Harvest", "TrainLight", "Attack", "BuildBarracks"]
symbols = {}
lastSeen = None
word = ""
for line in assignments:
    lastSeen = None
    word = ""
    for char in line:
        if char != " ":
            word += char
        elif char == " ":
            if len(word) > 0:
                if word in classes:
                    print("Found " + word)
                    symbols[lastSeen] = word
                elif word in symbols:
                    lastSeen = word
                elif word not in symbols:
                    print("Adding " + word)
                    symbols[word] = ""
                    lastSeen = word
                else:
                    print("ERROR")
                word = ""

print("\n\nassignmentParsing")
print(symbols)
for entry in symbols:
    print("Key: {}\t\tValue: {}".format(entry,symbols[entry]))
