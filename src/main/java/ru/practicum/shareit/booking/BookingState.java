package ru.practicum.shareit.booking;

/**
 * По умолчанию равен ALL (англ. «все»).
 * Также он может принимать значения
 * CURRENT (англ. «текущие»),
 * PAST (англ. «завершённые»),
 * FUTURE (англ. «будущие»),
 * WAITING (англ. «ожидающие подтверждения»),
 * REJECTED (англ. «отклонённые»).
 */

public enum BookingState {
    ALL, CURRENT, PAST, FUTURE, WAITING, REJECTED
}
