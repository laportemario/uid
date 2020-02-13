package com.document.uid.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.document.uid.entity.Uid;
import com.document.uid.model.response.UidResponse;
import com.document.uid.service.UidService;


@RestController
public class UidController {

	@Autowired
	private UidService uidService;
	
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping(value="/uid")
	public ResponseEntity<UidResponse> createUid() {
		Uid uidSave = uidService.createUid();

		UidResponse uidResponseDto = modelMapper.map(uidSave, UidResponse.class);
		
		return new ResponseEntity<>(uidResponseDto, HttpStatus.CREATED);
	}

	@GetMapping(value="/uid")
	public ResponseEntity<UidResponse> getUid() {		
		Uid uid = uidService.getUid();

		UidResponse uidResponseDto = modelMapper.map(uid, UidResponse.class);
		
		return new ResponseEntity<>(uidResponseDto, HttpStatus.OK);
	}


}
