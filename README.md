# Warehouse Management Application

### Introduction: 
This application is a simple desktop application that gives the user a way to track their warehouse inventory.
More specifically, it will provide a way to track and process current inventory, track exports and 
imports, view package details, and more. The application is targeted for users that have decided to or just recently
started up a warehouse business and has decided to use an industrial warehouse management software. The 
professional grade software systems can get quite difficult to understand and master. 
The goal of this application is to provide a basic demonstration of how a warehouse management software operates, so 
that the user can get comfortable using a more complicated management system. 
This project is of interest to me because my father has recently started up a warehouse business. By building 
this project I hope to acquire a better understanding of how a warehouse management software operates and discover the 
potential limitations associated with a system of its type.  

---

### User Stories: 
- As a user, I want to be able to add/import multiple packages into the Warehouse inventory
- As a user, I want to be able to remove/export a package out of the Warehouse inventory
- As a user, I want to be able to organize/view my warehouse inventory by package size  
- As a user, I want to be able to view the details regarding packages currently stored in the warehouse inventory
- As a user, I want to be able to view details of all past imports and exports that have occurred
- As a user, I want to be able to save changes of my warehouse inventory to file
- As a user, I want to be able to load my warehouse inventory from file

---

### Phase 4: Task 2
Two bi-directional associations were added in the model package. One bi-directional association was formed between the 
Warehouse class and the ImportEvent class, and the second bi-directional association was formed between the Warehouse 
class and the ExportEvent class.  

---

### Phase 4: Task 3

After reviewing the UML class diagram for this project, I realized that there are many unnecessary associations between 
the classes. Furthermore, the project seemed to lack an overall architectural design flow. 
If I had more time to work on the project, I would focus on creating an overall architectural design flow 
and eliminating the redundant associations seen in the UML class diagram. I would also integrate the observer design 
pattern into the current inventory display found on the GUI's main JFrame window to update every time a package is 
imported, exported, or whenever a saved file is loaded.

- eliminate the uni-directional association between all the Dialog classes and the Warehouse class
- eliminate the uni-directional association between ImportDialog and Package
- eliminate the uni-directional association between ExportDialog and Package
- integrate the Observer design pattern to make the current inventory display update every time changes are made to 
inventory





