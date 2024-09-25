package com.groupware.erp.member.repository;

import com.groupware.erp.member.dto.MemberMapperDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapperRepository {

    List<MemberMapperDTO> memberList(MemberMapperDTO memberMapperDTO);
}