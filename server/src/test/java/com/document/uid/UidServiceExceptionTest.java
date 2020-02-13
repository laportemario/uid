package com.document.uid;


import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

import com.document.uid.entity.Uid;
import com.document.uid.exception.UidException;
import com.document.uid.repository.UidRepository;
import com.document.uid.service.UidService;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UidServiceExceptionTest {
	@InjectMocks
	private UidService uidService;

	@Mock
	private UidRepository uidRepository;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	void whenExceptionThrown_thenAssertionSucceeds() {
		when(uidRepository.countUids()).thenReturn((int) 1_000_000_001);
		Exception exception = assertThrows(UidException.class, () -> {
			uidService.createUid();
		});

		String expectedMessage = "All UIDs have been generated (1000000000), please ask to increase the digits number of UID from 9 to 10";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void whenExceptionThrown_thenAssertionSucceeds2() {
		Uid uid = new Uid();

		when(uidRepository.getFirstUidIsNotUsed()).thenReturn(uid);
		when(uidRepository.save(uid)).thenReturn(null);

		Exception exception = assertThrows(UidException.class, () -> {
			uidService.getUid();
		});
		
		String expectedMessage = "All UIDs have been attributed (1000000000) please generate more ids or ask to increase the digits number of UID from 9 to 10";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

}
