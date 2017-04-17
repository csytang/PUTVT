# PUTVT
Python Unit Test Visualization Tool

This plugin is used for loading files with the logs of Pytest or Python failures/tracebacks and then visualizing them in the editor. This plugin also provides a Dependency Graph

Here are listed the actions, which will appear in the tools menu in IDE:

+ **Load and Visualize log .txt:** A windows will appear where if the file containing the logs is selected and then the Editor of the IDE is used for visualizing/highlighting the lines.
+ **Clear current View:** Clears the view for the current opened tab, after refreshing the visualization will be redrawn.
+ **Stop visualization:** Stops the visualization for all the files, keeping the settings, but not the so far visualized data.
+ **Visualize console logs:** Visualizes the logs from the internal IntelliJ console log. Logs are visualized, if any errors happened and are drawn when the editor tabs are opened/reopened.
+ **Listen for external jars:** Listens for external logs, which can be send through a UDP protocol on port 9876 as 1024 bit words. These are then visualized.

The visualization can be combined by will (for example - console logs + file logs).

Here are listed the actions which can be used on the Dependency Graph window.

+ **Visualize coverage:** Creates a dependency Graph visualizing the actual coverage for all the modules (python files) of the project. The nodes are differently colorized according to their code coverage.
+ **Reset view:** Resets the view of the visualized graph - changes the focus.
+ **Clean canvas:** Cleans the canvas and reoves the entire visualization
