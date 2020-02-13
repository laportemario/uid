package com.document.uid.service;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.document.uid.constant.Constant;
import com.document.uid.entity.Uid;
import com.document.uid.exception.UidException;
import com.document.uid.model.response.UidResponse;
import com.document.uid.repository.UidRepository;

@Service
@Transactional
public class UidService {

	@Autowired
	private UidRepository UidRepository;

	public String createUidWith9Digits() {
		String lUUID = String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16)).toString();

		return lUUID.substring(lUUID.length() - Constant.NUMBER_OF_DIGITS);
	}

	private String findUidNotUsed() {
		String uuid9digits;

		do {
			uuid9digits = this.createUidWith9Digits();
		} while(UidRepository.findById(uuid9digits).isPresent());

		return uuid9digits;
	}

	/**
     * Method that generates a UID and returns it.
	 * If all combinations of the UID have already been generated, an exception is thrown.
	 * @exception UidException
	 * @return Uid
	 */
	public Uid createUid() {
		Integer countUids = UidRepository.countUids();

		if(countUids != null && countUids >= Constant.MAX_NUMBER_OF_COMBINAISONS) {
			String error = String.format( "All UIDs have been generated (%s), please ask to increase the digits number of UID from %s to %s", 
					Constant.MAX_NUMBER_OF_COMBINAISONS, Constant.NUMBER_OF_DIGITS, Constant.NUMBER_OF_DIGITS + 1);

			throw new UidException(error);
		}

		String uidNotUsed = findUidNotUsed();
		Uid uid = new Uid();
		uid.setId(uidNotUsed);
		Uid uidSave = UidRepository.save(uid);

		return uidSave;
	}

	/**
     * Method that returns the first UID present in the database.
     * If all UIDs have already been assigned, an exception is thrown
	 * 
	 * @exception UidException
	 * @return Uid
	 */
	public Uid getUid() {
		Uid uid = UidRepository.getFirstUidIsNotUsed();
		uid.setUsed(true);

		Uid uidSave = UidRepository.save(uid);

		if(uidSave == null) {
			String error = String.format( "All UIDs have been attributed (%s) please generate more ids or ask to increase the digits number of UID from %s to %s", 
					Constant.MAX_NUMBER_OF_COMBINAISONS, Constant.NUMBER_OF_DIGITS, Constant.NUMBER_OF_DIGITS + 1);

			throw new UidException(error);
		}

		return uidSave;
	}
}