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
}

void tearDown(void) {}

void test_getTemp_call_drivers()
{
    getTemp();
    TEST_ASSERT_EQUAL_INT(dht11_get_fake.call_count, 1);
}

void test_getTemp_value()
{
    dht11_get_fake.return_val = DHT11_OK;
    temperature_integer = 10;
    int temp = getTemp();
    TEST_ASSERT_EQUAL_INT(temp, 10);
}

int main()
{
    UNITY_BEGIN();
    RUN_TEST(test_getTemp_call_drivers);
    RUN_TEST(test_getTemp_value);
    return UNITY_END();
}