#include "../fff.h"
#include "unity.h"

#include "parse_info.h"
#include "wifi.h"
#include "pc_comm.h"
#include "servo_controller.h"

#include "app.h"

#include <stdio.h>
#include <stdlib.h>
#include "wifi_controller.h"

#define TEST_APP_WIN

DEFINE_FFF_GLOBALS
FAKE_VALUE_FUNC(int, get_gramms_from_data, char *);
FAKE_VOID_FUNC(dispense, int);
FAKE_VALUE_FUNC(char *, sensor_get_data);
FAKE_VOID_FUNC(hc_sr04_init);
FAKE_VOID_FUNC(dht11_init);
FAKE_VOID_FUNC(rotate, uint8_t, int);
FAKE_VOID_FUNC(wifi_init);
FAKE_VALUE_FUNC(WIFI_ERROR_MESSAGE_t, wifi_command_join_AP, char *, char *);
FAKE_VALUE_FUNC(WIFI_ERROR_MESSAGE_t, wifi_command_quit_AP);
FAKE_VALUE_FUNC(WIFI_ERROR_MESSAGE_t, wifi_command_create_TCP_connection, char *, uint16_t, WIFI_TCP_Callback_t, char *);
FAKE_VALUE_FUNC(WIFI_ERROR_MESSAGE_t, wifi_command_close_TCP_connection);
FAKE_VALUE_FUNC(WIFI_ERROR_MESSAGE_t, wifi_command, char *, uint16_t);
FAKE_VALUE_FUNC(WIFI_ERROR_MESSAGE_t, wifi_command_TCP_transmit, uint8_t *, uint16_t);
FAKE_VOID_FUNC(uart_init, USART_t, uint32_t, UART_Callback_t);
FAKE_VOID_FUNC(uart_send_string_blocking, USART_t, char *);
FAKE_VOID_FUNC(uart_send_array_blocking, USART_t, uint8_t *, uint16_t);
FAKE_VOID_FUNC(uart_send_array_nonBlocking, USART_t, uint8_t *, uint16_t);
FAKE_VOID_FUNC(pc_comm_send_string_blocking, char *);
FAKE_VOID_FUNC(pc_comm_send_array_blocking, uint8_t *, uint16_t);
FAKE_VOID_FUNC(pc_comm_send_array_nonBlocking, uint8_t *, uint16_t);
FAKE_VOID_FUNC(pc_comm_init, uint32_t, pc_comm_callback_t);
FAKE_VOID_FUNC(pc_comm_send_int_blocking, int);
FAKE_VOID_FUNC(_delay_ms, int);
FAKE_VOID_FUNC(connect_wifi, WIFI_TCP_Callback_t, char*);
FAKE_VOID_FUNC(close_wifi);
FAKE_VALUE_FUNC(WIFI_ERROR_MESSAGE_t, transmit_data, char *);


void setUp(void)
{
    RESET_FAKE(uart_init);
    RESET_FAKE(dht11_init);
    RESET_FAKE(hc_sr04_init);
    RESET_FAKE(wifi_init);
    RESET_FAKE(wifi_command_join_AP);
    RESET_FAKE(wifi_command_create_TCP_connection);
    RESET_FAKE(dispense);
    RESET_FAKE(connect_wifi);
    wifi_command_join_AP_fake.return_val = WIFI_OK;
    wifi_command_create_TCP_connection_fake.return_val = WIFI_OK;
    strcpy(buffer, "");
}

void tearDown(void) {}

void test_buffer_size()
{
    TEST_ASSERT_EQUAL_INT(8, sizeof(buffer));
}

void test_tcpCallback()
{
    strcpy(buffer, "TEST");
    tcpCallback();
    TEST_ASSERT_EQUAL_INT(1, get_gramms_from_data_fake.call_count);
    TEST_ASSERT_EQUAL_STRING("TEST", get_gramms_from_data_fake.arg0_val);
}

void test_tcpCallback_error_parsing()
{
    get_gramms_from_data_fake.return_val = -1;
    tcpCallback();
    TEST_ASSERT_EQUAL_INT(0, dispense_fake.call_count);
}

void test_tcpCallback_dispense()
{
    get_gramms_from_data_fake.return_val = 50;
    tcpCallback();
    TEST_ASSERT_EQUAL_INT(1, dispense_fake.call_count);
    TEST_ASSERT_EQUAL_INT(50, dispense_fake.arg0_val);
}

void test_app_init_pc_comm()
{
    app_init();
    TEST_ASSERT_EQUAL_INT(1, pc_comm_init_fake.call_count);
    TEST_ASSERT_EQUAL_INT(9600, pc_comm_init_fake.arg0_val);
}

void test_app_init_sensor_init()
{
    app_init();
    TEST_ASSERT_EQUAL_INT(1, dht11_init_fake.call_count);
    TEST_ASSERT_EQUAL_INT(1, hc_sr04_init_fake.call_count);
}

void test_app_init_connect_wifi()
{
    strcpy(buffer, "TEST");

    app_init();

    TEST_ASSERT_EQUAL_INT(1, connect_wifi_fake.call_count);
    TEST_ASSERT_EQUAL(tcpCallback, connect_wifi_fake.arg0_val);
    TEST_ASSERT_EQUAL_STRING(buffer, connect_wifi_fake.arg1_val);
}

void test_app_start_send_data_wifi()
{
    char msg[] = "This is a test string";
    char msg_copy[22];

    strcpy(msg_copy, msg);
    sensor_get_data_fake.return_val = msg_copy;

    app_start();

    TEST_ASSERT_EQUAL_STRING(msg, transmit_data_fake.arg0_val);
}

int main()
{
    UNITY_BEGIN();
    RUN_TEST(test_buffer_size);
    RUN_TEST(test_tcpCallback);
    RUN_TEST(test_tcpCallback_error_parsing);
    RUN_TEST(test_tcpCallback_dispense);
    RUN_TEST(test_app_init_pc_comm);
    RUN_TEST(test_app_init_sensor_init);
    RUN_TEST(test_app_init_connect_wifi);
    RUN_TEST(test_app_start_send_data_wifi);
    return UNITY_END();
}