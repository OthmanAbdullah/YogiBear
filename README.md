# YogiBear Game
<h3><em>About the game:</em> </h3>
<strong><em>Yogi Bear</em></strong>  or <strong><em>Eating Bear</em></strong> is a cartoon character created by Hanna-Barbera Productions. He is a bear who is always trying to steal picnic<br> baskets from Jellystone Park visitors while evading Ranger Smith, the park ranger. There have been various video games based on the<br>Yogi Bear character.
<br><strong><em>Idea of the game:</em></strong> Yogi Bear wants to collect all the picnic baskets in the forest of the Yellowstone National Park. This park contains mountains and trees,that are obstacles for Yogi. Besided the obstacles, there are rangers, who make it harder for Yogi    to collect  the baskets. Rangers can<br>move only horizontally or vertically in the park. If a ranger gets too close (one unit distance) to Yogi, then Yogi loses one life. (It is up<br>to you to define the unit, but it should be at least that wide, as the sprite of Yogi.) If Yogi still has at least one life from the original <br>three, then he spawns at the entrance of the park. During the adventures of Yogi, the game counts the number of picnic baskets, that<br> Yogi collected. If all the baskets are collected, then load a new game level, or generate one. If Yogi loses all his lives, then show a popup messagebox, where the player can type his name and save it to the database.

<h3><em>Tools And Technologies used for the project::</em></h3>
<ul>
  <li>Java</li>
  <li>Java Swing</li>
  <li>MySql</li> 
  <li>netBeans</li> 
</ul>
<h3><em>Goal from the poject:</h3></em>
<ul>
<li>learn how to use mySql in java applications, how to create/connect to mysql database, 
doing CRUD operations on the locally created database, etc.</li>
<li>learn Java GUIs.</li>
</ul>

<h3><em>Building steps:</h3></em>
I used netBeans to implement this game, here are the steps to run the project on netBeans:
<ol>
  <li>Create your own mysql DataBase on your local machine</li>
  <li>Download the database connector jar and add it to the libraries in netbeans</li>
  <li>You will need to edit the following lines in the GameStaticVariables java file:<br><code>
    public static final int PORT = 3306; // Server port number 
    <br>public static final String USERNAME = "root"; // user name that can connect to the DB
    <br>public static final String PASSWORD = "password"; // your password for accessing the DB</code></li>
</ol>  

