# assignment3

assignment3 is designed to implement Turtle Graphics.
Quil is used for rendering.

## Usage

LightTable - open `core.clj` and press `Ctrl+Shift+Enter` to evaluate the file.

Emacs - run cider, open `core.clj` and press `C-c C-k` to evaluate the file.

REPL - run `(require 'assignment3.core)`.

The function defsketch is invoked to start the program.

The program reads from a text file named turtle-commands.txt located in the turtle directory.


## Turtle Commands

When running a Turtle program the result of the turtle’s pen is shown in a window.
A turtle program is just a list of turtle commands.

Turtle commands in the file are of the following format:

turn
move
pen up
pen down


Ex:
    turn 90
    move 10
    pen up
    pen down


1) turn :

The turn operation requires an amount to turn.
This operation turns the direction of the turtle the given amount in degrees.
It accepts both positive and negative angle values.
The given angle is converted into radians before execution.

Ex:
    turn 90
    turn -45

2) move :

The move operation requires an amount to move.
This operation moves the turtle given amount in the current direction.
It accepts both negative and positive length values.
Negative length values move the turtle backwards.

Ex:
    move 100
    move -50

3) pen:

The pen operation takes in either of these 2 values : up or down
pen up   - when the pen is up and the turtle moves, nothing is drawn.
pen down - when the pen is down and the turtle moves a black line is drawn.

Ex:
    pen up
    pen down

## Program Modes and Traversal

Modes:

1) Step-mode

A turtle program starts in the step mode.
In the step mode the user can step the program forward one operation by pressing the forward arrow key. The user can undo the last operation by pressing the back arrow key.
The user can undo back to the start of the turtle program.
Each time the program executes an operation it prints the operation in the display window.

2) Run-mode

Pressing the “r” key changes to the run mode.
In the run mode the turtle program is executed without the user having to press the arrow keys.
When the turtle program comes to the end we switch back to step mode so the user can step the program backwards.


## License

Copyright © 2014 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
