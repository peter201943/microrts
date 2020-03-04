

"""TryParse -- simple experiment to try parsing text in Python"""


parseLog = ""


def main():
    
    testString = "Hello. I am Bob."
    parseOnce("", testString)
    doQuit()


def undefined(leftText, rightText):

    send("debug","undefined","undefined","running!")
    isEmpty(leftText, rightText)
    isHello(leftText, rightText)
    stupid = advance(leftText, rightText)
    parseOnce(stupid[0], stupid[1])


def advance(leftText, rightText):

    send("debug","advance","leftText,rightText","advancing!")
    leftText += rightText[0]
    rightText = rightText[1:]
    return (leftText, rightText)


def isEmpty(leftText, rightText):

    if rightText == "":
        send("debug","isEmpty","rightText","is Empty")
        doQuit()


def isHello(leftText, rightText):

    if leftText.lower() == "hello":
        send("debug","isHello","leftText","Has \"hello\"")
        doQuit()


def findHello(leftText, rightText):
    
    if leftText.lower() == "hello":

        send("debug","findHello","leftText","Has \"hello\"")
        parseOnce(leftText, rightText, findHello)
    
    else:

        parseOnce(leftText, rightText, findHello)


def findSpace(leftText, rightText):
    
    if leftText[-1] == " ":
    
        send("debug","findSpace","leftText","Has a \"space\"")
        parseOnce(leftText, rightText, findSpace)
    
    else:
    
        parseOnce(leftText, rightText, findSpace)


def parseOnce(leftText, rightText, rule=undefined):

    send("debug","parseOnce","leftText,rightText", leftText + " : " + rightText)
    rule(leftText, rightText)


def send(sender,location,subject,message):
    
    global parseLog
    if location == "parseOnce":
        parseLog += sender + "@" + location + ":" + subject + " >> " + message + "\n"
    else:
        print(sender + "@" + location + ":" + subject + " >> " + message)


def doQuit():
    send("debug","doQuit","program","quitting")
    print(parseLog)
    quit()


if __name__ == "__main__":

    main()