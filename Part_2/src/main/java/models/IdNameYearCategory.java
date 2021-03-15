package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class IdNameYearCategory {
    private Long id;
    private String book_name;
    private Short year;
    private String cat_name;
}