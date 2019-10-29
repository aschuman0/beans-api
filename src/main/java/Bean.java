import java.util.UUID;

public class Bean {
    private UUID id;
    private String name;
    private String notes;
    private String origin;
    private String supplier;
    private String url;
    private double rating;

    private Bean() {}

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNotes() {
        return notes;
    }

    public String getOrigin() {
        return origin;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getUrl() {
        return url;
    }

    public double getRating() {
        return rating;
    }

    public static class Builder {

        private UUID id;
        private String name;
        private String notes;
        private String origin;
        private String supplier;
        private String url;
        private double rating;

        public Builder() {
            this.id = UUID.randomUUID();
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

        public Builder setRating(double rating) {
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
