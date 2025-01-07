# Full-Stack Ticket Management System  

## **Overview**
This project is a comprehensive full-stack ticket management system, combining a Java-based backend with a dynamic JavaFX frontend. The platform enables seamless ticket buying, selling, and user management. It emphasizes modular design, efficient workflows, multi-threading, and rigorous testing to ensure reliability and scalability.

## Key Features  

### 1. **Client-Server Architecture**  
- Designed with a client-server model, allowing buyers and sellers to communicate with a central server for data exchange.  
- Buyers can browse available tickets or search using descriptions, providing flexibility in ticket selection.  
- Sellers can manage their tickets by adding, updating, or deleting them with real-time feedback.  

### 2. **User Interface**  
- Employs a clean and consistent design for ease of navigation.  
- Interactive forms provide validation messages to enhance accuracy during ticket operations.  

### 3. **Advanced Ticket Search**  
- Implements a string matching algorithm to efficiently match buyer requests with available ticket listings.  
- Enhances search functionality and improves overall system performance.  

### 4. **Multi-Threading**  
- Supports multi-threading to handle simultaneous user interactions.  
- Ensures smooth execution of tasks like ticket updates, purchases, and data synchronization in real time.  

### 5. **Data Storage**  
- Stores tickets and user data in a text file for long-term reliability and easy access.  
- Regular updates maintain data consistency, even with multiple users interacting simultaneously.  

### 6. **Socket Programming**  
- Enables real-time communication between clients and the server using socket programming.  
- The server listens for incoming client connections, while clients seamlessly interact with the server for operations.  

### 7. **Scalability and Extensibility**  
- Built with modular components, making it easy to add features or integrate new functionality.  

### 8. **Extensive Testing**  
- Includes rigorous testing to ensure the system handles edge cases and normal operations without failures.  

## Usage  
1. **Server**: Start the server application to handle client connections.  
2. **Client**: Run the client application to perform ticket operations (buying, selling, searching).  
