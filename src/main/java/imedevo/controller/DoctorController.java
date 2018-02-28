package imedevo.controller;

import imedevo.httpStatuses.AccessDeniedException;
import imedevo.httpStatuses.UserNotFoundException;
import imedevo.model.Doctor;
import imedevo.service.DoctorService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for (@link Doctor) class.
 */

@RestController
@RequestMapping("/doctors")
public class DoctorController {

  @Autowired
  DoctorService doctorService;

  @GetMapping("/getall")
  public List<Doctor> getAllDoctors() {
    return doctorService.getAll();
  }

  @GetMapping("/{id}")
  public Map<String, Object> getDoctorById(@PathVariable long id) throws UserNotFoundException {
    return doctorService.getById(id);
  }

  @PostMapping("/createdoctor")
  @PreAuthorize("hasAnyRole('CLINIC_ADMIN', 'SUPER_ADMIN')")
  public Map<String, Object> createDoctor(@RequestBody Doctor doctor) throws UserNotFoundException {
    return doctorService.save(doctor);
  }

  @PutMapping("/updatedoctor")
  @PreAuthorize("hasAnyRole('DOCTOR', 'CLINIC_ADMIN', 'SUPER_ADMIN')")
  public Map<String, Object> updateDoctor(@RequestBody Doctor doctor)
      throws UserNotFoundException {
    return doctorService.updateDoctor(doctor);
  }

  @DeleteMapping("/deletedoctor/{id}")
  @PreAuthorize("hasAnyRole('CLINIC_ADMIN', 'SUPER_ADMIN')")
  public void deleteDoctor(@PathVariable long id)
      throws UserNotFoundException, AccessDeniedException {
    doctorService.delete(id);
  }

}
