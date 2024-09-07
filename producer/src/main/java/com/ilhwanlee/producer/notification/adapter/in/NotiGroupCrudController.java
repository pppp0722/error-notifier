package com.ilhwanlee.producer.notification.adapter.in;

import com.ilhwanlee.producer.notification.adapter.in.dto.NotiGroupSaveRequestDto;
import com.ilhwanlee.producer.notification.adapter.in.dto.NotiGroupSubscriptionRequestDto;
import com.ilhwanlee.producer.notification.application.in.CrudNotiGroupUseCase;
import com.ilhwanlee.producer.notification.domain.NotiGroup;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/noti-groups")
@RequiredArgsConstructor
public class NotiGroupCrudController {

    private final CrudNotiGroupUseCase useCase;

    @PostMapping
    public ResponseEntity<NotiGroup> save(@Valid @RequestBody NotiGroupSaveRequestDto requestDto) {
        NotiGroup notiGroup = useCase.save(requestDto.toCommand());
        return ResponseEntity.status(HttpStatus.CREATED).body(notiGroup);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<Void> subscribe(@Valid @RequestBody NotiGroupSubscriptionRequestDto requestDto) {
        useCase.subscribe(requestDto.toCommand());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/unsubscribe")
    public ResponseEntity<Void> unsubscribe(@Valid @RequestBody NotiGroupSubscriptionRequestDto requestDto) {
        useCase.unsubscribe(requestDto.toCommand());
        return ResponseEntity.ok().build();
    }
}
