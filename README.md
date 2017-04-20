# Battle of Dorvan V
A Star Trek 3D FPS written in Java for foundations CompSci
No game engines or frameworks are used, all rendering is from scratch using raycasting.

<<<Currently in development, don't expect anything to work>>>

Feel free to fork, but we will not be accepting merge requests, as it is a school project. (at least, not until we've turned in the project)

UML DIAGRAM:

   -----------
  |           |          -----------          -----------
  |           |         |           |        |           |
  |           | has a   |           |        |           |
  |   DRIVER  |---------|           | has a  |           |
  |           |         |    SCENE  |--------|   NPC     |
  |           |         |           |        |           |
   -----------          |           |        |           |
                         -----------\         -----------
                              |      \     //handles all npcs
                              |       \
                              | has a  \
                              |         \
                         -----------     \
                        |           |     \
                        |           |      \-----------
                        |           |      |           |
                        |    SAVE   |      |           |        
                        |           |      |           |         
                        |           |      |           |          
                         -----------       |   MAP     |
                     //loads and saves     |           |
                       player data         |           |
                                            -----------
                                      //loads and runs the map
