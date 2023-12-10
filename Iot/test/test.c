#include <unity.h>
#include "fff.h"
#include <stdint.h>
#include "dht11.h"
#include "sensor_controller.h"
#include "hc_sr04.h"

DEFINE_FFF_GLOBALS;


FAKE_VOID_FUNC(dht11_init);
FAKE_VALUE_FUNC(DHT11_ERROR_MESSAGE_t,dht11_get,uint8_t *,uint8_t *,uint8_t *,uint8_t *);

FAKE_VOID_FUNC(hc_sr04_init);
FAKE_VALUE_FUNC(uint16_t,hc_sr04_takeMeasurement_food);



//button fakes:
FAKE_VOID_FUNC(button_init);
FAKE_VALUE_FUNC(uint8_t, button_pressed);

//uart fakes:
//FAKE_VALUE_FUNC(char*, uart_receive);
//FAKE_VOID_FUNC(uart_send, char *);
//FAKE_VOID_FUNC(uart_init, int);


void setUp(void) {//unity calles this before each test
    //RESET_FAKE(led_init);
    //RESET_FAKE(button_init);
    //RESET_FAKE(dht11_init);
    RESET_FAKE(hc_sr04_init);
} 

void tearDown(void) {} //unity calles this after each test

/*void test_getTemp(){
    getTemp();
    TEST_ASSERT_EQUAL_INT(dht11_get_fake.call_count, 1);
}*/

void test_getFoodMeasureDriver(){
    hc_sr04_takeMeasurement_food();
    TEST_ASSERT_EQUAL_INT(hc_sr04_takeMeasurement_food_fake.call_count,1);
}

void test_getFoodMeasure(){
    getFoodMeasurement();
    TEST_ASSERT_EQUAL_INT(hc_sr04_takeMeasurement_food_fake.call_count,1);
}

/*void test_lamp_control_initialize_led(void) {
    lamp_control_init();
    TEST_ASSERT_EQUAL(1, led_init_fake.call_count);
}

void test_lamp_control_initialize_button(void) {
    lamp_control_init();
    TEST_ASSERT_EQUAL(1, button_init_fake.call_count);
}

void test_lamp_control_button_not_pressed() {
    lamp_control_init();
    button_pressed_fake.return_val = 0; //button not pressed
    lamp_control_control();
    TEST_ASSERT_EQUAL(0, led_on_fake.call_count);
    TEST_ASSERT_EQUAL(1, led_off_fake.call_count);
}

void test_dirty_word_init(){
    dirty_word_init();
    TEST_ASSERT_EQUAL(1, uart_init_fake.call_count);
    TEST_ASSERT_EQUAL(9600, uart_init_fake.arg0_val);
}

void test_dirty_word_control(){
    dirty_word_init();
    uart_receive_fake.return_val="from fake uart \n";
    dirty_word_receive_and_respond("dirty");
    TEST_ASSERT_EQUAL_STRING("from fake uart \ndirty", uart_send_fake.arg0_val);

}*/

int main()
{
    UNITY_BEGIN();

    //testing lamp control
    RUN_TEST(test_getFoodMeasureDriver);
    RUN_TEST(test_getFoodMeasure);
    //RUN_TEST(test_lamp_control_initialize_led);
    //RUN_TEST(test_lamp_control_initialize_button);
    //RUN_TEST(test_lamp_control_button_not_pressed);

    //testing dirty word
    //RUN_TEST(test_dirty_word_init);
    //RUN_TEST(test_dirty_word_control);
    
    return UNITY_END();
}
