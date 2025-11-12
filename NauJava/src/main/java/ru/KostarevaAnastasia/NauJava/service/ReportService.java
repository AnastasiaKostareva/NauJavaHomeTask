package ru.KostarevaAnastasia.NauJava.service;

import java.util.concurrent.CompletableFuture;

/**
 * Сервис для управления отчетами
 */
public interface ReportService {
    /**
     * Метод получения содержимого отчета
     * @param id идентификатор запрашиваемого отчета
     * @return содержимое отчета в виде строки
     */
    String getReportData(Long id);

    /**
     * Метод для создания отчета (При создании статус отчета по
     * умолчанию имеет значение “создан”)
     * @return идентификатор созданного отчета
     */
    Long createReport();

    /**
     * Асинхронный метод формирования отчета
     * @param id идентификатор запрашиваемого отчета
     * @return CompletableFuture, завершающийся после окончания формирования отчёта
     */
    CompletableFuture<Void> generateReport(Long id);
}
