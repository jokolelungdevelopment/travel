package com.jokolelung.travel.service.mapper;

import com.jokolelung.travel.domain.*;
import com.jokolelung.travel.service.dto.UserInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserInfo and its DTO UserInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface UserInfoMapper extends EntityMapper<UserInfoDTO, UserInfo> {

    @Mapping(source = "user.id", target = "userId")
    UserInfoDTO toDto(UserInfo userInfo);

    @Mapping(source = "userId", target = "user")
    UserInfo toEntity(UserInfoDTO userInfoDTO);

    default UserInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        return userInfo;
    }
}
