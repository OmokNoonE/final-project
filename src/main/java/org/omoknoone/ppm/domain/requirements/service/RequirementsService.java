package org.omoknoone.ppm.domain.requirements.service;

import java.util.List;

import org.omoknoone.ppm.domain.requirements.aggregate.Requirements;
import org.omoknoone.ppm.domain.requirements.dto.ModifyRequirementRequestDTO;
import org.omoknoone.ppm.domain.requirements.dto.RequirementsDTO;
import org.omoknoone.ppm.domain.requirements.dto.RequirementsListByProjectDTO;
import org.omoknoone.ppm.domain.requirements.vo.ResponseRequirement;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface RequirementsService {

	// TODO. Entity를 DTO로 변환하는 작업이 필요함
	ResponseRequirement modifyRequirement(ModifyRequirementRequestDTO requirementRequestDTO);

	// TODO. Entity를 DTO로 변환하는 작업이 필요함
	ResponseRequirement removeRequirement(ModifyRequirementRequestDTO requirementRequestDTO);

	/* ProjectId를 통한 RequirementsList 조회 */
	@Transactional(readOnly = true)
	List<RequirementsListByProjectDTO> viewRequirementsByProjectId(Long projectId, Boolean isDeleted);

	RequirementsDTO viewRequirement(Long projectId, Long requirementsId);

	RequirementsDTO createRequirement(RequirementsDTO requirementsDTO);

	Page<RequirementsListByProjectDTO> viewRequirementsByProjectIdByPage(Long projectId, int page, int size);

	List<RequirementsListByProjectDTO> searchRequirementsByName(Long projectId, String requirementsName);
}
