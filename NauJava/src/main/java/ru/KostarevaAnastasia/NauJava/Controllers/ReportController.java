package ru.KostarevaAnastasia.NauJava.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.KostarevaAnastasia.NauJava.models.Report;
import ru.KostarevaAnastasia.NauJava.repositories.ReportRepository;
import ru.KostarevaAnastasia.NauJava.service.ReportService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportRepository reportRepository;

    @PostMapping("/generate")
    public ResponseEntity<Long> startReportGeneration() {
        Long reportId = reportService.createReport();
        reportService.generateReport(reportId);
        return ResponseEntity.ok(reportId);
    }

    @GetMapping(value = "/{id}/content", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getReportContent(@PathVariable Long id) {
        Report report = reportRepository.findById(id).orElse(null);
        if (report == null) {
            return ResponseEntity.notFound().build();
        }

        return switch (report.getStatus()) {
            case CREATED ->
                    ResponseEntity.ok("<html><body><h2>Отчёт ещё не сформировался</h2></body></html>");
            case ERROR -> {
                String errorMsg = report.getData() == null || report.getData().isEmpty()
                        ? "Неизвестная ошибка при генерации отчёта."
                        : report.getData();
                yield ResponseEntity.ok(
                        "<html><body><h2 style='color:red;'>Ошибка при генерации отчёта</h2><p>" + errorMsg + "</p></body></html>"
                );
            }
            case COMPLETED -> {
                String data = report.getData();
                yield ResponseEntity.ok(data != null ? data : "");
            }
            default -> ResponseEntity.ok("<html><body><h2>Неизвестный статус отчёта</h2></body></html>");
        };
    }
}
