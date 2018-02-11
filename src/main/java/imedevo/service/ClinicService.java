package imedevo.service;


import imedevo.httpStatuses.DocStatus;
import imedevo.httpStatuses.HospitalStatus;
import imedevo.httpStatuses.NoSuchClinicException;
import imedevo.httpStatuses.UserStatus;
import imedevo.model.Clinic;
import imedevo.model.User;
import imedevo.repository.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.transaction.Transactional;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ClinicService {

    @Autowired
    private ClinicRepository clinicRepository;

    public List<Clinic> getAll() {
        return clinicRepository.findAll();
    }

    public Clinic getById(long id) throws NoSuchClinicException {
        Clinic clinic = clinicRepository.findOne(id);
        if (clinic == null) {
            throw new NoSuchClinicException();
        }
        return clinic;
    }

    @Transactional
    public Map<String, Object> save(Clinic clinic) {
        Map<String, Object> map = new HashMap<>();

            if (clinic.getLogo() == null) {
                map.put("status", HospitalStatus.REGISTRATION_ERROR_EMPTY_LOGO);
                return map;
            }

            if (clinic.getClinicName() == null) {
                map.put("status", HospitalStatus.REGISTRATION_ERROR_EMPTY_NAME);
                return map;
            }

            if (clinic.getMedicalLicense() == null) {
                map.put("status", HospitalStatus.REGISTRATION_ERROR_EMPTY_MEDICAL_LECENSE);
                return map;
            }

            if (clinic.getAddress() == null) {
                map.put("status", HospitalStatus.REGISTRATION_ERROR_EMPTY_ADDRESS);
                return map;
            }
            map.put("status", HospitalStatus.REGISTRATION_OK);
            map.put("clinic", clinicRepository.save(clinic));
            return map;
        }

        @Transactional
        public Map<String, Object> updateClinic (Clinic updatedClinic){
            Map<String, Object> map = new HashMap<>();
            if (updatedClinic.getEmail() != null) {
                Clinic checkClinicFromDb = clinicRepository.findByEmail(updatedClinic.getEmail());
                if (checkClinicFromDb != null && updatedClinic.getId() != checkClinicFromDb.getId()) {
                    map.put("status", HospitalStatus.EDIT_PROFILE_ERROR);
                    return map;
                }
            }
                Clinic clinicFromDb = clinicRepository.findOne(updatedClinic.getId());
                if (clinicFromDb == null) {
                    map.put("status", HospitalStatus.NOT_FOUND);
                } else {
                    Field[] fields = updatedClinic.getClass().getDeclaredFields();
                    AccessibleObject.setAccessible(fields, true);
                    for (Field field : fields) {
                        Object userFromDbValue = ReflectionUtils.getField(field, updatedClinic);
                        if (userFromDbValue != null) {
                            ReflectionUtils.setField(field, clinicFromDb, userFromDbValue);
                        }
                    }
                    map.put("status", HospitalStatus.EDIT_PROFILE_SUCCESS);
                    map.put("clinic", clinicRepository.save(clinicFromDb));
                }
            return map;
            }


        public Optional<Clinic> delete (Long id){
            Optional<Clinic> mayBeClinic = clinicRepository.findById(id);
            mayBeClinic.ifPresent(clinic -> clinicRepository.delete(clinic.getId()));
            return mayBeClinic;
        }


    }

