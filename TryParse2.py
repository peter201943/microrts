

# Spacing
print()


# String
PJMstring = "Hello, my name is Peter. What's Yours?"
print("PJMstring    " + PJMstring)


# Chars
PJMchars = []
for char in PJMstring:
    PJMchars.append(char)
print("PJMchars     " + str(PJMchars))


# Spaces
PJMspaces = []
for char in PJMchars:
    if char == " ":
        PJMspaces.append(char)
print("PJMspaces    " + str(PJMspaces))


# Unspaces
PJMunspaces = []
for char in PJMchars:
    if char != " ":
        PJMunspaces.append(char)
print("PJMunspaces  " + str(PJMunspaces))


# Spaced
PJMspaced = []
PJMtemp = []
for char in PJMchars:
    if char != " ":
        PJMtemp.append(char)
    else:
        PJMspaced.append(PJMtemp)
        PJMtemp = []
PJMspaced.append(PJMtemp)
PJMtemp = []
print("PJMspaced    " + str(PJMspaced))


# Punctuation
PJMpunc = []
for char in PJMchars:
    if (char in ",.!?'"):
        PJMpunc.append(char)
print("PJMpunc      " + str(PJMpunc))


# Unpunctuated
PJMunpunc = []
for char in PJMchars:
    if (char not in ",.!?'"):
        PJMunpunc.append(char)
print("PJMunpunc    " + str(PJMunpunc))


# Puntuated
PJMpunced = []
PJMtemp = []
for char in PJMchars:
    if (char not in ",.!?'"):
        PJMtemp.append(char)
    else:
        PJMpunced.append(PJMtemp)
        PJMtemp = []
PJMpunced.append(PJMtemp)
PJMtemp = []
print("PJMpunced    " + str(PJMpunced))


# Punctuated Spaced
PJMpuncspaced = []
for word in PJMspaced:
    for 