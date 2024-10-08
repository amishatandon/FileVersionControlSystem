# File Version Control System

## Overview

This is a simple File Version Control System implemented in Java. It allows users to manage files with features like adding, committing, logging changes, checking out previous versions, viewing the current status, and comparing file differences between commits. 

## Features

- **Add Files**: Stage new or modified files for commit.
- **Remove Files**: Mark files for deletion.
- **Commit Changes**: Save staged changes with a descriptive message.
- **Log Commits**: View the history of all commits.
- **Checkout Commits**: Revert to a specific commit.
- **Status Check**: Display the current working files and staged changes.
- **Diff**: Compare differences between two commits.

## Technologies Used

- Java
- Serialization for data persistence

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- An IDE or text editor (e.g., Visual Studio Code, IntelliJ IDEA)

### Installation

   Clone the repository:
   git clone https://github.com/yourusername/file-version-control-system.git
   
   cd file-version-control-system
   javac *.java
   java FileVersionControlSystem

   Commands: add, remove, commit, log, checkout, status, diff, exit
   Enter command: add
   Enter file name: example.txt
   Enter file content: This is my file.

   Enter command: commit
   Enter commit message: Initial commit

   Enter command: log

   ## File Storage
   The system persists the version control data in a binary file named version_control_data.ser. This file is created in the same directory as the application and stores the commit history and current file states.

  ## Contributing
  If you wish to contribute to this project, please fork the repository and submit a pull request.



<img width="911" alt="1" src="https://github.com/user-attachments/assets/b631fd66-1051-430e-ae8d-68c66361772a">

<img width="682" alt="2" src="https://github.com/user-attachments/assets/1bb086c9-0268-4a3a-a773-a01c4b6f922d">

<img width="755" alt="3" src="https://github.com/user-attachments/assets/5335ad16-e83d-4fd2-aa82-03fba103282e">

<img width="523" alt="4" src="https://github.com/user-attachments/assets/b1875628-f39d-45af-abca-2c5c3b702304">
