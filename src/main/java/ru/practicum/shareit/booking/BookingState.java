package ru.practicum.shareit.booking;

public enum BookingState {
    /**
     * По умолчанию равен ALL (англ. «все»).
     * Также он может принимать значения
     * CURRENT (англ. «текущие»),
     * PAST (англ. «завершённые»),
     * FUTURE (англ. «будущие»),
     * WAITING (англ. «ожидающие подтверждения»),
     * REJECTED (англ. «отклонённые»).
     */
    ALL, CURRENT, PAST, FUTURE, WAITING, REJECTED
}
