package com.auspost.location.api.hateoas;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.auspost.location.api.controller.LocationController;
import com.auspost.location.api.entity.LocationEntity;
import com.auspost.location.api.model.Location;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * @author : github.com/deepakpal1982
 * @project : locationMS
 * @created : 14/07/2021, Tuesday
 **/
@Component
public class LocationRepresentationModelAssembler extends
RepresentationModelAssemblerSupport<LocationEntity, Location> {

/**
* Creates a new {@link RepresentationModelAssemblerSupport} using the given controller class and
* resource type.
*/
public LocationRepresentationModelAssembler() {
super(LocationController.class, Location.class);
}

/**
* Coverts the Location entity to resource
*
* @param entity
*/
@Override
public Location toModel(LocationEntity entity) {
Location resource = createModelWithId(entity.getId(), entity);
BeanUtils.copyProperties(entity, resource);
resource.setId(entity.getId().toString());

return resource;
}

/**
* Coverts the collection of Location entities to list of resources.
*
* @param entities
*/
public List<Location> toListModel(Iterable<LocationEntity> entities) {
if (Objects.isNull(entities)) {
  return Collections.emptyList();
}
return StreamSupport.stream(entities.spliterator(), false).map(e -> toModel(e))
    .collect(toList());
}

}
