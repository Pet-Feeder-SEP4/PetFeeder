#include "pc_comm.h"
#include <stdio.h>
#include <string.h>
#include <unistd.h> 
#include "dht11.h"
#include <stdio.h>
#include "hc_sr04.h"

uint8_t humidity_integer, humidity_decimal, temperature_integer, temperature_decimal;
uint16_t measure;
int temperature;
int humidity;
int waterMeasurement;
int foodMeasurement;
int idNumber;


void getTempandHum(){
    // Read data from DHT11 sensor
    DHT11_ERROR_MESSAGE_t result = dht11_get(&humidity_integer, &humidity_decimal, &temperature_integer, &temperature_decimal);

    if (result == DHT11_OK) {
        temperature=temperature_integer;
        humidity=humidity_integer;
    } else {
        // Print an error message if the read operation fails
        pc_comm_send_string_blocking("Failed to read DHT11 sensor data.");
    }
}
int getTemp(){
    return temperature;
}

int getHum(){
    return humidity;
}

int getWaterMeasurement(){
    uint16_t temporaryMeasure;
    temporaryMeasure = hc_sr04_takeMeasurement_water();
   if (temporaryMeasure != 0)
        {
            int capacity = 300;
            int emptyper= (temporaryMeasure*100)/ capacity;
            int result = 100-emptyper;
            return result;
        }
        else
        {
            // Print an error message if the read operation fails
            pc_comm_send_string_blocking("Invalid water measurement\n");
        }
        return -1;
};

int getFoodMeasurement(){
    uint16_t temporaryMeasure;
    temporaryMeasure = hc_sr04_takeMeasurement_food();
   if (temporaryMeasure != 0)
        {
            int capacity=160;
            int emptyper= (temporaryMeasure*100)/ capacity;
            int result=100-emptyper;
            return result;
        }
        else
        {
            // Print an error message if the read operation fails
            pc_comm_send_string_blocking("Invalid food measurement\n");
        }
        return -1;
};

