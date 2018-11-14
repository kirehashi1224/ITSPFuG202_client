package jp.ac.titech.itpro.sdl.itspfug202;

import org.junit.Assert;
import org.junit.Test;

import jp.ac.titech.itpro.sdl.itspfug202.model.Restaurant;
import jp.ac.titech.itpro.sdl.itspfug202.model.Restaurant.*;

public class RestaurantTest {
    @Test
    public void testIsOpen() {
        Restaurant restaurant = new Restaurant();
        TimeSpanList timeSpanList = new Restaurant().new TimeSpanList(7);
        restaurant.setTimeSpanList(timeSpanList);

        Time open1 = new Restaurant().new Time(12, 34);
        Time close1 = new Restaurant().new Time(16, 54);
        TimeSpan timeSpan1 = new Restaurant().new TimeSpan(open1, close1);
        timeSpanList.setTimeSpanList(DayOfWeek.SUNDAY, timeSpan1);
        Time open2 = new Restaurant().new Time(25, 0);
        Time close2 = new Restaurant().new Time(26, 0);
        TimeSpan timeSpan2 = new Restaurant().new TimeSpan(open2, close2);
        timeSpanList.setTimeSpanList(DayOfWeek.THURSDAY, timeSpan2);
        Assert.assertTrue(restaurant.isOpen(DayOfWeek.SUNDAY, new Restaurant().new Time(14, 59)));
        Assert.assertTrue(restaurant.isOpen(DayOfWeek.SUNDAY, new Restaurant().new Time(12, 34)));
        Assert.assertTrue(restaurant.isOpen(DayOfWeek.SUNDAY, new Restaurant().new Time(16, 54)));
        Assert.assertFalse(restaurant.isOpen(DayOfWeek.SUNDAY, new Restaurant().new Time(16, 55)));
        Assert.assertFalse(restaurant.isOpen(DayOfWeek.SUNDAY, new Restaurant().new Time(12, 33)));
        Assert.assertFalse(restaurant.isOpen(DayOfWeek.SUNDAY, new Restaurant().new Time(17, 0)));
        Assert.assertFalse(restaurant.isOpen(DayOfWeek.SUNDAY, new Restaurant().new Time(11, 59)));
        Assert.assertFalse(restaurant.isOpen(DayOfWeek.SATURDAY, new Restaurant().new Time(14, 0)));
        Assert.assertFalse(restaurant.isOpen(DayOfWeek.MONDAY, new Restaurant().new Time(14, 0)));
        Assert.assertFalse(restaurant.isOpen(DayOfWeek.FRIDAY, new Restaurant().new Time(0, 59)));
        Assert.assertFalse(restaurant.isOpen(DayOfWeek.FRIDAY, new Restaurant().new Time(2, 1)));
        Assert.assertTrue(restaurant.isOpen(DayOfWeek.FRIDAY, new Restaurant().new Time(1, 0)));
    }
}
