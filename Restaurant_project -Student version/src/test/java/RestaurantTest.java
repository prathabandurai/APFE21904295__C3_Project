import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mockito;


class RestaurantTest {
    Restaurant restaurant;
    LocalTime openingTime = LocalTime.parse("10:30:00");
    LocalTime closingTime = LocalTime.parse("22:00:00");
    @BeforeEach
    void init() {

        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
    }

    @Test
    public void get_total_cost_of_the_items_selected(){

      List<Item> items = new ArrayList<>();

      items.add( new Item("Sweet corn soup",119));
      items.add(new  Item("Vegetable lasagne",269));
       var Total= restaurant.getTotalCost(items);
       assertEquals(Total,388);
    }

    @Test
    public void get_total_cost_when_items_not_selected(){

        List<Item> items = new ArrayList<>();

        var Total= restaurant.getTotalCost(items);
        assertEquals(Total,0);
    }

    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        Restaurant Restaurant = Mockito.spy(new Restaurant("test","chennai",openingTime,closingTime));
        Mockito.when(Restaurant.getCurrentTime()).thenReturn(LocalTime.parse("10:30:00"));
        Mockito.when(Restaurant.isRestaurantOpen()).thenReturn(true);
         boolean isRestaurantOpen= Restaurant.isRestaurantOpen();
         assertEquals(true,isRestaurantOpen);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        Restaurant Restaurant = Mockito.spy(new Restaurant("test","chennai",openingTime,closingTime));
        Mockito.when(Restaurant.getCurrentTime()).thenReturn(LocalTime.parse("09:45:00"));
        Mockito.when(Restaurant.isRestaurantOpen()).thenReturn(false);
        boolean isRestaurantOpen= Restaurant.isRestaurantOpen();
        assertEquals(false,isRestaurantOpen);
    }


    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }

    @AfterEach
    void dispose() {
        restaurant=null;
    }

}