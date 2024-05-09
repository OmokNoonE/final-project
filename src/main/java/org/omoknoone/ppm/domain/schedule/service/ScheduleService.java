package org.omoknoone.ppm.domain.schedule.service;

import java.util.List;

import org.omoknoone.ppm.domain.schedule.aggregate.Schedule;
import org.omoknoone.ppm.domain.schedule.dto.NewScheduleDTO;
import org.omoknoone.ppm.domain.schedule.dto.ScheduleDTO;

public interface ScheduleService {

    Schedule createSchedule(NewScheduleDTO newScheduleDTO);

    ScheduleDTO viewSchedule(Long scheduleId);

    List<ScheduleDTO> viewScheduleByProject(Long projectId);

    List<ScheduleDTO> viewScheduleOrderBy(Long projectId, String sort);

    List<ScheduleDTO> viewScheduleNearByStart(Long projectId);

    List<ScheduleDTO> viewScheduleNearByEnd(Long projectId);
}