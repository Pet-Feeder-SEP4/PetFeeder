#include "../fff.h"
#include "unity.h"

#include "sensor_controller.h"
#include "dht11.h"
#include "uart.h"

#include <stdio.h>

#define TEST_SENSOR_WIN

DEFINE_FFF_GLOBALS
FAKE_VALUE_FUNC(DHT11_ERROR_MESSAGE_t, dht11_get, uint8_t *, uint8_t *, uint8_t *, uint8_t *);
FAKE_VOID_FUNC(dht11_init)
FAKE_VOID_FUNC(hc_sr04_init);
FAKE_VALUE_FUNC(uint16_t, hc_sr04_takeMeasurement_food);
FAKE_VALUE_FUNC(uint16_t, hc_sr04_takeMeasurement_water);
FAKE_VOID_FUNC(uart_send_string_blocking, USART_t, char *);
FAKE_VOID_FUNC(uart_init, USART_t, uint32_t, UART_Callback_t);
FAKE_VOID_FUNC(uart_send_array_blocking, USART_t, uint8_t *, uint16_t);
FAKE_VOID_FUNC(uart_send_array_nonBlocking, USART_t, uint8_t *, uint16_t);

void setUp(void)
{
    RESET_FAKE(dht11_get);
    RESET_FAKE(hc_sr04_takeMeasurement_water);
    RESET_FAKE(hc_sr04_takeMeasurement_food);
    RESET_FAKE(uart_send_string_blocking);
}

void tearDown(void) {}

void test_getTempandHum_call_drivers()
{
    getTempandHum();
    TEST_ASSERT_EQUAL_INT(1, dht11_get_fake.call_count);
}

void test_getTemp_value()
{
    dht11_get_fake.return_val = DHT11_OK;
    temperature_integer = 10;
    getTempandHum();
    int temp = getTemp();
    TEST_ASSERT_EQUAL_INT(10, temp);
}


void test_getHum_value()
{
    dht11_get_fake.return_val = DHT11_OK;
    humidity_integer = 10;
    getTempandHum();
    int hum = getHum();
    TEST_ASSERT_EQUAL_INT(10, hum);
}

void test_getWaterMeasurament_call() {
    getWaterMeasurement();
    TEST_ASSERT_EQUAL_INT(1, hc_sr04_takeMeasurement_water_fake.call_count);
}

void test_getWaterMeasurament_0() {
    hc_sr04_takeMeasurement_water_fake.return_val = 0;
    getWaterMeasurement();
    TEST_ASSERT_EQUAL_INT(1, uart_send_string_blocking_fake.call_count);
    TEST_ASSERT_EQUAL_STRING("Invalid water measurement\n", uart_send_string_blocking_fake.arg1_val);
}

void test_getWaterMeasurament_value() {
    hc_sr04_takeMeasurement_water_fake.return_val = 10;
    int value = getWaterMeasurement();
     int capacity = 300;
    int expected= 100 - ((10*100)/ capacity);
    TEST_ASSERT_EQUAL_INT(expected, value);
}

void test_getFoodMeasurament_call() {
    getFoodMeasurement();
    TEST_ASSERT_EQUAL_INT(1, hc_sr04_takeMeasurement_food_fake.call_count);
}

void test_getFoodMeasurament_0() {
    hc_sr04_takeMeasurement_food_fake.return_val = 0;
    getFoodMeasurement();
    TEST_ASSERT_EQUAL_INT(1, uart_send_string_blocking_fake.call_count);
    TEST_ASSERT_EQUAL_STRING("Invalid food measurement\n", uart_send_string_blocking_fake.arg1_val);
}

void test_getFoodMeasurament_value() {
    hc_sr04_takeMeasurement_food_fake.return_val = 10;
    int value = getFoodMeasurement();
    int capacity = 160;
    int expected= 100 - ((10*100)/ capacity);
    TEST_ASSERT_EQUAL_INT(expected, value);
}


int main()
{
    UNITY_BEGIN();
    RUN_TEST(test_getTempandHum_call_drivers);
    RUN_TEST(test_getTemp_value);
    RUN_TEST(test_getHum_value);
    RUN_TEST(test_getWaterMeasurament_call);
    RUN_TEST(test_getWaterMeasurament_0);
    RUN_TEST(test_getWaterMeasurament_value);
    RUN_TEST(test_getFoodMeasurament_call);
    RUN_TEST(test_getFoodMeasurament_0);
    RUN_TEST(test_getFoodMeasurament_value);
    return UNITY_END();
}