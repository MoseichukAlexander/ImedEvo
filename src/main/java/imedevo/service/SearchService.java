package imedevo.service;


import imedevo.model.Clinic;
import imedevo.model.Doctor;
import imedevo.model.Specialization;
import imedevo.repository.ClinicRepository;
import imedevo.repository.DoctorRepository;
import imedevo.repository.SpecializationRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {


  @Autowired
  DoctorRepository doctorRepository;

  @Autowired
  ClinicRepository clinicRepository;

  @Autowired
  SpecializationRepository specializationRepository;


  @Transactional
  public Map<String, Object> findByAnyParams(String searchParams) {
    Map<String, Object> searchResult = new HashMap<>();

    List<Doctor> foundByDoctorSpecialization = findDoctorBySpecializatin(searchParams);
    List<Doctor> foundByDoctorLastname = findDoctorByLastname(searchParams);
    List<Clinic> foundByClinicName = findClinicByName(searchParams);

    if (foundByDoctorSpecialization.size() == 0 && foundByDoctorLastname.size() == 0) {
      searchResult.put("doctors", null);
    } else if (foundByDoctorSpecialization.size() != 0) {
      searchResult.put("doctors", foundByDoctorSpecialization);
    } else {
      searchResult.put("doctors", foundByDoctorLastname);
    }
    if (foundByClinicName.size() == 0) {
      searchResult.put("clinics", null);
    } else {
      searchResult.put("clinics", findClinicByName(searchParams));
    }
    return searchResult;
  }


  @Transactional
  public List<Doctor> findDoctorBySpecializatin(String searchParams) {
    List<Doctor> searchingResult = new ArrayList<>();

    if (searchParams.equals(null)) {
      return searchingResult;
    }
    Specialization foundSpecializatin = null;
    try {
      foundSpecializatin = specializationRepository.findBySpecializationName(searchParams);
    } catch (SQLGrammarException e) {
      System.err.println("SQLGrammarException: " + e.getMessage());
    } catch (NullPointerException e) {
      System.err.println("NullPointerException: " + e.getMessage());
    }
    if (foundSpecializatin == null || foundSpecializatin.getDoctors() == null) {
      return searchingResult;
    }
    return foundSpecializatin.getDoctors();
  }

  @Transactional
  public List<Doctor> findDoctorByLastname(String searchParams) {
    List<Doctor> foundByLastname = new ArrayList<>();

    if (searchParams.equals(null)) {
      return foundByLastname;
    }

    try {
      foundByLastname = doctorRepository.findByLastname(searchParams);
    } catch (SQLGrammarException e) {
      System.err.println("SQLGrammarException: " + e.getMessage());
    } catch (NullPointerException e) {
      System.err.println("NullPointerException: " + e.getMessage());
    }

    return foundByLastname;
  }

  @Transactional
  public List<Clinic> findClinicByName(String clinicName) {
    List<Clinic> foundClinics = new ArrayList<>();

    if (clinicName.equals(null)) {
      return foundClinics;
    }

    try {
      foundClinics = clinicRepository.findByClinicName(clinicName);
    } catch (SQLGrammarException e) {
      System.err.println("SQLGrammarException: " + e.getMessage());
    } catch (NullPointerException e) {
      System.err.println("NullPointerException: " + e.getMessage());
    }
    return foundClinics;
  }
}
