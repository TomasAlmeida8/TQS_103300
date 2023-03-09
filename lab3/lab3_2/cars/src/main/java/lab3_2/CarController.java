package lab3_2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class CarController {
    @Autowired
    private CarService carSerive;

    @PostMapping("/newcar")
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        return new ResponseEntity<>(carSerive.save(car), HttpStatus.CREATED);
    }

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getAllCars() {
        return new ResponseEntity<>(carSerive.getAllCars(), HttpStatus.OK);
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable long id) {
        Car car = carSerive.getCarDetails(id);
        HttpStatus code;

        if (car == null) {
            code = HttpStatus.NOT_FOUND;
        } else {
            code = HttpStatus.OK;
        }

        return new ResponseEntity<>(car, code);
    }

}
