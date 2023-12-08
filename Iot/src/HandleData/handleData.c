/*#include "HandleData.h"
#include "pc_comm.h"
#include "string.h"
#include <stdio.h>  // Include any necessary headers

// Implement the handle_received_data function
void handle_received_data(char *data) {
    pc_comm_send_string_blocking("Received data: ");
    pc_comm_send_string_blocking(data);
    

    // Tokenize the received message
    char *token = strtok(data, ":");
    if (token != NULL && strcmp(token, "DIS") == 0) {
        // Get the next token, which should be the gramms value
        token = strtok(NULL, ":");
        if (token != NULL) {
            int gramms = atoi(token);  // Convert the token to an integer

            pc_comm_send_string_blocking(" Dispensing ");
            pc_comm_send_int_blocking(gramms);
            pc_comm_send_string_blocking(" gramms of food\n");

            // Calculate the amount of turns based on the gramms value
            int amountOfTurns = gramms / 10; // Assuming 10 gramms per second

            // Call the rotate function from servo360.h
            rotate(50, amountOfTurns);  // Adjust the speed as needed
        }
    } else {
        pc_comm_send_string_blocking("Error parsing data\n");
    }
}

// Create an instance of the HandleData structure
HandleData handle_data_instance = {
    .handle_data = handle_received_data
};

// Implement the wifi_data_callback function
void wifi_data_callback(char *data) {
    // Call the handle_data function in the HandleData instance
    handle_data_instance.handle_data(data);
}*/