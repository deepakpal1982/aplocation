package com.auspost.location.api.repository;

import com.auspost.location.api.entity.LocationEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * @author : github.com/deepakpal1982
 * @project : locationMS
 * @created : 14/07/2021, Tuesday
 **/
public interface LocationRepository extends JpaRepository<LocationEntity, UUID> {
    Iterable<LocationEntity> findBySuburbsOrPincode(String suburbs,String pincode);
}
