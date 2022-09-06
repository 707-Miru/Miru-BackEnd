package com.back.miru.model.dao;

import com.back.miru.model.dto.User;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserDAO {
	int checkId(String id) throws SQLException;

	void registUser(Map<String, String> map) throws SQLException;

	void updateUser(Map<String, String> map) throws SQLException;

	void deleteUser(String id) throws SQLException;

	User infoUser(String id) throws SQLException;

	User loginUser(String id, String password) throws SQLException;

	int checkPasswordFind(Map<String, String> map) throws SQLException;

	void updatePassword(Map<String, String> map) throws SQLException;

}