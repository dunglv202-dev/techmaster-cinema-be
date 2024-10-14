package dev.dunglv202.techmaster.service.impl;

import dev.dunglv202.techmaster.constant.PlacementStatus;
import dev.dunglv202.techmaster.dto.req.PlacementDTO;
import dev.dunglv202.techmaster.entity.Placement;
import dev.dunglv202.techmaster.entity.Schedule;
import dev.dunglv202.techmaster.exception.ClientVisibleException;
import dev.dunglv202.techmaster.mapper.PlacementMapper;
import dev.dunglv202.techmaster.model.Seat;
import dev.dunglv202.techmaster.repository.PlacementRepository;
import dev.dunglv202.techmaster.repository.ScheduleRepository;
import dev.dunglv202.techmaster.service.TicketService;
import dev.dunglv202.techmaster.util.AuthHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final PlacementRepository placementRepository;
    private final ScheduleRepository scheduleRepository;
    private final AuthHelper authHelper;

    @Override
    @Transactional
    public void placeTickets(PlacementDTO placementDTO) {
        Schedule schedule = scheduleRepository.findById(placementDTO.getScheduleId())
            .orElseThrow(() -> new ClientVisibleException("{schedule.invalid}"));

        // Check if any sheet unavailable
        List<String> unavailableSeats = new ArrayList<>();
        placementDTO.getSeats().forEach(pos -> {
            Seat seat = schedule.getSeat(pos);
            if (seat.isUnavailable()) unavailableSeats.add(pos);
            else schedule.takeSeat(seat);
        });
        if (!unavailableSeats.isEmpty()) {
            throw new ClientVisibleException("{seat.unavailable}: " + String.join(", ", unavailableSeats));
        }

        // Do placement
        Placement placement = PlacementMapper.INSTANCE.toPlacement(placementDTO);
        placement.setSchedule(schedule);
        placement.setUser(authHelper.getSignedUser());
        placement.setTimestamp(LocalDateTime.now());
        placement.setStatus(PlacementStatus.PENDING_PAYMENT);
        placementRepository.save(placement);
    }
}
