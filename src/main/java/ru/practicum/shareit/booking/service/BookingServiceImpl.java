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
import java.util.stream.Collectors;

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

        if (!booking.getItem().getAvailable()) {
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

        if (ownerId == booking.getItem().getOwner().getId()) {
            if (approved) {
                booking.setStatus(BookingStatus.APPROVED);
            } else {
                booking.setStatus(BookingStatus.REJECTED);
            }
            return BookingMapper.toBookingRS(bookingRepository.save(booking));
        } else {
            throw new BadRequestException("This user can't make this");
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
            throw new BadRequestException("User with id " + ownerId + " can't see this information");
        }
    }

    @Override
    public List<BookingDtoRS> getBookings(long ownerId, String state) {
        User user = userRepository.findById(ownerId).orElseThrow(() -> {
            throw new NotFoundException("User with id = " + ownerId + " not found");
        });

        //Бронирования должны возвращаться отсортированными по дате от более новых к более старым.
        switch (state) {
            case "ALL":
                return bookingRepository.findAllByBookerIdOrderByStartDesc(ownerId).stream()
                        .map(BookingMapper::toBookingRS)
                        .collect(Collectors.toList());
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
        User user = userRepository.findById(ownerId).orElseThrow(() -> {
            throw new NotFoundException("User with id = " + ownerId + " not found");
        });
        return null;
    }
}
