package vn.chungdt.hola_shop_be.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {
    @NotEmpty(message = "category name la bat buoc")
    private String name;
}
