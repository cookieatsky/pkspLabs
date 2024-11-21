package com.javatunes.domain;

import com.javatunes.persistence.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    private ItemRepository itemRepository;

    // Получение всех услуг
    @GetMapping
    public List<ServiceItem> getServices() {
        return itemRepository.findAll(); // Возвращает JSON-ответ с массивом услуг
    }

    // Добавление новой услуги
    @PostMapping
    public ServiceItem addService(@RequestBody ServiceItem serviceItem) {
        return itemRepository.save(serviceItem); // Возвращает добавленную услугу в формате JSON
    }

    // Удаление услуги
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Обновление услуги
    @PutMapping("/{id}")
    public ResponseEntity<ServiceItem> updateService(@PathVariable Long id, @RequestBody ServiceItem updatedService) {
        if (!itemRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedService.setId(id);
        ServiceItem savedService = itemRepository.save(updatedService);
        return ResponseEntity.ok(savedService);
    }

    // Поиск услуг
    @GetMapping("/search")
    public Collection<ServiceItem> searchServices(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category) {

        if (name != null && category != null) {
            return itemRepository.findByNameContainingAndCategory(name, ServiceCategory.valueOf(category));
        } else if (name != null) {
            return itemRepository.findByNameContaining(name);
        } else if (category != null) {
            return itemRepository.findByCategory(ServiceCategory.valueOf(category));
        } else {
            return itemRepository.findAll();
        }
    }

    // Получение услуги по ID
    @GetMapping("/{id}")
    public ResponseEntity<ServiceItem> getServiceById(@PathVariable Long id) {
        return itemRepository.findById(id) // Находим услугу по ID
                .map(serviceItem -> ResponseEntity.ok(serviceItem)) // Если найдена, возвращаем 200 OK
                .orElseGet(() -> ResponseEntity.notFound().build()); // Если не найдена, возвращаем 404 Not Found
    }

}