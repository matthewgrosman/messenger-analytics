# messenger-analytics

## What does this project do?
This project ingests Facebook Messenger conversations and generates analytics.


## How does this project work?
Facebook lets you download all of your Facebook Messenger conversations in JSON format. This can be done by choosing JSON format and selecting only "Messages" to download at https://www.facebook.com/dyi/ .

This program takes the JSON generated by Facebook and goes through it to grab all important data. This includes going through every message and recording info such as sender name, conversation name, message contents, time sent, and more. All of the necessary data is stored nicely in an object and is written to a MongoDB database. 

After the MongoDB database is populated with all of the nececsary message data, the program can then generate analytics on that data. This includes information on how many messages are in a certain conversation, who are the most active participants in a group chat, when are chats the most active and more. I also plan to expand on the analytics in the future- mainly adding in some code to keep track of most used words.

<br></br>
## What tools does this project use?
The project uses MongoDB to store all of the neccesary Facebook Messenger message data for generating analytics. I took advantage of the MongoDB Java Driver to manage all MongoDB uses such as connecting, writing and reading from a local MongoDB database.

<br></br>
## Steps to use this project on your own Facebook Messenger data
1. Download your Facebook Messenger conversation data at: https://www.facebook.com/dyi/. When prompted, select JSON for the format and only "Messages" for the type of data you want to download.

2. Set up a local MongoDB instance to use for this project. I did this manually using MongoDB Compass (https://www.mongodb.com/try/download/compass). I just have this configured to receive traffic by sending requests to localhost, but feel free to change this to whatever IP you like. Create a new database on this instance with the title "messenger-data". It will prompt you to add a collection- add one with the name "messages-PROD".

3. Clone this repository to your machine.

4. Change the "MESSAGES_FOLDER" and "EXCEL_OUTPUT_FOLDER" variables in shared/Constants.java. The first variable "MESSAGES_DIRECTORY" points to the inbox directory in the download Facebook provided. The project expects that this is the directory given from this variable, so update it to whatever file path that is on your machine. The second variable, "EXCEL_OUTPUT_FOLDER" points to where you want analytic Excel files written to on your machine, this directory can be anywhere you like.

5. Run "ConversationParser.java", and once that is completed without any exceptions, run "Analytics.java". Either enter a conversation name (i.e. the name of an individual conversation like "John Smith", or a group chat like "My Group Chat", or simply leave the input blank and press enter to generate analytics aggregated across all conversations.).

6. Go to the Excel output file, and your Facebook Messenger analytics data is there!

I am currently in the process of making this application more user-friendly and easy to use, so in the future these steps will be shorter and easier to follow.
