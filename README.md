# software-development-courseworkT

## Introduction

Hello, welcome to the README for Pebble Game...

This application allows users to simulate a game that involves 'players'
taking pebbles out of a bag. Each pebble has a given value and if the sum of
these values adds up to exactly 100, then that player wins.

Each player starts by taking 10 random pebbles out of a randomly selected bag,
after this they randomly discard a pebble into it's original bag's white bag
before selecting a random bag to take another random pebble from. If a bag is
empty, its corresponding white bag is emptied back into it.

The user can choose how many players play the game. It is advised that the user
chooses a smaller number of players for the most optimal experience. If more
players are selected than number of tokens in the bag divided by 11, then the
simulation may not work properly. Each player is represented as a different
thread so that the taking of pebbles from a bag is simultaneous and realistic.

The pebble weights are taken from a .csv file. There are 3 bags of pebbles.

## Prerequisites

Java needs to be installed on your device. The program was written using the IDE
IntelliJ (version 2021.2.3, build 212.5457.46) and JDK version 17.

You will need pebble weight values in a .csv file. The program comes with an
example file 'example_file_1.csv' to show the format of the file. Feel free to
make your own files when you use the application.

## Getting Started

To get started, simply run the PebbleGame.java file. Enter the number of players
and the location of each bag's .csv file, and then watch as the game is 'played'
before your very eyes!

## Testing

To run and test the code, use the supplied test suite (ENTER NAME HERE). 
This runs unit testing using JUnit4. 

## License

MIT License

Copyright (c) 2021 Cameron Stackhouse and Simon James Puttock

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
