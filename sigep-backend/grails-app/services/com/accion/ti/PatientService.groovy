package com.accion.ti

import org.apache.commons.logging.LogFactory
import org.hibernate.transform.Transformers

class PatientService {

    private static final LOGGER = LogFactory.getLog(this)
    static transactional = false
    MessageService messageService
    UtilService utilService
//
//    ResponseListDTO<PatientSimpleDTO> listPagination(PaginationDTO paginationDTO) throws ServiceException {
//
//        try {
//
//            String search = "%${paginationDTO.search}%";
//            def criteria = Patient.createCriteria().list(max: paginationDTO.maxResults, offset: (paginationDTO.currentPage * paginationDTO.maxResults - paginationDTO.maxResults)) {
//                resultTransformer(Transformers.aliasToBean(PatientSimpleDTO.class))
//                createAlias 'person', 'person'
//
//                order("registrationDate", "desc")
//                or {
//                    ilike("", search)
//                    ilike("personidPatient.fullName", search)
//                    ilike("person.curp", search)
//
//                }
//
//                projections {
//                    property 'idPatient', 'idPatient'
//                    property 'person.fullName', 'fullName'
//                    property 'person.curp', 'curp'
//                    property 'registrationDate', 'registrationDate'
//                }
//            }
//
//
//            return new ResponseListDTO<PatientSimpleDTO>(items: criteria, totalCount: criteria.totalCount);
//
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage())
//            throw new ServiceException(messageService.getMessageException(e.getMessage()))
//        }
//
//    }
//
//    void register(PatientSaveDTO patientDTO) throws ServiceException {
//        Patient.withTransaction { status ->
//            try {
//
//                BranchOffice branchOffice = BranchOffice.findByPrincipal(1 as Short)
//                Person person = new Person()
//
//
//                /* Registro de dirección */
//                Address address = new Address()
//                address.interiorNumber = patientDTO.interiorNumber
//                address.outdoorNumber = patientDTO.outdoorNumber
//                address.postalCode = patientDTO.postalCode
//                address.street = patientDTO.street
//                address.federalEntityKey = patientDTO.federalEntity
//                address.municipalityKey = patientDTO.municipality
//                address.colonyKey = patientDTO.colony
//                address.save(flush: true, failOnError: true)
//
//
//                /* Registro de persona */
//                person.name = patientDTO.name
//                person.status = utilService.getStatus().status.toInteger()
//                person.firstLastname = patientDTO.firstLastname
//                person.secondLastname = patientDTO.secondLastname
//                person.curp = patientDTO.curp
//                person.gender = patientDTO.gender
//                person.birthDate = utilService.validateTypeData(patientDTO.birthDate)
//                person.birthFederalEntity = patientDTO.birthFederalEntity
//                person.maritalStatus = MaritalStatus.findById(patientDTO.maritalStatus as Long)
//                person.nationality = patientDTO.nationality
//                person.address = address
//                person.branchOffice = branchOffice
//
//                /* Registro de informacion de contacto */
//                String phone = patientDTO?.phone?.toString() ?: "0000000000";
//                ContactInformation contactInformation = new ContactInformation(email: patientDTO.email, phone: phone, homePhone: patientDTO.homePhone?.toString())
//                contactInformation.save(flush: true, failOnError: true)
//                person.contactInformation = contactInformation
//
//
//                person.save(flush: true, failOnError: true)
//
//                /* Registro de foto */
//                if (patientDTO.imagenBase64) {
//                    PersonPhoto personPhoto = new PersonPhoto(person: person, photo: patientDTO.imagenBase64)
//                    personPhoto.save(flush: true, failOnError: true)
//                }
//
//
//                /* Registro de paciente */
//
//                Patient patient = new Patient()
//                patient.setPatientNumber("")
//                patient.setRegistrationDate(new Date())
//                patient.setPerson(person)
//
//                HealthInsurance healthInsurance = HealthInsurance.findByName(patientDTO?.healthInsurance?.name)
//
//                if (patientDTO?.healthInsurance?.name?.equalsIgnoreCase("OTRO")) {
//                    healthInsurance = HealthInsurance.findByName(patientDTO.nameHealthInsurance)
//                    if (healthInsurance == null) {
//                        healthInsurance = new HealthInsurance(name: patientDTO.nameHealthInsurance, description: patientDTO.nameHealthInsurance)
//                        healthInsurance.save(flush: true, failOnError: true)
//                    }
//                    patient.setMembershipNumber(patientDTO.getMembershipNumber())
//                } else if (patientDTO?.healthInsurance?.name?.equalsIgnoreCase("IMSS")) {
//                    String idee = patientDTO.getImssNumber() + patientDTO.getMedicalAggregated();
//                    patient.setIdee(idee ?: "")
//                    patient.setImssNumber(patientDTO.getImssNumber())
//                    patient.setMedicalAggregated(patientDTO.getMedicalAggregated())
//                } else {
//                    patient.setMembershipNumber(patientDTO.membershipNumber)
//                }
//
//                patient.setHealthInsurance(healthInsurance)
//                patient.save(flush: true, failOnError: true)
//
//                /* Registro de evento clínico */
//                ClinicalEvent clinicalEvent = new ClinicalEvent()
//                clinicalEvent.setRegistrationDate(new Date())
//                clinicalEvent.setStatus(utilService.getStatus("Activo", "ClincalEvent").status.toInteger())
//                clinicalEvent.setEventType(1 as Short)
//                clinicalEvent.setPatient(patient)
//                clinicalEvent.save(flush: true, failOnError: true)
//
//                /* Registro de cita */
//                HumanResource currentHumanResource = utilService.getCurrentHumanResource();
//
//                String nameReception = "Recepción";
//                ConsultingRoom consultingRoomOrigin = ConsultingRoom.findByNameAndBranchOffice(nameReception, branchOffice)
//                if (consultingRoomOrigin == null) {
//                    consultingRoomOrigin = new ConsultingRoom(name: nameReception, status: 1, branchOffice: branchOffice)
//                    consultingRoomOrigin.save(flush: true, failOnError: true)
//                }
//                String nameDestination = "General";
//                ConsultingRoom consultingRoomDestination = ConsultingRoom.findByNameAndBranchOffice(nameDestination, branchOffice)
//                if (consultingRoomDestination == null) {
//                    consultingRoomDestination = new ConsultingRoom(name: nameDestination, status: 1, branchOffice: branchOffice)
//                    consultingRoomDestination.save(flush: true, failOnError: true)
//                }
//
//
//                Appointment appointment = new Appointment()
//                appointment.setRegistrationDate(new Date())
//                appointment.setReason("")
//                appointment.setSpeciality(Speciality.findById(1 as Long))
//                appointment.setType(1 as Short)
//                appointment.setStartTime(new Date())
//                appointment.setEndTime(new Date())
//                appointment.setTurn(4 as Short)
//                appointment.setServiceType(1 as Short)
//                appointment.setStatus(AppointmentStatusEnum.PENDIENTE.getValue())
//                appointment.setClinicalEvent(clinicalEvent)
//                appointment.setConsultingRoomDestination(consultingRoomDestination)
//                appointment.setConsultingRoomOrigin(consultingRoomOrigin)
//                appointment.setHumanResourceDestination(currentHumanResource)
//                appointment.setHumanResourceOrigin(currentHumanResource)
//                appointment.save(flush: true, failOnError: true)
//
//                /* Registro de consulta */
//                MedicalPractice medicalPractice = new MedicalPractice()
//                medicalPractice.setRegistrationDate(new Date())
//                medicalPractice.setStatus(utilService.getStatus("Pendiente", "Medicalpractice").status.toInteger())
//                medicalPractice.setSpeciality(Speciality.findById(1 as Long))
//                medicalPractice.setAppointment(appointment)
//
//                medicalPractice.save(flush: true, failOnError: true)
//
//            } catch (Exception e) {
//                status.setRollbackOnly()
//                LOGGER.error(messageService.getMessageErrors(e))
//                throw new ServiceException(messageService.getMessageErrors(e))
//            }
//        }
//    }
//
//    PatientSaveDTO get(String idPatient) {
//        try {
//
//            Patient patient = Patient.get(idPatient)
//            if (patient == null) {
//                throw new ServiceException(messageService.getMessage("default.error.not.found", "Paciente", idPatient))
//            }
//
//            PatientSaveDTO patientDTO = new PatientSaveDTO();
//            patientDTO.idPatient = idPatient
//            patientDTO.birthDate = utilService.formatDate(patient.person?.birthDate)
//            patientDTO.birthFederalEntity = patient.person?.birthFederalEntity
//            patientDTO.colony = patient.person?.address?.colonyKey
//            patientDTO.curp = patient.person?.curp
//            patientDTO.email = patient.person?.contactInformation?.email
//            patientDTO.federalEntity = patient.person?.address?.federalEntityKey
//            patientDTO.firstLastname = patient?.person?.firstLastname
//            patientDTO.gender = patient?.person?.gender
//            patientDTO.healthInsurance = new HealthInsuranceDTO(
//                    idHealthInsurance: patient?.healthInsurance?.idHealthInsurance,
//                    name: patient?.healthInsurance?.name)
//            patientDTO.homePhone = patient?.person?.contactInformation?.homePhone?.toString()?.isLong() ? patient?.person?.contactInformation?.homePhone?.toLong() : null
//            patientDTO.currentImagenBase64 = patient?.person?.personPhoto?.photo
//            patientDTO.maritalStatus = patient?.person?.maritalStatus?.id?.toInteger()
//            patientDTO.imssNumber = patient?.imssNumber
//            patientDTO.medicalAggregated = patient?.medicalAggregated
//            patientDTO.membershipNumber = patient?.membershipNumber
//            patientDTO.municipality = patient.person?.address?.municipalityKey
//            patientDTO.name = patient.person?.name
//            patientDTO.nameHealthInsurance = null
//            patientDTO.nationality = patient?.person?.nationality
//            patientDTO.outdoorNumber = patient.person?.address?.outdoorNumber
//            patientDTO.interiorNumber = patient.person?.address?.interiorNumber
//            patientDTO.phone = patient?.person?.contactInformation?.phone?.toString()?.isLong() ? patient?.person?.contactInformation?.phone?.toLong() : 0L
//            patientDTO.postalCode = patient.person?.address?.postalCode
//            patientDTO.secondLastname = patient?.person?.secondLastname
//            patientDTO.street = patient.person?.address?.street
//
//            return patientDTO;
//        } catch (Exception e) {
//            LOGGER.error(messageService.getMessageErrors(e))
//            throw new ServiceException(messageService.getMessageErrors(e))
//        }
//    }
//
//
//    void update(PatientSaveDTO patientDTO) throws ServiceException {
//        Patient.withTransaction { status ->
//            try {
//
//                Patient patient = Patient.get(patientDTO.idPatient)
//                if (patient == null) {
//                    throw new ServiceException(messageService.getMessage("default.error.not.found", "Paciente", patientDTO.idPatient))
//                }
//
//                Person person = patient.person?:new Person(patient: patient)
//
//                BranchOffice branchOffice = person.branchOffice ?: BranchOffice.findByPrincipal(1 as Short)
//
//
//                /* Actualizar dirección */
//                Address address = person?.address ?: new Address()
//                address.interiorNumber = patientDTO.interiorNumber
//                address.outdoorNumber = patientDTO.outdoorNumber
//                address.postalCode = patientDTO.postalCode
//                address.street = patientDTO.street
//                address.federalEntityKey = patientDTO.federalEntity
//                address.municipalityKey = patientDTO.municipality
//                address.colonyKey = patientDTO.colony
//                address.save(flush: true, failOnError: true)
//
//
//                /* Actualizar persona */
//                person.name = patientDTO.name
//                person.status = person.status == null ? utilService.getStatus().status.toInteger() : person.status
//                person.firstLastname = patientDTO.firstLastname
//                person.secondLastname = patientDTO.secondLastname
//                person.curp = patientDTO.curp
//                person.gender = patientDTO.gender
//                person.birthDate = utilService.validateTypeData(patientDTO.birthDate) as Date
//                person.birthFederalEntity = patientDTO.birthFederalEntity
//                person.maritalStatus = MaritalStatus.findById(patientDTO.maritalStatus as Long)
//                person.nationality = patientDTO.nationality
//                person.address = address
//                person.branchOffice = branchOffice
//
//                /*Actualizar informacion de contacto */
//                String phone = patientDTO?.phone?.toString() ?: "0000000000";
//                ContactInformation contactInformation = person?.contactInformation ?: new ContactInformation()
//                contactInformation.email = patientDTO.email
//                contactInformation.phone = phone
//                contactInformation.homePhone = patientDTO.homePhone?.toString()
//                contactInformation.save(flush: true, failOnError: true)
//                person.contactInformation = contactInformation
//
//
//                person.save(flush: true, failOnError: true)
//
//                /* Actualizar de foto */
//                if (patientDTO.imagenBase64) {
//                    PersonPhoto personPhoto = person.personPhoto ?: new PersonPhoto(person: person)
//                    personPhoto.photo = patientDTO.imagenBase64
//                    personPhoto.save(flush: true, failOnError: true)
//                }
//
//
//                /* Registro de paciente */
//
//                patient.setPatientNumber(patient.getPatientNumber() ?: "")
//                patient.setPerson(person)
//
//                HealthInsurance healthInsurance = HealthInsurance.findByName(patientDTO?.healthInsurance?.name)
//
//                if (patientDTO?.healthInsurance?.name?.equalsIgnoreCase("OTRO")) {
//                    healthInsurance = HealthInsurance.findByName(patientDTO.nameHealthInsurance)
//                    if (healthInsurance == null) {
//                        healthInsurance = new HealthInsurance(name: patientDTO.nameHealthInsurance, description: patientDTO.nameHealthInsurance)
//                        healthInsurance.save(flush: true, failOnError: true)
//                    }
//                    patient.setIdee(null)
//                    patient.setImssNumber(null)
//                    patient.setMedicalAggregated(null)
//                    patient.setMembershipNumber(patientDTO.getMembershipNumber())
//                } else if (patientDTO?.healthInsurance?.name?.equalsIgnoreCase("IMSS")) {
//                    String idee = patientDTO.getImssNumber() + patientDTO.getMedicalAggregated();
//                    patient.setIdee(idee ?: "")
//                    patient.setImssNumber(patientDTO.getImssNumber())
//                    patient.setMedicalAggregated(patientDTO.getMedicalAggregated())
//                    patient.setMembershipNumber(null)
//                } else {
//                    patient.setIdee(null)
//                    patient.setImssNumber(null)
//                    patient.setMedicalAggregated(null)
//                    patient.setMembershipNumber(patientDTO.membershipNumber)
//                }
//
//                patient.setHealthInsurance(healthInsurance)
//                patient.save(flush: true, failOnError: true)
//
//
//            } catch (Exception e) {
//                status.setRollbackOnly()
//                LOGGER.error(messageService.getMessageErrors(e))
//                throw new ServiceException(messageService.getMessageErrors(e))
//            }
//        }
//    }
//
//    def getBasicInformation(String idPatient){
//        try {
//
//            def patientMap = Patient.createCriteria().get(){
//                resultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
//                createAlias 'person', 'person'
//                eq("idPatient", idPatient)
//                projections {
//                    property 'idPatient', 'idPatient'
//                    property 'person.fullName', 'fullName'
//                    property 'person.birthDate', 'birthDate'
//                    property 'person.curp', 'curp'
//                    property 'person.gender', 'gender'
//                }
//            }
//            if( patientMap ){
//                patientMap.yearsOlg = utilService.getYearsOlg(patientMap.birthDate)
//                patientMap.gender = GenderEnum.getDescription(patientMap.gender)
//            }
//
//
//            return patientMap
//        } catch (Exception e) {
//            LOGGER.error(messageService.getMessageErrors(e))
//            throw new ServiceException(messageService.getMessageErrors(e))
//        }
//    }


}
