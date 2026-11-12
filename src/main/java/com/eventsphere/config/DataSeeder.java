
package com.eventsphere.config;

import com.eventsphere.model.Event;
import com.eventsphere.repository.EventRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedEvents(EventRepository repository) {

        return args -> {

            if (repository.count() == 0) {

                repository.save(new Event(
                    "Delhi Tech Summit",
                    "A day of technology talks, demos and networking.",
                    "Technology",
                    "India International Centre",
                    "New Delhi",
                    LocalDateTime.now().plusDays(12),
                    "https://images.unsplash.com/photo-1540575467063-178a50c2df87?w=900",
                    new BigDecimal("299"),
                    ""
                ));

                repository.save(new Event(
                    "Campus Buildathon",
                    "Build useful products with student teams and mentors.",
                    "Hackathon",
                    "Manav Rachna University",
                    "Faridabad",
                    LocalDateTime.now().plusDays(20),
                    "https://images.unsplash.com/photo-1504384308090-c894fdcc538d?w=900",
                    BigDecimal.ZERO,
                    ""
                ));

                repository.save(new Event(
                    "Indie Music Evening",
                    "An evening of live independent music.",
                    "Music",
                    "Venue to be announced",
                    "Delhi",
                    LocalDateTime.now().plusDays(28),
                    "https://images.unsplash.com/photo-1506157781611-7f6f2e2a2b8f?w=900",
                    new BigDecimal("499"),
                    ""
                ));
            }
        };
    }
}
