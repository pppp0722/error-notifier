package com.ilhwanlee.producer.notification.adapter.in.web;

import com.ilhwanlee.producer.notification.adapter.in.web.dto.NotiGroupSaveRequestDto;
import com.ilhwanlee.producer.notification.adapter.in.web.dto.NotiGroupSubscriptionRequestDto;
import com.ilhwanlee.producer.notification.application.in.CrudNotiGroupUseCase;
import com.ilhwanlee.producer.notification.domain.NotiGroup;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * 알림 그룹 생성, 가입 및 탈퇴 CRUD Controller
 */
@RestController
@RequestMapping("/v1/noti-groups")
@RequiredArgsConstructor
@Tag(name = "NotiGroup CRUD API", description = "알림 그룹 CRUD API")
class NotiGroupCrudController {

    private final CrudNotiGroupUseCase useCase;

    @PostMapping
    @ApiResponse(responseCode = "201", description = "Created")
    public ResponseEntity<NotiGroup> save(@Valid @RequestBody NotiGroupSaveRequestDto requestDto) {
        NotiGroup notiGroup = useCase.save(requestDto.toCommand());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(notiGroup.getId())
                .toUri();

        return ResponseEntity.created(uri).body(notiGroup);
    }

    @PostMapping("/subscribe")
    @ApiResponse(responseCode = "204", description = "No Content")
    public ResponseEntity<Void> subscribe(@Valid @RequestBody NotiGroupSubscriptionRequestDto requestDto) {
        useCase.subscribe(requestDto.toCommand());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/unsubscribe")
    @ApiResponse(responseCode = "204", description = "No Content")
    public ResponseEntity<Void> unsubscribe(@Valid @RequestBody NotiGroupSubscriptionRequestDto requestDto) {
        useCase.unsubscribe(requestDto.toCommand());
        return ResponseEntity.noContent().build();
    }
}
