package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Pizza
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-11-19T00:27:22.232Z")

@Entity
public class Pizza {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("name")
    private String name = null;

    /**
     * Size
     */
    public enum SizeEnum {
        STANDARD("Standard"),

        LARGE("Large");

        private String value;

        SizeEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static SizeEnum fromValue(String text) {
            for (SizeEnum b : SizeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonProperty("size")
    private SizeEnum size = null;

    @JsonProperty("price")
    private BigDecimal price = null;

    public Pizza id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     **/
    @ApiModelProperty(value = "")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pizza name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     **/
    @ApiModelProperty(value = "")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pizza size(SizeEnum size) {
        this.size = size;
        return this;
    }

    /**
     * Size
     *
     * @return size
     **/
    @ApiModelProperty(value = "Size")
    public SizeEnum getSize() {
        return size;
    }

    public void setSize(SizeEnum size) {
        this.size = size;
    }

    public Pizza price(BigDecimal price) {
        this.price = price;
        return this;
    }

    /**
     * Price including toppings.
     *
     * @return price
     **/
    @ApiModelProperty(value = "Price including toppings.")
    public BigDecimal getPrice() {
        BigDecimal totalprice = price;
        return totalprice;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pizza pizza = (Pizza) o;
        return Objects.equals(this.id, pizza.id) &&
                Objects.equals(this.name, pizza.name) &&
                Objects.equals(this.size, pizza.size) &&
                Objects.equals(this.price, pizza.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, size, price);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Pizza {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    size: ").append(toIndentedString(size)).append("\n");
        sb.append("    price: ").append(toIndentedString(price)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

