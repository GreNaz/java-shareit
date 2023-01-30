package ru.practicum.shareit;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "ru.practicum")
// остальные аннотации
public class PersistenceConfig {
    // остальное содержимое конфигурационного класса
}