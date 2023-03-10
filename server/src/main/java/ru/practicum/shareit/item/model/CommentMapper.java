package ru.practicum.shareit.item.model;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.model.dto.CommentDto;
import ru.practicum.shareit.user.model.User;

@UtilityClass
public class CommentMapper {
    public static Comment toComment(User author, Item item, CommentDto commentDto) {
        return new Comment(commentDto.getId(), commentDto.getText(), item, author, commentDto.getCreated());
    }

    public static CommentDto toCommentDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText(), comment.getAuthorId().getName(), comment.getCreated());
    }
}
