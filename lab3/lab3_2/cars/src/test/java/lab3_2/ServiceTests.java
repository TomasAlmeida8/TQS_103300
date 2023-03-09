package lab3_2;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ServiceTests {
    @Mock(lenient = true)
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    public void startUp() {
        Car gucci = new Car("Fiat", "Gucci");
        gucci.setId(69);
        Car ferrari = new Car("Ferrari", "F40");
        Car lamborghini = new Car("Lamborghini", "Aventador");

        when(carRepository.findAll()).thenReturn(Arrays.asList(gucci, ferrari, lamborghini));
        when(carRepository.findByCarId(69)).thenReturn(gucci);

        List<Car> cars = Arrays.asList(gucci, ferrari, lamborghini);
        when(carRepository.saveAll(cars)).thenReturn(cars);
    }

    @Test
    public void whenIdGood_thenReturnCar() {
        Car car = carService.getCarDetails(69);

        assertThat(car.getMaker()).isEqualTo("Fiat");
    }

    @Test
    public void whenIdBad_thenReturnNull() {
        Car car = carService.getCarDetails(123);

        assertThat(car).isNull();
    }
    
    @Test
    public void whenGetAll_thenReturnAllCars() {
        List<Car> cars = carService.getAllCars();

        assertThat(cars)
        .hasSize(3)
        .extracting(Car::getMaker).contains("Fiat", "Ferrari", "Lamborghini");

        verify(carRepository, times(1)).findAll();
    }
}
