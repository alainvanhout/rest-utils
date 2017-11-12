package alainvanhout.demo.petclinic.api.pets;

public class Pet {
    private String id;
    private String name;
    private String birthDate;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public Pet id(final String id) {
        this.id = id;
        return this;
    }

    public Pet name(final String name) {
        this.name = name;
        return this;
    }

    public Pet birthDate(final String birthDate) {
        this.birthDate = birthDate;
        return this;
    }
}