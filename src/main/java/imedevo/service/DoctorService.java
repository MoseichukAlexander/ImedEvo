package imedevo.service;

import imedevo.httpStatuses.AccessDeniedException;
import imedevo.httpStatuses.DocStatus;
import imedevo.httpStatuses.UserNotFoundException;
import imedevo.model.Doctor;
import imedevo.model.Role;
import imedevo.model.UserRole;
import imedevo.repository.DoctorRepository;
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
  UserRoleRepository userRoleRepository;

  @Autowired
  RolesService rolesService;

  public List<Doctor> getAll() {
    return doctorRepository.findAll();
  }

  public Doctor getById(long id) throws UserNotFoundException {
    Doctor doctor = doctorRepository.findById(id);
    if (doctor == null) {
      throw new UserNotFoundException();
    }
    return doctor;
  }

  @Transactional
  public Map<String, Object> save(Doctor doctor) throws UserNotFoundException {
    Map<String, Object> map = new HashMap<>();

    if (doctor.getUserId() == 0) {
      map.put("status", DocStatus.REGISTRATION_ERROR_INCORRECT_USER_ID);
      return map;
    }

    if (userService.getById(doctor.getUserId()) == null) {
      map.put("status", DocStatus.REGISTRATION_ERROR_USER_NOT_EXIST);
      return map;
    }
    doctor.setUser(userService.getById(doctor.getUserId()));

    map.put("status", DocStatus.REGISTRATION_OK);
    map.put("doctor", doctorRepository.save(doctor));

    List<UserRole> userRoles = rolesService.getUserRoles(doctor.getUserId());
    for (UserRole userRole : userRoles) {
      if (userRole.equals(Role.DOCTOR)) {
        return map;
      }
    }
    userRoles.add(new UserRole(doctor.getUserId(), Role.DOCTOR));
    rolesService.save(userRoles);
    map.put("userRoles", userRoles);
    return map;
  }

  @Transactional
  public Map<String, Object> updateDoctor(Doctor updatedDoctor)
      throws UserNotFoundException {
    Map<String, Object> map = new HashMap<>();

    if (updatedDoctor.getUserId() != 0) {

      updatedDoctor.setUser(userService.getById(updatedDoctor.getUserId()));

      Doctor checkDoctorFromDb = doctorRepository.findByUserId(updatedDoctor.getUserId());
      if (checkDoctorFromDb != null && updatedDoctor.getUserId() != checkDoctorFromDb.getUserId()) {
        map.put("status", DocStatus.EDIT_DOCTOR_PROFILE_ERROR);
        return map;
      }
    }

    Doctor doctorFromDb = doctorRepository.findOne(updatedDoctor.getUserId());
    if (doctorFromDb == null) {
      map.put("status", DocStatus.DOCTOR_NOT_FOUND);
    } else {
      Field[] fields = updatedDoctor.getClass().getDeclaredFields();
      AccessibleObject.setAccessible(fields, true);
      for (Field field : fields) {
        if (field.getName().equals("id") || field.getName().equals("reting")) {
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
//      List<UserRole> userRoles = doctorRepository.findOne(userId).getUser().getUserRoles();
      for (UserRole userRole : userRoleRepository.findByUserId(userId)) {
        if (userRoleRepository.findByUserId(userId).equals(Role.DOCTOR)) {
          userRoleRepository.delete(userRoleRepository.findByUserId(userId));
        }
      }
      doctorRepository.delete(userId);
    } else {
      throw new UserNotFoundException();
    }
  }
}
