package vn.iotstar.backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//HO Vu THanh Binh 22110287
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String image;
    private double price;
    @JsonIgnore
    @ManyToOne
    private Category category;
}
