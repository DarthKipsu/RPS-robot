# RPS-robot
Rock, paper &amp; scissors playing [Lego Mindstorms](https://mindstorms.lego.com) robot

The robot plays against a human player, using a webcam as it's eyes so that the human can play as he/she would against another human.

Image detection from the webcam is implemented using a [Perceptron](https://en.wikipedia.org/wiki/Perceptron) machine learning algorithm and one-vs-one technique for reducing the 3-dimensional (possible outcomes are rock, paper and scissor) problem to three seperate 2-dimensional problems.

The program saves all images taken during the game to add to it's hand image database, so that the more the program is played the better it will learn to identify which sign the human player played.

#### Play example

[![Play example](http://img.youtube.com/vi/TqbpJkDx-Y8/0.jpg)](http://www.youtube.com/watch?v=TqbpJkDx-Y8)

### Languages

The main running program and the Lego Mindstorms robot is implemented using Java. Lego robot has a [leJOS](http://www.lejos.org/) operating system running inside it and main program on computer runs commands to it using the LejOS interface. User interface uses [JafaFX](http://docs.oracle.com/javase/8/javase-clienttechnologies.htm).

Machine learning and statistical part of the program uses Python with [NumPy](http://www.numpy.org/) and [scikit-learn](http://scikit-learn.org/) tools.

### Installation without robotic hand

The program runs without problems even if no Lego robot is used. You simply need to imagine you would have a player against you and place the webcam you are using to face some dark background. You can read what the AI played from the hand sign recognition window.

First you should give execution rights to Python files for Java program to be able to run it, by running in the root folder:

```markdown
$ chmod +x MachineLearning/prophet.py
$ chmod +x MachineLearning/statistics.py
```

Also if you want to run tests:

```markdown
$ chmod +x MachineLearning/mocks/mprophet.py
$ chmod +x MachineLearning/mocks/mstatistics.py
```

Then make sure you have a webcam connected and run the program with [Gradle](http://gradle.org/) wrapper from **RPCRunner** folder:

```markdown
$ ./gradlew run
```

The orginal data set for hand sign recognition images is not yet available, but I will do it once I have collected more images. :) In the mean while you can create your own dataset by simply playing the game. You should start by creating at least one of each hand sign by running Labeler.java from data packet.

### Installation with robotic hand

Build a robotic hand with Lego Mindstorms set. ![For example like this.](/robot-building.md)

Install the main program as described above, but do not run it yet. The program will automatically detect wether or not a Lego robot is available. *The RPC program is for NXT brick and if you want to use EV3 brick, you need to make some changes to the RPC program to use EV3 motors instead of NXT motors, but other than that it should work similarly.*

These instructions are for Linux operating system:

1. Install virtualbox from your linux versions packet management.
2. [Download rojbos-4g-usb-image.img.gz from here.](http://www.cs.helsinki.fi/u/strommer/rojbos/) This is a Linux distro built by Jouko Strömmer with everything ready for programming with LejOs.
3. Change the RojbOS image to a form VirtualBox can read: `VBoxManage convertfromraw --format VDI [filename].img [filename].vdi`
4. Add a new computer to VirtualBox using a 32-bit Linux and the vdi file just created.
5. Add a shared folder from the computer settings > Shared folders > Add shared folder with folder name RPCConnect and path to wherever your RPCConnect folder is located. The shared folder should have full access (no read-only).
5. Do the same for folder name RPC.
6. Start the newly created computer and select RojbOS from the opening screen.
7. Flaxh LejOs operating system to your NXT brick by connecting it to the computer with USB and select Applications > leJOS > leJOS Tools > leJOS NXT Flasher GUI > Start program.
8. Onse LejOs is setup in the brick, make sure you have bluetooth connection to your NXT.
9. Open terminal and create folders for both projects: `mkdir /opt/lejos-workspace/RPC` and `mkdir /opt/lejos-workspace/RPCConnect`
10. Then connect those folder to the folders on your own computer: `sudo mount -t vboxsf -o uid=$UID,gid=$(id -g) RPCConnect /opt/lejos-workspace/RPCConnect` and `sudo mount -t vboxsf -o uid=$UID,gid=$(id -g) RPC /opt/lejos-workspace/RPC`
11. Open Eclipse from the desktop and import the two projects just linked.
12. Make sure NXT is on and run RPC project as Ant build. The program is transferred to NXT with bluetooth and started.
13. Once you see `waiting...` on the screen of your NXT, run the RPCConnector project (as normal Java Application)
14. Start the main program on your own computer as described in the installation without robotic hand.

The next time you run the program, you can simply select the program from your NXT, once it's running, run RPCConnector from VirtualBox computer and finally run the main program from your own computer.

**The program is still under development. While the game can be played, it uses very simple random guessing for computers part.**
