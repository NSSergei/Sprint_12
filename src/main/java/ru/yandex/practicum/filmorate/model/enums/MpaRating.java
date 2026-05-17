package ru.yandex.practicum.filmorate.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MpaRating {
    G(1,"G"),
    PG(2,"PG"),
    PG_13(3,"PG-13"),
    R(4,"R"),
    NC_17(5,"NC-17");

    private final long id;
    private final String name;

    MpaRating(long id, String name) {
        this.id = id;
        this.name = name;
        }

    public static MpaRating fromId(long id) {
        for (MpaRating r : MpaRating.values()) {
            if (r.getId() == id) {
                return  r;
            }
        }
        throw new NotFoundException("Рейтинг не найден");
    }

    @JsonCreator
    public static MpaRating fromJson(JsonNode node) {
        if (node.has("id")) {
            return fromId(node.get("id").asLong());
        }
        return fromId(node.asLong());
    }
}