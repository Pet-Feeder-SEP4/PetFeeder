#pragma once

#ifdef WINDOWS_TEST
extern uint8_t humidity_integer, humidity_decimal, temperature_integer, temperature_decimal;
#else
uint8_t humidity_integer, humidity_decimal, temperature_integer, temperature_decimal;
#endif

int getTemp();

int getHum();

int getWaterMeasurement();

int getFoodMeasurement();