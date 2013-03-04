
javac -d classes RGBToBlackAndWhite.java
javac -d classes ImageToRawData.java

javac -d classes constant.java
javac -d classes BoardStructure.java
javac -d classes BoardGui.java
javac -d classes CheckMove.java

javac -d classes Ghost.java
javac -d classes Pacman.java
javac -d classes Update.java
javac -d classes Controller.java
javac -d classes Main.java

java -classpath classes Main player0 player1 cpp java 0

pause
