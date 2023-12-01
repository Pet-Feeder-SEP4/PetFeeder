package com.example.petfeedercloud.services;

import com.example.petfeedercloud.dtos.ScheduleDTO;
import com.example.petfeedercloud.models.PetFeeder;
import com.example.petfeedercloud.models.Schedule;
import com.example.petfeedercloud.models.UserP;
import com.example.petfeedercloud.repositories.PetFeederRepository;
import com.example.petfeedercloud.repositories.PetRepository;
import com.example.petfeedercloud.repositories.ScheduleRepository;
import com.example.petfeedercloud.repositories.UserRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final PetFeederRepository petFeederRepository;



    @Override
    public ScheduleDTO getScheduleById(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found with id: " + scheduleId));
        return convertToDTO(schedule);
    }

    @Override
    public List<Schedule> getScheduleByUserId(Long userId) {
        List<Schedule> schedules = scheduleRepository.findByUserId(userId);
        return schedules;
    }

    @Override
    public List<Schedule> getScheduleByPetFeederId(Long petFeederId) {
        List<Schedule> schedules = scheduleRepository.findByPetFeederId(petFeederId);
        return schedules;
    }

    @Override
    public void deleteSchedule(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }

    @Override
    public Schedule createSchedule(ScheduleDTO scheduleDTO) {
        try {
            if (scheduleDTO.getScheduleLabel() == null || scheduleDTO.getScheduleLabel().isEmpty()) {
                throw new IllegalArgumentException("Please fill out the schedule label.");
            }
            Schedule schedule = convertToEntity(scheduleDTO);
            scheduleRepository.save(schedule);
            return schedule;
        } catch (IllegalArgumentException | ConstraintViolationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while creating the schedule =>" +ex);
        }
    }


    @Override
    public ScheduleDTO updateSchedule(Long scheduleId, ScheduleDTO scheduleDTO) {
        try {
            Optional<Schedule> existingScheduleOptional = scheduleRepository.findById(scheduleId);

            if (existingScheduleOptional.isPresent()) {
                Schedule existingSchedule = existingScheduleOptional.get();

                existingSchedule.setScheduleLabel(scheduleDTO.getScheduleLabel());

                Long userId = scheduleDTO.getUserId();
                UserP user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
                existingSchedule.setUser(user);

                Long petFeederId = scheduleDTO.getPetFeederId();
                PetFeeder petFeeder = petFeederRepository.findById(petFeederId).orElseThrow(() -> new NotFoundException("PetFeeder not found"));
                existingSchedule.setPetFeeder(petFeeder);

                scheduleRepository.save(existingSchedule);

                return convertToDTO(existingSchedule);
            } else {
                throw new NotFoundException("Schedule not found with ID: " + scheduleId);
            }
        } catch (NotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while updating the schedule");
        }
    }
    //helperss
    private ScheduleDTO convertToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setScheduleLabel(schedule.getScheduleLabel());

        if(schedule.getUser()!=null)
            scheduleDTO.setUserId(schedule.getUser().getUserId());
        if(schedule.getPetFeeder()!=null)
            scheduleDTO.setPetFeederId(schedule.getPetFeeder().getPetFeederId());
        return scheduleDTO;
    }

    private Schedule convertToEntity(ScheduleDTO scheduleDTO) {
        UserP user = userRepository.findByUserId(scheduleDTO.getUserId());
        PetFeeder petFeeder = petFeederRepository.findByPetFeederId(scheduleDTO.getPetFeederId());
        Schedule schedule = new Schedule();
        schedule.setScheduleLabel(scheduleDTO.getScheduleLabel());
        schedule.setPetFeeder(petFeeder);
        schedule.setUser(user);
        return schedule;
    }
}