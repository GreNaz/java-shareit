package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.dto.BookingDtoRS;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.error.exception.BadRequestException;
import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    @Override
    public BookingDtoRS create(long userId, Booking booking) {

        if (userId == booking.getItem().getOwner().getId()) {
            throw new NotFoundException("Unable to book your own");
        }

        if (Boolean.FALSE.equals(booking.getItem().getAvailable())) {
            throw new BadRequestException("Item not available for booking now");
        }

        if (booking.getEnd().isBefore(booking.getStart())) {
            throw new BadRequestException("Wrong time to book this item");
        }

        booking.setStatus(BookingStatus.WAITING);

        return BookingMapper.toBookingRS(bookingRepository.save(booking));
    }

    @Override
    public BookingDtoRS update(long bookingId, long ownerId, boolean approved) {
        User user = userRepository.findById(ownerId).orElseThrow(() -> {
            throw new NotFoundException("User with id = " + ownerId + " not found");
        });
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> {
            throw new NotFoundException("Booking with id = " + bookingId + " not found");
        });
        if (booking.getStatus().equals(BookingStatus.APPROVED)
                || booking.getStatus().equals(BookingStatus.REJECTED)) {
            throw new BadRequestException("This booking can't changed status");
        }

        if (ownerId == booking.getItem().getOwner().getId()) {
            if (approved) {
                booking.setStatus(BookingStatus.APPROVED);
            } else {
                booking.setStatus(BookingStatus.REJECTED);
            }
            return BookingMapper.toBookingRS(bookingRepository.save(booking));
        } else {
            throw new NotFoundException("This user can't make this");
        }

    }

    @Override
    public BookingDtoRS get(long bookingId, long ownerId) {

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()
                -> new NotFoundException("Booking with id " + bookingId + " was not found"));

        if (ownerId == booking.getItem().getOwner().getId()
                || ownerId == booking.getBooker().getId()) {
            return BookingMapper.toBookingRS(booking);
        } else {
            throw new NotFoundException("User with id " + ownerId + " can't see this information");
        }
    }

    @Override
    public List<BookingDtoRS> getBookings(long ownerId, String state) {
        User user = userRepository.findById(ownerId).orElseThrow(() -> {
            throw new NotFoundException("User with id = " + ownerId + " not found");
        });
        ArrayList<Booking> bookings = new ArrayList<>();
        switch (state) {
            case "ALL":
                bookings.addAll(bookingRepository
                        .findAllByBookerIdOrderByStartDesc(ownerId));
                break;
            case "CURRENT":
                bookings.addAll(bookingRepository
                        .findByBooker_IdAndEndAfterOrderByStartDesc(ownerId, LocalDateTime.now()));
                break;
            case "PAST":
                bookings.addAll(bookingRepository
                        .findByBooker_IdAndStartBeforeOrderByStartDesc(ownerId, LocalDateTime.now()));
                break;
            case "FUTURE":
                bookings.addAll(bookingRepository
                        .findByBooker_IdAndStartAfterOrderByStartDesc(ownerId, LocalDateTime.now()));
                break;
            case "WAITING":
                bookings.addAll(bookingRepository
                        .findByBooker_IdAndStatusOrderByStatusDesc(ownerId, BookingStatus.WAITING));
                break;
            case "REJECTED":
                bookings.addAll(bookingRepository
                        .findByBooker_IdAndStatusOrderByStatusDesc(ownerId, BookingStatus.REJECTED));
                break;
            default:
                throw new BadRequestException("Unknown state: " + state);
        }
        return bookings.stream()
                .map(BookingMapper::toBookingRS)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDtoRS> getBookingFromOwner(long ownerId, String state) {
        User user = userRepository.findById(ownerId).orElseThrow(() -> {
            throw new NotFoundException("User with id = " + ownerId + " not found");
        });
        ArrayList<Booking> bookings = new ArrayList<>();
        switch (state) {
            case "ALL":
                bookings.addAll(bookingRepository.findByItem_Owner_IdOrderByStartDesc(ownerId));
                break;
            case "CURRENT":
                bookings.addAll(bookingRepository.findByItem_Owner_IdAndEndAfterOrderByStartDesc(ownerId, LocalDateTime.now()));
                break;
            case "PAST":
                bookings.addAll(bookingRepository.findByItem_Owner_IdAndStartBeforeOrderByStartDesc(ownerId, LocalDateTime.now()));
                break;
            case "FUTURE":
                bookings.addAll(bookingRepository.findByItem_Owner_IdAndStartAfterOrderByStartDesc(ownerId, LocalDateTime.now()));
                break;
            case "WAITING":
                bookings.addAll(bookingRepository.findByItem_Owner_IdAndStatusOrderByStatusDesc(ownerId, BookingStatus.WAITING));
                break;
            case "REJECTED":
                bookings.addAll(bookingRepository.findByItem_Owner_IdAndStatusOrderByStatusDesc(ownerId, BookingStatus.REJECTED));
                break;
            default:
                throw new BadRequestException("Unknown state: " + state);
        }
        return bookings.stream()
                .map(BookingMapper::toBookingRS)
                .collect(Collectors.toList());
    }
}
