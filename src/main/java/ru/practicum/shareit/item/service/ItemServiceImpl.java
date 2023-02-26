package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.error.exception.BadRequestException;
import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.CommentMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.item.model.dto.CommentDto;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemDtoBooking;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private static final Sort SORT_START_DESC = Sort.by(DESC, "start");
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final BookingRepository bookingRepository;

    @Override
    public List<ItemDtoBooking> getUserItems(long userId) {
        List<Item> userItems = itemRepository.findByOwner_Id(userId);
        return setAllBookingsAndComments(userId, userItems);
    }

    @Override
    public ItemDtoBooking getById(long itemId, long userId) {
        Item item = itemRepository.findById(itemId).orElseThrow(()
                -> new NotFoundException("Item with id = " + itemId + " not found"));
        return setAllBookingsAndComments(userId, Collections.singletonList(item)).get(0);
    }

    @Override
    public ItemDto create(long userId, Item item) {
        item.setOwner(userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("User with id = " + userId + " not found")));

        return ItemMapper.toItemDto(itemRepository.save(item));
    }

    @Override
    public ItemDto update(long id, Item item, long ownerId) {
        Item reciveItem = itemRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Item with id = " + id + " not found"));

        //ownership check
        if (ownerId == reciveItem.getOwner().getId()) {
            if ((item.getName() != null) && (!item.getName().isBlank())) {
                reciveItem.setName(item.getName());
            }
            if ((item.getDescription() != null) && (!item.getDescription().isBlank())) {
                reciveItem.setDescription(item.getDescription());
            }
            if (item.getAvailable() != null) {
                reciveItem.setAvailable(item.getAvailable());
            }
            itemRepository.save(reciveItem);
            return ItemMapper.toItemDto(reciveItem);
        } else
            throw new NotFoundException("It is not possible to edit this item on behalf of the user with id = " + ownerId);
    }

    @Override
    public CommentDto createComment(long itemId, CommentDto commentDto, long authorId) {

        Item item = itemRepository.findById(itemId).orElseThrow(()
                -> new NotFoundException("Item with id = " + itemId + " not found"));
        User user = userRepository.findById(authorId).orElseThrow(()
                -> new NotFoundException("User with id = " + authorId + " not found"));

        Comment comment = CommentMapper.toComment(user, item, commentDto);

        if (!bookingRepository.findByBookerIdAndItemIdAndEndBefore(
                authorId,
                itemId,
                LocalDateTime.now(),
                SORT_START_DESC).isEmpty()) {
            comment.setItemId(item);
            comment.setAuthorId(user);
            comment.setCreated(LocalDateTime.now());
            commentRepository.save(comment);
            return CommentMapper.toCommentDto(comment);
        } else throw new BadRequestException("This User can not send comment to this Item");
    }

    @Override
    public List<ItemDto> search(String text) {
        return itemRepository.searchByText(text.toLowerCase()).stream()
                .map(ItemMapper::toItemDto)
                .collect(toList());
    }

    @Override
    public void delete(long id) {
        itemRepository.deleteById(id);
    }

    private List<ItemDtoBooking> setAllBookingsAndComments(long userId, List<Item> items) {

        //вытаскиваем айдишники наших предметов
        List<Long> ids = items.stream()
                .map(Item::getId)
                .collect(toList());

        //вытаскиваем все начавшиеся бронирования
        List<Booking> bookings = bookingRepository.findBookingsLast(
                ids,
                LocalDateTime.now(),
                userId,
                SORT_START_DESC);

        //мапим все наши предметы по айди в ДТО
        Map<Long, ItemDtoBooking> itemsMap = items.stream()
                .map(ItemMapper::toItemDtoBooking)
                .collect(Collectors.toMap(ItemDtoBooking::getId, x -> x, (a, b) -> b));

        // для всех начавшихся бронирований сетим последнее бронировнаие
        bookings.forEach(booking -> itemsMap.get(booking.getItem().getId())
                .setLastBooking(BookingMapper.toBookingDtoResponse(booking)));

        bookings = bookingRepository.findBookingsNext(
                ids,
                LocalDateTime.now(),
                userId,
                SORT_START_DESC);

        bookings.forEach(booking -> itemsMap.get(booking.getItem().getId())
                .setNextBooking(BookingMapper.toBookingDtoResponse(booking)));

        List<Comment> comments = commentRepository.findByItemId_IdIn(ids);

        comments.forEach(comment -> itemsMap.get(comment.getItemId().getId())
                .getComments().add(CommentMapper.toCommentDto(comment)));

        return new ArrayList<>(itemsMap.values());
    }
}
