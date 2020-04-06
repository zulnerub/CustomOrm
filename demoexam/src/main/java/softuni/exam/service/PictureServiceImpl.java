package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.PictureSeedRootDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


import static softuni.exam.constants.GlobalConstants.PICTURES_FILE_PATH;


@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidatorUtil validatorUtil) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public String importPictures() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        PictureSeedRootDto pictureSeedRootDto =
                this.xmlParser.importFromXml(PictureSeedRootDto.class, PICTURES_FILE_PATH);
        System.out.println();
        pictureSeedRootDto.getPictures()
                .forEach(p -> {
                    if (this.validatorUtil.isValid(p)){
                        if (this.pictureRepository.findByUrl(p.getUrl()) == null){
                            Picture picture = this.modelMapper.map(p, Picture.class);

                            sb.append("Successfully imported picture - ").append(picture.getUrl());

                            this.pictureRepository.saveAndFlush(picture);

                        }else {
                            sb.append("Picture already in DB");
                        }
                    }else {
                        sb.append("Invalid Picture!");
                    }
                    sb.append(System.lineSeparator());
                });


       return sb.toString();
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesXmlFile() throws IOException {
        return Files.readString(Path.of(PICTURES_FILE_PATH));

    }

    @Override
    public Picture getPictureByUrl(String url) {
        return this.pictureRepository.findByUrl(url);
    }


}
