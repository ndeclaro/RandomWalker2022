import sys
import math
import time
import numpy as np
from decimal import Decimal
from tkinter import *
from tkinter import ttk

#red scale https://www.color-hex.com/color-palette/1020534
#green scale https://www.color-hex.com/color-palette/1020538

def toPixel(x, y):
    x1 = x - 1
    y1 = y - 1
    x1 = 20 + (x1 * 100)
    y1 = 20 + (y1 * 100)
    return x1, y1

def colorPercentile(input, state):
    num = float(input)
    if(num < np.percentile(state, 15)):
        return "#ffffff"
    elif(num >= np.percentile(state, 15) and num < np.percentile(state, 33.3)):
        return "#7ab66f"
    elif(num >= np.percentile(state, 33.3) and num < np.percentile(state, 50)):
        return "#94c48b"
    elif(num >= np.percentile(state, 50) and num < np.percentile(state, 62.5)):
        return "#afd3a8"
    elif(num >= np.percentile(state, 62.5) and num < np.percentile(state, 75)):
        return "#d7e9d3"
    elif(num >= np.percentile(state, 75) and num < np.percentile(state, 82.5)):
        return "#f1f7f0"
    elif(num >= np.percentile(state, 82.5) and num < np.percentile(state, 90)):
        return "#f3cdd7"
    elif(num >= np.percentile(state, 90) and num < np.percentile(state, 95)):
        return "#e89cb0"
    elif(num >= np.percentile(state, 95) and num < np.percentile(state, 97)):
        return "#dc6b89"
    elif(num >= np.percentile(state, 97) and num < np.percentile(state, 99)):
        return "#d13a62"
    elif(num >= np.percentile(state, 99)):
        return "#c6093b"

#gather data--------------------------------
stringResult = []
with open("ResultC.txt", 'r') as file:
    for line in file:
        if line == "" or line == "Grid\n":
            continue
        arg = line.split(", ")
        stringResult.append(arg)
file.close()
inputState = []
states = {}
key = 0
for i in range(len(stringResult)):
    if(stringResult[i][0] == '\n'):
        #add to dictionary
        states[key] = inputState
        #reset input
        inputState = []
        key = key + 1
        continue
    inputRow = []
    for j in range(len(stringResult[i])):
        if(stringResult[i][j] == "\n"):
            inputState.append(inputRow)
            inputRow = []
            continue
        inputRow.append(float(stringResult[i][j]))

#making display---------------------
root = Tk()
root.geometry("800x800")
root.title('display')

#Top frame
frameTop = Frame(root, background = "red")
labelTop = Label(frameTop, text = "Grid Display").pack()
frameTop.pack(expand = True, fill = BOTH)

#Top display
displayTop = Canvas(frameTop)
displayTop.pack(fill = BOTH, expand = 1)

#scrollbars
yscrollbar = ttk.Scrollbar(displayTop, orient = VERTICAL, command = displayTop.yview)
yscrollbar.pack(side = RIGHT, fill = Y)
displayTop.configure(yscrollcommand = yscrollbar.set)
displayTop.bind('<Configure>', lambda e: displayTop.configure(scrollregion = displayTop.bbox("all")))

xscrollbar = ttk.Scrollbar(displayTop, orient = HORIZONTAL, command = displayTop.xview)
xscrollbar.pack(side = BOTTOM, fill = X)
displayTop.configure(xscrollcommand = xscrollbar.set)
displayTop.bind('<Configure>', lambda e: displayTop.configure(scrollregion = displayTop.bbox("all")))

#grid base creation -----
columns = 50
rows = 100
#grid end dimensions
gridx = (columns*100) + 20
gridy = (rows*100) + 20

#row drawing
for i in range(20, gridy + 100 , 100):
    displayTop.create_line(20, i, gridx, i, fill = "black")
    num = (i - 20)/100
    if(num < rows + 2):
        displayTop.create_text(10, 10 + i, text = int(num) + 1)
#column drawing
for i in range(20, gridx + 100 , 100):
    displayTop.create_line(i, 20, i, gridy, fill = "black")
    num = (i - 20)/100
    if(num < columns + 1):
            displayTop.create_text(10 + i, 10, text = int(num) + 1)
#diplaying states
show = 0
while(show != key):
    currentState = states[show]
    for i in range(100):
        for j in range(50):
            color = colorPercentile(currentState[i][j], currentState)
            fnum = '%.2E' % Decimal(currentState[i][j])
            col, row = toPixel(j + 1, i + 1)
            displayTop.create_rectangle(col, row, col + 100, row + 100, fill = color)
            displayTop.create_text(col + 50, row + 90, text = fnum)
            root.update()
    show = show + 1
    #time.sleep(.25)

root.mainloop()
