package imedevo.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

import imedevo.model.FavouriteDoctor;

public interface FavouriteDoctorsRepository extends CrudRepository<FavouriteDoctor, Long> {

  List<FavouriteDoctor> findByUserId(long userId);

  List<FavouriteDoctor> findByDoctorId(long doctorId);

}
