#include "../fff.h"
#include "unity.h"

#include "servo_controller.h"
#include "servo360.h"
#include "uart.h"

#define TEST_SERVO_WIN

DEFINE_FFF_GLOBALS
FAKE_VOID_FUNC(rotate, uint8_t, int);
FAKE_VOID_FUNC(uart_send_string_blocking, USART_t, char *);
FAKE_VOID_FUNC(uart_init, USART_t, uint32_t, UART_Callback_t);
FAKE_VOID_FUNC(uart_send_array_blocking, USART_t, uint8_t *, uint16_t);
FAKE_VOID_FUNC(uart_send_array_nonBlocking, USART_t, uint8_t *, uint16_t);

void setUp(void){}

void tearDown(void) {}

void test_dispense_0() {
    dispense(0);
    TEST_ASSERT_EQUAL_INT(0, rotate_fake.call_count);
}

void test_dispense_value() {
    int gramms = 50;
    dispense(gramms);
    int amountOfTurns = gramms /10;

    TEST_ASSERT_EQUAL_INT(1, rotate_fake.call_count);
    TEST_ASSERT_EQUAL_INT(gramms, rotate_fake.arg0_val);
    TEST_ASSERT_EQUAL_INT(amountOfTurns, rotate_fake.arg1_val);
}

int main()
{
    UNITY_BEGIN();
    RUN_TEST(test_dispense_0);
    RUN_TEST(test_dispense_value);
    return UNITY_END();
}