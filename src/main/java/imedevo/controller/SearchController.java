package imedevo.controller;


import imedevo.service.SearchService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
  public Map<String, Object> searchDoctor(@RequestParam(name = "params") String params) {
    return searchService.findDoctorBySpecializatin(params);
  }

  @GetMapping("/clinic")
  public Map<String, Object> searchClinic(@RequestParam(name = "params") String clinicName) {
    return searchService.findClinicByName(clinicName);
  }

}
