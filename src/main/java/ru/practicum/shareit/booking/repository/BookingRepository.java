package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestResource
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByBookerIdOrderByStartDesc(Long ownerId);

    List<Booking> findByItem_Owner_IdOrderByStartDesc(Long id);

    List<Booking> findByBooker_IdAndStatusOrderByStatusDesc(Long id, BookingStatus status);

    List<Booking> findByItem_Owner_IdAndStatusOrderByStatusDesc(Long id, BookingStatus status);

    List<Booking> findByBooker_IdAndStartAfterOrderByStartDesc(Long ownerId, LocalDateTime start);

    List<Booking> findByItem_Owner_IdAndStartAfterOrderByStartDesc(Long ownerId, LocalDateTime start);

    List<Booking> findByBooker_IdAndEndAfterOrderByStartDesc(Long ownerId, LocalDateTime start);

    List<Booking> findByItem_Owner_IdAndEndAfterOrderByStartDesc(Long ownerId, LocalDateTime start);

    List<Booking> findByBooker_IdAndStartBeforeOrderByStartDesc(Long ownerId, LocalDateTime start);

    List<Booking> findByItem_Owner_IdAndStartBeforeOrderByStartDesc(Long ownerId, LocalDateTime start);
}
