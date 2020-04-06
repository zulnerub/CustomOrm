package softuni.exam.models.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "passenger")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketSeedPassengerDto {
    @XmlElement(name = "email")
    @Pattern(regexp = "[a-z0-9]+[a-zA-Z0-9]*@[a-zA-Z0-9]+?(.[a-zA-Z0-9]+)+")
    @NotNull
    private String email;

    public TicketSeedPassengerDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
