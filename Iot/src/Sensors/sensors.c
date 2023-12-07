/*#include "sensors.h"
#include "pc_comm.h"
#include <stdio.h>
#include <string.h>
#include <unistd.h> 
#include <dht11.h>
#include <stdio.h>
#include "hc_sr04.h"
#include <stdlib.h>  // for malloc
#include <util/delay.h>

uint8_t humidity_integer, humidity_decimal, temperature_integer, temperature_decimal;
uint16_t measure;
char str[256];  // Increase the buffer size for JSON
//char humidTemp[256];
int temperature;
int humidity;
char* waterMeasurement;
char* foodMeasurement;
int idNumber;

// Function prototypes
void sensor_init();
char* sensor_get_data();
void getTempandHum();
char* intToString(int value);
int getWaterMeasurement();
int getFoodMeasurement();

void sensor_init() {
    //pc_comm_init(9600, NULL);  // Initialize communication at the beginning

    dht11_init();
    hc_sr04_init();
    idNumber = 8888;  // Set the petFeederId to 1
}

char* sensor_get_data() {
    getTempandHum();
    waterMeasurement = intToString(getWaterMeasurement());
    foodMeasurement = intToString(getFoodMeasurement());

    // Create JSON string
    sprintf(str, "{\"petFeederId\":\"%d\",\"foodLevel\":\"%s\",\"foodHumidity\":\"%d\",\"waterTemperature\":\"%d\",\"waterLevel\":\"%s\"}\n",
            idNumber, foodMeasurement, humidity, temperature, waterMeasurement);

    pc_comm_send_string_blocking(str);
    return str;
}

void getTempandHum(){
    // Read data from DHT11 sensor
    DHT11_ERROR_MESSAGE_t result = dht11_get(&humidity_integer, &humidity_decimal, &temperature_integer, &temperature_decimal);
    //pc_comm_init(9600, NULL);
    temperature=temperature_integer;
    humidity=humidity_integer;
    //sprintf(humidTemp,"humidity:%d temperature:%d \n\n",
            //humidity,temperature);
    pc_comm_send_string_blocking(str);
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


//method to convert measurement result into string to pass it to cloud
char* intToString(int value) {
    
    char buffer[3];

    if(value<100){
        // Convert the integer to a string using sprintf with a 0 in front
    sprintf(buffer, "0%d", value);
    } else{
        sprintf(buffer, "%d", value);
    }
    
    // Allocate memory for the result string and copy the buffer
    char *resultString = malloc(strlen(buffer) + 1);
    strcpy(resultString, buffer);

    return resultString;
}*/