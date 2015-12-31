# RPS-robot
Rock, paper &amp; scissors playing [Lego Mindstorms](https://mindstorms.lego.com) robot

The robot plays against a human player, using a webcam as it's eyes so that the human can play as he/she would against another human.

Image detection from the webcam is implemented using a [Perceptron](https://en.wikipedia.org/wiki/Perceptron) machine learning algorithm and one-vs-one technique for reducing the 3-dimensional (possible outcomes are rock, paper and scissor) problem to three seperate 2-dimensional problems.

The program saves all images taken during the game to add to it's hand image database, so that the more the program is played the better it will learn to identify which sign the human player played.

### Languages

The main running program and the Lego Mindstorms robot is implemented using Java. Lego robot has a [leJOS](http://www.lejos.org/) operating system running inside it and main program on computer runs commands to it using the LejOS interface. User interface uses [JafaFX](http://docs.oracle.com/javase/8/javase-clienttechnologies.htm).

Machine learning and statistical part of the program uses Python with [NumPy](http://www.numpy.org/) and [scikit-learn](http://scikit-learn.org/) tools.

### Installation

You should give execution rights to Python files for Java program to be able to run it, by running in the root folder:

```markdown
$ chmod +x MachineLearning/prophet.py
$ chmod +x MachineLearning/statistics.py
```

And for running tests:

```markdown
$ chmod +x MachineLearning/mocks/mprophet.py
$ chmod +x MachineLearning/mocks/mstatistics.py
```

Then make sure you have a webcam connected and run the program with [Gradle](http://gradle.org/) wrapper from **RPCRunner** folder:

```markdown
$ ./gradlew run
```

The orginal data set for hand sign recognition images is not yet available, but I will do it once I have collected more images. :) In the mean while you can create your own dataset by simply playing the game. You should start by creating at least one of each hand sign by running Labeler.java from data packet.

**The program is still under development. While the game can be played, it uses very simple random guessing for computers part. Also the Lego robot part is not yet functional and no building instructions for the robot will be available until the final design of the robot is ready.**
