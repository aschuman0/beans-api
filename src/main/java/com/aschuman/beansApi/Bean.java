package com.aschuman.beansApi;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import org.codehaus.jackson.map.ObjectMapper;
import javax.sql.DataSource;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "product")
public class Bean {
    public UUID id;
    public String name;
    public String notes;
    public String origin;
    public String supplier;
    public String url;
    public int rating;

    private Bean() { this.id = UUID.randomUUID(); }

    @XmlElement(name = "id")
    public UUID getId() {
        return id;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    @XmlElement(name = "notes")
    public String getNotes() {
        return notes;
    }

    @XmlElement(name = "origin")
    public String getOrigin() {
        return origin;
    }

    @XmlElement(name = "supplier")
    public String getSupplier() {
        return supplier;
    }

    @XmlElement(name = "url")
    public String getUrl() {
        return url;
    }

    @XmlElement(name = "rating")
    public double getRating() {
        return rating;
    }

    public String toJson() {
        String jsonString = new String();
        ObjectMapper objMapper = new ObjectMapper();

        try {
            jsonString = objMapper.writeValueAsString(this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public void createInDb(DataSource pool) throws SQLException {
        Timestamp now = new Timestamp(new Date().getTime());

        try (Connection conn = pool.getConnection()) {
            PreparedStatement createBean = conn.prepareStatement(
                    "INSERT INTO beans " +
                            "(bean_id, created, name, origin, supplier, url, rating, notes)" +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?);"
            );
            createBean.setString(1, this.getId().toString());
            createBean.setTimestamp(2, now);
            createBean.setString(3, this.getName());
            createBean.setString(4, this.getOrigin());
            createBean.setString(5, this.getSupplier());
            createBean.setString(6, this.getUrl());
            createBean.setInt(7, (int) this.getRating());
            createBean.setString(8, this.getNotes());

            createBean.execute();
        }
    }

    @Override
    public String toString() {
        return "com.aschuman.beansApi.Bean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", notes='" + notes + '\'' +
                ", origin='" + origin + '\'' +
                ", supplier='" + supplier + '\'' +
                ", url='" + url + '\'' +
                ", rating=" + rating +
                '}';
    }

    public static class Builder {

        private UUID id;
        private String name;
        private String notes;
        private String origin;
        private String supplier;
        private String url;
        private int rating;

        public Builder() { }

        public Builder setId(String bean_id) {
            this.id = UUID.fromString(bean_id);
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setNotes(String notes) {
            this.notes = notes;
            return this;
        }

        public Builder setOrigin(String origin) {
            this.origin = origin;
            return this;
        }

        public Builder setSupplier(String supplier) {
            this.supplier = supplier;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setRating(int rating) {
            this.rating = rating;
            return this;
        }

        public Bean build() {
            Bean bean = new Bean();
            bean.id = this.id;
            bean.name = this.name;
            bean.notes = this.notes;
            bean.origin = this.origin;
            bean.supplier = this.supplier;
            bean.url = this.url;
            bean.rating = this.rating;

            return bean;
        }

    }

}
