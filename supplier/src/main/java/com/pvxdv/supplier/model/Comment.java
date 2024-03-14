package com.pvxdv.supplier.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(name = "rating")
    @Min(0)
    @Max(5)
    Integer rating;
    String text;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "products_id")
    Product product;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", rating=" + rating +
                ", text='" + text + '\'' +
                ", product=" + product.getId() +
                '}';
    }
}
