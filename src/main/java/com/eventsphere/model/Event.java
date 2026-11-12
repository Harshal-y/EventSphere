
package com.eventsphere.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 2000)
    private String description;

    private String category;

    private String venue;

    private String city;

    private LocalDateTime eventDate;

    private String imageUrl;

    private BigDecimal price;

    @Column(length = 500)
    private String telegramInviteLink;

    private boolean active = true;

    public Event() {
    }

    public Event(String title, String description,
                 String category, String venue,
                 String city, LocalDateTime eventDate,
                 String imageUrl, BigDecimal price,
                 String telegramInviteLink) {

        this.title = title;
        this.description = description;
        this.category = category;
        this.venue = venue;
        this.city = city;
        this.eventDate = eventDate;
        this.imageUrl = imageUrl;
        this.price = price;
        this.telegramInviteLink = telegramInviteLink;
        this.active = true;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getVenue() {
        return venue;
    }

    public String getCity() {
        return city;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getTelegramInviteLink() {
        return telegramInviteLink;
    }

    public boolean isActive() {
        return active;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setTelegramInviteLink(String telegramInviteLink) {
        this.telegramInviteLink = telegramInviteLink;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
