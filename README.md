# Warehouse Management Application

<!-- ABOUT THE PROJECT -->
|![warehouseapp](https://user-images.githubusercontent.com/82549471/167297442-4b55203e-a464-4b70-a8e5-f29fbf85f9c6.png)|
|:--:|
|Warehouse management application GUI designed using Java Swing|

### Introduction

### Description

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

<!-- USAGE EXAMPLES -->

## Usage
Click below link for a more in-depth project demonstration
![project demonstration][https://github.com/patricknoda11]

#### Importing products
|![importing](https://user-images.githubusercontent.com/82549471/167299324-5d336689-63c9-48fd-bab9-5e44f0b5a538.gif)|
|:--:|
|Products can be imported by specifying details in the import panel that can be selected on the left hand side. Attempts to import products for an non-existent customer or an previously-used invoice number are rejected.|
#### Exporting Products
|![ezgif com-gif-maker (3)](https://user-images.githubusercontent.com/82549471/167300217-31fceac1-efa6-47ad-881e-c2b02c8cedd6.gif)|
|:--:|
|The application supports partial and full export of inventory products. Every partial removal is recorded under the export column for each product. Fully exported products will be removed from current inventory and have records kept under the history display panel.|
#### Filtering through Current Inventory & Past Transactions
|![ezgif com-gif-maker (2)](https://user-images.githubusercontent.com/82549471/167299939-55287385-5ebf-483f-9c16-ec1823cc82fe.gif)|
|:--:|
|The entries in current inventory and History can be filtered by adding a keyword and clicking the "Filter" button. To return back to original inventory, click "Clear".|
#### Loading Data From File
|![ezgif com-gif-maker (2)](https://user-images.githubusercontent.com/82549471/167299694-136b7f4f-cb07-4db6-a27c-0275022f499b.gif)|
|:--:|
|Previously saved data can be opened by clicking the "Load" button, located on the top-left of the GUI, and selecting the file and pressing open. |
#### Saving Data to File
|![ezgif com-gif-maker (2)](https://user-images.githubusercontent.com/82549471/167299849-aec725b8-0384-4bb8-b89c-f930ba117060.gif)|
|:--:|
|Current warehouse state can be saved by clicking the "Save" button, located on the top-right of the GUI, and specifying the file and destination to save to|

---

### Built Using

- [Java](https://www.oracle.com/ca-en/java/)
- [Java Swing](https://docs.oracle.com/javase/7/docs/api/javax/swing/package-summary.html)
- [JUnit 5](https://junit.org/junit5/)

---

<!-- GETTING STARTED -->

## Getting Started

1. Clone the repository
   ```sh
   git clone https://github.com/patricknoda11/warehouse-management-app.git
   ```
2. Run main using an IDE that supports Java (e.g. IntelliJ, Eclipse)
![ezgif com-gif-maker](https://user-images.githubusercontent.com/82549471/167298659-d79f6673-503b-4398-b385-53d952ce85d8.gif)

---
