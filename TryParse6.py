
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

def lines(text):
    lines = text.split("\n")
    print()
    i = 0
    for line in lines:
        if i > 9:
            start = "["
        else:
            start = " ["
        print(start + str(i) + "] " + str(line))
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
    for word in words:
        if word == "=":
            print("assign(" + str(i) + "):")
            print("\tAssigning _" + words[i - 1] + "_ to _" + words[i + 1])
        i += 1


def condition(words):
    print()
    i = 0
    for word in words:
        if word == ":":
            print("condition(" + str(i) + "):")
            rest = ""
            j = i+1
            while j < len(words):
                rest += " _" + words[j] + "_"
                j += 1
            print("\tConditioning _" + words[i - 1] + "_ on _" + rest)
        i += 1


def comment(words):
    print()
    i = 0
    for word in words:
        if word == "#":
            print("comment(" + str(i) + "):")
            rest = ""
            j = i+1
            while j < len(words):
                rest += " _" + words[j] + "_"
                j += 1
            print("\tIgnoring _" + rest + "_")
        i += 1


def iterate(lines):
    i = 0
    for line in lines:
        print("\n\n\n\n")
        print("iterate(" + str(i) + "):")
        myWords = words(line)
        comment(myWords)
        assign(myWords)
        condition(myWords)
        i += 1


def main():
    iterate(lines(plainString))









if __name__ == "__main__":
    main()
