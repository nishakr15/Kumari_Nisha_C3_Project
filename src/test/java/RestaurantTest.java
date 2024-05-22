import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RestaurantTest {
    Restaurant restaurant;

    @BeforeEach
    public void setup(){
        // Arrange
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){

        // Arrange
        LocalTime mockedLocalTime = LocalTime.parse("14:00:00");
        Restaurant restaurantMock = Mockito.spy(restaurant);
        Mockito.doReturn(mockedLocalTime).when(restaurantMock).getCurrentTime();

        // Act
        boolean isRestaurantOpen = restaurantMock.isRestaurantOpen();

        // Assert
        assertTrue(isRestaurantOpen);
        Mockito.verify(restaurantMock).getCurrentTime();
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){

        // Arrange
        LocalTime mockedLocalTime = LocalTime.parse("09:00:00");
        Restaurant restaurantMock = Mockito.spy(restaurant);
        Mockito.doReturn(mockedLocalTime).when(restaurantMock).getCurrentTime();

        // Act
        boolean isRestaurantOpen = restaurantMock.isRestaurantOpen();

        // Assert
        assertFalse(isRestaurantOpen);
        Mockito.verify(restaurantMock).getCurrentTime();
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        // Arrange
        int initialMenuSize = restaurant.getMenu().size();

        // Act
        restaurant.addToMenu("Sizzling brownie",319);

        // Assert
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        // Arrange
        int initialMenuSize = restaurant.getMenu().size();

        // Act
        restaurant.removeFromMenu("Vegetable lasagne");

        // Assert
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        // Assert
        assertThrows(
                itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}