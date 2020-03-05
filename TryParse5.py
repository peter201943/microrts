

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


symbols = {}
temp = {}


def readUntil(stop, string):
    results = ""
    for char in string:
        if char not in stop:
            results += char
        else:
            print("readUntil @OK stop found")
            return results
    print("readUntil @ERROR no stop found")
    return results


def specific(strings, end):
    if len(strings[0]) == 0:
        return strings
    for char in strings[0]:
        if char == end:
            strings[0] = strings[0][1:]
            return strings
        else:
            strings[1] += strings[0][0]
            strings[0] = strings[0][1:]


def newLine(strings):
    return specific(strings, "\n")


def spaces(strings):
    return specific(strings, " ")
        

def parseAll(aString):
    results = [plainString, ""]
    old = []
    count = 0
    while (old != results):
        count += 1
        print("\n\n\n" + "(" + str(count) + ")")
        old = results.copy()
        results = newLine(results)
        print("0\n\"\"\"\n" + str(results[0]) + "\"\"\"\n1\n\"\"\"\n" + str(results[1]) + "\n\"\"\"")


parseAll(plainString)


# https://stackoverflow.com/questions/2612802/how-to-clone-or-copy-a-list


