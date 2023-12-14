#include "../fff.h"
#include "unity.h"

#include "wifi_controller.h"
#include "wifi.h"
#include "pc_comm.h"
#include "app.h"
#include "configuration.h"
#include "dht11.h"
#include "hc_sr04.h"

#define TEST_WIFI_WIN

DEFINE_FFF_GLOBALS
FAKE_VOID_FUNC(wifi_init);
FAKE_VALUE_FUNC(WIFI_ERROR_MESSAGE_t, wifi_command_join_AP, char *, char *);
FAKE_VALUE_FUNC(WIFI_ERROR_MESSAGE_t, wifi_command_quit_AP);
FAKE_VALUE_FUNC(WIFI_ERROR_MESSAGE_t, wifi_command_create_TCP_connection, char *, uint16_t, WIFI_TCP_Callback_t, char *);
FAKE_VALUE_FUNC(WIFI_ERROR_MESSAGE_t, wifi_command_close_TCP_connection);
FAKE_VALUE_FUNC(WIFI_ERROR_MESSAGE_t, wifi_command_TCP_transmit, uint8_t *, uint16_t);
FAKE_VOID_FUNC(_delay_ms, int);
FAKE_VOID_FUNC(pc_comm_send_string_blocking, char *);
FAKE_VOID_FUNC(pc_comm_send_array_blocking, uint8_t *, uint16_t);
FAKE_VOID_FUNC(pc_comm_send_array_nonBlocking, uint8_t *, uint16_t);
FAKE_VOID_FUNC(pc_comm_init, uint32_t, pc_comm_callback_t);
FAKE_VOID_FUNC(pc_comm_send_int_blocking, int);
FAKE_VOID_FUNC(hc_sr04_init);
FAKE_VOID_FUNC(dht11_init);
FAKE_VALUE_FUNC(DHT11_ERROR_MESSAGE_t, dht11_get, uint8_t *, uint8_t *, uint8_t *, uint8_t *);
FAKE_VALUE_FUNC(uint16_t, hc_sr04_takeMeasurement_food);
FAKE_VALUE_FUNC(uint16_t, hc_sr04_takeMeasurement_water);
FAKE_VOID_FUNC(rotate, uint8_t, int);

void setUp(void)
{
    RESET_FAKE(wifi_init);
    RESET_FAKE(wifi_command_join_AP);
    RESET_FAKE(wifi_command_create_TCP_connection);
    wifi_command_join_AP_fake.return_val = WIFI_OK;
    wifi_command_create_TCP_connection_fake.return_val = WIFI_OK;
}

void tearDown(void) {}

void callback() {};

void test_connect_wifi_join_AP_reconnect()
{
    WIFI_ERROR_MESSAGE_t returns[] = {
        WIFI_ERROR_NOT_RECEIVING,
        WIFI_ERROR_RECEIVED_ERROR,
        WIFI_ERROR_RECEIVING_GARBAGE,
        WIFI_FAIL,
        WIFI_OK};

    SET_RETURN_SEQ(wifi_command_join_AP, returns, 5);

    char buffer[] = "";
    connect_wifi(callback, buffer);
    TEST_ASSERT_EQUAL_INT(5, wifi_command_join_AP_fake.call_count);
}

void test_connect_wifi_create_TCP_connection_reconnect()
{
    WIFI_ERROR_MESSAGE_t returns[] = {
        WIFI_ERROR_NOT_RECEIVING,
        WIFI_ERROR_RECEIVED_ERROR,
        WIFI_ERROR_RECEIVING_GARBAGE,
        WIFI_FAIL,
        WIFI_OK};

    SET_RETURN_SEQ(wifi_command_create_TCP_connection, returns, 5);

    char buffer[] = "";
    connect_wifi(callback, buffer);
    TEST_ASSERT_EQUAL_INT(5, wifi_command_create_TCP_connection_fake.call_count);
}

void test_close_wifi()
{
    close_wifi();
    TEST_ASSERT_EQUAL_INT(1, wifi_command_close_TCP_connection_fake.call_count);
    TEST_ASSERT_EQUAL_INT(1, wifi_command_quit_AP_fake.call_count);
}

int str_length(char *str)
{
    char data_copy[256];
    strcpy(data_copy, str);
    char *p = data_copy;
    int length = 0;
    for (; *p; ++p)
    {
        length++;
    }
    return length;
}

void test_transit_data()
{
    char data[] = "This is fake data";
    transmit_data(data);
    TEST_ASSERT_EQUAL_INT(1, wifi_command_TCP_transmit_fake.call_count);
    TEST_ASSERT_EQUAL_STRING(data, wifi_command_TCP_transmit_fake.arg0_val);
    TEST_ASSERT_EQUAL_INT(str_length(data), wifi_command_TCP_transmit_fake.arg1_val);
}

void test_wifi_connect_wifi()
{
    strcpy(buffer, "TEST");

    connect_wifi(callback, buffer);

    TEST_ASSERT_EQUAL_INT(1, wifi_init_fake.call_count);
    TEST_ASSERT_EQUAL_INT(1, wifi_command_join_AP_fake.call_count);
    TEST_ASSERT_EQUAL_STRING(WIFI_NAME, wifi_command_join_AP_fake.arg0_val);
    TEST_ASSERT_EQUAL_STRING(WIFI_PASSWORD, wifi_command_join_AP_fake.arg1_val);
    TEST_ASSERT_EQUAL_INT(1, wifi_command_create_TCP_connection_fake.call_count);
    TEST_ASSERT_EQUAL_STRING(IP, wifi_command_create_TCP_connection_fake.arg0_val);
    TEST_ASSERT_EQUAL_INT(PORT, wifi_command_create_TCP_connection_fake.arg1_val);
    TEST_ASSERT_EQUAL(callback, wifi_command_create_TCP_connection_fake.arg2_val);
    TEST_ASSERT_EQUAL_STRING(buffer, wifi_command_create_TCP_connection_fake.arg3_val);
}

int main()
{
    UNITY_BEGIN();
    RUN_TEST(test_connect_wifi_join_AP_reconnect);
    RUN_TEST(test_connect_wifi_create_TCP_connection_reconnect);
    RUN_TEST(test_close_wifi);
    RUN_TEST(test_transit_data);
    RUN_TEST(test_wifi_connect_wifi);
    return UNITY_END();
}