package imedevo.controller;

import imedevo.httpStatuses.NoSuchClinicException;
import imedevo.model.Clinic;
import imedevo.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

@RestController
@RequestMapping("/clinics")
public class ClinicController {


  @Autowired
  private ClinicService clinicService;

  @GetMapping("/getall")
  public List<Clinic> getAllClinics() {
    return clinicService.getAll();

  }

  @GetMapping("/{id}")
  public Clinic getClinicById(@PathVariable Long id) throws NoSuchClinicException {
    return clinicService.getById(id);
  }

  @PostMapping("/createclinic")
  @PreAuthorize("hasAnyRole('CLINIC_ADMIN', 'SUPER_ADMIN')")
  public Map<String, Object> createClinic(@RequestBody Clinic clinic) {
    return clinicService.save(clinic);
  }

  @PutMapping("/updateclinic")
  @PreAuthorize("hasAnyRole('CLINIC_ADMIN', 'SUPER_ADMIN')")
  public Map<String, Object> updateClinic(@RequestBody Clinic clinic) {
    return clinicService.updateClinic(clinic);
  }

  @DeleteMapping("/deleteclinic/{id}")
  @PreAuthorize("hasAnyRole('CLINIC_ADMIN', 'SUPER_ADMIN')")
  public void deleteClinic(@PathVariable Long id) {
    clinicService.delete(id)
        .orElseThrow(NoSuchClinicException::new);
  }


}
