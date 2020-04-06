package com.example.payment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class PaymentClientTest {

	@MockBean
	private DataWarehouseClient dataWarehouseClient;

	@Autowired
	private PaymentClient paymentClient;

	private Object lock = new Object();

	@Timeout(value = 2, unit = TimeUnit.SECONDS)
	@Test
	void dataWarehouseDoesNotBlockPayment() throws InterruptedException {
		Answer<Boolean> sleepThreeSeconds = new Answer<Boolean>() {
			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				notifyLock();
				TimeUnit.SECONDS.sleep(3);
				return true;
			}
		};
		when(dataWarehouseClient.slowSave()).thenAnswer(sleepThreeSeconds);

		PaymentStatus paymentResult = paymentClient.createPayment();
		waitForNotify();

		assertThat(paymentResult).isEqualTo(PaymentStatus.SUCCESS);
		verify(dataWarehouseClient).slowSave();
	}

	private void waitForNotify() throws InterruptedException {
		synchronized (lock) {
			lock.wait();
		}
	}

	private void notifyLock() {
		synchronized (lock) {
			lock.notify();
		}
	}

}
