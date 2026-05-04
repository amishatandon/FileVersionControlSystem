# File Version Control System

## 📌 Overview
This project is a simplified version control system inspired by Git. It allows users to track file changes, create commits, manage branches, and merge changes with conflict detection.

The main goal of this project was to understand how version control systems work internally rather than just using Git commands.

## 🚀 Features

- Add and remove files
- Staging area for tracking changes
- Commit system using snapshot model
- Branch creation and switching
- Merge functionality with conflict detection
- Reset to previous commits
- Diff between commits (line-by-line comparison)
- Data persistence using serialization

## 🧠 Core Concepts

### 1. Working Directory
Represents the current state of files where changes are made.

### 2. Staging Area
Temporarily holds changes before they are committed.

### 3. Commit System
Each commit stores a **complete snapshot** of all files at that point in time.

### 4. Branching
Each branch maintains its own commit history, enabling independent development.

### 5. Merge
Combines changes from different branches and detects conflicts when the same file is modified differently.

### 6. Conflict Resolution
Conflicts are handled using markers:

<<<<<<< HEAD

current changes

incoming changes

>>>>>>> branch

## 🛠️ Technologies Used

- Java
- HashMap (file storage)
- LinkedList (commit history)
- Serialization (data persistence)


## 🔍 How It Works

1. Files are added → moved to staging area  
2. Commit → stores snapshot of all files  
3. Branch → creates parallel development path  
4. Merge → combines changes and detects conflicts  
5. Reset → reverts to previous commits  


## ⚖️ Design Decisions

- Used **snapshot model** instead of diff-based storage for simplicity
- Used **UUIDs** for commit IDs instead of hashing
- Implemented **two-way merge** instead of full 3-way merge


## ❗ Limitations

- No DAG (Directed Acyclic Graph) structure
- No 3-way merge using common ancestor
- Not a distributed system
- No storage optimization or compression


## 🔄 Future Improvements

- Implement DAG-based commit structure
- Add 3-way merge (LCA-based)
- Introduce file hashing for efficiency
- Support remote repositories


## 📚 Learning Outcomes

- Understood how Git-like systems work internally  
- Learned about version tracking, branching, and merging  
- Explored design trade-offs between simplicity and performance  
- Gained hands-on experience with system design concepts  


## 👤 Author

Amish Tandon



