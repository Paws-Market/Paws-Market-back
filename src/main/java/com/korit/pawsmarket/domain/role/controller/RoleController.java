package com.korit.pawsmarket.domain.role.controller;

import com.korit.pawsmarket.domain.role.dto.req.RoleCreateReqDto;
import com.korit.pawsmarket.domain.role.entity.Role;
import com.korit.pawsmarket.domain.role.facade.RoleFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Role", description = "롤 API")
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleFacade roleFacade;

    @Operation(summary = "롤 생성", description = "새로운 롤을 생성할 수 있습니다.")
    @PostMapping
    public ResponseEntity<?> createRole(@Valid @RequestBody RoleCreateReqDto req) {
        roleFacade.createRole(req);
        return  ResponseEntity.ok().body("롤 생성이 완료되었습니다.");
    }
}
