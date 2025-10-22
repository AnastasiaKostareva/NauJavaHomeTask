package ru.KostarevaAnastasia.NauJava;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import ru.KostarevaAnastasia.NauJava.models.Question;

@Configuration
public class Config
{
    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Autowired
    private CommandProcessor commandProcessor;
    @Bean
    @Profile("!test")
    public CommandLineRunner commandScanner()
    {
        return args ->
        {
            try (Scanner scanner = new Scanner(System.in))
            {
                System.out.println("Введите команду. 'exit' для выхода.");
                while (true)
                {
// Показать приглашение для ввода
                    System.out.print("> ");
                    String input = scanner.nextLine();
// Выход из цикла, если введена команда "exit"
                    if ("exit".equalsIgnoreCase(input.trim()))
                    {
                        System.out.println("Выход из программы...");
                        break;
                    }
// Обработка команды
                    commandProcessor.processCommand(input);
                }
            }
        };
    }

    @PostConstruct
    public void printAppInfo() {
        System.out.println("Запущено приложение: " + appName + " v" + appVersion);
    }
}


