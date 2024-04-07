# My Personal Project

## Code Defenders

The project that I will create will be a game called code defenders. There will be snippets of Java (maybe other languages that can be selected by the user) that will "rain down" from the top of the screen. The user commands a type of laser gun at the bottom of the screen, and is able to "load" it by typing snippets of code on the screen. The laser will fire at the code typed out by the user and eliminate it. Any snippets that make it to the bottom of the screen will "damage" the user. When the user runs out of health the game will be over.

The game will also have some "powerups" the user can get by eliminating certain powerup snippets. Some examples of powerups are:
- *time freeze*
- *slow mode*
- *clear board*
- *one character only* (the user can eliminate a snippet by typing only the first character)

As the user progresses, they will reach checkpoints, and the game will get faster after each one. Incorrectly typed snippets with be presented to the user at the end of the game to allow them to see areas of potential improvement.


I hope that my project will be useful to those taking 210 or learning Java in the future, especially people like me that have never used the language before and could use some practice with syntax.

This project is of interest to me as I want to get better at typing, and some extra practice with Java syntax also wouldn't hurt.

## User Stories

- As a user, I would like to be able to have an arbitrary number of code snippets rain down, so I can continuously engage with the game.
- As a user, I would like to be able to see a list of incorrectly typed words at the end of the game to see where I can improve.
- As a user, I would like to see the game difficulty progress as I go.
- As a user, I would like to be able to save checkpoints at my choosing.
- As a user, I would like to be able to load from a previous save
- As a user, I would like to see power-ups that adds a layer of strategy and user engagement.

## Instructions for Grader
- You can generate the first user story by typing one of the words and removing them
- You can generate the second user story by typing a word that is red and removing all X's from the Y
- You can locate my visual component by looking at any of the words on the screen
- You can save the state of my application by pressing the "save" button at the top of the screen
- You can reload the state of my application by pressing the "load" button at the top of the screen

## Phase 4: Task 2
Sample console output: \
Sat Apr 06 20:47:29 PDT 2024 \
Killed snippet: list.add(1); \
Sat Apr 06 20:47:35 PDT 2024 \
Killed snippet: list.remove(0); \
Sat Apr 06 20:47:38 PDT 2024 \
Froze all snippets! \
Sat Apr 06 20:47:38 PDT 2024 \
Killed snippet: n/=3; \
Sat Apr 06 20:47:47 PDT 2024 \
Cleared all snippets!\

## Phase 4: Task 3
Given the design in of my project in my UML diagram, I definitely think I could have followed object-orientated design practices a little better.
A particularly bad class is my Game class, which certainty violates the Single Responsibility Principal. That single class is responsible for managing all aspects of the game.
A major refactoring step I would take would be to create a "CodeSnippetManager" class that would take a lot of the responsibility away from the game class. I would also have my Game,
Player, and CodeSnippet class extend a new interface "Writable" which would have a toJson() method. Many of my methods were also very long and hard to read. I would re-factor many of the longer
methods with private helpers to improve readability. The fact that not a single class extends or implements anything (Other than built in Java classes)
is certainly a red flag that my design was not very well-thought-out. I am ok with this however, as this was my first experience with Java and Object Orientation and this project was a learning experience.

