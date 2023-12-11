#include "../fff.h"
#include "unity.h"

#include "parse_info.h"
#include "wifi.h"
#include "pc_comm.h"

#include "app.h"
#include "configuration.h"

#define TEST_APP_WIN

DEFINE_FFF_GLOBALS
FAKE_VOID_FUNC(handle_received_data, char *);
FAKE_VOID_FUNC(sensor_get_data);
FAKE_VOID_FUNC(hc_sr04_init);
FAKE_VOID_FUNC(dht11_init);
FAKE_VALUE_FUNC(uint16_t, hc_sr04_takeMeasurement_food);
FAKE_VOID_FUNC(rotate, uint8_t, int);
FAKE_VALUE_FUNC(WIFI_ERROR_MESSAGE_t, wifi_command_join_AP, char *, char*);
FAKE_VOID_FUNC(wifi_init);
FAKE_VALUE_FUNC(WIFI_ERROR_MESSAGE_t, wifi_command_create_TCP_connection, char *, uint16_t, WIFI_TCP_Callback_t, char *);
FAKE_VOID_FUNC(uart_send_string_blocking, USART_t, char *);
FAKE_VOID_FUNC(uart_init, USART_t, uint32_t, UART_Callback_t);
FAKE_VOID_FUNC(uart_send_array_blocking, USART_t, uint8_t *, uint16_t);
FAKE_VOID_FUNC(uart_send_array_nonBlocking, USART_t, uint8_t *, uint16_t);
FAKE_VALUE_FUNC(UART_Callback_t, uart_get_rx_callback, USART_t)
FAKE_VOID_FUNC(_delay_ms, int);
FAKE_VOID_FUNC(_delay_us, int);

void setUp(void)
{
    RESET_FAKE(uart_init);
    RESET_FAKE(dht11_init);
    RESET_FAKE(hc_sr04_init);
    RESET_FAKE(wifi_init);
    RESET_FAKE(wifi_command_join_AP);
    RESET_FAKE(wifi_command_create_TCP_connection);
    strcpy(buffer, "");
}

void tearDown(void) {}

void test_buffer_size() {
    TEST_ASSERT_EQUAL_INT(8, sizeof(buffer));
}

void test_tcpCallback() {
    strcpy(buffer, "TEST");
    tcpCallback();
    TEST_ASSERT_EQUAL_INT(1, handle_received_data_fake.call_count);
    TEST_ASSERT_EQUAL_STRING("TEST", handle_received_data_fake.arg0_val);
}

void test_app_init_pc_comm() {
    app_init();
    TEST_ASSERT_EQUAL_INT(1, uart_init_fake.call_count);
    TEST_ASSERT_EQUAL_INT(9600, uart_init_fake.arg1_val);
}

void test_app_init_sensor_init() {
    app_init();
    TEST_ASSERT_EQUAL_INT(1, dht11_init_fake.call_count);
    TEST_ASSERT_EQUAL_INT(1, hc_sr04_init_fake.call_count);
    
}

void test_app_init_wifi() {
    strcpy(buffer, "TEST");

    app_init();

    TEST_ASSERT_EQUAL_INT(1, wifi_init_fake.call_count);
    TEST_ASSERT_EQUAL_INT(1, wifi_command_join_AP_fake.call_count);
    TEST_ASSERT_EQUAL_STRING(WIFI_NAME, wifi_command_join_AP_fake.arg0_val);
    TEST_ASSERT_EQUAL_STRING(WIFI_PASSWORD, wifi_command_join_AP_fake.arg1_val);
    TEST_ASSERT_EQUAL_INT(1, wifi_command_create_TCP_connection_fake.call_count);
    TEST_ASSERT_EQUAL_STRING(IP, wifi_command_create_TCP_connection_fake.arg0_val);
    TEST_ASSERT_EQUAL_INT(PORT, wifi_command_create_TCP_connection_fake.arg1_val);
    TEST_ASSERT_EQUAL(tcpCallback, wifi_command_create_TCP_connection_fake.arg2_val);
    TEST_ASSERT_EQUAL_STRING(buffer, wifi_command_create_TCP_connection_fake.arg3_val);
}

int main()
{
    UNITY_BEGIN();
    RUN_TEST(test_buffer_size);
    RUN_TEST(test_tcpCallback);
    RUN_TEST(test_app_init_pc_comm);
    RUN_TEST(test_app_init_sensor_init);
    RUN_TEST(test_app_init_wifi);
    return UNITY_END();
}