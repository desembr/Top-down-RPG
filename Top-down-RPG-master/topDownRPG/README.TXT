BlueJ project "zuul-for-images".

This project extends the "World of Zuul" project in chapter 7
to add the ability to display images with the game.

This version  prepares the display of images by adding a GUI that is capable of
easily displaying an image, but it does not actually make use of that capability.

Compared to the "zuul-better" project discussed in the book, the following changes
were made:

 o The classes CommandWords, Command and Room are unchanged.

 o The Parser was changed not to read from System.in, but to process a String instead.

 o Most of the functionality of the Game class was moved to GameEngine.
 
 o A UserInterface class was added that provides a simple GUI. Students should
   look at the javadoc interface of this class to use it.
   
 o The new Game class simply creates a GameEngine and a UserInterface.

