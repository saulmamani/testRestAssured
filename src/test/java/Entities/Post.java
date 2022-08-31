package Entities;

import lombok.Getter;
import lombok.Setter;

public class Post {
    @Getter @Setter
    String title;
    @Getter @Setter
    String body;
    @Getter @Setter
    int userId;
}
