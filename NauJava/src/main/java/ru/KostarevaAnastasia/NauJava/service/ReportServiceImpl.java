package ru.KostarevaAnastasia.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.KostarevaAnastasia.NauJava.models.Question;
import ru.KostarevaAnastasia.NauJava.models.Report;
import ru.KostarevaAnastasia.NauJava.models.ReportData;
import ru.KostarevaAnastasia.NauJava.models.StatusToReport;
import ru.KostarevaAnastasia.NauJava.repositories.QuestionRepository;
import ru.KostarevaAnastasia.NauJava.repositories.ReportRepository;
import ru.KostarevaAnastasia.NauJava.repositories.UserRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public String getReportData(Long id) {
        return reportRepository
                .findById(id)
                .map(Report::getData)
                .orElse(null);
    }

    @Override
    public Long createReport() {
        Report report = new Report();
        report.setStatus(StatusToReport.CREATED);
        return reportRepository.save(report).getId();
    }

    @Override
    public CompletableFuture<Void> generateReport(Long id) {
        return CompletableFuture.runAsync(() -> {
            long totalStart = System.currentTimeMillis();

            AtomicReference<Long> userCount = new AtomicReference<>(0L);
            AtomicReference<Long> userTime = new AtomicReference<>(0L);
            AtomicReference<List<?>> questions = new AtomicReference<>();
            AtomicReference<Long> questionTime = new AtomicReference<>(0L);
            AtomicReference<Exception> errorRef = new AtomicReference<>();

            Thread userThread = new Thread(() -> {
                try {
                    long start = System.currentTimeMillis();
                    long count = userRepository.count();
                    long elapsed = System.currentTimeMillis() - start;
                    userCount.set(count);
                    userTime.set(elapsed);
                } catch (Exception e) {
                    errorRef.set(e);
                }
            });

            Thread questionThread = new Thread(() -> {
                try {
                    long start = System.currentTimeMillis();
                    Iterable<Question> iterable = questionRepository.findAll();
                    List<Question> list = StreamSupport.stream(iterable.spliterator(), false)
                            .collect(Collectors.toList());
                    long elapsed = System.currentTimeMillis() - start;
                    questions.set(list);
                    questionTime.set(elapsed);
                } catch (Exception e) {
                    errorRef.set(e);
                }
            });

            userThread.start();
            questionThread.start();

            try {
                userThread.join();
                questionThread.join();
            } catch (InterruptedException e) {
                errorRef.set(e);
                Thread.currentThread().interrupt();
            }

            Report report = reportRepository.findById(id).orElse(null);
            if (report == null) {
                return;
            }

            try {
                if (errorRef.get() != null) {
                    report.setStatus(StatusToReport.ERROR);
                    report.setData("Ошибка при генерации отчёта: " + errorRef.get().getMessage());
                } else {
                    long totalTime = System.currentTimeMillis() - totalStart;
                    ReportData reportData = new ReportData();
                    reportData.setUserCount(userCount.get());
                    reportData.setUserTime(userTime.get());
                    reportData.setQuestions(questions.get());
                    reportData.setQuestionTime(questionTime.get());
                    reportData.setTotalTime(totalTime);
                    String htmlContent = renderReportHtml(reportData);
                    report.setData(htmlContent);
                    report.setStatus(StatusToReport.COMPLETED);
                }
                reportRepository.save(report);

            } catch (Exception e) {
                report.setStatus(StatusToReport.ERROR);
                report.setData("Ошибка при завершении отчёта: " + e.getMessage());
                reportRepository.save(report);
            }
        });
    }

    private String renderReportHtml(ReportData data) {
        Context context = new Context();
        context.setVariable("userCount", data.getUserCount());
        context.setVariable("userTime", data.getUserTime());
        context.setVariable("questions", data.getQuestions());
        context.setVariable("questionTime", data.getQuestionTime());
        context.setVariable("totalTime", data.getTotalTime());
        return templateEngine.process("report", context);
    }
}
