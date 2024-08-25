package com.Ticket.reservation.Ticket.reservation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.Ticket.reservation.Ticket.reservation.AbstractTestContainer.postgresContainer;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers

class TicketReservationApplicationTests extends AbstractTestContainer {


	@Test
	void canStartPostgresDB() {
		assertThat(postgresContainer.isRunning()).isTrue();
		assertThat(postgresContainer.isCreated()).isTrue();
	}


}
