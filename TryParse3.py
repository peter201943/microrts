

def PJMstring(string):
    return string


def PJMchars(string):
    chars = []
    for char in string:
        chars.append(char)
    return chars


def PJMspaces(chars):
    spaces = []
    for char in chars:
        if char == " ":
            spaces.append(char)
    return spaces


def PJMunspaces(chars):
    unspaces = []
    for char in chars:
        if char != " ":
            unspaces.append(char)
    return unspaces


def PJMspaced(chars):
    spaced = []
    temp = []
    for char in chars:
        if char != " ":
            temp.append(char)
        else:
            spaced.append(temp)
            temp = []
    spaced.append(temp)
    temp = []
    return spaced






# Elemental, needed
def str_to_lst(aString):   # ['a','b','c']...
    chars = []
    for aChar in aString:
        chars.append(aChar)
    return chars

# How to detect if argument is a list of lists and call again?
def lst_to_lstlst(aList):     # [['a'] [['b'][['c']]]]...
    pass


def lst_to_str(aThing):
    mystr = ""
    if isinstance(aThing, list):
        mystr += "["
        for thing in aThing:
            mystr += lst_to_str(thing)
            mystr += " "
        mystr += "]"
        return mystr
    else:
        return str(aThing)



myList = [["a","b","c"], [1,2,3], ["a3","2b","1c"]]

print(myList)
print(lst_to_str(myList))





































"""
def monitor(function, argument):
    print(str(function) + " " + str(argument))
    result = function(argument)
    print(result)
    print()
    return result


def main():
    print()
    aString = "Hello, my name is Peter. What's Yours?"
    monitor(PJMstring, aString)
    aChars = monitor(PJMchars, aString)
    monitor(PJMspaces, aChars)
    monitor(PJMunspaces, aChars)
    monitor(PJMspaced, aChars)


if __name__ == "__main__":
    main()
"""