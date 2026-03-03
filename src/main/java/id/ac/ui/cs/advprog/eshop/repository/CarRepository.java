package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import java.util.Iterator;

public interface CarRepository {
    public Car create(Car car);
    public Iterator<Car> findAll();
    public Car findById(String id);
    public Car update(String id, Car updatedCar);
    public void delete(String id);
}