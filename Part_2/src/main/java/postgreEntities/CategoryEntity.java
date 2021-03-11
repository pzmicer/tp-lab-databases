package postgreEntities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "category", schema = "public", catalog = "postgres")
public class CategoryEntity {
    private Long id;
    private String catName;
    private String catDescription;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "cat_name")
    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    @Basic
    @Column(name = "cat_description")
    public String getCatDescription() {
        return catDescription;
    }

    public void setCatDescription(String catDescription) {
        this.catDescription = catDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryEntity that = (CategoryEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(catName, that.catName) && Objects.equals(catDescription, that.catDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, catName, catDescription);
    }
}
