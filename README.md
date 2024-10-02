# Java Swing Application

## Overview

This project, developed in Java, showcases a graphical user interface (GUI) using Java Swing. It implements several key functionalities related to **inventory management**, with serialization of key data objects such as articles, depots, and shipments. The application also supports event-driven programming through listeners and threads for concurrency handling.

## Features

- **Java Swing UI**: A user-friendly interface built using Java's Swing framework.
- **Data Serialization**: Utilizes `.ser` files for storing and loading application data such as articles (`artikli.ser`), depots (`depoji.ser`), and shipments (`posiljke.ser`).
- **Threading Support**: Implements multi-threading for handling concurrent processes efficiently.
- **Exception Handling**: Custom exceptions are implemented to manage various error scenarios gracefully.
- **Comparators**: Includes comparator classes to handle sorting of data objects as per business logic.
- **Modular Design**: The project is structured into multiple modules including data models, listeners, and UI components for maintainability and scalability.

## Folder Structure

- **src/**: Contains the source code.
  - `Zagonski.java`: Main entry point of the application.
  - `si.feri.opj.imamovic`: Package containing the logic and UI components.
    - **cetrti/**, **drugi/**, **prvi/**, **tretji/**: Application logic divided into modules.
    - **ui/**: Contains the Swing UI class (`Swing.java`).
    - **comparators/**: Implements sorting logic.
    - **listeners/**: Handles event-driven functionalities.
    - **threads/**: Manages concurrent tasks.
- **out/**: Contains compiled `.class` files.
- **.ser files**: Serialized objects that store the application's data between sessions.

## Installation and Setup

1. Clone the repository.
2. Ensure you have **Java 8** or later installed.
3. Open the project in your preferred IDE (such as IntelliJ IDEA or Eclipse).
4. Run the `Zagonski.java` file to launch the application.

## Usage

- Upon starting the application, you can manage various inventory and shipment processes through the provided UI.
- Data is stored in serialized form, ensuring persistence across sessions.

## Error Handling

If any issues arise during usage, error details are logged into the `error_log.txt` file.
