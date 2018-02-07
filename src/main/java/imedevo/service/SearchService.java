package imedevo.service;


import imedevo.httpStatuses.DocStatus;
import imedevo.model.Clinic;
import imedevo.model.Doctor;
import imedevo.repository.ClinicRepository;
import imedevo.repository.DoctorRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {


  @Autowired
  DoctorRepository doctorRepository;

  @Autowired
  ClinicRepository clinicRepository;

  @Transactional
  public Map<String, Object> findByAnyParams(String searchParams) {
    Map<String, Object> result = new HashMap<>();
    result.put("doctors", findDoctorBySpecializatin(searchParams));
//    result.put("doctors", findDoctorByLastname(searchParams));
    result.put("clinics", findClinicByName(searchParams));
    return result;
  }


  @Transactional
  public Map<String, Object> findDoctorBySpecializatin(String searchParams) {
    Map<String, Object> searchingResult = new HashMap<>();

    if (searchParams.equals(null)) {
      searchingResult.put("status", DocStatus.NOT_SPECIFIED_PARAMETERS);
      return searchingResult;
    }
    List<Doctor> foundBySpecializatin;
    try {
      foundBySpecializatin = doctorRepository.findByDoctorSpecialization(searchParams);
    }catch (Exception e){
      throw null;
    }
//    if (foundBySpecializatin == null) {
//      searchingResult.put("status", DocStatus.DOCTOR_PROFILE_NOT_EXIST);
//      return searchingResult;
//    } else {

//      searchingResult.put("status", DocStatus.REQUEST_PASSED);
      searchingResult.put("doctors", foundBySpecializatin);

      return searchingResult;
//    }
  }

  @Transactional
  public Map<String, Object> findDoctorByLastname(String searchParams) {
    Map<String, Object> searchingResult = new HashMap<>();

    if (searchParams.equals(null)) {
      searchingResult.put("status", DocStatus.NOT_SPECIFIED_PARAMETERS);
      return searchingResult;
    }

    if (doctorRepository.findByDoctorSpecialization(searchParams) == null) {
      searchingResult.put("status", DocStatus.DOCTOR_PROFILE_NOT_EXIST);
      return searchingResult;
    }

    List<Doctor> foundByLastname = doctorRepository.findByDoctorSpecialization(searchParams);
    searchingResult.put("status", DocStatus.REQUEST_PASSED);
    searchingResult.put("doctors", foundByLastname);

    return searchingResult;
  }

  @Transactional
  public Map<String, Object> findClinicByName(String clinicName) {
    Map<String, Object> searchingResult = new HashMap<>();

    List<Clinic> selectedClinics = clinicRepository.findByClinicName(clinicName);

    searchingResult.put("clinics", selectedClinics);

    return searchingResult;
  }


}
