#include "servo_controller.h"
#include "servo360.h"
#include "pc_comm.h"

void dispense(int gramms) {
    pc_comm_send_string_blocking(" Dispensing ");
    pc_comm_send_int_blocking(gramms);
    pc_comm_send_string_blocking(" gramms of food\n");

    if (gramms > 0) {
        // Calculate the amount of turns based on the gramms value
        int amountOfTurns = gramms / 10; // Assuming 10 gramms per second
        // Call the rotate function from servo360.h
        rotate(50, amountOfTurns); // Adjust the speed as needed
    }
}