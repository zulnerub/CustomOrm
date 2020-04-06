package softuni.exam.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "planes")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlaneSeedRootDto {
    @XmlElement(name = "plane")
    List<PlaneSeedDto> planeSeedDtoList;

    public PlaneSeedRootDto() {
    }

    public List<PlaneSeedDto> getPlaneSeedDtoList() {
        return planeSeedDtoList;
    }

    public void setPlaneSeedDtoList(List<PlaneSeedDto> planeSeedDtoList) {
        this.planeSeedDtoList = planeSeedDtoList;
    }
}
