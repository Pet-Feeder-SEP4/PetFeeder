#pragma once
#include <stdint.h>

extern int temperature;
extern int humidity;
extern int waterMeasurement;
extern int foodMeasurement;
extern int idNumber;
#ifdef WINDOWS_TEST
extern int humidity_integer, humidity_decimal, temperature_integer, temperature_decimal;
#else
uint8_t humidity_integer, humidity_decimal, temperature_integer, temperature_decimal;
#endif

void getTempandHum();

int getTemp();

int getHum();

int getWaterMeasurement();

int getFoodMeasurement();

