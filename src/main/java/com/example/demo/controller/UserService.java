package com.example.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserService {
	private RestHighLevelClient client;
	private ObjectMapper objectMapper;

	public static final String INDEX = "userindex";
	public static final String TYPE = "usertype";
	public static final String CANT_FOUND = "can't found";
	public static final String DELETE_SUCCESS = "delete success";

	public UserService(RestHighLevelClient client, ObjectMapper objectMapper) {
		this.client = client;
		this.objectMapper = objectMapper;
	}

//	Create document
//	public String createUser(User user) throws IOException {
//		UUID uuid = UUID.randomUUID();
//		user.setId(uuid.toString());
//
//		Map<String, Object> userMapper = objectMapper.convertValue(user, Map.class);
//		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, user.getId()).source(userMapper);
//		IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
//		return indexResponse.getResult().name();
//	}

	public User createUser(User user) throws IOException {
		if(null == user.getId()) {
			UUID uuid = UUID.randomUUID();
			user.setId(uuid.toString());
		}
		Map<String, Object> userMapper = objectMapper.convertValue(user, Map.class);
		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, user.getId()).source(userMapper);
		IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
		return user;
	}
// update user
	public User updateUser(User user) throws IOException {
		User userUpdate = findUserById(user.getId());
		if(userUpdate == null) {
			return createUser(user);
		}
		Map<String, Object> userMapper = objectMapper.convertValue(user, Map.class);
		UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, userUpdate.getId()).doc(userMapper);
		UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
		return user;
	}
// find user by Id
	public User findUserById(String id) throws IOException {
		GetRequest request = new GetRequest(INDEX, TYPE, id);
		GetResponse response = client.get(request, RequestOptions.DEFAULT);
		Map<String, Object> mapUser = response.getSource();
		return objectMapper.convertValue(mapUser, User.class);
	}
//	delete user by Id
	public String deleteUserById(String id) throws IOException {
		User user = findUserById(id);
		if(user == null) {
			return CANT_FOUND;
		}
		Map<String, Object> userMapper = objectMapper.convertValue(user, Map.class);
		DeleteRequest request = new DeleteRequest(INDEX, TYPE, id);
		DeleteResponse response = client.delete(request,RequestOptions.DEFAULT);
		return DELETE_SUCCESS;
	}
//	get all user
	public List<User> getUsers(int skip, int take){
		GetRequest request = new GetRequest(INDEX).type(TYPE);
		
	}
}
