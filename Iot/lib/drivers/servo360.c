#include <servo360.h>
#include "includes.h"

#define DDR_SERVO DDRE
#define P_SERVO PE3
#define PORT_SERVO PORTE
void servo360(int8_t speed)
{
    // Initialize as output
    DDR_SERVO |= (1 << P_SERVO);

    // Convert speed to pulse width in microseconds
    // 1500us pulse width for stop, 1000us for full speed in one direction, 2000us for full speed in the opposite direction
    uint16_t pulse_width_us;

    if (speed > 100)
        speed = 100;
    if (speed < -100)
        speed = -100;

    pulse_width_us = 1500 + (speed * 5);

    // Initialize Timer2 in normal mode
    uint8_t TCCR2A_state = TCCR2A;
    TCCR2A = 0;

    // Choose prescaler based on delay
    uint8_t TCCR2B_state = TCCR2B;
    TCCR2B = (1 << CS22) | (1 << CS21); // 256

    // Calculate the number of timer ticks needed for the specified delay
    uint8_t num_ticks = ((F_CPU / 1000000UL) * pulse_width_us) / 256;

    for (uint16_t i = 0; i < 50; i++) // Assuming it can get there in 1 second (50 * 20ms)
    {
        // Set PA1 high
        PORT_SERVO |= (1 << P_SERVO);
        // Reset the timer counter
        TCNT2 = 0;

        // Wait until the timer counter reaches the required ticks
        while (TCNT2 < num_ticks)
        {}

        // Set PA1 low
        PORT_SERVO &= ~(1 << P_SERVO);

        _delay_ms(20); // A delay to control the servo movement
    }

    TCCR2A = TCCR2A_state; // Restore Timer2 state
    TCCR2B = TCCR2B_state;
}

void rotate(uint8_t speed, int amountOfTurns){
    for (size_t i = 0; i < amountOfTurns+4; i++)
    {
        if (i<amountOfTurns)
        {
            servo360(speed);
            _delay_ms(1000);
        } else{
            servo360(0);
            break;
        }
    }
}