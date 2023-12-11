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


int getTemp(){
    if (dht11_get(&humidity_integer, &humidity_decimal, &temperature_integer, &temperature_decimal) == DHT11_OK) {
        return temperature_integer;
     }
     return -1000;
}

int getHum(){
    if (dht11_get(&humidity_integer, &humidity_decimal, &temperature_integer, &temperature_decimal) == DHT11_OK) {
        return humidity_integer;
     }
     return -1;
}

int getWaterMeasurement(){
    uint16_t temporaryMeasure;
    temporaryMeasure = hc_sr04_takeMeasurement_water();
   if (temporaryMeasure != 0)
        {
           return temporaryMeasure;
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
           return temporaryMeasure;
        }
        else
        {
            // Print an error message if the read operation fails
            pc_comm_send_string_blocking("Invalid food measurement\n");
        }
        return -1;
};

int waterMeasurementPercentage(){
    //put height of the cup
    int capacity=1000;
    int temporaryMeasure=getWaterMeasurement();
    int result= (temporaryMeasure*100)/ capacity;
    return result;
}

int foodMeasurementPercentage(){
    //put height of the cup
    int capacity=1000;
    int temporaryMeasure=getFoodMeasurement();
    int result= (temporaryMeasure*100)/ capacity;
    return result;
}
