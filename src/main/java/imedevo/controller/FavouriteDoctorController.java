package imedevo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import imedevo.httpStatuses.UserNotFoundException;
import imedevo.model.Doctor;
import imedevo.model.FavouriteDoctor;
import imedevo.service.FavouriteDoctorService;

@RestController
@RequestMapping("/favouritedoctors")
public class FavouriteDoctorController {

  @Autowired
  private FavouriteDoctorService favouriteDoctorService;

  @GetMapping("/{id}")
  public List<Doctor> getFavouriteDoctors(@PathVariable long id) throws UserNotFoundException {
    return favouriteDoctorService.getById(id);
  }

  @PostMapping("/addfavouritedoctor")
  @PreAuthorize("hasAnyRole('USER', 'DOCTOR', 'CLINIC_ADMIN', 'SUPER_ADMIN', 'BLOGGER')")
  public Map<String, Object> addFavouriteDoctor(@RequestBody FavouriteDoctor favouriteDoctor) {
    return favouriteDoctorService.addFavouriteDoctor(favouriteDoctor);
  }

  @DeleteMapping("/deletefavouritedoctor")
  @PreAuthorize("hasAnyRole('USER', 'DOCTOR', 'CLINIC_ADMIN', 'SUPER_ADMIN', 'BLOGGER')")
  public Map<String, Object> deleteFavouriteDoctor(@RequestParam(name = "id") long id) {
    return favouriteDoctorService.deleteFavouriteDoctor(id);
  }
}
