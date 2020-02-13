package com.document.uid;


import org.junit.Before;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

import com.document.uid.entity.Uid;
import com.document.uid.repository.UidRepository;
import com.document.uid.service.UidService;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class UidServiceTest {
	@Autowired
	private UidService uidService;

	@Autowired
	private UidRepository uidRepository;

	private final int NUMBER_OF_LOTS_INSERTED = 100;
	private final int NUMBER_OF_THREADS = 10;
	private final int MAX_NUMBER_OF_UIDS_TESTED = NUMBER_OF_LOTS_INSERTED * NUMBER_OF_THREADS;

	@Before
	void resetState() {
		uidRepository.deleteAll();	
	}
	
	@Test
	void createUidWith9DigitsAndVerifyAreUniqueInBDD() throws InterruptedException {
		Collection<Uid> syncList = Collections
				.synchronizedList( new ArrayList<Uid>());
		List<Thread> threads = new ArrayList<Thread>();
		HashSet<Uid> res = new HashSet<Uid>();

		int i = 0;

		while(i < NUMBER_OF_THREADS) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					for(int i = 0; i < NUMBER_OF_LOTS_INSERTED; i++) {
						String uuid9digits = uidService.createUidWith9Digits();
						Uid uid = new Uid(uuid9digits);
						syncList.add(uid);
					}

				}
			});
			threads.add(thread);
			thread.start();
			i++;
		}

		for (Thread thread : threads) {
			thread.join();
		}

		for (Uid uid : syncList) { 
			res.add(uid); 
		}

		while(res.size() < MAX_NUMBER_OF_UIDS_TESTED) {
			String uuid9digits = uidService.createUidWith9Digits();
			Uid uid = new Uid(uuid9digits);
			res.add(uid);
		}

		uidRepository.saveAll(res);

		assertEquals(res.size(), MAX_NUMBER_OF_UIDS_TESTED);
		assertEquals(uidRepository.count(), MAX_NUMBER_OF_UIDS_TESTED);
		
		syncList.clear();
		res.clear();
	}
}
