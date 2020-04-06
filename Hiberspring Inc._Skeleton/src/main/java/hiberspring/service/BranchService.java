package hiberspring.service;


import hiberspring.domain.entities.Branch;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

//TODO
public interface BranchService {
    Branch getByName(String name);

    Boolean branchesAreImported();

    String readBranchesJsonFile() throws IOException;

    String importBranches(String branchesFileContent) throws FileNotFoundException;
}
