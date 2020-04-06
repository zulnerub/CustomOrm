package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.domain.dtos.BranchSeedDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Town;
import hiberspring.repository.BranchRepository;
import hiberspring.service.BranchService;
import hiberspring.service.TownService;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

import static hiberspring.common.GlobalConstants.BRANCHES_FILE_PATH;


@Service
@Transactional
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final TownService townService;



    public BranchServiceImpl(BranchRepository branchRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, TownService townService) {
        this.branchRepository = branchRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.townService = townService;
    }


    @Override
    public Branch getByName(String name) {
        return this.branchRepository.findByName(name).orElse(null);
    }

    @Override
    public Boolean branchesAreImported() {
        return this.branchRepository.count() > 0;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        return Files.readString(Path.of(BRANCHES_FILE_PATH));
    }

    @Override
    public String importBranches(String branchesFileContent) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        BranchSeedDto[] branchSeedDto =
                this.gson.fromJson( new FileReader(BRANCHES_FILE_PATH), BranchSeedDto[].class);

        Arrays.stream(branchSeedDto)
                .forEach(b -> {
                    System.out.println();
                    if (this.validationUtil.isValid(b)){
                        if (this.branchRepository.findByName(b.getName()).orElse(null) == null){
                            Branch branch = this.modelMapper.map(b, Branch.class);
                            Town town = this.townService.getByName(b.getTown());
                            if (this.validationUtil.isValid(town) && town != null){
                                branch.setTown(town);

                                sb.append(String.format("Successfully imported %s %s.",
                                        branch.getClass().getSimpleName(), branch.getName()));

                                this.branchRepository.saveAndFlush(branch);
                            }else {
                                sb.append("Error: Invalid data.");
                            }
                        }else {
                            sb.append("Error: Invalid data.");
                        }
                    }else {
                        sb.append("Error: Invalid data.");
                    }
                    sb.append(System.lineSeparator());
                });



        return sb.toString();
    }
}
