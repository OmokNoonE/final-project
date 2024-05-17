package org.omoknoone.ppm.domain.schedule.service;

import java.util.List;
import java.util.Map;

import org.omoknoone.ppm.domain.schedule.aggregate.Schedule;
import org.omoknoone.ppm.domain.schedule.dto.CreateScheduleDTO;
import org.omoknoone.ppm.domain.schedule.dto.ModifyScheduleDateDTO;
import org.omoknoone.ppm.domain.schedule.dto.ModifyScheduleProgressDTO;
import org.omoknoone.ppm.domain.schedule.dto.ModifyScheduleTitleAndContentDTO;
import org.omoknoone.ppm.domain.schedule.dto.RequestModifyScheduleDTO;
import org.omoknoone.ppm.domain.schedule.dto.ScheduleDTO;
import org.omoknoone.ppm.domain.schedule.dto.SearchScheduleListDTO;

public interface ScheduleService {

    /* 생성 */
    Schedule createSchedule(CreateScheduleDTO createScheduleDTO);

    /* 조회 */
    ScheduleDTO viewSchedule(Long scheduleId);

    List<ScheduleDTO> viewScheduleByProject(Long projectId);

    List<ScheduleDTO> viewScheduleOrderBy(Long projectId, String sort);

    List<ScheduleDTO> viewScheduleNearByStart(Long projectId);

    List<ScheduleDTO> viewScheduleNearByEnd(Long projectId);

    /* 수정 */
    Long modifySchedule(RequestModifyScheduleDTO requestModifyScheduleDTO);

    ModifyScheduleTitleAndContentDTO modifyScheduleTitleAndContent(RequestModifyScheduleDTO requestModifyScheduleDTO);

    ModifyScheduleDateDTO modifyScheduleDate(RequestModifyScheduleDTO requestModifyScheduleDTO);

    ModifyScheduleProgressDTO modifyScheduleProgress(RequestModifyScheduleDTO requestModifyScheduleDTO);

    /* 삭제 */
    /* TODO. 매개변수를 DeleteScheduleDTO로 변경, 반환형을 Long(PK)로 변경하여 삭제되었음을 명확히 확인할 수 있도록 수정해야함. */
    void removeSchedule(Long scheduleId);

    /* 도구 */
    /* 업무 일정인지 확인 */
    boolean isTaskSchedule(Long scheduleId);

    /* Title을 통한 일정 검색 */
    List<SearchScheduleListDTO> searchSchedulesByTitle(String scheduleTitle);

    /* 설명. 대시보드 게이지 제공 데이터 추출 */
    int updateGauge(Long projectId);

    /* 설명. 대시보드 파이 제공 데이터 추출 */
    int[] updatePie(Long projectId);

    /* 설명. 대시보드 테이블 제공 데이터 추출 */
    Map<String, Map<String, Integer>> updateTable(Long projectId);

    /* 설명. 대시보드 컬럼 제공 데이터 추출 */

    /* 설명. 대시보드 라인 제공 데이터 추출 */

    /* 상태 코드에 따른 해당 일정 확인 */
    List<Schedule> getSchedulesByStatusCodes(List<Long> codeIds);

}
