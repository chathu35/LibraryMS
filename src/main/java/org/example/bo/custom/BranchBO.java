package org.example.bo.custom;

import org.example.bo.SuperBO;
import org.example.dto.BranchDto;

import java.util.List;

public interface BranchBO extends SuperBO {
    boolean addBranch(BranchDto dto);
    List<BranchDto> getAllBranch();
    boolean updateBranch(BranchDto dto);
    boolean isExistBranch(String id);
    BranchDto searchBranch(String id);
    boolean deleteBranch(String id);

    int generateNextBranchId();
}
