package imedevo.controller;


import imedevo.model.Clinic;
import imedevo.model.Doctor;
import imedevo.service.SearchService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for searching (@link Doctor) & (@link Clinic).
 */

@RestController
@RequestMapping("/search")
public class SearchController {

  @Autowired
  SearchService searchService;

  @GetMapping("/byanyparams")
  public Map<String, Object> searchByAnyParams(@RequestParam(name = "params") String searchParams) {
    return searchService.findByAnyParams(searchParams);
  }

  @GetMapping("/doctor")
  public List<Doctor> searchDoctor(@RequestParam(name = "params") String params) {
    return searchService.findDoctorBySpecializatin(params);
  }

  @GetMapping("/clinic")
  public List<Clinic> searchClinic(@RequestParam(name = "params") String clinicName) {
    return searchService.findClinicByName(clinicName);
  }
}
