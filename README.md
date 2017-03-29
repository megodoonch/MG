# MG
Minimalist Grammar in Java

## Quickstart: There are two places to run the program, and they have kind of stupid names right now.

* Main: Command-line interface to build via a numeration
* Test: Currently contains several examples, including a numeration-style derivation, merge/moveStep trees that return expressions, and actual derivation trees and their evaluations. There is also a run of the automaton.

## What is this?

This is my first crack at a string-generating minimalist grammar written in Java. 

## How to use it

### Numeration

You can build a numeration and merge and move its componenents in Main.java. There's a user interface and a small built-in grammar that you can add to. This is a bit awkward because you always have to choose things from a list by their index, whether you're taking something from the lexicon or applying an operation to members of the numeration.

The built-in grammar can be modified from the interface, but not fully yet. You can find the current grammar in `Main.GenerateMG` and modify it there if you like.

### DerivationTree

Derivation trees can be built and interpreted in Test.java.  There's no user interface yet, but there are a few examples built in.

## Some quirks

* Merge is always to the right, Move is always to the left
* Includes the capacity for 4 types of "move": overt (the default), covert, copy, and delete
  * This is contained in Polarity, where a polarity may have +/-combine and +/-store. 
  
|        | +combine | -combine |
|--------|----------|----------|
| +store | copy     | overt    |
| -store | covert   | delete   |
