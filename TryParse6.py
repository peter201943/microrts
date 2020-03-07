
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


def TrainWorker():
    print("action():\n\tBASE: Training Worker\n")

def TrainLight():
    print("action():\n\tBARRACKS: Training Light\n")

def BuildBase():
    print("action():\n\tWORKER: Building Base\n")

def Attack():
    print("action():\n\tLIGHT: Attacking Enemy\n")

def Harvest():
    print("action():\n\tWORKER: Harvesting Resources\n")

def BuildBarracks():
    print("action():\n\tWORKER: Building Barracks\n")

def Idle(unit):
    return True

def Have(unit):
    return True

def Not(condition):
    return True

def Afford(unit):
    return True

class Agent:
    def __init__(self):
        self.name = ""
        self.rules = {
            "doTrainWorker" : {
                "action" : "TrainWorker", 
                "condition" : {
                    "Idle": "Base",
                    "Have": "Base",
                    "Not": {"Have": "Worker"},
                    "Afford": "Worker"
                    }
                }
            }
    def __str__(self):
        text = self.name + "():\n"
        for rule in self.rules:
            text += "\t" + rule + "\n"
            for entry in self.rules[rule]:
                text += "\t\t" + entry + "\n"
                if entry is dict:
                    for subentry in entry:
                        text += "\t\t\t" + subentry + "\n"
                        text += "\t\t\t\t" + entry[subentry] + "\n"
        return text


def lines(text):
    lines = text.split("\n")
    print()
    i = 0

    print("lines(0):")
    for line in lines:
        if i > 9:
            start = "["
        else:
            start = " ["
        print("\t" + start + str(i) + "] " + str(line))
        i += 1

    return lines


def words(line):
    print()
    i = 0
    words = []
    word = ""

    for char in line:
        if char == " ":
            if len(word) > 0:
                words.append(word)
            word = ""
        else:
            word += char
    if len(word) > 0:
        words.append(word)

    print("words(" + str(i) + "):")
    for word in words:
        print("\t_" + word + "_")

    return words



def assign(words):
    print()
    i = 0
    assignments = []

    for word in words:
        if word == "=":
            print("assign(" + str(i) + "):")
            assignment = "\tAssigning _" + words[i - 1] + "_ to _" + words[i + 1]
            assignments.append(assignment)
            print(assignment)
        i += 1
    
    return assignments


def condition(words):
    print()
    i = 0
    conditions = []

    for word in words:
        if word == ":":
            print("condition(" + str(i) + "):")
            rest = ""
            j = i+1
            while j < len(words):
                rest += "\n\t\t_" + words[j] + "_"
                j += 1
            condition = "\tConditioning _" + words[i - 1] + "_ on " + rest
            conditions.append(condition)
            print(condition)
        i += 1
    
    return conditions


def comment(words):
    print()
    i = 0
    comments = []

    for word in words:
        if word == "#":
            print("comment(" + str(i) + "):")
            rest = ""
            j = i+1
            while j < len(words):
                rest += " _" + words[j] + "_"
                j += 1
            comm = "\tIgnoring " + rest
            comments.append(comm)
            print(comm)
        i += 1
    
    return comments


def rules(lines):
    i = 0
    results = []

    for line in lines:
        print("\n\n\n\n")
        print("rules(" + str(i) + "):")
        myWords = words(line)
        results.append(comment(myWords))
        results.append(assign(myWords))
        results.append(condition(myWords))
        i += 1
    
    return results


def main():
    print()
    TrainWorker()
    TrainLight()
    BuildBase()
    Attack()
    Harvest()
    BuildBarracks()
    print()
    print("main(0):\n\t" + str(rules(lines(plainString))))
    print()
    testAgent = Agent()
    testAgent.name = "Test"
    print(testAgent)



if __name__ == "__main__":
    main()
