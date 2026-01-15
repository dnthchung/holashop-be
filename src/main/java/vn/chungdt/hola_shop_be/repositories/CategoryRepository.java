package vn.chungdt.hola_shop_be.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.chungdt.hola_shop_be.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
