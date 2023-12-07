#include "includes.h"

#include <inttypes.h>


// Vcc
#define DDR_VccF DDRC
#define PORT_VccF PORTC
#define P_VccF PC0

// GND
#define DDR_GndF DDRC
#define P_GndF PC6

// Trigger
#define DDR_TrigF DDRC
#define P_TrigF PC2
#define PORT_TrigF PORTC

// Echo
#define PIN_EchoF PINC
#define P_EchoF PC4

// Additional ports
// Vcc-
#define DDR_VccW DDRB
#define PORT_VccW PORTB
#define P_VccW PB2

// GND
#define DDR_GndW DDRL
#define P_GndW PL4

// Trigger
#define DDR_TrigW DDRL
#define P_TrigW PL0
#define PORT_TrigW PORTL

// Echo
#define PIN_EchoW PINL
#define P_EchoW PL2

    void hc_sr04_init()
    {
        // Vcc
        DDR_VccF |= (1 << P_VccF);
        PORT_VccF |= (1 << P_VccF);

        // GND
        DDR_GndF |= (1 << P_GndF);

        // Trigger
        DDR_TrigF |= (1 << P_TrigF);

        // Vcc
        DDR_VccW |= (1 << P_VccW);
        PORT_VccW |= (1 << P_VccW);

        // GND
        DDR_GndW |= (1 << P_GndW);

        // Trigger
        DDR_TrigW |= (1 << P_TrigW);

    }

uint16_t hc_sr04_takeMeasurement_food()
{
    uint16_t cnt = 0;

    _delay_us(10);
    PORT_TrigF |= (1 << P_TrigF); // trig is set to high for 10 us to start measurement.
    _delay_us(10);
    PORT_TrigF &= ~(1 << P_TrigF);


    
    uint8_t TCCR1B_state = TCCR1B; // The display is using timer1. But Ill just borrow it briefly. But therefor the state of TCCR1B needs to be saved.

    // Set the Timer/Counter1 prescaler to 256
     TCCR1B = (1 << CS12);
//    TCCR1B |= (1 << CS12);
//    TCCR1B &= ~(1 << CS11);
//    TCCR1B &= ~(1 << CS10);
   
TCNT1 = 0;
    while (!(PIN_EchoF & (1 << P_EchoF)))
    {

                // Check for timer overflow (24 ms)
        if (TCNT1 >= (F_CPU / 256) * 0.1) //timeout after 100ms. Chip is not working
        {
            // Timer overflowed, return 0
            return 0;
        }
    }
         // Wait for signal to begin /TODO implement some timeout...


    TCNT1 = 0; // Setting the timer to Zero. This is  messing up the display, but hopefully the reader of the display wont notice.

    while (PIN_EchoF & (1 << P_EchoF))
    {
        // Check for timer overflow (24 ms)
        if (TCNT1 >= (F_CPU / 256) * 0.024)
        {
            // Timer overflowed, return 0
            break;
        }
    }
    cnt = TCNT1; // Save the value of the timer!

    TCCR1B = TCCR1B_state; // thx Display, for letting me borrow timer1

    /*
    So a bit of math is goint into the following calculation
    The amount of clock cycles to that it took is the prescaler times the counter
    cnt*256
    To get the time, it should be devided with the clock frequency which is 16000000Hz
    cnt*256/16000000
    This is the time it takes for the ultrasound signal to travel to the target and back. '
    To get the time to the target, this time should be divided by 2
    time to target = cnt*256/16000000/2
    To get the distance we should multiply with the speed of sound. This is 343m/s or 34300cm/s
    Distance = cnt*256/16000000/2 * 34300
    To encure that we keep the calculation in a nice uint16 range (no floating point in target), the
    constant that cnt should me multipliued with is calculated: 256/16000000/2 * 343000 = 2.744. This
    value is not good, as it is a floating point. Another way of writing 2.755 is 343/125. This is better,
    as it is integers. In the calculation we make it UL (unsigned Long, 64bit)  to make sure that 343 times cnt
    does not overfloat before its devided by 125
    */
    cnt = cnt * 343UL / 125UL;
    return cnt;
}

//sensor added by us manually
uint16_t hc_sr04_takeMeasurement_water()
    {
        uint16_t cnt = 0;

        _delay_us(10);
        PORT_TrigW |= (1 << P_TrigW);
        _delay_us(10);
        PORT_TrigW &= ~(1 << P_TrigW);

        // Additional ports logic
        // You can customize the initialization and usage of additional ports as needed

        uint8_t TCCR1B_state = TCCR1B;

        // Set the Timer/Counter1 prescaler to 256
        TCCR1B = (1 << CS12);
        TCNT1 = 0;

        while (!(PIN_EchoW & (1 << P_EchoW)))
        {
            if (TCNT1 >= (F_CPU / 256) * 0.1)
            {
                return 0;
            }
        }

        TCNT1 = 0;

        while (PIN_EchoW & (1 << P_EchoW))
        {
            if (TCNT1 >= (F_CPU / 256) * 0.024)
            {
                break;
            }
        }

        cnt = TCNT1;

        TCCR1B = TCCR1B_state;

        // Additional ports measurement calculation
        cnt = cnt * 343UL / 125UL;
        return cnt;
    }