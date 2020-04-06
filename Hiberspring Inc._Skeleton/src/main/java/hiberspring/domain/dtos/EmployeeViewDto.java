package hiberspring.domain.dtos;

import javax.validation.constraints.NotNull;

public class EmployeeViewDto {
    private String fullName;
    private String position;
    private String cardNumber;

    public EmployeeViewDto() {
    }

    @NotNull
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @NotNull
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @NotNull
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
