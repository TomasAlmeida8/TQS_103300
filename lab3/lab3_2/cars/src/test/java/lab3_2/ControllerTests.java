package lab3_2;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(CarController.class)
public class ControllerTests {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarService service;

    @BeforeEach
    public void startUp() {

    }

    @Test
    public void whenPostCar_thenCreateCar() throws Exception {
        Car gucci = new Car("Fiat", "Gucci");

        when(service.save(any())).thenReturn(gucci);

        mvc.perform(post("/newcar")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(gucci)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.maker", is("Fiat")));

        verify(service, times(1)).save(any());
    }

    @Test
    public void whenGetAllCars_returnTheJsonArray() throws Exception {
        Car gucci = new Car("Fiat", "Gucci");
        Car ferrari = new Car("Ferrari", "F40");
        Car lamborghini = new Car("Lamborghini", "Aventador");

        List<Car> cars = Arrays.asList(gucci, ferrari, lamborghini);

        when(service.getAllCars()).thenReturn(cars);

        mvc.perform(get("/cars")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].maker", is("Fiat")))
            .andExpect(jsonPath("$[1].maker", is("Ferrari")))
            .andExpect(jsonPath("$[2].maker", is("Lamborghini")));

        verify(service, times(1)).getAllCars();
    }

    @Test
    public void whenGetCarById_thenReturnCorrectCar() throws Exception{
        Car gucci = new Car("Fiat", "Gucci");

        when(service.getCarDetails(anyLong())).thenReturn(gucci);

        mvc.perform(get("/cars/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.maker", is("Fiat")));

        verify(service, times(1)).getCarDetails(anyLong());
    }

    @Test
    public void whenBadCarId_thenReturnNull() throws Exception {

        when(service.getCarDetails(anyLong())).thenReturn(null);

        mvc.perform(get("/cars/2")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(service, times(1)).getCarDetails(anyLong());
    }
}
