#include <unity.h>
#include "../src/HandleData/HandleData.h"

void test_demo(void)
{
    TEST_ASSERT_EQUAL_INT(1, 1);
}
int main(void)
{
    UNITY_BEGIN();
    
    return UNITY_END();
}