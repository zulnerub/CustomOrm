package softuni.exam.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "tickets")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketRootSeedDto {
    @XmlElement(name = "ticket")
    private List<TicketSeedDto> ticketSeedDtoList;

    public TicketRootSeedDto() {
    }

    public List<TicketSeedDto> getTicketSeedDtoList() {
        return ticketSeedDtoList;
    }

    public void setTicketSeedDtoList(List<TicketSeedDto> ticketSeedDtoList) {
        this.ticketSeedDtoList = ticketSeedDtoList;
    }
}
