package hiberspring.domain.dtos;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;

public class CardSeedDto {
    @Expose
    private String number;

    public CardSeedDto() {
    }

    public CardSeedDto(String number) {
        this.number = number;
    }

    @NotNull
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
