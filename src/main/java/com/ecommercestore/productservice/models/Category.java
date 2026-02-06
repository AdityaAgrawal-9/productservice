package com.ecommercestore.productservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;
@Getter
@Setter
@Entity
public class Category extends BaseModel{
    private String name;
    private String description;

//    @OneToMany
//    private List<Product> featureProducts;

    /*
     * Using @Fetch and @BatchSize to optimize the loading of allProducts collection.
     * There are multiple FetchModes available like JOIN, SELECT, SUBSELECT.
     * Also, FetchTypes are EAGER and LAZY. By default, OneToMany is LAZY.
     * Here we are using FetchMode.SELECT with BatchSize of 10 to reduce the number of queries when loading the collection.
     * This will load the products in batches of 10 when accessed.
     */
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
    private List<Product> allProducts;
}
