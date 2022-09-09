package com.co.example.store.customer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Builder
@Entity
@Table(name = "tbl_customers")
@NoArgsConstructor @AllArgsConstructor
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The document number cannot be empty")
    @Size(min = 8, max = 8, message = "The size of the document number must be eight")
    @Column(name = "number_id", unique = true, length = 8,nullable = false)
    private String numberID;

    @NotEmpty(message = "The name cannot be empty")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotEmpty(message = "The last name cannot be empty")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotEmpty(message = "the email cannot be empty")
    @Email(message = "the email format is incorrect")
    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "photo_url")
    private String photoUrl;

    @NotNull(message = "The region cannot be empty")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Region region;

    private String state;

}
