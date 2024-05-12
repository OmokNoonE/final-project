package org.omoknoone.ppm.projectDashboard.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.omoknoone.ppm.projectDashboard.aggregate.ProjectDashboard;
import org.omoknoone.ppm.projectDashboard.dto.ProjectDashboardDTO;
import org.omoknoone.ppm.projectDashboard.repository.ProjectDashboardRepository;
import org.omoknoone.ppm.schedule.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.mongodb.core.query.Query;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectDashboardServiceImpl implements ProjectDashboardService {

	private final ProjectDashboardRepository projectDashboardRepository;
	private final ScheduleService scheduleService;
	private final MongoTemplate mongoTemplate;
	private final ModelMapper modelMapper;


	// 프로젝트 Id를 통해 대시보드(그래프) 조회
	public List<ProjectDashboardDTO> viewProjectDashboardByProjectId(String projectId) {

		List<ProjectDashboard> projectDashboards = projectDashboardRepository.findAllByProjectId(projectId);
		return modelMapper.map(projectDashboards, new TypeToken<List<ProjectDashboard>>() {}.getType());
	}


	// 전체진행률 (게이지) 업데이트
	public void updateGauge(String projectId) {

		Criteria criteria = new Criteria().andOperator(
			Criteria.where("projectId").is(projectId),
			Criteria.where("type").is("gauge"),
			Criteria.where("series.name").is("전체진행률")
		);

		Query query = new Query(criteria);

		Update update = new Update();
		update.set("series.$.data", 30);

		mongoTemplate.updateMulti(
			query,
			update,
			ProjectDashboard.class
		);

	}

	// pie (준비, 진행, 완료)
	public void updatePie(String projectId, String type) {
		int[] datas = new int[] {10, 30, 50};

		ProjectDashboard projectDashboard = projectDashboardRepository.findAllByProjectIdAndType(projectId, type);

		for (int i = 0; i < datas.length; i++) {
			Map<String, Object> data = new HashMap<>();
			data.put("name", projectDashboard.getSeries().get(i).get("name"));
			data.put("data", datas[i]);
			projectDashboard.getSeries().set(i, data);
		}

		List<Map<String, Object>> mapList = projectDashboardRepository.save(projectDashboard).getSeries();
		System.out.println("mapList = " + mapList);

	}

	// table (구성원별 진행상태)
	public void updateTable(String projectId, String type) {

		// example data
		// projectId = 1, type = table

		ProjectDashboard projectDashboard = projectDashboardRepository.findAllByProjectIdAndType(projectId, type);
		List<Map<String, Object>> series = projectDashboard.getSeries();

		// update 할 data를 담고 있는 Map
		Map<String, Map<String, Integer>> updates = Map.of(
			"조예린", Map.of("준비", 55, "진행", 55, "완료", 55),
			"오목이", Map.of("준비", 3, "진행", 2, "완료", 1)
		);

		// 새로운 값으로 update
		for(Map<String, Object> data : series) {
			String memberName = (String)data.get("구성원명");
			if(updates.containsKey(memberName)) {
				Map<String, Integer> memberUpdates = updates.get(memberName);
				for(Map.Entry<String, Integer> entry : memberUpdates.entrySet()) {
					data.put(entry.getKey(), entry.getValue());
				}
			}
		}

		projectDashboardRepository.save(projectDashboard).getSeries();

	}


}
