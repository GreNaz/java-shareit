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

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    @Override
    public BookingDtoRS create(long userId, Booking booking) {

        if (userId == booking.getItem().getOwner().getId()) {
            throw new BadRequestException("Unable to book your own");
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

        if (approved) {
            booking.setStatus(BookingStatus.APPROVED);
            bookingRepository.save(booking);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
            bookingRepository.save(booking);
        }
        return BookingMapper.toBookingRS(booking);
    }

    @Override
    public List<BookingDtoRS> getWithState(long bookingId, long ownerId, String state) {
        // Бронирования должны возвращаться отсортированными по дате от более новых к более старым.
        switch (state) {
            case "ALL":
                break;
            case "CURRENT":
                break;
            case "PAST":
                break;
            case "FUTURE":
                break;
            case "WAITING":
                break;
            case "REJECTED":
                break;
            default:
                throw new BadRequestException("UNSUPPORTED_STATUS");
        }

        return null;
    }

    @Override
    public List<BookingDtoRS> getBookingFromOwner(long ownerId, String state) {
        return null;
    }
}
