package imedevo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import imedevo.httpStatuses.NoSuchClinicException;
import imedevo.model.Laboratory;
import imedevo.service.LaboratoryService;

@RestController
@RequestMapping("/laboratories")
public class LaboratoryController {

  @Autowired
  private LaboratoryService laboratoryService;

  @GetMapping("/getall")
  public List<Laboratory> getAllLaboratories() {
    return laboratoryService.getAll();
  }

  @GetMapping("/{id}")
  public Laboratory getLaboratoryById(@PathVariable Long id) throws NoSuchClinicException {
    return laboratoryService.getById(id);
  }

  @PostMapping("/admin/createlaboratory")
  @PreAuthorize("hasAnyRole('CLINIC_ADMIN', 'SUPER_ADMIN')")
  public Map<String, Object> createLaboratory(@RequestBody Laboratory laboratory) {
    return laboratoryService.saveLaboratory(laboratory);
  }

  @PutMapping("/admin/updatelaboratory")
  @PreAuthorize("hasAnyRole('CLINIC_ADMIN', 'SUPER_ADMIN')")
  public Map<String, Object> updateLaboratory(@RequestBody Laboratory laboratory) {
    return laboratoryService.updateLaboratory(laboratory);
  }

  @DeleteMapping("/admin/deletelaboratory")
  @PreAuthorize("hasAnyRole('CLINIC_ADMIN', 'SUPER_ADMIN')")
  public void deleteLaboratory(@RequestParam ("id") Long id) {
    laboratoryService.deleteLaboratory(id).orElseThrow(NoSuchClinicException::new);
  }

}
