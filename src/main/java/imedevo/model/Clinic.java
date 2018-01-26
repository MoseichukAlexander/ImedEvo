package imedevo.model;

import javax.persistence.*;

@Entity
@Table(name="clinics")
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "rating")
    private Double rating;
    @Column(name = "clinic_name")
    private String clinicName;
    @Column(name = "medical_license")
    private String medicalLicense;
    @Column(name = "address")
    private String address;
    @Column(name = "phone")
    private String phoneNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "days_of_admission")
    private String daysOfAdmission;
    @Column(name = "working_hours")
    private String workingHours;
    @Column(name = "description")
    private String description;
    @Column(name = "services")
    private String services;
    @Column(name = "coordinates_latitude")
    private Double  coordinatesLatitude;
    @Column(name = "coordinates_Longitude")
    private Double  coordinatesLongitude;
    @Column(name = "logo")
    private String logo;
    public Clinic(){

    }

    public Clinic(Double rating, String clinicName, String medicalLicense,
                  String address, String phoneNumber, String email,
                  String daysOfAdmission, String workingHours, String description,
                  String services, Double coordinatesLatitude, Double coordinatesLongitude, String logo) {
        this.rating = rating;
        this.clinicName = clinicName;
        this.medicalLicense = medicalLicense;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.daysOfAdmission = daysOfAdmission;
        this.workingHours = workingHours;
        this.description = description;
        this.services = services;
        this.coordinatesLatitude = coordinatesLatitude;
        this.coordinatesLongitude = coordinatesLongitude;
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "Clinic{" +
                "id=" + id +
                ", rating=" + rating +
                ", clinicName='" + clinicName + '\'' +
                ", medicalLicense='" + medicalLicense + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", daysOfAdmission='" + daysOfAdmission + '\'' +
                ", workingHours='" + workingHours + '\'' +
                ", description='" + description + '\'' +
                ", services='" + services + '\'' +
                ", coordinatesLatitude=" + coordinatesLatitude +
                ", coordinatesLongitude=" + coordinatesLongitude +
                ", logo='" + logo + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getMedicalLicense() {
        return medicalLicense;
    }

    public void setMedicalLicense(String medicalLicense) {
        this.medicalLicense = medicalLicense;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDaysOfAdmission() {
        return daysOfAdmission;
    }

    public void setDaysOfAdmission(String daysOfAdmission) {
        this.daysOfAdmission = daysOfAdmission;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public Double getCoordinatesLatitude() {
        return coordinatesLatitude;
    }

    public void setCoordinatesLatitude(Double coordinatesLatitude) {
        this.coordinatesLatitude = coordinatesLatitude;
    }

    public Double getCoordinatesLongitude() {
        return coordinatesLongitude;
    }

    public void setCoordinatesLongitude(Double coordinatesLongitude) {
        this.coordinatesLongitude = coordinatesLongitude;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
