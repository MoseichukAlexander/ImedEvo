package imedevo.service;

import imedevo.httpStatuses.AccessDeniedException;
import imedevo.httpStatuses.DocStatus;
import imedevo.httpStatuses.UserNotFoundException;
import imedevo.model.AppUser;
import imedevo.model.Doctor;
import imedevo.model.Role;
import imedevo.model.UserRole;
import imedevo.repository.DoctorRepository;
import imedevo.repository.UserRepository;
import imedevo.repository.UserRoleRepository;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

/**
 * Service for (@link Doctor) class.
 */

@Service
public class DoctorService {

  @Autowired
  DoctorRepository doctorRepository;

  @Autowired
  UserService userService;

  @Autowired
  UserRepository userRepository;

  @Autowired
  UserRoleRepository userRoleRepository;

  @Autowired
  RolesService rolesService;

  @Autowired
  SpecializationService specializationService;


  public List<Doctor> getAll() {
    List<Doctor> listOfDoctors = doctorRepository.findAll();
    for (Doctor doctor : listOfDoctors) {
      doctor.getUser().setUserRoles(rolesService.getUserRoles(doctor.getUserId()));
    }
    return listOfDoctors;
  }

  public Map<String, Object> getById(long id) throws UserNotFoundException {
    Doctor doctor = doctorRepository.findById(id);
    if (doctor == null) {
      throw new UserNotFoundException();
    }
    Map<String, Object> map = new HashMap<>();
    doctor.getUser().setUserRoles(rolesService.getUserRoles(doctor.getUserId()));
    map.put("doctor", doctor);
    return map;
  }

  @Transactional
  public Map<String, Object> save(Doctor doctor) throws UserNotFoundException {
    Map<String, Object> map = new HashMap<>();

    if (doctor.getUserId() <= 0) {
      map.put("status", DocStatus.REGISTRATION_ERROR_INCORRECT_USER_ID);
      return map;
    }

//    if (doctor.getSpecialization() == null ) {
//      map.put("status", DocStatus.REGISTRATION_ERROR_INCORRECT_DOCTOR_SPECIALIZATION);
//      return map;
//    }

    if (doctor.getDoctorQualification() == null || doctor.getDoctorQualification().length() < 5) {
      map.put("status", DocStatus.REGISTRATION_ERROR_EMPTY_FIELD_DOCTOR_QUALIFICATION);
      return map;
    }

    if (doctor.getEducation() == null || doctor.getEducation().length() < 3) {
      map.put("status", DocStatus.REGISTRATION_ERROR_EMPTY_FIELD_DOCTOR_EDUCATION);
      return map;
    }

    if (doctor.getPrice() == 0) {
      map.put("status", DocStatus.REGISTRATION_ERROR_INCORRECT_PRICE_VALUE);
      return map;
    }

    if (doctor.getWorkExperience() == 0) {
      map.put("status", DocStatus.REGISTRATION_ERROR_INCORRECT_EXPERIENCE_VALUE);
      return map;
    }

    if (userRepository.findOne(doctor.getUserId()) == null) {
      map.put("status", DocStatus.REGISTRATION_ERROR_USER_NOT_EXIST);
      return map;
    }

    doctor.setUser(userService.getById(doctor.getUserId()));
    doctor.setId(doctor.getUserId());

    List<UserRole> userRoles = rolesService.getUserRoles(doctor.getUserId());
    for (UserRole userRole : userRoles) {
      if (userRole.getRole().equals(Role.DOCTOR)) {
        map.put("status", DocStatus.REGISTRATION_ERROR_DOCTOR_ALREADY);
        return map;
      }
    }
    userRoles.add(new UserRole(doctor.getUserId(), Role.DOCTOR));
    map.put("status", DocStatus.REGISTRATION_OK);
    map.put("doctor", doctorRepository.save(doctor));
    map.put("userRoles", rolesService.save(userRoles));
    return map;
  }

  @Transactional
  public Map<String, Object> updateDoctor(Doctor updatedDoctor)
      throws UserNotFoundException {
    Map<String, Object> map = new HashMap<>();

    Doctor doctorFromDb = doctorRepository.findOne(updatedDoctor.getUserId());

    if (doctorFromDb == null) {
      map.put("status", DocStatus.DOCTOR_NOT_FOUND);
    } else {

      AppUser userUpd = updatedDoctor.getUser();
      if (userUpd != null) {
        userUpd.setId(updatedDoctor.getUserId());
        AppUser userFromDb = userRepository.findOne(userUpd.getId());
        Field[] fields = userUpd.getClass().getDeclaredFields();
        AccessibleObject.setAccessible(fields, true);
        for (Field field : fields) {
          if (field.getName().equals("id") || field.getName().equals("password") ||
              field.getName().equals("dateOfRegistration") || field.getName().equals("userRoles")) {
            continue;
          }
          Object userFromDbValue = ReflectionUtils.getField(field, userUpd);
          if (userFromDbValue != null) {
            ReflectionUtils.setField(field, userFromDb, userFromDbValue);
          }
        }
        userRepository.save(userFromDb);
      }

      Field[] fields = updatedDoctor.getClass().getDeclaredFields();
      AccessibleObject.setAccessible(fields, true);
      for (Field field : fields) {
        if (field.getName().equals("userId") || field.getName().equals("reting") ||
            field.getName().equals("user")) {
          continue;
        }
        Object doctorFromDbValue = ReflectionUtils.getField(field, updatedDoctor);
        if (doctorFromDbValue != null) {
          ReflectionUtils.setField(field, doctorFromDb, doctorFromDbValue);
        }
      }
      map.put("status", DocStatus.EDIT_DOCTOR_PROFILE_SUCCESS);
      map.put("user", doctorRepository.save(doctorFromDb));
    }
    return map;
  }

  @Transactional
  public void delete(long userId) throws UserNotFoundException, AccessDeniedException {

    if (doctorRepository.findOne(userId) != null) {
      List<UserRole> userRoles = userRoleRepository.findByUserId(userId);
      for (UserRole userRole : userRoles) {
        if (userRole.getRole().equals(Role.DOCTOR)) {
          userRoleRepository.delete(userRole);
        }
      }
      doctorRepository.delete(userId);
    } else {
      throw new UserNotFoundException();
    }
  }
}
