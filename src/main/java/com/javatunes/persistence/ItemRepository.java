package com.javatunes.persistence;

import com.javatunes.domain.ServiceCategory;
import com.javatunes.domain.ServiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ItemRepository extends JpaRepository<ServiceItem, Long> {
    public Collection<ServiceItem> findByNameContaining(String name);

    public Collection<ServiceItem> findByCategory(ServiceCategory category);

    public Collection<ServiceItem> findByNameContainingAndCategory(String name, ServiceCategory status);

}
