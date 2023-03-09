package lab3_2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DataJpaTest
public class RepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    @Test
    public void whenInvalidId_thenReturnNull() {
        Car car = carRepository.findByCarId(-69);
        assertThat(car).isNull();
    }

    @Test
    public void whenGivenArray_persistAndFindAll() {
        Car gucci = new Car("Fiat", "Gucci");
        Car ferrari = new Car("Ferrari", "F40");
        Car lamborghini = new Car("Lamborghini", "Aventador");

        entityManager.persist(gucci);
        entityManager.persist(ferrari);
        entityManager.persist(lamborghini);
        entityManager.flush();

        List<Car> cars = carRepository.findAll();

        assertThat(cars)
        .hasSize(3)
        .extracting(Car::getMaker).contains("Fiat", "Ferrari", "Lamborghini");
    }
}
