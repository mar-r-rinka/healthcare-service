import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;

public class SendAlertServiceImplTest {

    @Test
    public void sendTest_ArgumentCaptor() {
        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        sendAlertService.send("Привет");
        Mockito.verify(sendAlertService, Mockito.only()).send(argumentCaptor.capture());
    }

    @Test
    public void checkBloodPressureMockito_verify_never() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById(any()))
                .thenReturn(new PatientInfo("Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));
        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        BloodPressure currentPressure = new BloodPressure(120, 80);
        medicalService.checkBloodPressure(patientInfoRepository.getById(any()).getId(), currentPressure);
        Mockito.verify(sendAlertService, Mockito.never()).send(argumentCaptor.capture());
    }

    @Test
    public void checkBloodPressureMockito_verify_only() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById(any()))
                .thenReturn(new PatientInfo("Семен", "Михайлов", LocalDate.of(1982, 1, 16),
                        new HealthInfo(new BigDecimal("36.6"), new BloodPressure(125, 78))));
        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);
        BloodPressure currentPressure = new BloodPressure(120, 80);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        medicalService.checkBloodPressure(patientInfoRepository.getById(any()).getId(), currentPressure);
        Mockito.verify(sendAlertService, Mockito.only()).send(argumentCaptor.capture());
    }
    @Test
    public void checkTemperature_Mockito_verify_never() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById(any()))
                .thenReturn(new PatientInfo("Семен", "Михайлов", LocalDate.of(1982, 1, 16),
                        new HealthInfo(new BigDecimal("36.6"), new BloodPressure(125, 78))));
        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);
        BigDecimal currentTemperature = new BigDecimal("36.6");
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        medicalService.checkTemperature(patientInfoRepository.getById(any()).getId(), currentTemperature);
        Mockito.verify(sendAlertService, Mockito.never()).send(argumentCaptor.capture());
    }

    @Test
    public void checkTemperature_Mockito_verify_only() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById(any()))
                .thenReturn(new PatientInfo("Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("38.65"), new BloodPressure(120, 80))));
        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);
        BigDecimal currentTemperature = new BigDecimal("36.6");
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        medicalService.checkTemperature(patientInfoRepository.getById(any()).getId(), currentTemperature);
        Mockito.verify(sendAlertService, Mockito.only()).send(argumentCaptor.capture());
    }


}
