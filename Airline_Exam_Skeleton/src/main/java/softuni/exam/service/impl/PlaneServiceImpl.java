package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.models.dtos.PlaneSeedRootDto;
import softuni.exam.models.entities.Plane;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.service.PlaneService;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Optional;

import static softuni.exam.constants.GlobalConstants.PLANES_FILE_PATH;

@Service
@Transactional
public class PlaneServiceImpl implements PlaneService {
    private final PlaneRepository planeRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    @Autowired
    public PlaneServiceImpl(PlaneRepository planeRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.planeRepository = planeRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public Optional<Plane> getPlaneByRegisterNumber(String regNum) {
        return this.planeRepository.findPlaneByRegisterNumber(regNum);
    }

    @Override
    public boolean areImported() {
        return this.planeRepository.count() > 0;
    }

    @Override
    public String readPlanesFileContent() throws IOException {
        return this.fileUtil.readFile(PLANES_FILE_PATH);
    }

    @Override
    public String importPlanes() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();
        System.out.println();
        PlaneSeedRootDto planeSeedRootDto =
                this.xmlParser.importFromXml(PlaneSeedRootDto.class, PLANES_FILE_PATH);
        planeSeedRootDto.getPlaneSeedDtoList()
                .forEach(pl -> {
                    if (this.validationUtil.isValid(pl)){
                        Plane plane = this.modelMapper.map(pl, Plane.class);
                        System.out.println();
                        if (this.planeRepository.findPlaneByRegisterNumber(pl.getRegisterNumber()).orElse(null) == null){
                            sb.append(String.format("Successfully imported %s %s",
                                    plane.getClass().getSimpleName(), plane.getRegisterNumber()));

                            this.planeRepository.saveAndFlush(plane);
                        }else {
                            sb.append("Invalid Plane");
                        }
                    }else{
                        sb.append("Invalid Plane");
                    }
                    sb.append(System.lineSeparator());
                });


        return sb.toString();
    }
}
