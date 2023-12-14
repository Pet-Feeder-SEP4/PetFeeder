#define FFF_ARG_HISTORY_LEN 10

#include "../fff.h"
#include "unity.h"

#include "parse_info.h"
#include "sensor_controller.h"

#include "pc_comm.h"
#include "servo360.h"

#include <stdio.h>
#include <stdlib.h>

#define TEST_PARSE_INFO_WIN

DEFINE_FFF_GLOBALS
FAKE_VOID_FUNC(getTempandHum);
FAKE_VALUE_FUNC(int, getTemp);
FAKE_VALUE_FUNC(int, getHum);
FAKE_VALUE_FUNC(int, getWaterMeasurement);
FAKE_VALUE_FUNC(int, getFoodMeasurement);
FAKE_VOID_FUNC(rotate, uint8_t, int);
FAKE_VOID_FUNC(uart_send_string_blocking, USART_t, char *);
FAKE_VOID_FUNC(uart_init, USART_t, uint32_t, UART_Callback_t);
FAKE_VOID_FUNC(uart_send_array_blocking, USART_t, uint8_t *, uint16_t);
FAKE_VOID_FUNC(uart_send_array_nonBlocking, USART_t, uint8_t *, uint16_t);
FAKE_VOID_FUNC(_delay_ms, int)

void setUp(void)
{
    RESET_FAKE(uart_init);
    RESET_FAKE(uart_send_string_blocking);
}

void tearDown(void) {}

void test_sensor_get_data_pc_comm()
{
    sensor_get_data();
    TEST_ASSERT_EQUAL_INT(1, uart_init_fake.call_count);
    TEST_ASSERT_EQUAL_INT(9600, uart_init_fake.arg1_val);
}

char *getMsgStr(int idNumber, int foodMeasurement, int humidity, int temperature, int waterMeasurement, int msg_length)
{
    char *msg = malloc(msg_length);
    sprintf(msg, "{\"petFeederId\":\"%d\",\"foodLevel\":\"%d\",\"foodHumidity\":\"%d\",\"waterTemperature\":\"%d\",\"waterLevel\":\"%d\"}\n",
            idNumber, foodMeasurement, humidity, temperature, waterMeasurement);
    return msg;
}

void test_sensor_get_data_wifi_send()
{
    int foodMeasurement = 1;
    int humidity = 2;
    int temperature = 3;
    int waterMeasurement = 4;
    int msg_length = 99;
    getTemp_fake.return_val = temperature;
    getHum_fake.return_val = humidity;
    getWaterMeasurement_fake.return_val = waterMeasurement;
    getFoodMeasurement_fake.return_val = foodMeasurement;
    int idNumber = 37;

    char *msg = getMsgStr(idNumber, foodMeasurement, humidity, temperature, waterMeasurement, msg_length);
    TEST_ASSERT_EQUAL_STRING(msg, sensor_get_data());
    free(msg);
}

void test_handle_received_data_good_input()
{
    handle_received_data("DIS:050");
    int expected = 50 / 10;
    TEST_ASSERT_EQUAL_INT(1, rotate_fake.call_count);
    TEST_ASSERT_EQUAL_INT(expected, rotate_fake.arg1_val);
}


void test_handle_received_data_wrong_input(char* input)
{
    handle_received_data(input);

    char error_msg[] = "Error parsing data\n";
    int err_counter = 0;

    for (int i = 0; i < FFF_ARG_HISTORY_LEN; i++)
    {
        
        if (uart_send_string_blocking_fake.arg1_history[i] != NULL && strcmp(uart_send_string_blocking_fake.arg1_history[i], error_msg) == 0)
        {
            err_counter++;
        }
    }

    TEST_ASSERT_EQUAL_INT(1, err_counter);
}

void test_handle_received_data_wrong_input_0()
{  
    test_handle_received_data_wrong_input("DIS:000");
}

void test_handle_received_data_wrong_input_name_short()
{  
    test_handle_received_data_wrong_input("DI:050");
}

void test_handle_received_data_wrong_input_no_separator()
{  
    test_handle_received_data_wrong_input("DIS050");
}

void test_handle_received_data_wrong_input_value_not_number()
{  
    test_handle_received_data_wrong_input("DIS:A10");
}

void test_handle_received_data_wrong_input_name_wrong()
{  
    test_handle_received_data_wrong_input("DIT:050");
}

void test_handle_received_data_wrong_input_no_value()
{  
    test_handle_received_data_wrong_input("DIT:");
}


int main()
{
    UNITY_BEGIN();
    RUN_TEST(test_sensor_get_data_pc_comm);
    RUN_TEST(test_handle_received_data_good_input);
    RUN_TEST(test_handle_received_data_wrong_input_0);
    RUN_TEST(test_handle_received_data_wrong_input_name_short);
    RUN_TEST(test_handle_received_data_wrong_input_no_separator);
    RUN_TEST(test_handle_received_data_wrong_input_value_not_number);
    RUN_TEST(test_handle_received_data_wrong_input_name_wrong);
    RUN_TEST(test_handle_received_data_wrong_input_no_value);
    return UNITY_END();
}