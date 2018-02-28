package imedevo.controller;

import imedevo.httpStatuses.NoSuchClinicException;
import imedevo.model.Diagnostic;
import imedevo.service.DiagnosticService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/diagnostics")
public class DiagnosticController {

  @Autowired
  private DiagnosticService diagnosticService;

  @GetMapping("/getall")
  public List<Diagnostic> getAllDiagnostics() {
    return diagnosticService.getAll();
  }

  @GetMapping("/{id}")
  public Diagnostic getDiagnosticById(@PathVariable Long id) throws NoSuchClinicException {
    return diagnosticService.getById(id);
  }

  @PostMapping("/admin/creatediagnostic")
  @PreAuthorize("hasAnyRole('CLINIC_ADMIN', 'SUPER_ADMIN')")
  public Map<String, Object> createDiagnostic(@RequestBody Diagnostic diagnostic) {
    return diagnosticService.saveDiagnostic(diagnostic);
  }

  @PutMapping("/admin/updatediagnostic")
  @PreAuthorize("hasAnyRole('CLINIC_ADMIN', 'SUPER_ADMIN')")
  public Map<String, Object> updateDiagnostic(@RequestBody Diagnostic diagnostic) {
    return diagnosticService.updateDiagnostic(diagnostic);
  }

  @DeleteMapping("/admin/deletediagnostic")
  @PreAuthorize("hasAnyRole('CLINIC_ADMIN', 'SUPER_ADMIN')")
  public void deleteDiagnostic(@RequestParam("id") Long id) {
    diagnosticService.deleteDiagnostic(id).orElseThrow(NoSuchClinicException::new);
  }

}
