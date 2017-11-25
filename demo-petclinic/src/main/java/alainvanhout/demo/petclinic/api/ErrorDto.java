package alainvanhout.demo.petclinic.api;

public class ErrorDto {
    private String exMessage;
    private String className;

    public String getExMessage() {
        return exMessage;
    }

    public void setExMessage(String exMessage) {
        this.exMessage = exMessage;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
