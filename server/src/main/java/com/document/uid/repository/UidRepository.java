package com.document.uid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.document.uid.entity.Uid;


@Repository
public interface UidRepository extends JpaRepository<Uid, String> {
	
	//@Query(value = "SELECT MAX(serial) FROM uid", nativeQuery = true)
	//Integer countUids();
	
	@Query(value = "SELECT count(id) from uid", nativeQuery = true)
	Integer countUids();
	
		
	@Query(value = "SELECT * FROM uid WHERE serial = (SELECT MIN(serial) FROM uid WHERE is_used = false)", nativeQuery = true)
	Uid getFirstUidIsNotUsed();
}

