#ifndef HANDLE_DATA_H
#define HANDLE_DATA_H

// Define a structure for handling data
typedef struct {
    void (*handle_data)(char *data);  // Function pointer to handle data
} HandleData;

// Function to handle received data
void handle_received_data(char *data);

// Callback function to be passed to wifi_command_create_TCP_connection
void wifi_data_callback(char *data);

#endif // HANDLE_DATA_H