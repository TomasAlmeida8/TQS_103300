package lab3_2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long>{
    public Car findByCarId(long id);
    public List<Car> findAll();
    
}
