package lab3_2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class APITests {
    
    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository carRepository;

    @AfterEach
    public void resetDb() {
        carRepository.deleteAll();
    }

    @Test
    public void whenPostValidCar_thenCreateCar() {
        Car gucci = new Car("Fiat", "Gucci");
        ResponseEntity<Car> entity = restTemplate.postForEntity("/newcar", gucci, Car.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        
        List<Car> cars = carRepository.findAll();
        assertThat(cars).extracting(Car::getMaker).containsOnly(gucci.getMaker());
    }

    @Test
    public void whenGetAllCars_returnJsonArray(){
        Car gucci = new Car("Fiat", "Gucci");
        Car ferrari = new Car("Ferrari", "F40");
        Car lamborghini = new Car("Lamborghini", "Aventador");
        carRepository.save(gucci);
        carRepository.save(ferrari);
        carRepository.save(lamborghini);

        ResponseEntity<List<Car>> response = restTemplate.exchange("/cars", HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>() {});
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getMaker).containsExactly(gucci.getMaker(), ferrari.getMaker(), lamborghini.getMaker());
    }

    @Test
    public void whenGetCarId_returnCar() {
        Car gucci = new Car("Fiat", "Gucci");
        carRepository.save(gucci);

        ResponseEntity<Car> response = restTemplate.exchange("/cars/" + gucci.getCarId(), HttpMethod.GET, null, Car.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Car> cars = carRepository.findAll();
        assertThat(cars).extracting(Car::getMaker).containsOnly(gucci.getMaker());
    }

    @Test
    public void whenGetNonExistingCarById_return404() {
        ResponseEntity<Car> response = restTemplate.exchange("/cars/1", HttpMethod.GET, null, Car.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        List<Car> cars = carRepository.findAll();
        assertThat(cars).isEmpty();
    }
}
