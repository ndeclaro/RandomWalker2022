import sys
import math
import time
from tkinter import *
from tkinter import ttk

#coords to pixels
def toPixel(x, y):
    x1 = x - 1
    y1 = y - 1
    x1 = 20 + (x1 * 100)
    y1 = 20 + (y1 * 100)
    return x1, y1

#reading data
with open("ResultB.txt", 'r') as file:
    line = file.readline()
    arg = line.split()
    rows = int(arg[0])
    columns = int(arg[1])
    gridData = [[0 for i in range(50)] for j in range(100)]
    
    #reading grid data
    for line in file:
        arg = line.split()
        row = int(arg[0]) - 1
        col = int(arg[1]) - 1
        cellType = arg[2]
        gridData[row][col] = cellType
        if(int(arg[0]) == rows and int(arg[1]) == columns):
            break #exit grid data
        
    #reading path data
    file.readline()
    file.readline()
    line = file.readline()
    arg = line.split(",")
    startRow = int(arg[0])
    startCol = int(arg[1])
    path = sys.argv[1]
    for line in file:
        if line == path + "\n":
            actionsInput = file.readline().split()
            pathInput = file.readline().split()
            sensorInput = file.readline().split()
file.close()

#making display----------------------------------------------------------
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

#Bottom Frame
frameBot = Frame(root, background = "green")
LabelBot = Label(frameBot, text = "Data").pack()
frameBot.pack(expand = True, fill = BOTH)

#Bottom Display
displayBot = Canvas(frameBot, height= 200)
displayBot.pack(fill = BOTH, expand = 1)

#scrollbars
yscrollbar = ttk.Scrollbar(displayBot, orient = VERTICAL, command = displayBot.yview)
yscrollbar.pack(side = RIGHT, fill = Y)
displayBot.configure(yscrollcommand = yscrollbar.set)
displayBot.bind('<Configure>', lambda e: displayBot.configure(scrollregion = displayBot.bbox("all")))

#grid end dimensions
gridx = (columns*100) + 20
gridy = (rows*100) + 20

#row drawing
for i in range(20, gridy + 100 , 100):
    displayTop.create_line(20, i, gridx, i, fill = "black")
    num = (i - 20)/100
    if(num < row + 2):
        displayTop.create_text(10, 10 + i, text = int(num) + 1)
#column drawing
for i in range(20, gridx + 100 , 100):
    displayTop.create_line(i, 20, i, gridy, fill = "black")
    num = (i - 20)/100
    if(num < columns + 1):
            displayTop.create_text(10 + i, 10, text = int(num) + 1)

#CellType
for row in range(100):
    for col in range(50):
        input = gridData[row][col]
        x, y = toPixel(col + 1, row + 1)
        displayTop.create_text(x + 10, y + 10, text = input)
     
#y coordinates
for i in range (100, gridy, 100):
    num = i/100 + 1
    displayTop.create_text(110, i + 10, text = int(num))
    for j in range(100, gridx, 100):
        displayTop.create_text(j + 10, i + 10, text = int(num))

#x coordinates
for i in range(100, gridx, 100):
    num = i/100 + 1
    displayTop.create_text(i + 30, 110, text = int(num))
    for j in range(100, gridy, 100):
        displayTop.create_text(i + 30, j + 10, text = int(num))
        num = i/100 + 1

#solution trace/data display-----------------------------
startx, starty = toPixel(startCol, startRow)
startx = startx + 50
starty = starty + 50
displayTop.create_oval(startx - 5, starty - 5, startx + 5, starty + 5, fill = "green")
displayTop.create_text(startx - 20, starty + 10, text = 'start')

#trace
for i in range(100):
    arg = pathInput[i].split(",")
    row = int(arg[0])
    col = int(arg[1])
    x, y = toPixel(col, row)
    xend = x + 50
    yend = y + 50
    displayTop.create_line(startx, starty, xend, yend, fill = "red")
    startx = xend
    starty = yend
displayTop.create_oval(xend - 5, yend - 5, xend + 5, yend + 5, fill = "red")
displayTop.create_text(xend - 20, yend - 10, text = 'end')

#data display
start = str(startRow) + ", " + str(startCol)
displayBot.create_text(10, 10, text = "Initial Point: " + start, font = ("Arial", 12))
startDisplayPoint = 60
for i in range(100):
    actionDisplay = "Action " +str(i + 1) + ": " + actionsInput[i] + "\n"
    pathDisplay = "Point: " + pathInput[i] + "\n"
    sensorDisplay = "Sensor: " + sensorInput[i] + "\n"
    textDisplay = actionDisplay + pathDisplay + sensorDisplay
    displayBot.create_text(10, startDisplayPoint, text = textDisplay, font = ("Arial", 12))
    startDisplayPoint = startDisplayPoint + 60
    
root.mainloop()