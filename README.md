# Java Gelbooru.com Parser

## Features:

- Download images with ID and save tags to data.json
- Get the ID, tags, post link or visit post on Gelbooru.com
- Get and save tags with [key] and [ID] from post
- Get tags from post

# How to use:

***REQUIRES JAVA 21+***

1. Download latest build or compile it by yourself  
2. Execute it with args or without  
    * With args (recommended):  
    `java -jar Gelbooru.com_Parser-v1.4.jar [command]`  
    (NOTE: See the commands below or with "help" command)  

    * Without args (Enter command in runtime):  
    `java -jar Gelbooru.com_Parser-v1.4.jar`

# Commands
| Command             | Description                               | Params         |
|---------------------|-------------------------------------------|----------------|
| help                | Display the help menu                     | -              |
| get [id]            | Get tags from post                        | [id] - post id |
> im lazy to write it. the help command will explain all  


# CHANGELOG 

## v1.4

Fixed Issues:

- \>link gives API link
- \>visit doesnt work on linux
- data updates only after restart

New:

- added merge command [BETA]
- added conflict warning and flag to overwrite
- added clear src file command
- added >path command

Working on:

- \>merge command